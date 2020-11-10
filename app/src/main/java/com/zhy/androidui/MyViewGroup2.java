package com.zhy.androidui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import static com.zhy.androidui.EventUtils.getName;

public class MyViewGroup2 extends LinearLayout {

    private static final String TAG = "ViewGroup2";

    public MyViewGroup2(Context context) {
        super(context);
    }

    public MyViewGroup2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        return super.onTouchEvent(event);
//        return true;
    }


}
