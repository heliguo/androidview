package com.example.androidview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author lgh on 2020/8/19:21:32
 * @description
 */
public class CustomScrollView extends ScrollView {

    private static final String TAG = "CustomScrollView";

    public CustomScrollView(Context context) {
        this(context,null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setVerticalScrollBarEnabled(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onTouchEvent: ");
        return super.onTouchEvent(ev);
    }
}
