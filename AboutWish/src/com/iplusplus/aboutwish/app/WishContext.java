package com.iplusplus.aboutwish.app;

public interface WishContext {
	
	public static final String WISH_MANAGER = "com.iplusplus.aboutwish.service.WISH_MANAGER";
	
	public Object getService(String name);

}
