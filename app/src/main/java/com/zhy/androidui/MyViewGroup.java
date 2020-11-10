package com.zhy.androidui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import static com.zhy.androidui.EventUtils.getName;

public class MyViewGroup extends LinearLayout {

    private static final String TAG = "ViewGroup1";

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent " + getName(ev));
        return super.dispatchTouchEvent(ev);
//        return true;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.d(TAG, "onInterceptTouchEvent " + getName(ev));
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent " + getName(event));
//        return super.onTouchEvent(event);
        return true;
    }


}
