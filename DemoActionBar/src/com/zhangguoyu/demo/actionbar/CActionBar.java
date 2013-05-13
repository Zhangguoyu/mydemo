package com.zhangguoyu.demo.actionbar;

import android.graphics.drawable.Drawable;
import android.view.View;

public abstract class CActionBar {
	
	/**
	 * ����Logo��ͼ
	 * 
	 * @param view
	 * @return
	 */
	public abstract CActionBar setLogoView(View view);
	
	/**
	 * ���÷�����ͼ
	 * @param view
	 * @return
	 */
	public abstract CActionBar setBackButton(View view);
	
	/**
	 * ���ñ�����ͼ
	 * @param view
	 * @return
	 */
	public abstract CActionBar setTitleView(View view);
	
	/**
	 * ����ѡ��˵���ͼ
	 * @param view
	 * @return
	 */
	public abstract CActionBar setOptionsView(View view);
	
	/**
	 * �����Զ�����ͼ
	 * @param view
	 * @return
	 */
	public abstract CActionBar setCustomView(View view);
	
	/**
	 * ����logo
	 * @param resId
	 * @return
	 */
	public abstract CActionBar setLogo(int resId);
	
	/**
	 * ����logo
	 * @param drawable
	 * @return
	 */
	public abstract CActionBar setLogo(Drawable drawable);
	
	/**
	 * ���÷��ذ�ťͼƬ
	 * @param resId
	 * @return
	 */
	public abstract CActionBar setBackDrawable(int resId);
	
	/**
	 * ���÷��ذ�ťͼƬ
	 * @param drawable
	 * @return
	 */
	public abstract CActionBar setBackDrawable(Drawable drawable);
	
	/**
	 * ���ñ���
	 * @param resId
	 * @return
	 */
	public abstract CActionBar setTitle(int resId);
	
	/**
	 * ���ñ���
	 * @param title
	 * @return
	 */
	public abstract CActionBar setTitle(CharSequence title);
	
	/**
	 * �����ӱ���
	 * @param resId
	 * @return
	 */
	public abstract CActionBar setSubTitle(int resId);
	
	/**
	 * �����ӱ���
	 * @param subTitle
	 * @return
	 */
	public abstract CActionBar setSubTitle(CharSequence subTitle);
	
	/**
	 * ��ʾActionBar
	 */
	public abstract void show();
	
	/**
	 * ����ActionBar
	 */
	public abstract void hide();
	
	/**
	 * @return true ��ʾActionBar������ʾ��false ��ʾActionBar��������
	 */
	public abstract boolean isShowing();
	
	/**
	 * ����ActionBar����
	 * @param resId ����ͼƬ��ԴId
	 * @return
	 */
	public abstract CActionBar setBackground(int resId);
	
	/**
	 * ����ActionBar����
	 * @param drawable
	 * @return
	 */
	public abstract CActionBar setBackground(Drawable drawable);
	
	/**
	 * �����Ƿ���ʾ���ذ�ť
	 * @param showBackButton
	 * @return
	 */
	public abstract CActionBar setDisplayShowBackButtonEnable(boolean showBackButton);
	
	/**
	 * �����Ƿ���ʾlogo
	 * @param showHome
	 * @return
	 */
	public abstract CActionBar setDisplayShowLogoEnable(boolean showHome);
	
	/**
	 * �����Ƿ���ʾ�Զ�����ͼ
	 * @param showCustom
	 * @return
	 */
	public abstract CActionBar setDisplayShowCustomEnable(boolean showCustom);
	
	/**
	 * �����Ƿ���ʾ����
	 * @param showTitle
	 * @return
	 */
	public abstract CActionBar setDisplayShowTitleEnable(boolean showTitle);
	
	/**
	 * �½�һ��Tab
	 * @return
	 */
	public abstract Tab buildTab();
	
	/**
	 * ��ȡ��ǰѡ�е�Tab
	 * @return
	 */
	public abstract Tab getSelectedTab();
	
	/**
	 * ��ȡActionBar��ǰTab������
	 * @return
	 */
	public abstract int getTabCount();
	
	/**
	 * ��ȡָ��λ�õ�Tab
	 * @param index
	 * @return
	 */
	public abstract Tab getTabAt(int index);
	
	/**
	 * ���Tab
	 * @param tab
	 */
	public abstract CActionBar addTab(Tab tab);
	
	/**
	 * ���Tab��һ��ָ����λ��
	 * @param tab
	 * @param position
	 */
	public abstract CActionBar addTab(Tab tab, int position);
	
	/**
	 * ���Tab
	 * @param tab
	 * @param selected
	 */
	public abstract CActionBar addTab(Tab tab, boolean selected);
	
	/**
	 * ���Tab
	 * @param tab
	 * @param position
	 * @param selected
	 */
	public abstract CActionBar addTab(Tab tab, int position, boolean selected);
	
	/**
	 * ѡ��Tab
	 * @param tab
	 */
	public abstract void selectTab(Tab tab);
	
	/**
	 * �Ƴ�ָ��λ�õ�Tab
	 * @param index
	 * @return
	 */
	public abstract CActionBar removeTabAt(int index);
	
	/**
	 * �Ƴ�Tab
	 * @param tab
	 * @return
	 */
	public abstract CActionBar removeTab(Tab tab);
	
	/**
	 * �������Tab
	 * @return
	 */
	public abstract CActionBar removeAllTabs();
	
	public static abstract class Tab {
		
		/**
		 * ��ȡTab������Tab���е�λ��
		 * @return
		 */
		public abstract int getPosition();
		
		/**
		 * ����Tab�ı���
		 * @param resId
		 * @return
		 */
		public abstract Tab setTitle(int resId);
		
		/**
		 * ����Tab�ı���
		 * @param title
		 * @return
		 */
		public abstract Tab setTitle(CharSequence title);
		
		/**
		 * ��ȡTab�ı���
		 * @return
		 */
		public abstract CharSequence getTitle();
		
		/**
		 * ����Tab��ͼ��
		 * @param resId
		 * @return
		 */
		public abstract Tab setIcon(int resId);
		
		/**
		 * ����Tab��ͼ��
		 * @param icon
		 * @return
		 */
		public abstract Tab setIcon(Drawable icon);
		
		/**
		 * ��ȡTab��ͼ��
		 * @return
		 */
		public abstract Drawable getIcon();
		
		/**
		 * ѡ�е�ǰTab
		 * ����Ѿ�ͨ��setTabListener����������Tabѡ���¼�������
		 * ��ô���ø÷����ᴥ��TabListener.onTabSelected()�����Ļص�
		 */
		public abstract void select();
		
		/**
		 * ���ص�ǰTab�Ƿ�ѡ��
		 * @return
		 */
		public abstract boolean isSelected();
		
		/**
		 * ����Tab���
		 * @param tag
		 * @return
		 */
		public abstract Tab setTag(Object tag);
		
		/**
		 * ��ȡTab���
		 * @return
		 */
		public abstract Object getTag();
		
		/**
		 * �����Զ�����ͼ
		 * @param custom
		 * @return
		 */
		public abstract Tab setCustom(View custom);
		
		/**
		 * ��ȡ�Զ�����ͼ
		 * @return
		 */
		public abstract View getCustom();
		
		/**
		 * ����Tab����������Tab��ѡ��ʱ��TabListener��onTabSelected()�ᱻ�ص�
		 * @param listener
		 */
		public abstract void setTabListener(TabListener listener);
		
		/**
		 * ����Tab����������
		 * @return
		 */
		public abstract Tab setContetnDescription(CharSequence description);
		
		/**
		 * ��ȡTab����������
		 * @return
		 */
		public abstract CharSequence getContentDescription();
		
	}
	
	public interface TabListener {
		
		/**
		 * ��������Tab.select()�������ú�ִ��
		 * @param actionBar
		 * @param selectedTab
		 */
		public void onTabSelected(CActionBar actionBar, Tab selectedTab);
		
	}

}
