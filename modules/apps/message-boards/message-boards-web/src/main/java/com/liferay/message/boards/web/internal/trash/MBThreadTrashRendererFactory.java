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

package com.liferay.message.boards.web.internal.trash;

import com.liferay.asset.util.AssetHelper;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.trash.TrashRendererFactory;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBThread",
	service = TrashRendererFactory.class
)
public class MBThreadTrashRendererFactory implements TrashRendererFactory {

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		MBThreadTrashRenderer mbThreadTrashRenderer = new MBThreadTrashRenderer(
			_mbThreadLocalService.getThread(classPK), _assetHelper);

		mbThreadTrashRenderer.setServletContext(_servletContext);

		return mbThreadTrashRenderer;
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.message.boards.web)"
	)
	private ServletContext _servletContext;

}