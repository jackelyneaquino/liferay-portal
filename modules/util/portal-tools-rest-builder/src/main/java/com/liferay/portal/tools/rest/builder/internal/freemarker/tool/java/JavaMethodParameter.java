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

package com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Peter Shin
 */
public class JavaMethodParameter {

	public JavaMethodParameter(String parameterName, String parameterType) {
		if (!Validator.isVariableName(parameterName)) {
			_parameterName = parameterName + "Value";
		}
		else {
			_parameterName = parameterName;
		}

		_parameterType = parameterType;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getParameterType() {
		return _parameterType;
	}

	private final String _parameterName;
	private final String _parameterType;

}