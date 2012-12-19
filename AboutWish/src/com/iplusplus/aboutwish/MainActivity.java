package com.iplusplus.aboutwish;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.iplusplus.aboutwish.widget.AWishListView;

public class MainActivity extends Activity {

	private ListView mLsvWishList;
	private EditText mEdtInput;
	private AWishListView mAwListView;
	
	private MyAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAwListView = (AWishListView) findViewById(R.id.aw_listview);
		mEdtInput = mAwListView.getInputView();
		mLsvWishList = mAwListView.getListView();
		
		List<String> list = new ArrayList<String>();
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		list.add("我要 一颗苹果");
		mAdapter = new MyAdapter(this, list);
		mLsvWishList.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private class MyAdapter extends BaseAdapter {

		private List<String> mList;
		private LayoutInflater mInflater;

		public MyAdapter(Context context, List<String> list) {
			mList = list;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public String getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item, null);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.wish_item);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.textView.setText(getItem(position));
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView textView;
	}
}
