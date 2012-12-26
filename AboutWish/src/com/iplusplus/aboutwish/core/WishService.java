package com.iplusplus.aboutwish.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.text.TextUtils;

import com.iplusplus.aboutwish.Wish;

public abstract class WishService implements IWishService {
	
	public static class ResultInfo {
		boolean result;
		int code;
		Object data;
	}
	
	public ResultInfo postWish(Wish wish) {
		return execRequest(getPostWishServiceApi(wish));
	}

	public ResultInfo postSupport(Wish wish) {
		return execRequest(getPostSupportServiceApi(wish));
	}

	public ResultInfo postReplay(Wish wish) {
		return execRequest(getPostRelayServiceApi(wish));
	}

	public ResultInfo requestWishs() {
		return execRequest(getRequestWishsServiceApi());
	}
	
	private ResultInfo execRequest(String url) {
		if(TextUtils.isEmpty(url)) {
			return null;
		}
		
		AndroidHttpClient client = AndroidHttpClient.newInstance("com.iplusplus.aboutwish.api");
		HttpGet request = new HttpGet(url);
		StringBuilder sb = new StringBuilder();
		boolean result = false;
		int resultCode = -1;
		try {
			HttpResponse response = client.execute(request);
			resultCode = response.getStatusLine().getStatusCode();
			if(resultCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if(entity != null) {
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content, "utf-8"));
					
					String line = null;
					while((line = reader.readLine()) != null) {
						sb.append(line);
					}
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.close();
		
		return handleResponse(url, result, resultCode, sb.toString());
		
	}
	
	protected abstract String getPostWishServiceApi(Wish wish);
	
	protected abstract String getPostSupportServiceApi(Wish wish);
	
	protected abstract String getPostRelayServiceApi(Wish wish);
	
	protected abstract String getRequestWishsServiceApi();
	
	protected abstract ResultInfo handleResponse(String api, boolean result, int code, String content);

}
