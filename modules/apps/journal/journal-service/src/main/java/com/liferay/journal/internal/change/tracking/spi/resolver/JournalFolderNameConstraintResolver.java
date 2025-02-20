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

package com.liferay.journal.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.language.LanguageResources;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(service = ConstraintResolver.class)
public class JournalFolderNameConstraintResolver
	implements ConstraintResolver<JournalFolder> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-folder-name";
	}

	@Override
	public Class<JournalFolder> getModelClass() {
		return JournalFolder.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "rename-the-folder-in-the-publication";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "parentFolderId", "name"};
	}

	@Override
	public void resolveConflict(
		ConstraintResolverContext<JournalFolder> constraintResolverContext) {
	}

}