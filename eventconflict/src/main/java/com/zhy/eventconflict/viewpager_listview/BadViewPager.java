package com.zhy.eventconflict.viewpager_listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class BadViewPager extends ViewPager {

    private int mLastX, mLastY;

    public BadViewPager(@NonNull Context context) {
        super(context);
    }

    public BadViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //ListView内部拦截法，不拦截down
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            super.onInterceptTouchEvent(event);
            return false;
        }
        return true;

//        外部拦截法
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mLastX = (int) event.getX();
//                mLastY = (int) event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltaX = x - mLastX;
//                int deltaY = y - mLastY;
//                //左右滑动
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    return true;
//                }
//                break;
//
//            default:
//                break;
//
//        }
//        //交给父亲处理
//        return super.onInterceptTouchEvent(event);
    }
}
