package com.zhangguoyu.demo.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {
	
	private ListView mLsvDemo;
	private MyListAdapter mAdapter;
	private List<ItemInfo> mNameList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mLsvDemo = (ListView) findViewById(R.id.demo_listview);
        
        mNameList = new ArrayList<ItemInfo>();
        for(int i=0; i<18; i++) {
        	char c = (char) (65 + i);
        	ItemInfo item = new ItemInfo("" + c + c + c + c + c + c, false, false);
        	mNameList.add(item);
        }
        mAdapter = new MyListAdapter(this, mNameList);
        mLsvDemo.setAdapter(mAdapter);
        mLsvDemo.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private class MyListAdapter extends BaseAdapter {
    	
    	private LayoutInflater mInflater;
    	
    	private List<ItemInfo> mList;
    	
    	public MyListAdapter(Context context, List<ItemInfo> list) {
    		
    		mInflater = (LayoutInflater) context.getSystemService(
    				Context.LAYOUT_INFLATER_SERVICE);
    		mList = list;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public ItemInfo getItem(int position) {
			final int count = getCount();
			int pos = count - position - 1;
			return mList.get(pos);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.demo_itemview, null);
				holder = new ViewHolder();
				holder.txvTitle = (TextView) convertView.findViewById(R.id.item_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			final int childHeight = convertView.getMeasuredHeight();
			Log.d("demo listview", "child height " + childHeight);
			
			final ItemInfo item = getItem(position);
			holder.txvTitle.setText(item.name);
			
			AbsListView.LayoutParams lp = (LayoutParams) convertView.getLayoutParams();
			if(lp == null) {
				lp = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			}
			
			if(item.animation) {
				lp.height = 0;
			} else {
				lp.height = LayoutParams.WRAP_CONTENT;
			}
			
			return convertView;
		}
    	
    }
    
    private static class ViewHolder {
    	TextView txvTitle;
    }
    
    private static class ItemInfo {
    	String name;
    	boolean animation = false;
    	boolean in;
    	
    	ItemInfo(String n, boolean a, boolean i) {
    		name = n;
    		animation = a;
    		in = i;
    	}
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		char c = (char) (65 + mNameList.size());
		ItemInfo newItem = new ItemInfo("" + c + c + c + c + c +c, true, true);
		mNameList.add(newItem);
		mAdapter.notifyDataSetChanged();
		
	}
}
