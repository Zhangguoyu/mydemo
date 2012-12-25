package com.iplusplus.aboutwish.core;

import android.content.ContentResolver;
import android.content.Context;

import com.iplusplus.aboutwish.Wish;
import com.iplusplus.aboutwish.WishSettings.WishData;

public class WishManager implements IWishManager {
	
	private static final int ARRAY_CAPACITY_INCREMENT = 12;
	
	private Context mContext;
	private ContentResolver mCr;
	private Wish[] mWishList = null;
	private int mWishCount = 0;
	
	public WishManager(Context context) {
		mContext = context;
		mCr = context.getContentResolver();
	}

	@Override
	public boolean post(Wish wish) {
		if(WishData.insertWish(mCr, wish) > 0) {
			addInArray(wish);
			return true;
		}
		return false;
	}

	@Override
	public int support(Wish wish) {
		int support = wish.getSupportNumber();
		int newSupport = ++support;
		if(WishData.updateWish(mCr, wish.getId(), newSupport, Wish.FLAG_SUPPORT) > 0) {
			wish.setSupportNumber(newSupport);
			return newSupport;
		}
		return support;
	}

	@Override
	public int replay(Wish wish) {
		int relay = wish.getRelayNumber();
		int newRelay = ++relay;
		if(WishData.updateWish(mCr, wish.getId(), newRelay, Wish.FLAG_RELAY) > 0) {
			wish.setSupportNumber(newRelay);
			return newRelay;
		}
		return relay;
	}
	
	void buildWishList() {
		mWishList = WishData.getWishList(mCr);
		mWishCount = mWishList.length;
	}
	
	private void addInArray(Wish newWish) {
		Wish[] oldList = mWishList;
		final int count = mWishCount;
		final int size = mWishList.length;
		if(size == mWishCount) {
			Wish[] newList = new Wish[size+ARRAY_CAPACITY_INCREMENT];
			System.arraycopy(oldList, 0, newList, 0, count);
			mWishList = newList;
		}
		mWishList[mWishCount++] = newWish;
	}

	@Override
	public int getWishCount() {
		return mWishCount;
	}

	@Override
	public Wish getWishAt(int position) {
		if(position < 0 || position >= mWishCount) {
			throw new IndexOutOfBoundsException("index=" + position + " count=" + mWishCount);
		}
		return mWishList[position];
	}

}
