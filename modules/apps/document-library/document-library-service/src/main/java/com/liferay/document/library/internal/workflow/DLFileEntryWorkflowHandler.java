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

package com.liferay.document.library.internal.workflow;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Alexander Chow
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = WorkflowHandler.class
)
public class DLFileEntryWorkflowHandler
	extends BaseWorkflowHandler<DLFileEntry> {

	@Override
	public AssetRenderer<DLFileEntry> getAssetRenderer(long classPK)
		throws PortalException {

		AssetRendererFactory<DLFileEntry> assetRendererFactory =
			getAssetRendererFactory();

		if (assetRendererFactory != null) {
			DLFileVersion dlFileVersion =
				_dlFileVersionLocalService.getFileVersion(classPK);

			return assetRendererFactory.getAssetRenderer(
				dlFileVersion.getFileEntryId(),
				AssetRendererFactory.TYPE_LATEST);
		}

		return null;
	}

	@Override
	public AssetRendererFactory<DLFileEntry> getAssetRendererFactory() {
		return (AssetRendererFactory<DLFileEntry>)
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());
	}

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public long getDiscussionClassPK(
		Map<String, Serializable> workflowContext) {

		try {
			AssetRenderer<DLFileEntry> dlFileEntryAssetRenderer =
				getAssetRenderer(super.getDiscussionClassPK(workflowContext));

			return dlFileEntryAssetRenderer.getClassPK();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return super.getDiscussionClassPK(workflowContext);
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException {

		DLFileVersion dlFileVersion = _dlFileVersionLocalService.getFileVersion(
			classPK);

		long folderId = dlFileVersion.getFolderId();

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = _dlFolderLocalService.getFolder(folderId);

			if (dlFolder.getRestrictionType() !=
					DLFolderConstants.RESTRICTION_TYPE_INHERIT) {

				break;
			}

			folderId = dlFolder.getParentFolderId();
		}

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				companyId, groupId, DLFolder.class.getName(), folderId,
				dlFileVersion.getFileEntryTypeId(), true);

		if (workflowDefinitionLink == null) {
			workflowDefinitionLink =
				_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
					companyId, groupId, DLFolder.class.getName(), folderId,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL, true);
		}

		return workflowDefinitionLink;
	}

	@Override
	public boolean isVisible() {
		return _VISIBLE;
	}

	@Override
	public DLFileEntry updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		return _dlFileEntryLocalService.updateStatus(
			userId, classPK, status, serviceContext, workflowContext);
	}

	private static final boolean _VISIBLE = false;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryWorkflowHandler.class);

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}