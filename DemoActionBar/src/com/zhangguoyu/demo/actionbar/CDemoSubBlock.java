package com.zhangguoyu.demo.actionbar;

import android.os.Bundle;
import android.util.Log;
import com.zhangguoyu.app.CBlock;

/**
 * Created by Forcs on 13-5-20.
 */
public class CDemoSubBlock extends CBlock {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CDemoSubBlock", "@@@ onCreate " + getClass().getName());
        setContentView(R.layout.demo2);
    }
}
