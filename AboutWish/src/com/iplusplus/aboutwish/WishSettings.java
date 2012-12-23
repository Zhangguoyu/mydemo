package com.iplusplus.aboutwish;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public final class WishSettings {
	
	private static final String LOG_TAG = "WishSettings";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + WishProvider.AUTHORITY);
	
	public interface WishColumns extends BaseColumns {
		
		static final String WISH = "wish";
		
		static final String SUPPORT = "support";
		
		static final String RELAY = "relay";
		
		static final String DATE = "date";
	}
	
	public static class WishData implements WishColumns {
		
		public static final String TABLE = "wish";
		
		public static final Uri CONTENT_URI = Uri.withAppendedPath(WishSettings.CONTENT_URI, TABLE);
		
		public static final String[] PROJECTIONS = new String[]{
			_ID, WISH, SUPPORT, RELAY, DATE
		};
		
		public static final Wish[] getWishList(ContentResolver cr) {
			Wish[] wishList = null;
			Cursor c = null;
			try {
				c = cr.query(CONTENT_URI, PROJECTIONS, null, null, null);
				int count = 0;
				if(c != null && c.moveToFirst() && (count = c.getCount()) > 0) {
					wishList = new Wish[count];
					
					final int indexId = c.getColumnIndexOrThrow(_ID);
					final int indexWish = c.getColumnIndexOrThrow(WISH);
					final int indexSupport = c.getColumnIndexOrThrow(SUPPORT);
					final int indexRelay = c.getColumnIndexOrThrow(RELAY);
					final int indexDate = c.getColumnIndexOrThrow(DATE);
					
					do {
						
						Wish wish = new Wish();
						wish.setId(c.getLong(indexId));
						wish.setWish(c.getString(indexWish));
						wish.setSupportNumber(c.getInt(indexSupport));
						wish.setRelayNumber(c.getInt(indexRelay));
						try {
							wish.setDate(SimpleDateFormat.getDateInstance().parse(c.getString(indexDate)));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						wishList[count++] = wish;
						
					} while (c.moveToNext());
				}
			} finally {
				if (c != null) {
					c.close();
				}
			}
			
			return wishList;
		}
		
		public static final long insertWish(ContentResolver cr, Wish wish) {
			ContentValues values = new ContentValues();
			values.put(_ID, wish.getId());
			values.put(WISH, wish.getWish());
			values.put(SUPPORT, wish.getSupportNumber());
			values.put(RELAY, wish.getRelayNumber());
			
			final Date date = wish.getDate();
			if(date != null) {
				values.put(DATE, date.toString());

			}
			Uri uri = cr.insert(CONTENT_URI, values);
			return ContentUris.parseId(uri);
		}
		
		public static final int insertWishs(ContentResolver cr, Wish[] wishs) {
			if(wishs == null || wishs.length == 0) {
				Log.d(LOG_TAG, "insert wishs faild, cause wishs " + wishs + " is invalid");
				return 0;
			}
			int N = wishs.length;
			ContentValues[] valuesList = new ContentValues[N];
			for(int i=0; i<N; i++) {
				Wish wish = wishs[i];
				if(wish == null) {
					continue;
				}
				
				ContentValues values = new ContentValues();
				values.put(WISH, wish.getWish());
				values.put(SUPPORT, wish.getSupportNumber());
				values.put(RELAY, wish.getRelayNumber());
				
				final Date date = wish.getDate();
				if(date != null) {
					values.put(DATE, date.toString());

				}
				valuesList[i] = values;
			}
			
			return cr.bulkInsert(CONTENT_URI, valuesList);
		}
		
		public static final int updateWish(ContentResolver cr, long id, int number, int flag) {
			int res = 0;
			ContentValues values = new ContentValues();
			String column = null;
			switch(flag) {
				case Wish.FLAG_SUPPORT:
					values.put(SUPPORT, number);
					break;
				case Wish.FLAG_RELAY:
					values.put(RELAY, number);
					break;
				default:
					throw new IllegalArgumentException("flag " + flag + " is invaild");	
			}
			Log.i(LOG_TAG, "update wish id " + id + " " + column + " " + number);
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
			res = cr.update(uri, values, null, null);
			return res;
		}
	}
	
	private WishSettings() {}

}
