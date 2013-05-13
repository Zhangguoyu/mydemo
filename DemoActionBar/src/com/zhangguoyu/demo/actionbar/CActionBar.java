package com.zhangguoyu.demo.actionbar;

import android.graphics.drawable.Drawable;
import android.view.View;

public abstract class CActionBar {
	
	/**
	 * 设置Logo视图
	 * 
	 * @param view
	 * @return
	 */
	public abstract CActionBar setLogoView(View view);
	
	/**
	 * 设置返回视图
	 * @param view
	 * @return
	 */
	public abstract CActionBar setBackButton(View view);
	
	/**
	 * 设置标题视图
	 * @param view
	 * @return
	 */
	public abstract CActionBar setTitleView(View view);
	
	/**
	 * 设置选项菜单视图
	 * @param view
	 * @return
	 */
	public abstract CActionBar setOptionsView(View view);
	
	/**
	 * 设置自定义视图
	 * @param view
	 * @return
	 */
	public abstract CActionBar setCustomView(View view);
	
	/**
	 * 设置logo
	 * @param resId
	 * @return
	 */
	public abstract CActionBar setLogo(int resId);
	
	/**
	 * 设置logo
	 * @param drawable
	 * @return
	 */
	public abstract CActionBar setLogo(Drawable drawable);
	
	/**
	 * 设置返回按钮图片
	 * @param resId
	 * @return
	 */
	public abstract CActionBar setBackDrawable(int resId);
	
	/**
	 * 设置返回按钮图片
	 * @param drawable
	 * @return
	 */
	public abstract CActionBar setBackDrawable(Drawable drawable);
	
	/**
	 * 设置标题
	 * @param resId
	 * @return
	 */
	public abstract CActionBar setTitle(int resId);
	
	/**
	 * 设置标题
	 * @param title
	 * @return
	 */
	public abstract CActionBar setTitle(CharSequence title);
	
	/**
	 * 设置子标题
	 * @param resId
	 * @return
	 */
	public abstract CActionBar setSubTitle(int resId);
	
	/**
	 * 设置子标题
	 * @param subTitle
	 * @return
	 */
	public abstract CActionBar setSubTitle(CharSequence subTitle);
	
	/**
	 * 显示ActionBar
	 */
	public abstract void show();
	
	/**
	 * 隐藏ActionBar
	 */
	public abstract void hide();
	
	/**
	 * @return true 表示ActionBar正在显示，false 表示ActionBar正在隐藏
	 */
	public abstract boolean isShowing();
	
	/**
	 * 设置ActionBar背景
	 * @param resId 背景图片资源Id
	 * @return
	 */
	public abstract CActionBar setBackground(int resId);
	
	/**
	 * 设置ActionBar背景
	 * @param drawable
	 * @return
	 */
	public abstract CActionBar setBackground(Drawable drawable);
	
	/**
	 * 设置是否显示返回按钮
	 * @param showBackButton
	 * @return
	 */
	public abstract CActionBar setDisplayShowBackButtonEnable(boolean showBackButton);
	
	/**
	 * 设置是否显示logo
	 * @param showHome
	 * @return
	 */
	public abstract CActionBar setDisplayShowLogoEnable(boolean showHome);
	
	/**
	 * 设置是否显示自定义视图
	 * @param showCustom
	 * @return
	 */
	public abstract CActionBar setDisplayShowCustomEnable(boolean showCustom);
	
	/**
	 * 设置是否显示标题
	 * @param showTitle
	 * @return
	 */
	public abstract CActionBar setDisplayShowTitleEnable(boolean showTitle);
	
	/**
	 * 新建一个Tab
	 * @return
	 */
	public abstract Tab buildTab();
	
	/**
	 * 获取当前选中的Tab
	 * @return
	 */
	public abstract Tab getSelectedTab();
	
	/**
	 * 获取ActionBar当前Tab的数量
	 * @return
	 */
	public abstract int getTabCount();
	
	/**
	 * 获取指定位置的Tab
	 * @param index
	 * @return
	 */
	public abstract Tab getTabAt(int index);
	
	/**
	 * 添加Tab
	 * @param tab
	 */
	public abstract CActionBar addTab(Tab tab);
	
	/**
	 * 添加Tab到一个指定的位置
	 * @param tab
	 * @param position
	 */
	public abstract CActionBar addTab(Tab tab, int position);
	
	/**
	 * 添加Tab
	 * @param tab
	 * @param selected
	 */
	public abstract CActionBar addTab(Tab tab, boolean selected);
	
	/**
	 * 添加Tab
	 * @param tab
	 * @param position
	 * @param selected
	 */
	public abstract CActionBar addTab(Tab tab, int position, boolean selected);
	
	/**
	 * 选择Tab
	 * @param tab
	 */
	public abstract void selectTab(Tab tab);
	
	/**
	 * 移除指定位置的Tab
	 * @param index
	 * @return
	 */
	public abstract CActionBar removeTabAt(int index);
	
	/**
	 * 移除Tab
	 * @param tab
	 * @return
	 */
	public abstract CActionBar removeTab(Tab tab);
	
	/**
	 * 清楚所有Tab
	 * @return
	 */
	public abstract CActionBar removeAllTabs();
	
	public static abstract class Tab {
		
		/**
		 * 获取Tab在整个Tab栏中的位置
		 * @return
		 */
		public abstract int getPosition();
		
		/**
		 * 设置Tab的标题
		 * @param resId
		 * @return
		 */
		public abstract Tab setTitle(int resId);
		
		/**
		 * 设置Tab的标题
		 * @param title
		 * @return
		 */
		public abstract Tab setTitle(CharSequence title);
		
		/**
		 * 获取Tab的标题
		 * @return
		 */
		public abstract CharSequence getTitle();
		
		/**
		 * 设置Tab的图标
		 * @param resId
		 * @return
		 */
		public abstract Tab setIcon(int resId);
		
		/**
		 * 设置Tab的图标
		 * @param icon
		 * @return
		 */
		public abstract Tab setIcon(Drawable icon);
		
		/**
		 * 获取Tab的图标
		 * @return
		 */
		public abstract Drawable getIcon();
		
		/**
		 * 选中当前Tab
		 * 如果已经通过setTabListener方法设置了Tab选中事件监听，
		 * 那么调用该方法会触发TabListener.onTabSelected()方法的回调
		 */
		public abstract void select();
		
		/**
		 * 返回当前Tab是否选中
		 * @return
		 */
		public abstract boolean isSelected();
		
		/**
		 * 设置Tab标记
		 * @param tag
		 * @return
		 */
		public abstract Tab setTag(Object tag);
		
		/**
		 * 获取Tab标记
		 * @return
		 */
		public abstract Object getTag();
		
		/**
		 * 设置自定义视图
		 * @param custom
		 * @return
		 */
		public abstract Tab setCustom(View custom);
		
		/**
		 * 获取自定义视图
		 * @return
		 */
		public abstract View getCustom();
		
		/**
		 * 设置Tab监听器，当Tab被选中时，TabListener的onTabSelected()会被回调
		 * @param listener
		 */
		public abstract void setTabListener(TabListener listener);
		
		/**
		 * 设置Tab的内容描述
		 * @return
		 */
		public abstract Tab setContetnDescription(CharSequence description);
		
		/**
		 * 获取Tab的内容描述
		 * @return
		 */
		public abstract CharSequence getContentDescription();
		
	}
	
	public interface TabListener {
		
		/**
		 * 方法会在Tab.select()方法调用后执行
		 * @param actionBar
		 * @param selectedTab
		 */
		public void onTabSelected(CActionBar actionBar, Tab selectedTab);
		
	}

}
