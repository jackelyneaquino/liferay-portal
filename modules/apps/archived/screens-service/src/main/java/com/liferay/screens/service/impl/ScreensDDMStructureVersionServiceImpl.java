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

package com.liferay.screens.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.screens.service.base.ScreensDDMStructureVersionServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	property = {
		"json.web.service.context.name=screens",
		"json.web.service.context.path=ScreensDDMStructureVersion"
	},
	service = AopService.class
)
public class ScreensDDMStructureVersionServiceImpl
	extends ScreensDDMStructureVersionServiceBaseImpl {

	@Override
	public JSONObject getDDMStructureVersion(long structureId)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionService.getLatestStructureVersion(structureId);

		return JSONUtil.put(
			"ddmFormLayout",
			_jsonFactory.createJSONObject(
				_jsonFactory.looseSerializeDeep(
					ddmStructureVersion.getDDMFormLayout()))
		).put(
			"ddmStructure",
			_jsonFactory.createJSONObject(
				_jsonFactory.looseSerialize(ddmStructureVersion.getStructure()))
		);
	}

	@Reference
	private DDMStructureVersionService _ddmStructureVersionService;

	@Reference
	private JSONFactory _jsonFactory;

}