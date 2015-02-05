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

package com.liferay.mobile.screens.assetlist.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.AssetListListener;
import com.liferay.mobile.screens.util.LiferayServerContext;
import com.liferay.mobile.screens.util.SessionContext;

import java.util.Locale;

import org.json.JSONArray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * @author Silvio Santos
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AssetListInteractorTest {

	@Test
	public void firstRowForPage() {
		AssetListInteractorImpl interactor = new AssetListInteractorImpl(
			0, 0, 10);

		int row = interactor.getFirstRowForPage(4);

		assertTrue(row == 40);
	}

	@Test
	public void loadPage() throws Exception {
		AssetListInteractorImpl interactor = new AssetListInteractorImpl(
			0, 50, 25);

		interactor.onScreenletAttachted(new AssetListListener() {
			@Override
			public void onAssetListPageReceived(JSONArray jsonArray) {
			}

			@Override
			public void onAssetListLoadFailure(Exception e) {
			}
		});

		interactor.loadPage(
			LiferayServerContext.getGroupId(), 10011, 0, Locale.ENGLISH);

		Robolectric.runBackgroundTasks();
	}

	@Before
	public void setUp() {
		Session session = SessionContext.createSession(
			"test@liferay.com", "test");
	}

}