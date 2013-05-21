package com.zhangguoyu.app;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.*;
import android.widget.*;

import com.zhangguoyu.app.CActionBarImpl.CMenuItemImpl;
import com.zhangguoyu.demo.actionbar.R;
import com.zhangguoyu.widget.CMenu;
import com.zhangguoyu.widget.CMenuItem;


public class CMenuBarView extends CButtonBarView {
	
	public interface OnMenuItemSelectedListener {
		
		public void onMenuItemSelected(CMenuBarView view, CMenuItem menuItem);
		
	}

	private int mCurrentSelected = -1;

    private List<CMenuItemImpl> mItems = null;
	private List<CMenuItemImpl> mMoreItems = null;
	private List<CMenuItemImpl> mUsedItems = null;

    private MenuItemClickListener mMenuClickListener = null;
    private OnMenuItemSelectedListener mMenuItemSelectedListener = null;

    private CharSequence mMoreTitle = null;
    private Drawable mMoreIconDrawable = null;
    private int mDisplayCountLimits = 5;

    private CMenuItemImpl mCurrentSelectedItem = null;

    private CMenuItemImpl mMoreMenuItem = null;
	
	public CMenuBarView(Context context) {
		super(context);
        init(context, null, 0);
	}
	
	public CMenuBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
        init(context, attrs, 0);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public CMenuBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init(context, attrs, defStyle);
	}

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CMenuBarView, defStyle, 0);
            mMoreTitle = a.getText(R.styleable.CMenuBarView_moreTitle);
            mMoreIconDrawable = a.getDrawable(R.styleable.CMenuBarView_moreIcon);
            mDisplayCountLimits = a.getInteger(R.styleable.CMenuBarView_displayCountLimits,
                    getDefaultDisplayCountLimits());
            a.recycle();
        }
    }

    @Override
    protected void onChangeItemTextSize(float size) {

    }

    @Override
    protected void onChangeItemTextColor(ColorStateList color) {

    }

    @Override
    protected void onChangeItemTypeface(Typeface tf, int style) {

    }

    @Override
    protected void onChangeItemMargin(int left, int top, int right, int bottom) {

    }

    @Override
    protected void onChangeItemGravity(int gravity) {

    }

    @Override
    public void onChangeItemBackground(Drawable drawable) {

    }

    @Override
    protected void onChangeButtonBarStyle(int style) {

    }

    @Override
    public void onChangeItemPadding(int left, int top, int right, int bottom) {

    }

    public void setDisplayCountLimits(int limits) {
        if (mDisplayCountLimits == limits) {
            return;
        }

        mDisplayCountLimits = limits;
        if (mItems == null || mItems.isEmpty()) {
            return;
        }

        if (mUsedItems != null) {
            mUsedItems.clear();
        }
        if (mMoreItems != null) {
            mMoreItems.clear();
        }

        final int count = mItems.size();
        for (int i=0; i<count; i++) {
            addMenuItem(mItems.get(i));
        }
    }

    public int getDisplayCountLimits() {
        return mDisplayCountLimits;
    }

    protected int getDefaultDisplayCountLimits() {
        return mDisplayCountLimits;
    }

    public CMenuItem getCurrentSelectedItem() {
        return mCurrentSelectedItem;
    }

    protected void onCreateSubMenuPanel(CMenuItem moreItem) {
    }
	
	public void inflatedByMenu(CMenu menu) {
		final int itemCount = menu.getItemCount();
		if(itemCount <= 0) {
			return;
		}
		for (int i=0; i<itemCount; i++) {
			addMenuItem((CMenuItemImpl) menu.getItemAt(i));
		}
	}
	
	public void addMenuItem(CMenuItemImpl item) {
		addMenuItem(item, -1);
	}
	
	public void addMenuItem(CMenuItemImpl item, int position) {
        if (mItems == null) {
            mItems = new ArrayList<CMenuItemImpl>();
        }

        if (mUsedItems == null) {
            mUsedItems = new ArrayList<CMenuItemImpl>();
        }

		final int usedSize = mUsedItems.size();
		if(usedSize < mDisplayCountLimits) {

			mUsedItems.add(item);
			final MenuItemView menuItem = createMenuItemView(item);
	        addView(menuItem, position, new LayoutParams(
	                0, LayoutParams.MATCH_PARENT, 1));
		} else {
			if (mMoreItems == null) {
				mMoreItems = new ArrayList<CMenuItemImpl>();
			}

			CMenuItemImpl lastUsedItem = (CMenuItemImpl) mUsedItems.get(usedSize-1);
			if (!lastUsedItem.isMore()) {
				mUsedItems.remove(usedSize - 1);
                mMoreMenuItem = createMoreMenuItem();
                CActionBarImpl.CSubMenuImpl moreSubMenu = new CActionBarImpl.CSubMenuImpl(
                        getContext(), mMoreMenuItem);
                mMoreMenuItem.setSubMenu(moreSubMenu);
                moreSubMenu.add(lastUsedItem.getTitle(), lastUsedItem.getIcon())
                        .setItemId(lastUsedItem.getItemId())
                        .setGroupId(lastUsedItem.getGroupId())
                        .setCustomView(lastUsedItem.getCustomView());

                mMoreItems.add(lastUsedItem);
                mUsedItems.add(mMoreMenuItem);

				final MenuItemView menuItem = createMenuItemView(mMoreMenuItem);
                removeViewAt(getChildCount()-1);
		        addView(menuItem, position, new LayoutParams(
		                0, LayoutParams.MATCH_PARENT, 1));
			}

            mMoreItems.add(item);
            mMoreMenuItem.getSubMenu().add(item.getTitle(), item.getIcon())
                    .setItemId(item.getItemId())
                    .setGroupId(item.getGroupId())
                    .setCustomView(item.getCustomView());

		}

        mItems.add(item);
        
    }

    protected CMenuItemImpl createMoreMenuItem() {
        final CharSequence title = mMoreTitle;
        final Drawable icon = mMoreIconDrawable;
        return new CMenuItemImpl(getContext(), 0, 0, title, icon, true);
    }
	
	public void updateMenuItem(int position) {
        ((MenuItemView)getChildAt(position)).update();
    }
	
	public void removeMenuItemAt(int position) {
        removeViewAt(position);
    }

    public void removeAllMenuItems() {
        removeAllViews();
    }
	
	public void setMenuItemSelected(int position) {
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
	
	public int getMenuItemCount() {
		return getChildCount();
	}

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener listener) {
        mMenuItemSelectedListener = listener;
    }
	
	protected MenuItemView createMenuItemView(CMenuItemImpl item) {
        final MenuItemView menuItem = new MenuItemView(getContext());
        menuItem.init(this, item);
        menuItem.setFocusable(true);
        if (mMenuClickListener == null) {
            mMenuClickListener = new MenuItemClickListener();
        }
        menuItem.setOnClickListener(mMenuClickListener);
        return menuItem;
    }
	
	private class MenuItemClickListener implements OnClickListener {
        public void onClick(View view) {
        	MenuItemView itemView = (MenuItemView) view;
        	final CMenuItemImpl item = itemView.getMenuItem();
            mCurrentSelectedItem = item;
            final int tabCount = getChildCount();
            for (int i = 0; i < tabCount; i++) {
                final View child = getChildAt(i);
                child.setSelected(child == view);
            }

            if (mMenuItemSelectedListener != null) {
                mMenuItemSelectedListener.onMenuItemSelected(CMenuBarView.this, item);
            }

            onCreateSubMenuPanel(item);
        }
    }

	private class MenuItemView extends LinearLayout {
		
		private CMenuItemImpl mMenuItem = null;
		private TextView mTxvTitle = null;
		private ImageView mImgIcon = null;
		private View mCustomView = null;
		private CMenuBarView mParent = null;

		public MenuItemView(Context context) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
		}
		
		public void init(CMenuBarView parent, CMenuItemImpl tab) {
			mParent = parent;
			mMenuItem = tab;
			update();
		}
		
		public void update() {
			final CMenuItem item = mMenuItem;
			final View custom = item.getCustomView();
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
				
				final Drawable icon = item.getIcon();
                final CharSequence text = item.getTitle();

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

        CMenuItemImpl getMenuItem() {
			return mMenuItem;
		}
		
	}
	
}
