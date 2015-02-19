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

package com.liferay.mobile.screens.themes.ddl.form.fields;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.StringWithOptionsField;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.util.ViewUtil;

import java.util.List;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldRadioView extends RadioGroup
	implements DDLFieldViewModel<StringWithOptionsField>, CompoundButton.OnCheckedChangeListener {

	public DDLFieldRadioView(Context context) {
		super(context);
	}

	public DDLFieldRadioView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	@Override
	public StringWithOptionsField getField() {
		return _field;
	}

	@Override
	public void setField(StringWithOptionsField field) {
		_field = field;

		if (_field.isShowLabel()) {
			TextView label = (TextView) findViewById(R.id.label);

			label.setText(field.getLabel());
			label.setVisibility(VISIBLE);
		}

		LayoutParams layoutParams = new LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		List<StringWithOptionsField.Option> availableOptions = field.getAvailableOptions();

		for (int i = 0; i < availableOptions.size(); ++i) {
			StringWithOptionsField.Option opt = availableOptions.get(i);

			RadioButton radioButton = new RadioButton(getContext());

			radioButton.setLayoutParams(layoutParams);
			radioButton.setText(opt.label);
			radioButton.setTag(opt);
			radioButton.setOnCheckedChangeListener(this);
			radioButton.setTypeface(getTypeface());

			radioButton.setId(ViewUtil._generateUniqueId());

			addView(radioButton, getChildCount() - 1);
		}

		refresh();
	}

	@Override
	public void refresh() {
		List<StringWithOptionsField.Option> selectedOptions = _field.getCurrentValue();

		if (selectedOptions != null) {
			for (StringWithOptionsField.Option opt : selectedOptions) {
				RadioButton radioButton = (RadioButton) findViewWithTag(opt);

				if (radioButton != null) {
					radioButton.setChecked(true);
				}
			}
		}
	}

	@Override
	public void onPostValidation(boolean valid) {
		if (valid) {
			return;
		}
		if (_field.isShowLabel()) {
			TextView label = (TextView) findViewById(R.id.label);

			label.setError(getContext().getString(R.string.required_value));
		}
		else {
			List<StringWithOptionsField.Option> availableOptions = _field.getAvailableOptions();
			StringWithOptionsField.Option opt = availableOptions.get(0);
			RadioButton radioButton = (RadioButton) findViewWithTag(opt);
			if (radioButton != null) {
				radioButton.setError(getContext().getString(R.string.required_value));
			}
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setSaveEnabled(false);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		RadioButton radioButton = (RadioButton) buttonView;

		StringWithOptionsField.Option opt = (StringWithOptionsField.Option) radioButton.getTag();

		_field.selectOption(opt);
	}

	private Typeface getTypeface() {
		//FIXME replace with constructor with styles when we have the drawables
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			return Typeface.DEFAULT;
		}
		return Typeface.create("sans-serif-light", Typeface.NORMAL);
	}

	private StringWithOptionsField _field;

}