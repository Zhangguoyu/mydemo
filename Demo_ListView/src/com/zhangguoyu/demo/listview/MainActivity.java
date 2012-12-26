package com.zhangguoyu.demo.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {
	
	private static final int MSG_ANIMATE_ITEM = 0;
	
	private ListView mLsvDemo;
	private MyListAdapter mAdapter;
	private List<ItemInfo> mNameList;
	
	private static class HandlerData {
		View view;
		ItemInfo itemInfo;
	}
	
	private Handler mHandler = new Handler() {
		
		
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MSG_ANIMATE_ITEM:
				HandlerData data = (HandlerData) msg.obj;
				final ItemInfo item = data.itemInfo;
				Tweener tweener = new Tweener(data.view, false, 500, new TweenCallback() {
					
					private int mViewHeight;
					
					@Override
					public void onTweenValueChanged(View view, float time, float value) {
						int height = (int) (mViewHeight*value);
						item.height = height>0?height:1;
						mAdapter.notifyDataSetChanged();
						
						Log.d("demo listview", "onTweenValueChanged time " + time + ", value " + value + " h " + item.height);
					}
					
					@Override
					public void onTweenStarted(View view) {
						mViewHeight = view.getMeasuredHeight();
						item.animating = true;
						Log.d("demo listview", "onStart");
					}
					
					@Override
					public void onTweenFinished(View view) {
						item.height = LayoutParams.WRAP_CONTENT;
						item.needAnimation = false;
						item.animating = false;
						mAdapter.notifyDataSetChanged();
						Log.d("demo listview", "onFinish");
					}
				});
				tweener.setInterpolator(new AccelerateDecelerateInterpolator());
				tweener.start(true);
				break;
			}
		}
	};

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
		public View getView(int position, View convertView, final ViewGroup parent) {
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
			//Log.d("demo listview", "position "+position+" child height " + childHeight);
			
			final ItemInfo item = getItem(position);
			holder.txvTitle.setText(item.name);
			
			AbsListView.LayoutParams lp = (LayoutParams) convertView.getLayoutParams();
			if(lp == null) {
				lp = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				convertView.setLayoutParams(lp);
			}
			
			if(item.needAnimation) {
				
				if(item.animating) {
					lp.height = item.height;
					//Log.d("demo listview", "position "+ position+" animation " + item.height);
				} else {
					Message msg = mHandler.obtainMessage(MSG_ANIMATE_ITEM);
					HandlerData data = new HandlerData();
					data.view = convertView;
					data.itemInfo = item;
					msg.obj = data;
					mHandler.sendMessage(msg);
					item.animating = true;
				}
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
    	boolean needAnimation = false;
    	boolean animating = false;
    	boolean in;
    	int height = 1;
    	
    	ItemInfo(String n, boolean a, boolean i) {
    		name = n;
    		needAnimation = a;
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
