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

package com.liferay.mobile.screens.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.util.SessionContext;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class LoginActivity extends Activity implements LoginListener {

	private LoginScreenlet _screenlet;

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);

		setContentView(R.layout.activity_login);

		System.out.println("MainActivity.onCreate");

		SessionContext.createSession("test@liferay.com", "test");

		_screenlet = (LoginScreenlet)findViewById(
			R.id.login_screenlet);

		_screenlet.setListener(this);
	}

	@Override
	public void onLoginFailure(Exception e) {
		String message = "Failed to login: " + e.getMessage();

		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLoginSuccess(JSONObject userAttributes) {
		Intent intent = new Intent(this, MainActivity.class);

		startActivity(intent);
	}


}
