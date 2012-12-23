package com.iplusplus.aboutwish.app;

import com.iplusplus.aboutwish.core.WishContextImpl;

import android.app.Application;

public class WishApplication extends Application implements WishContext {
	
	private WishContext mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = new WishContextImpl();
	}

	@Override
	public Object getService(String name) {
		return mContext.getService(name);
	}

}
