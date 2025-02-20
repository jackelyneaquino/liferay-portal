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

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.BalancedListSplitter;

/**
 * @author Peter Yoo
 */
public class TestClassBalancedListSplitter
	extends BalancedListSplitter<TestClass> {

	public TestClassBalancedListSplitter(long maxListWeight) {
		super(maxListWeight);
	}

	@Override
	public long getWeight(ListItemList listItemList) {
		if ((listItemList == null) || listItemList.isEmpty()) {
			return 0L;
		}

		long averageDuration = 0L;
		long averageOverheadDuration = 0L;

		for (ListItem listItem : listItemList) {
			TestClass testClass = listItem.getItem();

			averageDuration += testClass.getAverageDuration();
			averageOverheadDuration += testClass.getAverageOverheadDuration();
		}

		averageDuration += averageOverheadDuration / listItemList.size();

		return averageDuration;
	}

	@Override
	public long getWeight(TestClass item) {
		return item.getAverageDuration() + item.getAverageOverheadDuration();
	}

}