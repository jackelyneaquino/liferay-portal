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

package com.liferay.portal.monitoring.internal.statistics.portal;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.monitoring.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false, property = "namespace=com.liferay.monitoring.Portal",
	service = {DataSampleProcessor.class, ServerStatistics.class}
)
public class ServerStatistics
	implements DataSampleProcessor<PortalRequestDataSample> {

	public Set<Long> getCompanyIds() {
		return _companyStatisticsByCompanyId.keySet();
	}

	public CompanyStatistics getCompanyStatistics(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics = _companyStatisticsByCompanyId.get(
			companyId);

		if (companyStatistics == null) {
			throw new MonitoringException(
				"No statistics found for company ID " + companyId);
		}

		return companyStatistics;
	}

	public CompanyStatistics getCompanyStatistics(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics = _companyStatisticsByWebId.get(
			webId);

		if (companyStatistics == null) {
			throw new MonitoringException(
				"No statistics found for web ID " + webId);
		}

		return companyStatistics;
	}

	public Set<CompanyStatistics> getCompanyStatisticsSet() {
		return new HashSet<>(_companyStatisticsByWebId.values());
	}

	public Set<String> getWebIds() {
		return _companyStatisticsByWebId.keySet();
	}

	@Override
	public void processDataSample(
		PortalRequestDataSample portalRequestDataSample) {

		long companyId = portalRequestDataSample.getCompanyId();

		CompanyStatistics companyStatistics = _companyStatisticsByCompanyId.get(
			companyId);

		if (companyStatistics == null) {
			try {
				Company company = _companyLocalService.getCompany(companyId);

				companyStatistics = register(company.getWebId());
			}
			catch (Exception exception) {
				throw new IllegalStateException(
					"Unable to get company with company ID " + companyId,
					exception);
			}
		}

		companyStatistics.processDataSample(portalRequestDataSample);
	}

	public synchronized CompanyStatistics register(String webId) {
		CompanyStatistics companyStatistics = new CompanyStatistics(
			_companyLocalService, webId);

		_companyStatisticsByCompanyId.put(
			companyStatistics.getCompanyId(), companyStatistics);
		_companyStatisticsByWebId.put(webId, companyStatistics);

		return companyStatistics;
	}

	public void reset() {
		_companyLocalService.forEachCompanyId(
			companyId -> reset(companyId),
			ArrayUtil.toLongArray(_companyStatisticsByCompanyId.keySet()));
	}

	public void reset(long companyId) {
		CompanyStatistics companyStatistics = _companyStatisticsByCompanyId.get(
			companyId);

		if (companyStatistics == null) {
			return;
		}

		companyStatistics.reset();
	}

	public void reset(String webId) {
		CompanyStatistics companyStatistics = _companyStatisticsByWebId.get(
			webId);

		if (companyStatistics == null) {
			return;
		}

		companyStatistics.reset();
	}

	public synchronized void unregister(String webId) {
		CompanyStatistics companyStatistics = _companyStatisticsByWebId.remove(
			webId);

		if (companyStatistics != null) {
			_companyStatisticsByCompanyId.remove(
				companyStatistics.getCompanyId());
		}
	}

	@Activate
	protected void activate() {
		CompanyStatistics companyStatistics = new CompanyStatistics();

		_companyStatisticsByCompanyId.put(
			companyStatistics.getCompanyId(), companyStatistics);
		_companyStatisticsByWebId.put(
			companyStatistics.getWebId(), companyStatistics);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Map<Long, CompanyStatistics> _companyStatisticsByCompanyId =
		new TreeMap<>();
	private final Map<String, CompanyStatistics> _companyStatisticsByWebId =
		new TreeMap<>();

}