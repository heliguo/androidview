package com.example.androidview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/8/25:23:14
 * @description
 */
public class TestDispatchView extends LinearLayout {

    public TestDispatchView(Context context) {
        super(context);
    }

    public TestDispatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestDispatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TAG", "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("TAG", "onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
