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
String languageId = LanguageUtil.getLanguageId(request);
%>

<div class="calendar-asset-abstract">
	<p>
		<liferay-ui:icon
			icon="user"
			markupView="lexicon"
			message=""
		/>

		<%
		Calendar calendar = calendarBooking.getCalendar();
		%>

		<strong><%= HtmlUtil.escape(calendar.getName(languageId)) %></strong>

		<%
		List<CalendarBooking> childCalendarBookings = calendarBooking.getChildCalendarBookings();
		%>

		<c:if test="<%= !childCalendarBookings.isEmpty() %>">
			<br />

			<liferay-ui:icon
				icon="globe"
				markupView="lexicon"
				message="resources"
			/>

			<liferay-ui:message key="resources" />:

			<%
			List<String> calendarResourcesNames = new ArrayList<String>();

			for (CalendarBooking childCalendarBooking : childCalendarBookings) {
				CalendarResource calendarResource = childCalendarBooking.getCalendarResource();

				calendarResourcesNames.add(calendarResource.getName(languageId));
			}
			%>

			<%= HtmlUtil.escape(StringUtil.merge(calendarResourcesNames, ", ")) %>
		</c:if>

		<br />
		<br />

		<liferay-ui:icon
			icon="calendar"
			markupView="lexicon"
			message="starts"
		/>

		<%
		java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(calendarBooking.getStartTime(), user.getTimeZone());
		%>

		<c:choose>
			<c:when test="<%= calendarBooking.isAllDay() %>">
				<liferay-ui:message key="starts" />: <%= utcLongDateJFormat.format(startTimeJCalendar.getTime()) %>, <%= utcTimeJFormat.format(startTimeJCalendar.getTime()) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="starts" />: <%= longDateJFormat.format(startTimeJCalendar.getTime()) %>, <%= timeJFormat.format(startTimeJCalendar.getTime()) %>
			</c:otherwise>
		</c:choose>

		<br />

		<liferay-ui:icon
			icon="calendar"
			markupView="lexicon"
			message="ends"
		/>

		<%
		java.util.Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(calendarBooking.getEndTime(), user.getTimeZone());
		%>

		<c:choose>
			<c:when test="<%= calendarBooking.isAllDay() %>">
				<liferay-ui:message key="ends" />: <%= utcLongDateJFormat.format(endTimeJCalendar.getTime()) %>, <%= utcTimeJFormat.format(endTimeJCalendar.getTime()) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="ends" />: <%= longDateJFormat.format(endTimeJCalendar.getTime()) %>, <%= timeJFormat.format(endTimeJCalendar.getTime()) %>
			</c:otherwise>
		</c:choose>
	</p>
</div>

<br />