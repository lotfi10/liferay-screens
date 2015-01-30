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

package com.liferay.mobile.screens.userportrait;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractor;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorImpl;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitScreenlet
	extends BaseScreenlet<BaseViewModel, UserPortraitInteractor>
	implements Target {

	public UserPortraitScreenlet(Context context) {
		this(context, null);
	}

	public UserPortraitScreenlet(
		Context context, AttributeSet attributes) {

		this(context, attributes, 0);
	}

	public UserPortraitScreenlet(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);

		setInteractor(new UserPortraitInteractorImpl());
	}

	public void load() throws Exception {
		if (_portraitId == 0) {
			throw new IllegalArgumentException("portraitId cannot be 0");
		}
		if (_uuid == null) {
			throw new IllegalArgumentException("uuidId cannot be null");
		}

		getInteractor().load(_male, _portraitId, _uuid);
	}

	@Override
	public void onUserAction(String userActionName) {
	}

	public void setListener(UserPortraitListener listener) {
		_listener = listener;
	}

	protected void autoLoad() {
		if ((_portraitId != 0) && (_uuid != null)) {
			try {
				load();
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.UserPortraitScreenlet, 0, 0);

		_autoLoad = typedArray.getBoolean(
			R.styleable.UserPortraitScreenlet_autoLoad, true);

		_male = typedArray.getBoolean(R.styleable.UserPortraitScreenlet_male, true);

		_portraitId = typedArray.getInt(R.styleable.UserPortraitScreenlet_portraitId, 0);
		_uuid = typedArray.getString(R.styleable.UserPortraitScreenlet_uuid);

		int layoutId = typedArray.getResourceId(R.styleable.UserPortraitScreenlet_layoutId, 0);

		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected void onScreenletAttached() {
		if (_autoLoad) {
			autoLoad();
		}
	}

	private boolean _autoLoad;
	private UserPortraitListener _listener;
	private boolean _male;
	private long _portraitId;
	private String _uuid;

	@Override
	public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
		UserPortraitListener view = (UserPortraitListener)getScreenletView();
		view.onUserPortraitReceived(bitmap);

		if (_listener != null) {
			_listener.onUserPortraitReceived(bitmap);
		}
	}

	@Override
	public void onBitmapFailed(Drawable errorDrawable) {
		IOException exception = new IOException("Portrait cannot be loaded");

		UserPortraitListener view = (UserPortraitListener)getScreenletView();
		view.onUserPortraitFailure(exception);

		if (_listener != null) {
			_listener.onUserPortraitFailure(exception);
		}
	}

	@Override
	public void onPrepareLoad(Drawable placeHolderDrawable) {
	}
}