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

<%@ include file="/captcha/init.jsp" %>

<c:if test="<%= captchaEnabled %>">
	<script src="<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaScriptURL()) %>?hl=<%= HtmlUtil.escapeAttribute(locale.getLanguage()) %>" type="text/javascript"></script>

	<div class="g-recaptcha" data-sitekey="<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaPublicKey()) %>"></div>

	<noscript>
		<div style="height: 525px; width: 302px;">
			<div style="height: 525px; position: relative; width: 302px;">
				<div style="height: 525px; position: absolute; width: 302px;">
					<iframe frameborder="0" scrolling="no" src="<%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaNoScriptURL()) %><%= HtmlUtil.escapeAttribute(captchaConfiguration.reCaptchaPublicKey()) %>" style="border-style: none; height: 525px; width: 302px;"></iframe>
				</div>

				<div style="background: #F9F9F9; border: 1px solid #C1C1C1; border-radius: 3px; bottom: 25px; height: 60px; left: 0; margin: 0; padding: 0; position: absolute; right: 25px; width: 300px;">
					<textarea class="g-recaptcha-response" id="g-recaptcha-response" name="g-recaptcha-response" style="border: 1px solid #C1C1C1; height: 40px; margin: 10px 25px; padding: 0; resize: none; width: 250px;"></textarea>
				</div>
			</div>
		</div>
	</noscript>
</c:if>