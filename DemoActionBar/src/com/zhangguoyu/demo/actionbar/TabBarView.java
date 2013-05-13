package com.zhangguoyu.demo.actionbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangguoyu.demo.actionbar.CActionBar.Tab;

public class TabBarView extends LinearLayout {

	private TabClickListener mTabClickListener;
	private int mCurrentSelected = -1;

	public TabBarView(Context context) {
		super(context);
	}
	
	public TabBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void addTab(Tab tab, boolean selected) {
		addTab(tab, -1, selected);
	}
	
	public void addTab(Tab tab, int position, boolean selected) {
        final TabView tabView = createTabView(tab);
        addView(tabView, position, new LayoutParams(
                0, LayoutParams.MATCH_PARENT, 1));
        if (selected) {
            tabView.setSelected(true);
        }
    }
	
	public void updateTab(int position) {
        ((TabView)getChildAt(position)).update();
    }
	
	public void removeTabAt(int position) {
        removeViewAt(position);
    }

    public void removeAllTabs() {
        removeAllViews();
    }
	
	public void setTabSelected(int position) {
        mCurrentSelected = position;
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final boolean isSelected = i == position;
            child.setSelected(isSelected);
        }
    }
	
	public int getCurrentSelected() {
		return mCurrentSelected;
	}
	
	public int getTabCount() {
		return getChildCount();
	}
	
	private TabView createTabView(Tab tab) {
        final TabView tabView = new TabView(getContext());
        tabView.init(this, tab);
        tabView.setFocusable(true);
        if (mTabClickListener == null) {
            mTabClickListener = new TabClickListener();
        }
        tabView.setOnClickListener(mTabClickListener);
        return tabView;
    }
	
	private class TabClickListener implements OnClickListener {
        public void onClick(View view) {
            TabView tabView = (TabView) view;
            tabView.getTab().select();
            final int tabCount = getChildCount();
            for (int i = 0; i < tabCount; i++) {
                final View child = getChildAt(i);
                child.setSelected(child == view);
            }
        }
    }

	private class TabView extends LinearLayout {
		
		private Tab mTab = null;
		private TextView mTxvTitle = null;
		private ImageView mImgIcon = null;
		private View mCustomView = null;
		private TabBarView mParent = null;

		public TabView(Context context) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
		}
		
		public void init(TabBarView parent, Tab tab) {
			mParent = parent;
			mTab = tab;
			update();
		}
		
		public void update() {
			final Tab tab = mTab;
			final View custom = tab.getCustom();
			if (custom != null) {
				final ViewParent customParent = custom.getParent();
                if (customParent != this) {
                    if (customParent != null) {
                    	((ViewGroup) customParent).removeView(custom);
                    }
                    addView(custom);
                }
                mCustomView = custom;
                if (mTxvTitle != null) {
                	mTxvTitle.setVisibility(GONE);
                }
                if (mImgIcon != null) {
                	mImgIcon.setVisibility(GONE);
                	mImgIcon.setImageDrawable(null);
                }
			} else {
				if(mCustomView != null) {
					mParent.removeView(mCustomView);
					mCustomView = null;
				}
				
				final Drawable icon = tab.getIcon();
                final CharSequence text = tab.getTitle();

                if (icon != null) {
                    if (mImgIcon == null) {
                        ImageView iconView = new ImageView(getContext());
                        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER;
                        iconView.setLayoutParams(lp);
                        addView(iconView, 0);
                        mImgIcon = iconView;
                    }
                    mImgIcon.setImageDrawable(icon);
                    mImgIcon.setVisibility(VISIBLE);
                } else if (mImgIcon != null) {
                	mImgIcon.setVisibility(GONE);
                	mImgIcon.setImageDrawable(null);
                }
                
                if (mImgIcon != null) {
                	mImgIcon.setContentDescription(tab.getContentDescription());
                }
                
                if (text != null) {
                    if (mTxvTitle == null) {
                        TextView textView = new TextView(getContext());
                        textView.setEllipsize(TruncateAt.END);
                        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER;
                        textView.setLayoutParams(lp);
                        textView.setText(text);
                        addView(textView);
                        mTxvTitle = textView;
                    }
                    mTxvTitle.setVisibility(VISIBLE);
                } else if (mTxvTitle != null) {
                	mTxvTitle.setVisibility(GONE);
                	mTxvTitle.setText(null);
                }
			}
		}
		
		Tab getTab() {
			return mTab;
		}
		
	}

}
