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

package com.liferay.users.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "screen.navigation.category.order:Integer=30",
	service = ScreenNavigationCategory.class
)
public class UserPreferencesScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return UserScreenNavigationEntryConstants.CATEGORY_KEY_PREFERENCES;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "preferences");
	}

	@Override
	public String getScreenNavigationKey() {
		return UserScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_USERS;
	}

	@Reference
	private Language _language;

}