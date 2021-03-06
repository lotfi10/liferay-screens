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

package com.liferay.mobile.screens.viewsets.defaultviews.auth.forgotpassword;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.auth.forgotpassword.view.ForgotPasswordViewModel;
import com.liferay.mobile.screens.base.ModalProgressBar;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

/**
 * @author Jose Manuel Navarro
 */
public class ForgotPasswordView extends LinearLayout
		implements ForgotPasswordViewModel, View.OnClickListener {

	public ForgotPasswordView(Context context) {
		super(context);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public ForgotPasswordView(Context context, AttributeSet attributes) {
		super(context, attributes);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public ForgotPasswordView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	@Override
	public AuthMethod getAuthMethod() {
		return _authMethod;
	}

	@Override
	public String getLogin() {
		return _loginEditText.getText().toString();
	}

	@Override
	public void showStartOperation(String actionName) {
		_progressBar.startProgress();
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new AssertionError("Use showFinishOperation(passwordSent) instead");
	}

	@Override
	public void showFinishOperation(boolean passwordSent) {
		_progressBar.finishProgress();

		int operationMsg = (passwordSent) ? R.string.password_sent : R.string.password_sent;

		String msg = getResources().getString(operationMsg) + " " +
			getResources().getString(R.string.check_your_inbox);

		LiferayLogger.i(msg);
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		_progressBar.finishProgress();

		LiferayCrouton.error(getContext(), getContext().getString(R.string.password_request_error), e);
		LiferayLogger.e("Could not send password", e);
	}

	@Override
	public void onClick(View view) {
		ForgotPasswordScreenlet screenlet = (ForgotPasswordScreenlet) getParent();

		screenlet.performUserAction();
	}

	public void setAuthMethod(AuthMethod authMethod) {
		_authMethod = authMethod;

		refreshLoginEditTextStyle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_loginEditText = (EditText) findViewById(R.id.forgot_password_email);
		_progressBar = (ModalProgressBar) findViewById(R.id.progress_bar);

		Button requestButton = (Button) findViewById(R.id.request_button);
		requestButton.setOnClickListener(this);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		refreshLoginEditTextStyle();
	}

	protected void refreshLoginEditTextStyle() {
		_loginEditText.setInputType(_authMethod.getInputType());
		_loginEditText.setCompoundDrawablesWithIntrinsicBounds(
			getResources().getDrawable(getLoginEditTextDrawableId()), null, null, null);
	}

	protected int getLoginEditTextDrawableId() {
		if (AuthMethod.USER_ID.equals(_authMethod)) {
			return R.drawable.default_user_icon;
		}
		else if (AuthMethod.EMAIL.equals(_authMethod)) {
			return R.drawable.default_mail_icon;
		}

		return R.drawable.default_user_icon;
	}

	protected EditText getLoginEditText() {
		return _loginEditText;
	}

	private AuthMethod _authMethod;
	private EditText _loginEditText;
	private ModalProgressBar _progressBar;

}