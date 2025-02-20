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

package com.liferay.asset.auto.tagger.internal.configuration.persistence.listener;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerCompanyConfiguration;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"model.class.name=com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerCompanyConfiguration",
		"model.class.name=com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerCompanyConfiguration.scoped"
	},
	service = ConfigurationModelListener.class
)
public class AssetAutoTaggerCompanyConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		int maximumNumberOfTagsPerAsset = GetterUtil.getInteger(
			properties.get("maximumNumberOfTagsPerAsset"));

		if (maximumNumberOfTagsPerAsset < 0) {
			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					_getResourceBundle(),
					"maximum-number-of-tags-per-asset-cannot-be-negative"),
				AssetAutoTaggerCompanyConfiguration.class, getClass(),
				properties);
		}

		AssetAutoTaggerConfiguration systemAssetAutoTaggerConfiguration =
			_assetAutoTaggerConfigurationFactory.
				getSystemAssetAutoTaggerConfiguration();

		int systemMaximumNumberOfTagsPerAsset =
			systemAssetAutoTaggerConfiguration.getMaximumNumberOfTagsPerAsset();

		if ((systemMaximumNumberOfTagsPerAsset != 0) &&
			((maximumNumberOfTagsPerAsset == 0) ||
			 (systemMaximumNumberOfTagsPerAsset <
				 maximumNumberOfTagsPerAsset))) {

			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					_getResourceBundle(),
					"maximum-number-of-tags-per-asset-invalid"),
				AssetAutoTaggerCompanyConfiguration.class, getClass(),
				properties);
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			LocaleThreadLocal.getThemeDisplayLocale(), getClass());
	}

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

}