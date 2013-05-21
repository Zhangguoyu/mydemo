package com.zhangguoyu.app;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.zhangguoyu.demo.actionbar.R;
import com.zhangguoyu.widget.CBlockPager;

public class CPageBlockActivity extends CActivity {

    private CBlockPager mBlockPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBlockPager = new CBlockPager(getBaseContext());
        mBlockPager.setId(R.id.page_frame_id);
        mBlockPager.setSupportFragmentManager(getSupportFragmentManager());
        setContentView(mBlockPager);
    }

    public void addBlockFromResurce(int blockXmlResId) {
        mBlockPager.addBlockFromResource(blockXmlResId);
    }

    public void addBlock(CBlock block, Bundle args) {
        mBlockPager.addBlock(block, args);
    }

    public void setCurrentItem(int item) {
        mBlockPager.setCurrentItem(item);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mBlockPager.setCurrentItem(item, smoothScroll);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mBlockPager.setOnPageChangeListener(listener);
    }

    public void arrowScroll(int direction) {
        mBlockPager.arrowScroll(direction);
    }
}
