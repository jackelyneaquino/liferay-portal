<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceInventoryWarehousesDisplayContext.getPortletURL();
%>

<c:if test="<%= commerceInventoryWarehousesDisplayContext.hasManageCommerceInventoryWarehousePermission() %>">
	<div class="pt-4">
		<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

			<frontend-data-set:headless-display
				apiURL="/o/headless-commerce-admin-inventory/v1.0/warehouses?sort=name:asc"
				creationMenu="<%= commerceInventoryWarehousesDisplayContext.getWarehouseCreationMenu() %>"
				fdsActionDropdownItems="<%= commerceInventoryWarehousesDisplayContext.getWarehouseFDSActionDropdownItems() %>"
				formName="fm"
				id="<%= CommerceInventoryWarehouseFDSNames.WAREHOUSES %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= portletURL %>"
				style="stacked"
			/>
		</aui:form>
	</div>
</c:if>