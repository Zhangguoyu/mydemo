package com.zhangguoyu.demo.listview;

import android.view.View;

interface TweenCallback {
    void onTweenValueChanged(View view, float time, float value);
    void onTweenStarted(View view);
    void onTweenFinished(View view);
}

