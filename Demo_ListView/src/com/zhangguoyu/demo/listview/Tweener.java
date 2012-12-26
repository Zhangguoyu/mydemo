package com.zhangguoyu.demo.listview;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Interpolator;

class Tweener {

    private static final int FPS = 30;
    private static final int FRAME_TIME = 1000 / FPS;

    private Handler mHandler;
    private int mDuration;
    private TweenCallback mCallback;

    private boolean mRunning;
    private long mBase;
    private boolean mDirection;
    private float mValue;
    
    private View mView;
    
    private Interpolator mInterpolator;

    /**
     * @param duration milliseconds duration
     * @param callback callbacks
     */
    public Tweener(boolean initial, int duration, TweenCallback callback) {
        this(null, initial,duration, callback);
    }
    
    public Tweener(View view, boolean initial, int duration, TweenCallback callback) {
    	mView = view;
        mValue = initial ? 1.0f : 0.0f;
        mDirection = initial;
        mDuration = duration;
        mCallback = callback;
        mHandler = new Handler();
    }
    
    public void setView(View view) {
    	mView = view;
    }
    
    public void setInterpolator(Interpolator interpolator) {
    	mInterpolator = interpolator;
    }

    public void start(boolean direction) {
        start(direction, SystemClock.uptimeMillis());
    }

    public void start(boolean direction, long baseTime) {
        if (direction != mDirection) {
            if (!mRunning) {
                mBase = baseTime;
                mRunning = true;
                mCallback.onTweenStarted(mView);
                long next = SystemClock.uptimeMillis() + FRAME_TIME;
                mHandler.postAtTime(mTick, next);
            } else {
                // reverse direction
                long now = SystemClock.uptimeMillis();
                long diff = now - mBase;
                mBase = now + diff - mDuration;
            }
            mDirection = direction;
        }
    }

    Runnable mTick = new Runnable() {
        public void run() {
            long base = mBase;
            long now = SystemClock.uptimeMillis();
            long diff = now-base;
            int duration = mDuration;
            float t = diff/(float)duration;
            if (!mDirection) {
                t = 1.0f - t;
            }
            if (t > 1.0f) {
                t = 1.0f;
            } else if (t < 0.0f) {
                t = 0.0f;
            }
            mValue = t;
            float v = t;
            if (mInterpolator != null) {
            	v = mInterpolator.getInterpolation(t);
            }
            mCallback.onTweenValueChanged(mView, t, v);
            int frame = (int)(diff / FRAME_TIME);
            long next = base + ((frame+1)*FRAME_TIME);
            if (diff < duration) {
                mHandler.postAtTime(this, next);
            }
            if (diff >= duration) {
                mCallback.onTweenFinished(mView);
                mRunning = false;
            }
        }
    };
}

