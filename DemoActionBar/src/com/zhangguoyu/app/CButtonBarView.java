package com.zhangguoyu.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.zhangguoyu.demo.actionbar.R;

/**
 * Created by zhangguoyu on 13-5-21.
 */
public abstract  class CButtonBarView extends LinearLayout {

    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int MONOSPACE = 3;

    private int mHeight;
    private int mItemMargin = 0;
    private int mItemLeftMargin = 0;
    private int mItemTopMargin = 0;
    private int mItemRightMargin = 0;
    private int mItemBottomMargin = 0;
    private float mItemTextSize;
    private Typeface mItemTextTypeface;
    private int mItemTextColor;
    private ColorStateList mItemTextColorStateList = null;
    private int mItemGravity;
    private Drawable mItemBackground = null;
    private int mButtonBarStyle;
    private int mItemLeftPadding = 0;
    private int mItemTopPadding = 0;
    private int mItemRightPadding = 0;
    private int mItemBottomPadding = 0;
    private int mItemPadding = 0;

    public CButtonBarView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CButtonBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CButtonBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CButtonBarView, defStyle, 0);
        final int N = a.getIndexCount();
        for (int index=0; index<N; index++) {
            switch(index) {
                case R.styleable.CButtonBarView_itemBackground:
                    mItemBackground = null;
                    break;
            }
        }
        a.recycle();
    }

    public CButtonBarView setBarHeight(int height) {
        ViewGroup.LayoutParams p = getLayoutParams();
        final int h = p.height;
        if (h == height) {
            return this;
        }
        p.height = height;
        updateViewLayout(this, p);
        return this;
    }

    public float getItemTextSize() {
        return mItemTextSize;
    }

    public CButtonBarView setItemTextSize(float size) {
        if (mItemTextSize == size) {
            return this;
        }
        mItemTextSize = size;
        onChangeItemTextSize(size);
        return this;
    }

    protected abstract void onChangeItemTextSize(float size);

    public int getmItemTextColor() {
        return mItemTextColor;
    }

    public CButtonBarView setItemTextColor(int color) {
        if (mItemTextColor == color) {
            return this;
        }
        mItemTextColor = color;
        onChangeItemTextColor(ColorStateList.valueOf(color));
        return this;
    }

    public CButtonBarView setItemTextColor(ColorStateList color) {
        if (mItemTextColorStateList != null && mItemTextColorStateList.equals(color)) {
            return this;
        }
        mItemTextColorStateList = color;
        onChangeItemTextColor(color);
        return this;
    }

    protected abstract void onChangeItemTextColor(ColorStateList color);

    public Typeface getItemTypeface() {
        return mItemTextTypeface;
    }

    public CButtonBarView setItemTypeface(Typeface tf) {
        return setItemTypeface(tf, -1);
    }

    public CButtonBarView setItemTypeface(Typeface tf, int style) {
        if (mItemTextTypeface.equals(tf)) {
            return this;
        }
        mItemTextTypeface = tf;
        onChangeItemTypeface(tf, style);
        return this;
    }

    protected abstract void onChangeItemTypeface(Typeface tf, int style);

    public int getItemLeftMargin() {
        return mItemLeftMargin;
    }

    public CButtonBarView setItemLeftMargin(int left) {
        if (mItemLeftMargin == left) {
            return this;
        }
        mItemLeftMargin = left;
        onChangeItemMargin(mItemLeftMargin, mItemTopMargin, mItemRightMargin, mItemBottomMargin);
        return this;
    }

    public int getItemTopMargin() {
        return mItemTopMargin;
    }

    public CButtonBarView setItemTopMargin(int top) {
        if (mItemTopMargin == top) {
            return this;
        }
        mItemTopMargin = top;
        onChangeItemMargin(mItemLeftMargin, mItemTopMargin, mItemRightMargin, mItemBottomMargin);
        return this;
    }

    public int getItemRightMargin() {
        return mItemRightMargin;
    }

    public CButtonBarView setItemRightMargin(int right) {
        if (mItemRightMargin == right) {
            return this;
        }
        mItemRightMargin = right;
        onChangeItemMargin(mItemLeftMargin, mItemTopMargin, mItemRightMargin, mItemBottomMargin);
        return this;
    }

    public int getItemBottomMargin() {
        return mItemBottomMargin;
    }

    public CButtonBarView setItemBottomMargin(int bottom) {
        if (mItemBottomMargin == bottom) {
            return this;
        }
        mItemBottomMargin = bottom;
        onChangeItemMargin(mItemLeftMargin, mItemTopMargin, mItemRightMargin, mItemBottomMargin);
        return this;
    }

    public int getItemMargin() {
        return mItemMargin;
    }

    public CButtonBarView setItemMargin(int margin) {
        if (mItemMargin == margin) {
            return this;
        }
        mItemMargin = margin;
        mItemLeftMargin = margin;
        mItemTopMargin = margin;
        mItemRightMargin = margin;
        mItemBottomMargin = margin;
        onChangeItemMargin(mItemLeftMargin, mItemTopMargin, mItemRightMargin, mItemBottomMargin);
        return this;
    }

    protected abstract void onChangeItemMargin(int left, int top, int right, int bottom);

    public int getItemGravity() {
        return mItemGravity;
    }

    public CButtonBarView setItemGravity(int gravity) {
        onChangeItemGravity(gravity);
        return this;
    }

    protected abstract void onChangeItemGravity(int gravity);

    public Drawable getItemBackground() {
        return mItemBackground;
    }

    public CButtonBarView setItemBackground(Drawable drawable) {
        onChangeItemBackground(drawable);
        return this;
    }

    public abstract void onChangeItemBackground(Drawable drawable);

    public int getButtonBarStyle() {
        return mButtonBarStyle;
    }

    public CButtonBarView setButtonBarStyle(int style) {
        onChangeButtonBarStyle(style);
        return this;
    }

    protected void onChangeButtonBarStyle(int style) {
        if (mButtonBarStyle == style) {
            return;
        }
        final Context c = getContext();
        final float scaledDensity = c.getResources().getDisplayMetrics().scaledDensity;
        TypedArray a = c.obtainStyledAttributes(null, R.styleable.CButtonBarView, style, 0);
        int tf = -1;
        int textStyle = -1;
        String fontFamily = null;
        int shadowColor = 0;
        float dx = 0f;
        float dy = 0f;
        float r = 0f;
        final int N = a.getIndexCount();
        for (int index=0; index<N; index++) {
            switch(index) {
                case R.styleable.CButtonBarView_itemBackground:
                    onChangeItemBackground(a.getDrawable(index));
                    break;
                case R.styleable.CButtonBarView_itemTextSize:
                    float textSize = a.getDimension(index, 0f);
                    onChangeItemTextSize(textSize/scaledDensity);
                    break;
                case R.styleable.CButtonBarView_itemTextColor:
                    onChangeItemTextColor(a.getColorStateList(index));
                    break;
                case R.styleable.CButtonBarView_itemTextFamilyName:
                    fontFamily = a.getString(index);
                    break;
                case R.styleable.CButtonBarView_itemTextShadowColor:
                    shadowColor = a.getColor(index, shadowColor);
                    break;
                case R.styleable.CButtonBarView_itemTextShadowDx:
                    dx = a.getFloat(index, dx);
                    break;
                case R.styleable.CButtonBarView_itemTextShadowDy:
                    dy = a.getFloat(index, dy);
                    break;
                case R.styleable.CButtonBarView_itemTextShadowRadius:
                    r = a.getFloat(index, r);
                    break;
                case R.styleable.CButtonBarView_itemGravity:
                    onChangeItemGravity(a.getInt(index, 0));
                    break;
                case R.styleable.CButtonBarView_itemTextStyle:
                    textStyle = a.getInt(index, textStyle);
                    break;
                case R.styleable.CButtonBarView_itemTextTypeface:
                    tf = a.getInt(index, tf);
                    break;
                case R.styleable.CButtonBarView_menubarHeight:
                    setBarHeight(a.getLayoutDimension(index, ViewGroup.LayoutParams.WRAP_CONTENT));
                    break;
                case R.styleable.CButtonBarView_itemMargin:
                    break;
                case R.styleable.CButtonBarView_itemLeftMargin:
                    break;
                case R.styleable.CButtonBarView_itemTopMargin:
                    break;
                case R.styleable.CButtonBarView_itemRightMargin:
                    break;
                case R.styleable.CButtonBarView_itemBottomMargin:
                    break;
                case R.styleable.CButtonBarView_itemPadding:
                    break;
                case R.styleable.CButtonBarView_itemLeftPadding:
                    break;
                case R.styleable.CButtonBarView_itemTopPadding:
                    break;
                case R.styleable.CButtonBarView_itemRightPadding:
                    break;
                case R.styleable.CButtonBarView_itemBottomPadding:
                    break;
            }
        }
        a.recycle();

        setTypefaceFromAttrs(fontFamily, tf, textStyle);
    }

    private void setTypefaceFromAttrs(String familyName, int typefaceIndex, int styleIndex) {
        Typeface tf = null;
        if (familyName != null) {
            tf = Typeface.create(familyName, styleIndex);
            if (tf != null) {
                onChangeItemTypeface(tf, -1);
                return;
            }
        }
        switch (typefaceIndex) {
            case SANS:
                tf = Typeface.SANS_SERIF;
                break;
            case SERIF:
                tf = Typeface.SERIF;
                break;
            case MONOSPACE:
                tf = Typeface.MONOSPACE;
                break;
        }

        onChangeItemTypeface(tf, styleIndex);
    }

    public int getItemLeftPadding() {
        return mItemLeftPadding;
    }

    public CButtonBarView setItemLeftPadding(int left) {
        if (mItemLeftPadding == left) {
            return this;
        }
        mItemLeftPadding = left;
        onChangeItemPadding(mItemLeftPadding, mItemTopPadding,
                mItemRightPadding, mItemBottomPadding);
        return this;
    }

    public int getItemTopPadding() {
        return mItemTopPadding;
    }

    public CButtonBarView setItemTopPadding(int top) {
        if (mItemTopPadding == top) {
            return this;
        }
        mItemTopPadding = top;
        onChangeItemPadding(mItemLeftPadding, mItemTopPadding,
                mItemRightPadding, mItemBottomPadding);
        return this;
    }

    public int getItemRightPadding() {
        return mItemRightPadding;
    }

    public CButtonBarView setItemRightPadding(int right) {
        if (mItemRightPadding == right) {
            return this;
        }
        mItemRightPadding = right;
        onChangeItemPadding(mItemLeftPadding, mItemTopPadding,
                mItemRightPadding, mItemBottomPadding);
        return this;
    }

    public int getItemBottomPadding() {
        return mItemBottomPadding;
    }

    public CButtonBarView setItemBottomPadding(int bottom) {
        if (mItemBottomPadding == bottom) {
            return this;
        }
        mItemBottomPadding = bottom;
        onChangeItemPadding(mItemLeftPadding, mItemTopPadding,
                mItemRightPadding, mItemBottomPadding);
        return this;
    }

    public int getItemPadding() {
        return mItemPadding;
    }

    public CButtonBarView setItemPadding(int padding) {
        mItemPadding = padding;
        mItemLeftPadding = padding;
        mItemTopPadding = padding;
        mItemRightPadding = padding;
        mItemBottomPadding = padding;
        onChangeItemPadding(mItemLeftPadding, mItemTopPadding,
                mItemRightPadding, mItemBottomPadding);
        return this;
    }

    public abstract void onChangeItemPadding(int left, int top, int right, int bottom);

    public CButtonBarView setItemTextShadow(int color, float dx, float dy, float radius) {
        onChangeItemTextShadow(color, dx, dy, radius);
        return this;
    }

    public abstract void onChangeItemTextShadow(int color, float dx, float dy, float radius);

}
