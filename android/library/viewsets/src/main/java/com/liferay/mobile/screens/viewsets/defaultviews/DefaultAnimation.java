/*
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

package com.liferay.mobile.screens.viewsets.defaultviews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * @author Javier Gamarra
 */
public class DefaultAnimation {

	public static void showViewWithReveal(View view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

			// get the center for the clipping circle
			int cx = (view.getLeft() + view.getRight()) / 2;
			int cy = (view.getTop() + view.getBottom()) / 2;

			// get the final radius for the clipping circle
			int finalRadius = Math.max(view.getWidth(), view.getHeight());

			// create the animator for this view (the start radius is zero)
			Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

			// make the view visible and start the animation
			view.setVisibility(View.VISIBLE);
			anim.start();
		}
		else {
			view.setVisibility(View.VISIBLE);
		}
	}

	public static void hideViewWithReveal(final View view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

			// get the center for the clipping circle
			int cx = (view.getLeft() + view.getRight()) / 2;
			int cy = (view.getTop() + view.getBottom()) / 2;

			// get the initial radius for the clipping circle
			int initialRadius = view.getWidth();

			// create the animation (the final radius is zero)
			Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

			// make the view invisible when the animation is done
			anim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					view.setVisibility(View.INVISIBLE);
				}
			});

			// start the animation
			anim.start();
		}
		else {
			view.setVisibility(View.INVISIBLE);
		}
	}

	public static void startActivityWithAnimation(Activity activity, Class destination) {
		Intent intent = new Intent(activity, destination);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			activity.startActivity(intent,
					ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
		}
		else {
			activity.startActivity(intent);
		}
	}
}