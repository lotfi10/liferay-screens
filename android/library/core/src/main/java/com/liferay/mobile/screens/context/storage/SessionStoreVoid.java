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

package com.liferay.mobile.screens.context.storage;

import android.content.Context;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.screens.context.User;

/**
 * @author Jose Manuel Navarro
 */
public class SessionStoreVoid implements SessionStore {

	@Override
	public void storeSession() {
	}

	@Override
	public void removeStoredSession() {
	}

	@Override
	public String getStoreName() {
		return null;
	}

	@Override
	public void setAuthentication(BasicAuthentication auth) {
	}

	@Override
	public void setUser(User user) {
	}

	@Override
	public void setContext(Context ctx) {
	}

}