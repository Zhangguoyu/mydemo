package com.zhangguoyu.widget;

import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewParent;
import com.zhangguoyu.app.CBlock;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.zhangguoyu.demo.actionbar.R;

public class CRelativeLayoutBlock extends RelativeLayout implements
		CBlockingView {

    private CBlock mBlock;

	public CRelativeLayoutBlock(Context context) {
		super(context);
        init(context, null);
	}
	
	public CRelativeLayoutBlock(Context context, AttributeSet attrs) {
		super(context, attrs);
        init(context, attrs);
	}

	public CRelativeLayoutBlock(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
        init(context, attrs);
	}

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CBlock);
            final String blockClassName = a.getString(R.styleable.CBlock_blockName);
            final int blockId = a.getResourceId(R.styleable.CBlock_blockId, CBlock.NO_ID);
            final Object blockTag = a.getString(R.styleable.CBlock_blockTag);
            if (!TextUtils.isEmpty(blockClassName)) {
                try {
                    Class<?> blockClass = context.getClassLoader().loadClass(blockClassName);
                    mBlock = (CBlock) blockClass.newInstance();
                    mBlock.setId(blockId)
                            .setTag(blockTag)
                            .setContainer(this);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            a.recycle();
        }
    }

    @Override
    public CBlock getBlock() {
        return mBlock;
    }

    @Override
    public void bindBlock(String name, int id, int layoutResId, Object tag) {

    }

}
