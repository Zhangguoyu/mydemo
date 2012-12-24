package com.iplusplus.aboutwish;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.iplusplus.aboutwish.app.WishActivity;
import com.iplusplus.aboutwish.widget.AWishListView;
import com.iplusplus.aboutwish.widget.AWishListView.OnWishListener;

public class MainActivity extends WishActivity implements OnWishListener {

	private ListView mLsvWishList;
	private EditText mEdtInput;
	private AWishListView mAwListView;

	private MyAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAwListView = (AWishListView) findViewById(R.id.aw_listview);
		mAwListView.setOnWishListener(this);
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
				holder.txvWishContent = (TextView) convertView
						.findViewById(R.id.wish_content);
				holder.txvWishDate = (TextView) convertView
						.findViewById(R.id.wish_date);
				holder.txvWishRelay = (TextView) convertView
						.findViewById(R.id.wish_relay);
				holder.txvWishSupport = (TextView) convertView
						.findViewById(R.id.wish_support);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.txvWishContent.setText(getItem(position));
			holder.txvWishDate.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
			holder.txvWishRelay.setText(""+709);
			holder.txvWishSupport.setText("" + 1040);
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView txvWishContent;
		TextView txvWishDate;
		TextView txvWishRelay;
		TextView txvWishSupport;
	}

	@Override
	public void onWish(String wish) {
		// TODO Auto-generated method stub
		Log.d("MainActivity", "OnWish " + wish);
	}
}
