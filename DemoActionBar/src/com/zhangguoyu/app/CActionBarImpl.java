package com.zhangguoyu.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.zhangguoyu.demo.actionbar.R;
import com.zhangguoyu.widget.CMenu;
import com.zhangguoyu.widget.CMenuItem;
import com.zhangguoyu.widget.CSubMenu;

import java.util.ArrayList;
import java.util.List;

public class CActionBarImpl extends CActionBar {
	
	private static final String LOG_TAG = "CActionBarImpl";
	
	private Context mContext = null;
	private CActivity mActivity = null;
	private CTitleActionBarView mTitleActionBarView = null;
    private CMenuBarView mNavigationActionBarView = null;
	private boolean mShowing;
	private TabImpl mSelectedTab = null;
	private List<TabImpl> mTabs = null;
	
	CActionBarImpl(CActivity activity) {
		mActivity = activity;
		Window window = activity.getWindow();
		View decor = window.getDecorView();
		mContext = decor.getContext();
		mTitleActionBarView = (CTitleActionBarView) decor.findViewById(R.id.actionbar_id);
        mTitleActionBarView.setOnOptionsBarSelectedListener(mOptionsSelectedListener);
		mNavigationActionBarView = (CMenuBarView) decor.findViewById(R.id.navbar_id);
        mNavigationActionBarView.setOnMenuItemSelectedListener(mNavigationSelectedListener);
		mTabs = new ArrayList<TabImpl>();
	}

	@Override
	public CActionBar setLogoView(View view) {
		mTitleActionBarView.setHomeView(view);
		return this;
	}

	@Override
	public CActionBar setBackButton(View view) {
		mTitleActionBarView.setBackButton(view);
		return this;
	}

	@Override
	public CActionBar setTitleView(View view) {
		mTitleActionBarView.setTitleView(view);
		return this;
	}

	@Override
	public CActionBar setOptionsView(View view) {
		mTitleActionBarView.setMenus(view);
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
		mTitleActionBarView.setHomeView(home);
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
		mTitleActionBarView.setTitle(title);
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
		mTitleActionBarView.setSubTitle(subTitle);
		return this;
	}
	

	@Override
	public void show() {
		if(isShowing()) {
			return;
		}
		mShowing = true;
		mTitleActionBarView.setVisibility(View.VISIBLE);
	}

	@Override
	public void hide() {
		if(!isShowing()) {
			return;
		}
		mShowing = false;
		mTitleActionBarView.setVisibility(View.GONE);
	}

	@Override
	public boolean isShowing() {
		return mShowing;
	}

	@Override
	public CActionBar setBackground(int resId) {
		mTitleActionBarView.setBackgroundResource(resId);
		return this;
	}

	@Override
	public CActionBar setBackground(Drawable drawable) {
		mTitleActionBarView.setBackgroundDrawable(drawable);
		return this;
	}

	@Override
	public CActionBar setDisplayShowBackButtonEnable(boolean showBackButton) {
		mTitleActionBarView.setDisplayShowBackButtonEnable(showBackButton);
		return this;
	}

    @Override
    public CActionBar setDisplayShowBackButtonEnableWithAnimation(
            boolean showBackButton, Animation anim) {
        mTitleActionBarView.setDisplayShowBackButtonEnable(showBackButton, anim);
        return this;
    }

    @Override
    public CActionBar setDisplayShowBackButtonEnableWithAnimation(
            boolean showBackButton, int anim) {
        mTitleActionBarView.setDisplayShowBackButtonEnable(showBackButton,
                AnimationUtils.loadAnimation(mContext, anim));
        return null;
    }

    @Override
	public CActionBar setDisplayShowLogoEnable(boolean showHome) {
		mTitleActionBarView.setDisplayShowHomeEnable(showHome);
		return this;
	}

    @Override
    public CActionBar setDisplayShowLogoEnableWithAnimation(
            boolean showLogo, Animation anim) {
        mTitleActionBarView.setDisplayShowHomeEnable(showLogo, anim);
        return this;
    }

    @Override
    public CActionBar setDisplayShowLogoEnableWithAnimation(
            boolean showLogo, int anim) {
        mTitleActionBarView.setDisplayShowHomeEnable(showLogo,
                AnimationUtils.loadAnimation(mContext, anim));
        return this;
    }

    @Override
	public CActionBar setDisplayShowCustomEnable(boolean showCustom) {
		mTitleActionBarView.setDisplayShowCustomEnable(showCustom);
		return this;
	}

    @Override
    public CActionBar setDisplayShowCustomEnableWithAnimation(
            boolean showCustom, Animation anim) {
        mTitleActionBarView.setDisplayShowCustomEnable(showCustom, anim);
        return this;
    }

    @Override
    public CActionBar setDisplayShowCustomEnableWithAnimation(
            boolean showCustom, int anim) {
        mTitleActionBarView.setDisplayShowCustomEnable(showCustom,
                AnimationUtils.loadAnimation(mContext, anim));
        return this;
    }

    @Override
	public CActionBar setDisplayShowTitleEnable(boolean showTitle) {
		mTitleActionBarView.setDisplayShowTitleEnable(showTitle);
		return this;
	}

    @Override
    public CActionBar setDisplayShowTitleEnableWithAnimation(
            boolean showTitle, Animation anim) {
        mTitleActionBarView.setDisplayShowTitleEnable(showTitle, anim);
        return this;
    }

    @Override
    public CActionBar setDisplayShowTitleEnableWithAnimation(
            boolean showTitle, int anim) {
        mTitleActionBarView.setDisplayShowTitleEnable(showTitle,
                AnimationUtils.loadAnimation(mContext, anim));
        return this;
    }

    @Override
	public CTab buildTab() {
		return new TabImpl();
	}
	
	private class TabImpl extends CTab {
		
		private int mPosition = -1;
		private CharSequence mTitle = null;
		private Drawable mIcon = null;
		private View mCustom = null;
		private Object mTag = null;
		private CharSequence mDescription = null;
		private OnTabSelectedListener mListener = null;

		@Override
		public int getPosition() {
			return mPosition;
		}
		
		public CTab setPosition(int position) {
			mPosition = position;
			return this;
		}

		@Override
		public CTab setTitle(int resId) {
			CharSequence title = mContext.getText(resId);
			return setTitle(title);
		}

		@Override
		public CTab setTitle(CharSequence title) {
			mTitle = title;
			if (mPosition > -1) {
				mTitleActionBarView.updateTab(mPosition);
			}
			return this;
		}

		@Override
		public CharSequence getTitle() {
			return mTitle;
		}

		@Override
		public CTab setIcon(int resId) {
			Drawable icon = mContext.getResources().getDrawable(resId);
			return setIcon(icon);
		}

		@Override
		public CTab setIcon(Drawable icon) {
			mIcon = icon;
			if (mPosition > -1) {
				mTitleActionBarView.updateTab(mPosition);
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
				mTitleActionBarView.setTabSelected(mPosition);
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
		public CTab setTag(Object tag) {
			mTag = tag;
			return this;
		}

		@Override
		public Object getTag() {
			return mTag;
		}

		@Override
		public void setTabListener(OnTabSelectedListener listener) {
			mListener = listener;
		}

		@Override
		public CTab setCustom(View custom) {
			mCustom = custom;
			if (mPosition > -1) {
				mTitleActionBarView.updateTab(mPosition);
			}
			return this;
		}

		@Override
		public View getCustom() {
			return mCustom;
		}

		@Override
		public CTab setContetnDescription(CharSequence description) {
			mDescription = description;
			if (mPosition > -1) {
				mTitleActionBarView.updateTab(mPosition);
			}
			return this;
		}

		@Override
		public CharSequence getContentDescription() {
			return mDescription;
		}
		
	}

	@Override
	public CTab getSelectedTab() {
		return mSelectedTab;
	}

	@Override
	public int getTabCount() {
		return mTabs.size();
	}

	@Override
	public CTab getTabAt(int index) {
		return mTabs.get(index);
	}

	@Override
	public CActionBar addTab(CTab tab) {
		addTab(tab, -1, mTabs.isEmpty());
		return this;
	}

	@Override
	public CActionBar addTab(CTab tab, int position) {
		addTab(tab, position, mTabs.isEmpty());
		return this;
	}

	@Override
	public CActionBar addTab(CTab tab, boolean selected) {
		addTab(tab, -1, selected);
		return this;
	}

	@Override
	public CActionBar addTab(CTab tab, int position, boolean selected) {
		mTitleActionBarView.addTab(tab, position, selected);
		configureTab(tab, position);
		if (selected) {
            selectTab(tab);
        }
		return this;
	}

	@Override
	public void selectTab(CTab tab) {
		if(mTitleActionBarView.getTabCount() > 0) {
			mSelectedTab = (TabImpl) tab;
			tab.select();
		}
	}
	
	private void configureTab(CTab tab, int position) {
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
	public CActionBar removeTab(CTab tab) {
		return removeTabAt(tab.getPosition());
	}

	@Override
	public CActionBar removeAllTabs() {
        cleanupTabs();
        return this;
    }

    private void cleanupTabs() {
        mTabs.clear();
        mTitleActionBarView.removeAllTabs();
    }
    
	static class CMenuImpl implements CMenu {
		
		private Context mContext;
		private List<CMenuItemImpl> mItems = null;
		private Resources mRes = null;
		
		CMenuImpl(Context context) {
			mContext = context;
			mRes = context.getResources();
		}
		
		Context getContext() {
			return mContext;
		}

		@Override
		public CMenuItem add(CharSequence title) {
			return addInternal(0, 0, title, null, false);
		}

		@Override
		public CMenuItem add(int titleResId) {
			return addInternal(0, 0, mRes.getString(titleResId), null, false);
		}

        @Override
        public CMenuItem addIconItem(int iconResId) {
            return addInternal(0, 0, null, mRes.getDrawable(iconResId), false);
        }

        @Override
        public CMenuItem addIconItem(Drawable icon) {
            return addInternal(0, 0, null, icon, false);
        }

        @Override
		public CMenuItem add(CharSequence title, Drawable icon) {
			return addInternal(0, 0, title, icon, false);
		}

		@Override
		public CMenuItem add(int titleResId, int iconResId) {
			return addInternal(0, 0, mRes.getString(titleResId), 
					mRes.getDrawable(iconResId), false);
		}

		@Override
		public CMenuItem add(int groupId, int itemId, CharSequence title) {
			return addInternal(groupId, itemId, title, null, false);
		}

		@Override
		public CMenuItem add(int groupId, int itemId, int titleResId) {
			return addInternal(groupId, itemId, mRes.getString(titleResId), null, false);
		}

		@Override
		public CMenuItem add(int groupId, int itemId, CharSequence title,
				Drawable icon) {
			return addInternal(groupId, itemId, title, icon, false);
		}

		@Override
		public CMenuItem add(int groupId, int itemId, int titleResId, int iconResId) {
			return addInternal(groupId, itemId, mRes.getString(titleResId), 
					mRes.getDrawable(iconResId), false);
		}
		
		private CMenuItemImpl addInternal(int groupId, int itemId, CharSequence title, 
				Drawable icon, boolean isMore) {
			CMenuItemImpl impl = new CMenuItemImpl(mContext, 
					groupId, itemId, title, icon, isMore);
			
			if (mItems == null) {
				mItems = new ArrayList<CMenuItemImpl>();
			}
			mItems.add(impl);
			return impl;
		}

		@Override
		public CMenuItem findItem(int id) {
			if (mItems != null) {
				final int N = mItems.size();
				for (int i=0; i<N; i++) {
					final CMenuItem item = mItems.get(i);
					if (item.getItemId() == id) {
						return item;
					} else if (item.hasSubMenu()) {
						CSubMenu subMenu = item.getSubMenu();
						CMenuItem subItem = subMenu.findItem(id);
						if(subItem != null) {
							return subItem;
						}
					}
				}
			}
			return null;
		}

		@Override
		public CMenuItem getItemAt(int index) {
			if (mItems != null && index > -1 && index < mItems.size()) {
				return mItems.get(index);
			}
			return null;
		}

		@Override
		public int getItemCount() {
			if (mItems != null) {
				return mItems.size();
			}
			return 0;
		}

		@Override
		public CSubMenu addSubMenu(CharSequence title) {
			return addSubMenuInternal(0, 0, title, null);
		}

		@Override
		public CSubMenu addSubMenu(int titleResId) {
			return addSubMenuInternal(0, 0, mRes.getString(titleResId), null);
		}

		@Override
		public CSubMenu addSubMenu(CharSequence title, Drawable icon) {
			return addSubMenuInternal(0, 0, title, icon);
		}

		@Override
		public CSubMenu addSubMenu(int titleResId, int iconResId) {
			return addSubMenuInternal(0, 0, mRes.getString(titleResId), 
					mRes.getDrawable(iconResId));
		}

		@Override
		public CSubMenu addSubMenu(int groupId, int itemId, CharSequence title) {
			return addSubMenuInternal(groupId, itemId, title, null);
		}

		@Override
		public CSubMenu addSubMenu(int groupId, int itemId, int titleResId) {
			return addSubMenuInternal(groupId, itemId, mRes.getString(titleResId), null);
		}

		@Override
		public CSubMenu addSubMenu(int groupId, int itemId, CharSequence title,
				Drawable icon) {
			return addSubMenuInternal(groupId, itemId, title, icon);
		}

		@Override
		public CSubMenu addSubMenu(int groupId, int itemId, int titleResId,
				int iconResId) {
			return addSubMenuInternal(groupId, itemId, mRes.getString(titleResId), 
					mRes.getDrawable(iconResId));
		}
		
		private CSubMenuImpl addSubMenuInternal(int groupId, int itemId, CharSequence title, 
				Drawable icon) {
			CMenuItemImpl item = addInternal(groupId, itemId, title, icon, false);
			CSubMenuImpl impl = new CSubMenuImpl(mContext, item);
			item.setSubMenu(impl);
			return impl;
		}

		@Override
		public CMenu removeItem(int itemId) {
			CMenuItem removeItem = null;
			if (mItems != null) {
				final int N = mItems.size();
				for (int i=0; i<N; i++) {
					final CMenuItem item = mItems.get(i);
					if (item.getItemId() == itemId) {
						removeItem = item;
						break;
					} 
				}
			}
			
			if (removeItem != null) {
				mItems.remove(removeItem);
			}
			return this;
		}

		@Override
		public CMenu removeGroup(int groupId) {
			List<CMenuItem> removeItems = new ArrayList<CMenuItem>();
			if (mItems != null) {
				final int N = mItems.size();
				for (int i=0; i<N; i++) {
					final CMenuItem item = mItems.get(i);
					if (item.getGroupId() == groupId) {
						removeItems.add(item);
					} 
				}
			}
			
			if (!removeItems.isEmpty()) {
				mItems.remove(removeItems);
			}
			
			return this;
		}

		@Override
		public CMenu clear() {
			mItems.clear();
			mItems = null;
			return this;
		}

		@Override
		public void show() {
			
		}

		@Override
		public void hide() {

		}
	}

    static class CSubMenuImpl extends CMenuImpl implements CSubMenu {
		
		private CMenuItemImpl mItemImpl = null;

		CSubMenuImpl(Context context, CMenuItemImpl item) {
			super(context);
			mItemImpl = item;
		}

		@Override
		public CMenuItem getMenuItem() {
			return mItemImpl;
		}

	}

    static class CMenuItemImpl implements CMenuItem {
		
		private Context mContext = null;
		private int mGroupId = 0;
		private int mItemId = 0;
		private int mIconResId = 0;
		private Drawable mIcon = null;
		private int mTitleResId = 0;
		private CharSequence mTitle = null;
		private int mCustomLayoutResId = 0;
		private View mCustomView = null;
		private Intent mIntent = null;
		private int mVisibility = View.VISIBLE;
		private boolean mIsEnabled = true;
		private boolean mIsSelected = false;
		private boolean mIsMore = false;
		private CSubMenu mSubMenu = null;
		private LayoutInflater mInflater = null;
		
		public CMenuItemImpl(Context context, int groupId, int itemId, 
				CharSequence title, Drawable icon, boolean isMore) {
			mContext = context;
			mIsMore = isMore;
			mGroupId = groupId;
			mItemId = itemId;
			mTitle = title;
			mIcon = icon;
			
			mInflater = (LayoutInflater) context.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
		}
		
		public void setSubMenu(CSubMenu subMenu) {
			mSubMenu = subMenu;
		}

		@Override
		public CMenuItem setIcon(Drawable icon) {
			mIcon = icon;
			mIconResId = 0;
			return this;
		}

		@Override
		public CMenuItem setIcon(int iconResId) {
			mIconResId = iconResId;
			mIcon = null;
			return this;
		}

		@Override
		public CMenuItem setTitle(CharSequence title) {
			mTitle = title;
			mTitleResId = 0;
			return this;
		}

		@Override
		public CMenuItem setTitle(int titleResId) {
			mTitleResId = titleResId;
			mTitle = null;
			return this;
		}

		@Override
		public CMenuItem setCustomView(int customLayoutResId) {
			mCustomLayoutResId = customLayoutResId;
			return this;
		}

		@Override
		public CMenuItem setCustomView(View customView) {
			mCustomView = customView;
			return this;
		}

		@Override
		public CMenuItem setVisibility(int visibility) {
			mVisibility = visibility;
			return this;
		}

		@Override
		public CMenuItem setEnabled(boolean enabled) {
			mIsEnabled = enabled;
			return this;
		}

		@Override
		public CMenuItem setSelected(boolean selected) {
			mIsSelected = selected;
			return this;
		}

		@Override
		public CMenuItem setIntent(Intent intent) {
			mIntent = intent;
			return this;
		}

		@Override
		public boolean hasSubMenu() {
			return mSubMenu != null && mSubMenu.getItemCount()>0;
		}

		@Override
		public CSubMenu getSubMenu() {
			return mSubMenu;
		}

		@Override
		public Drawable getIcon() {
			if (mIcon == null) {
				if (mIconResId > 0) {
					mIcon = mContext.getResources().getDrawable(mIconResId);
				}
			}
			return mIcon;
		}

		@Override
		public CharSequence getTitle() {
			if (mTitle == null) {
				if (mTitleResId > 0) {
					mTitle = mContext.getResources().getText(mTitleResId);
				}
			}
			return mTitle;
		}

		@Override
		public View getCustomView() {
			if (mCustomView == null) {
				if (mCustomLayoutResId > 0) {
					mCustomView = mInflater.inflate(mCustomLayoutResId, null);
				}
			}
			return mCustomView;
		}

		@Override
		public Intent getIntent() {
			return mIntent;
		}

		@Override
		public int getVisibility() {
			return mVisibility;
		}

		@Override
		public boolean isEnabled() {
			return mIsEnabled;
		}

		@Override
		public boolean isSelected() {
			return mIsSelected;
		}
		
		boolean isMore() {
			return mIsMore;
		}

		@Override
		public CMenuItem setItemId(int id) {
			mItemId = id;
			return this;
		}

		@Override
		public CMenuItem setGroupId(int groupId) {
			mGroupId = groupId;
			return this;
		}

		@Override
		public int getGroupId() {
			return mGroupId;
		}

		@Override
		public int getItemId() {
			return mItemId;
		}

	}

	@Override
	public CActionBar setNavigationBarBackground(Drawable drawable) {
		mNavigationActionBarView.setBackgroundDrawable(drawable);
		return this;
	}

	@Override
	public CActionBar setNavigationBarBackground(int resId) {
		mNavigationActionBarView.setBackgroundResource(resId);
		return this;
	}
	
	public CMenu newMenu() {
		return new CMenuImpl(mContext);
	}
	
	public void inflatedNavigationBarByMenu(CMenu menu) {
		mNavigationActionBarView.inflatedByMenu(menu);
	}

    public void inflatdOptionsBarByMenu(CMenu menu) {
        mTitleActionBarView.inflateOptionsBarByMenu(menu);
    }

    private final CMenuBarView.OnMenuItemSelectedListener mOptionsSelectedListener =
            new CMenuBarView.OnMenuItemSelectedListener() {

        @Override
        public void onMenuItemSelected(CMenuBarView view, CMenuItem menuItem) {
            mActivity.onOptionsMenuItemSelected(menuItem);
        }
    };

    private final CMenuBarView.OnMenuItemSelectedListener mNavigationSelectedListener =
            new CMenuBarView.OnMenuItemSelectedListener() {
        @Override
        public void onMenuItemSelected(CMenuBarView view, CMenuItem menuItem) {
            mActivity.onNavigationMenuItemSelected(menuItem);
        }
    };

}
