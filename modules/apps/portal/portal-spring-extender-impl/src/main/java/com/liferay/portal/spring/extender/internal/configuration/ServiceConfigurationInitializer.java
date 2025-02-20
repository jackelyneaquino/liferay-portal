/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.spring.extender.internal.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.service.configuration.ServiceComponentConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
public class ServiceConfigurationInitializer {

	public ServiceConfigurationInitializer(
		Bundle bundle, ClassLoader classLoader,
		Configuration serviceConfiguration,
		ServiceComponentLocalService serviceComponentLocalService) {

		_bundle = bundle;
		_classLoader = classLoader;
		_serviceConfiguration = serviceConfiguration;
		_serviceComponentLocalService = serviceComponentLocalService;
	}

	public void stop() {
		if (_configurationServiceRegistration != null) {
			_configurationServiceRegistration.unregister();

			_configurationServiceRegistration = null;
		}
	}

	protected void start() {
		_initServiceComponent();

		BundleContext bundleContext = _bundle.getBundleContext();

		_configurationServiceRegistration = bundleContext.registerService(
			Configuration.class, _serviceConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"name", "service"
			).put(
				"origin.bundle.symbolic.name", _bundle.getSymbolicName()
			).build());
	}

	private void _initServiceComponent() {
		Properties properties = _serviceConfiguration.getProperties();

		if (properties.isEmpty()) {
			return;
		}

		String buildNamespace = GetterUtil.getString(
			properties.getProperty("build.namespace"));
		long buildNumber = GetterUtil.getLong(
			properties.getProperty("build.number"));
		long buildDate = GetterUtil.getLong(
			properties.getProperty("build.date"));

		if (_log.isDebugEnabled()) {
			_log.debug("Build namespace " + buildNamespace);
			_log.debug("Build number " + buildNumber);
			_log.debug("Build date " + buildDate);
		}

		if (Validator.isNull(buildNamespace)) {
			return;
		}

		try {
			_serviceComponentLocalService.initServiceComponent(
				_serviceComponentConfiguration, _classLoader, buildNamespace,
				buildNumber, buildDate);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to initialize service component", portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceConfigurationInitializer.class);

	private final Bundle _bundle;
	private final ClassLoader _classLoader;
	private ServiceRegistration<Configuration>
		_configurationServiceRegistration;
	private final ServiceComponentConfiguration _serviceComponentConfiguration =
		new ModuleResourceLoader();
	private final ServiceComponentLocalService _serviceComponentLocalService;
	private final Configuration _serviceConfiguration;

	private class ModuleResourceLoader
		implements ServiceComponentConfiguration {

		@Override
		public InputStream getHibernateInputStream() {
			return _getInputStream("/META-INF/module-hbm.xml");
		}

		@Override
		public InputStream getModelHintsExtInputStream() {
			return _getInputStream("/META-INF/portlet-model-hints-ext.xml");
		}

		@Override
		public InputStream getModelHintsInputStream() {
			return _getInputStream("/META-INF/portlet-model-hints.xml");
		}

		@Override
		public String getServletContextName() {
			return _bundle.getSymbolicName();
		}

		@Override
		public InputStream getSQLIndexesInputStream() {
			return _getInputStream("/META-INF/sql/indexes.sql");
		}

		@Override
		public InputStream getSQLSequencesInputStream() {
			return _getInputStream("/META-INF/sql/sequences.sql");
		}

		@Override
		public InputStream getSQLTablesInputStream() {
			return _getInputStream("/META-INF/sql/tables.sql");
		}

		private InputStream _getInputStream(String location) {
			URL url = _bundle.getResource(location);

			if (url == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to find " + location);
				}

				return null;
			}

			InputStream inputStream = null;

			try {
				inputStream = url.openStream();
			}
			catch (IOException ioException) {
				_log.error("Unable to read " + location, ioException);
			}

			return inputStream;
		}

	}

}