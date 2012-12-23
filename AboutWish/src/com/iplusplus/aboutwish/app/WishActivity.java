package com.iplusplus.aboutwish.app;

import android.app.Activity;
import android.os.Bundle;

public class WishActivity extends Activity implements WishContext {
	
	private WishContext mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = (WishApplication)getApplicationContext();
	}

	@Override
	public Object getService(String name) {
		return mContext.getService(name);
	}

}
