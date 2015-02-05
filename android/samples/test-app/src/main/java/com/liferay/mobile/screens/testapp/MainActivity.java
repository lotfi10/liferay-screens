package com.liferay.mobile.screens.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.liferay.mobile.screens.assetlist.AssetListScreenlet;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.util.LiferayServerContext;
import com.liferay.mobile.screens.util.SessionContext;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class MainActivity extends Activity {

	private AssetListScreenlet _screenlet;

	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);

		setContentView(R.layout.activity_main);

		System.out.println("MainActivity.onCreate");
		_screenlet = (AssetListScreenlet)findViewById(R.id.assetlist_screenlet);

//		if (state == null) {
//			_screenlet.load(10202, 10011);
//		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		System.out.println("MainActivity.onResume");
	}

	@Override
	protected void onStop() {
		super.onStop();

		System.out.println("MainActivity.onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		System.out.println("MainActivity.onDestroy");
	}

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
	}
}
