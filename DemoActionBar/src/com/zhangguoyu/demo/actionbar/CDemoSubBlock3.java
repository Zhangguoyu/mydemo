package com.zhangguoyu.demo.actionbar;

import android.os.Bundle;
import android.util.Log;
import com.zhangguoyu.app.CBlock;

/**
 * Created by zhangguoyu on 13-5-21.
 */
public class CDemoSubBlock3 extends CBlock {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CDemoSubBlock3", "@@@ onCreate " + getClass().getName());
        setContentView(R.layout.demo2);
    }
}
