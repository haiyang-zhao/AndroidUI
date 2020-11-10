package com.zhy.androidui;

import android.view.MotionEvent;

public class EventUtils {

    public static String getName(MotionEvent event) {
        return MotionEvent.actionToString(event.getAction());
    }
}
