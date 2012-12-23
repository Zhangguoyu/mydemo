package com.iplusplus.aboutwish;

import java.util.Date;

public class Wish {
	
	public static final int FLAG_SUPPORT = 1;
	public static final int FLAG_RELAY = 2;
	
	private long mId;
	
	private String mWish;
	
	private int mSupportNumber;
	
	private int mRelayNumber;
	
	private Date mDate;
	
	public void setId(long id) {
		mId = id;
	}
	
	public long getId() {
		return mId;
	}
	
	public void setWish(String wish) {
		mWish = wish;
	}
	
	public String getWish() {
		return mWish;
	}
	
	public void setSupportNumber(int n) {
		mSupportNumber = n;
	}
	
	public int getSupportNumber() {
		return mSupportNumber;
	}
	
	public void setRelayNumber(int n) {
		mRelayNumber = n;
	}
	
	public int getRelayNumber() {
		return mRelayNumber;
	}
	
	public void setDate(Date d) {
		mDate = d;
	}
	
	public Date getDate() {
		return mDate;
	}

}
