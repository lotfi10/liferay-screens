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

package com.liferay.mobile.screens.themes.ddl.fields;

import android.app.DatePickerDialog;

import android.content.Context;

import android.os.Bundle;
import android.os.Parcelable;

import android.text.InputType;
import android.text.format.DateUtils;

import android.util.AttributeSet;

import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;

import com.liferay.mobile.screens.ddl.model.DateField;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class DDLFieldDateView extends BaseDDLFieldTextView<DateField>
	implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

	public DDLFieldDateView(Context context) {
		super(context, null);
	}

	public DDLFieldDateView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public DDLFieldDateView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onClick(View view) {
		Date date = getField().getCurrentValue();
		Calendar.getInstance().setTime(date);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		_pickerDialog = new DatePickerDialog(
			getContext(), this, year, month, day);

		_pickerDialog.show();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Locale locale = getResources().getConfiguration().locale;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.set(year, month, day);

		getField().setCurrentValue(calendar.getTime());
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (_pickerDialog != null) {
			_pickerDialog.dismiss();
			_pickerDialog = null;
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		EditText editText = getTextEditText();
		editText.setCursorVisible(false);
		editText.setFocusableInTouchMode(false);
		editText.setOnClickListener(this);
		editText.setInputType(InputType.TYPE_NULL);
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle)inState;
		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);

		Bundle dialogState = state.getBundle(_STATE_DIALOG);

		if (dialogState != null) {
			_pickerDialog = new DatePickerDialog(getContext(), this, 0, 0, 0);
			_pickerDialog.onRestoreInstanceState(dialogState);
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		Bundle state = new Bundle();
		state.putParcelable(_STATE_SUPER, superState);

		if (_pickerDialog != null && _pickerDialog.isShowing()) {
			state.putBundle(_STATE_DIALOG, _pickerDialog.onSaveInstanceState());
		}

		return state;
	}

	@Override
	protected void onTextChanged(String text) {
		//not doing anything at the moment, because field is being set
		//using the DatePickerDialog
	}

	private static final String _STATE_DIALOG = "dialog";

	private static final String _STATE_SUPER = "super";

	private DatePickerDialog _pickerDialog;

}