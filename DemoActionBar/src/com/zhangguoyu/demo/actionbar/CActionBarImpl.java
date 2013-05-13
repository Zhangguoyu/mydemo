package com.zhangguoyu.demo.actionbar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class CActionBarImpl extends CActionBar {
	
	private static final String LOG_TAG = "CActionBarImpl";
	
	private Context mContext = null;
	private Activity mActivity = null;
	private CActionBarView mActionBarView = null;
	private boolean mShowing;
	private TabImpl mSelectedTab = null;
	private List<TabImpl> mTabs = null;
	
	CActionBarImpl(Activity activity) {
		mActivity = activity;
		Window window = activity.getWindow();
		View decor = window.getDecorView();
		mContext = decor.getContext();
		mActionBarView = (CActionBarView) decor.findViewById(R.id.actionbar_id);
		mTabs = new ArrayList<TabImpl>();
	}

	@Override
	public CActionBar setLogoView(View view) {
		mActionBarView.setHomeView(view);
		return this;
	}

	@Override
	public CActionBar setBackButton(View view) {
		mActionBarView.setBackButton(view);
		return this;
	}

	@Override
	public CActionBar setTitleView(View view) {
		mActionBarView.setTitleView(view);
		return this;
	}

	@Override
	public CActionBar setOptionsView(View view) {
		mActionBarView.setMenus(view);
		return this;
	}

	@Override
	public CActionBar setCustomView(View view) {
		return this;
	}

	@Override
	public CActionBar setLogo(int resId) {
		ImageView home = new ImageView(mContext);
		home.setImageResource(R.drawable.ic_launcher);
		mActionBarView.setHomeView(home);
		return this;
	}

	@Override
	public CActionBar setLogo(Drawable drawable) {
		return this;
	}

	@Override
	public CActionBar setBackDrawable(int resId) {
		return this;
	}

	@Override
	public CActionBar setBackDrawable(Drawable drawable) {
		return this;
	}

	@Override
	public CActionBar setTitle(int resId) {
		CharSequence title = mContext.getText(resId);
		setTitle(title);
		return this;
	}

	@Override
	public CActionBar setTitle(CharSequence title) {
		mActionBarView.setTitle(title);
		return this;
	}

	@Override
	public CActionBar setSubTitle(int resId) {
		CharSequence title = mContext.getText(resId);
		setSubTitle(title);
		return this;
	}

	@Override
	public CActionBar setSubTitle(CharSequence subTitle) {
		mActionBarView.setSubTitle(subTitle);
		return this;
	}
	

	@Override
	public void show() {
		if(isShowing()) {
			return;
		}
		mShowing = true;
		mActionBarView.setVisibility(View.VISIBLE);
	}

	@Override
	public void hide() {
		if(!isShowing()) {
			return;
		}
		mShowing = false;
		mActionBarView.setVisibility(View.GONE);
	}

	@Override
	public boolean isShowing() {
		return mShowing;
	}

	@Override
	public CActionBar setBackground(int resId) {
		mActionBarView.setBackgroundResource(resId);
		return this;
	}

	@Override
	public CActionBar setBackground(Drawable drawable) {
		mActionBarView.setBackgroundDrawable(drawable);
		return this;
	}

	@Override
	public CActionBar setDisplayShowBackButtonEnable(boolean showBackButton) {
		mActionBarView.setDisplayShowBackButtonEnable(showBackButton);
		return this;
	}

	@Override
	public CActionBar setDisplayShowLogoEnable(boolean showHome) {
		mActionBarView.setDisplayShowHomeEnable(showHome);
		return this;
	}

	@Override
	public CActionBar setDisplayShowCustomEnable(boolean showCustom) {
		mActionBarView.setDisplayShowCustomEnable(showCustom);
		return this;
	}

	@Override
	public CActionBar setDisplayShowTitleEnable(boolean showTitle) {
		mActionBarView.setDisplayShowTitleEnable(showTitle);
		return this;
	}

	@Override
	public Tab buildTab() {
		return new TabImpl();
	}
	
	private class TabImpl extends Tab {
		
		private int mPosition = -1;
		private CharSequence mTitle = null;
		private Drawable mIcon = null;
		private View mCustom = null;
		private Object mTag = null;
		private CharSequence mDescription = null;
		private TabListener mListener = null;

		@Override
		public int getPosition() {
			return mPosition;
		}
		
		public Tab setPosition(int position) {
			mPosition = position;
			return this;
		}

		@Override
		public Tab setTitle(int resId) {
			CharSequence title = mContext.getText(resId);
			return setTitle(title);
		}

		@Override
		public Tab setTitle(CharSequence title) {
			mTitle = title;
			if (mPosition > -1) {
				mActionBarView.updateTab(mPosition);
			}
			return this;
		}

		@Override
		public CharSequence getTitle() {
			return mTitle;
		}

		@Override
		public Tab setIcon(int resId) {
			Drawable icon = mContext.getResources().getDrawable(resId);
			return setIcon(icon);
		}

		@Override
		public Tab setIcon(Drawable icon) {
			mIcon = icon;
			if (mPosition > -1) {
				mActionBarView.updateTab(mPosition);
			}
			return this;
		}

		@Override
		public Drawable getIcon() {
			return mIcon;
		}

		@Override
		public void select() {
			if(mPosition > -1) {
				mActionBarView.setTabSelected(mPosition);
				if(mListener != null) {
					mListener.onTabSelected(CActionBarImpl.this, this);
				}
			}
		}

		@Override
		public boolean isSelected() {
			return this == getSelectedTab();
		}

		@Override
		public Tab setTag(Object tag) {
			mTag = tag;
			return this;
		}

		@Override
		public Object getTag() {
			return mTag;
		}

		@Override
		public void setTabListener(TabListener listener) {
			mListener = listener;
		}

		@Override
		public Tab setCustom(View custom) {
			mCustom = custom;
			if (mPosition > -1) {
				mActionBarView.updateTab(mPosition);
			}
			return this;
		}

		@Override
		public View getCustom() {
			return mCustom;
		}

		@Override
		public Tab setContetnDescription(CharSequence description) {
			mDescription = description;
			if (mPosition > -1) {
				mActionBarView.updateTab(mPosition);
			}
			return this;
		}

		@Override
		public CharSequence getContentDescription() {
			return mDescription;
		}
		
	}

	@Override
	public Tab getSelectedTab() {
		return mSelectedTab;
	}

	@Override
	public int getTabCount() {
		return mTabs.size();
	}

	@Override
	public Tab getTabAt(int index) {
		return mTabs.get(index);
	}

	@Override
	public CActionBar addTab(Tab tab) {
		addTab(tab, -1, mTabs.isEmpty());
		return this;
	}

	@Override
	public CActionBar addTab(Tab tab, int position) {
		addTab(tab, position, mTabs.isEmpty());
		return this;
	}

	@Override
	public CActionBar addTab(Tab tab, boolean selected) {
		addTab(tab, -1, selected);
		return this;
	}

	@Override
	public CActionBar addTab(Tab tab, int position, boolean selected) {
		mActionBarView.addTab(tab, position, selected);
		configureTab(tab, position);
		if (selected) {
            selectTab(tab);
        }
		return this;
	}

	@Override
	public void selectTab(Tab tab) {
		if(mActionBarView.getTabCount() > 0) {
			mSelectedTab = (TabImpl) tab;
			tab.select();
		}
	}
	
	private void configureTab(Tab tab, int position) {
		if(position < 0) {
			position = mTabs.size();
		}
        final TabImpl tabi = (TabImpl) tab;

        tabi.setPosition(position);
        mTabs.add(position, tabi);

        final int count = mTabs.size();
        for (int i = position + 1; i < count; i++) {
            mTabs.get(i).setPosition(i);
        }
    }

	@Override
	public CActionBar removeTabAt(int index) {
		return this;
	}

	@Override
	public CActionBar removeTab(Tab tab) {
		return removeTabAt(tab.getPosition());
	}

	@Override
	public CActionBar removeAllTabs() {
        cleanupTabs();
        return this;
    }

    private void cleanupTabs() {
        mTabs.clear();
        mActionBarView.removeAllTabs();
    }

}
