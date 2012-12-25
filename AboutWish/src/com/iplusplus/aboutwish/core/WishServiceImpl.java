package com.iplusplus.aboutwish.core;

import java.io.InputStream;

import android.content.Context;

import com.iplusplus.aboutwish.Wish;

public class WishServiceImpl extends WishService {
	
	private Context mContext;
	
	public WishServiceImpl(Context context) {
		mContext = context;
	}

	@Override
	protected String getPostWishServiceApi(Wish wish) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPostSupportServiceApi(Wish wish) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPostRelayServiceApi(Wish wish) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getRequestWishsServiceApi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ResultInfo handleResponse(String api, boolean result, int code, String content) {
		// TODO Auto-generated method stub
		return null;
	}

}
