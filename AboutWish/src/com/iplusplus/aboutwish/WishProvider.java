package com.iplusplus.aboutwish;

import com.iplusplus.aboutwish.WishSettings.WishData;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class WishProvider extends ContentProvider {

	private static final String LOG_TAG = "WishProvider";

	static final String AUTHORITY = "com.iplusplus.aboutwish.settings";

	static final String DATABASE_NAME = "aboutwish.db";

	static final int DATABASE_VERSION = 1;

	private DatabaseHelper mDbHelper = null;

	private static final int TABLE_SHIFT = 12;

	private static final int BASE_WISH = 0x0 << TABLE_SHIFT;
	private static final int WISH = BASE_WISH + 1;
	private static final int WISH_ID = BASE_WISH + 2;

	private static UriMatcher sUriMatcher;

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, WishData.TABLE, WISH);
		sUriMatcher.addURI(AUTHORITY, WishData.TABLE + "/#", WISH_ID);
	}

	private static final String[] TABLE_NAME = new String[] { WishData.TABLE };

	@Override
	public boolean onCreate() {
		mDbHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		final int match = sUriMatcher.match(uri);
		final int table = match >> TABLE_SHIFT;
		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		Cursor c = null;
		switch (match) {
		case WISH:
			c = db.query(TABLE_NAME[table], projection, selection,
					selectionArgs, null, null, null);
			break;
		case WISH_ID:
			final long id = ContentUris.parseId(uri);
			c = db.query(TABLE_NAME[table], projection, "_id=" + id, null,
					null, null, null);
			break;
		default:
			throw new IllegalArgumentException("Query: table "
					+ TABLE_NAME[table] + " match=" + match + " is invalid");
		}
		return c;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final int match = sUriMatcher.match(uri);
		final int table = match >> TABLE_SHIFT;
		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		long newId = -1l;
		switch (match) {
		case WISH:
			newId = db.insert(TABLE_NAME[table], null, values);
			break;
		default:
			throw new IllegalArgumentException("Insert: table "
					+ TABLE_NAME[table] + " match=" + match + " is invalid");
		}
		return ContentUris.withAppendedId(uri, newId);
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		if (values == null || values.length == 0) {
			return 0;
		}

		final int match = sUriMatcher.match(uri);
		final int table = match >> TABLE_SHIFT;
		final SQLiteDatabase db = mDbHelper.getWritableDatabase();

		int count = 0;
		switch (match) {
		case WISH:
			try {
				db.beginTransaction();
				final int N = values.length;
				for (int i = 0; i < N; i++) {
					ContentValues v = values[i];
					if (v == null) {
						continue;
					}
					db.insert(TABLE_NAME[table], null, v);
					count++;
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
				db.endTransaction();
			} finally {
				db.endTransaction();
			}
			break;
		}

		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException(
				"Delete is unsupported operation");
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		final int match = sUriMatcher.match(uri);
		final int table = match >> TABLE_SHIFT;
		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int res = 0;
		switch (match) {
		case WISH_ID:
			res = db.update(TABLE_NAME[table], values, "_id="+ContentUris.parseId(uri), null);
			break;
		default:
			throw new IllegalArgumentException("Update: table "
					+ TABLE_NAME[table] + " match=" + match + " is invalid");
		}
		return res;
	}

	private static void createWishTable(SQLiteDatabase db) {
		Log.i(LOG_TAG, "create wish table");

		db.execSQL("CREATE TABLE " + WishData.TABLE + " (" + WishData._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + WishData.WISH
				+ " TEXT," + WishData.SUPPORT + " INTEGER," + WishData.RELAY
				+ " INTEGER," + WishData.DATE + " TEXT" + ");");
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createWishTable(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}

}
