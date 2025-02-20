/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.search.spi.model.index.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseTextEmbeddingModelDocumentContributor {

	protected void addTextEmbedding(
		Document document, String languageId, Double[] textEmbedding) {

		if (textEmbedding.length == 0) {
			return;
		}

		_addTextEmbeddingField(document, languageId, textEmbedding);
	}

	protected void addTextEmbeddingForAvailableLanguages(
		long companyId, Document document, Double[] textEmbedding) {

		if (textEmbedding.length == 0) {
			return;
		}

		List<String> languageIds = Arrays.asList(
			semanticSearchConfiguration.languageIds());

		for (Locale locale :
				LanguageUtil.getCompanyAvailableLocales(companyId)) {

			String languageId = LocaleUtil.toLanguageId(locale);

			if (!languageIds.contains(languageId)) {
				continue;
			}

			_addTextEmbeddingField(document, languageId, textEmbedding);
		}
	}

	protected Double[] getTextEmbedding(
		Function<String, Double[]> function, String text) {

		try {
			return function.apply(text);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return new Double[0];
	}

	protected boolean isAddTextEmbedding(Class<?> clazz) {
		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688")) &&
			semanticSearchConfiguration.textEmbeddingsEnabled() &&
			ArrayUtil.contains(
				semanticSearchConfiguration.assetEntryClassNames(),
				clazz.getName(), true)) {

			return true;
		}

		return false;
	}

	protected volatile SemanticSearchConfiguration semanticSearchConfiguration;

	private void _addTextEmbeddingField(
		Document document, String languageId, Double[] textEmbedding) {

		Field field = new Field(_getFieldName(languageId));

		field.setNumeric(true);
		field.setNumericClass(Double.class);
		field.setTokenized(false);
		field.setValues(ArrayUtil.toStringArray(textEmbedding));

		document.add(field);
	}

	private String _getFieldName(String languageId) {
		return StringBundler.concat(
			"text_embedding_",
			semanticSearchConfiguration.embeddingVectorDimensions(),
			StringPool.UNDERLINE, languageId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseTextEmbeddingModelDocumentContributor.class);

}