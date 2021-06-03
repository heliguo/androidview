package com.example.androidview.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author lgh on 2021/4/13 14:37
 * @description
 */
public class MyViewGroup extends ViewGroup {

    public MyViewGroup(@NonNull Context context) {
        super(context);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * @return 返回true 事件结束 不会回调任何onTouchEvent事件；返回false 由上级viewGroup 执行onTouchEvent
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TagTagTagTagTagTag", "dispatchTouchEvent: father");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("TagTagTagTagTagTag", "onInterceptTouchEvent: father");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.layout(l / 2, t / 2, r / 2, b / 2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TagTagTagTagTagTag", "onTouchEvent: father");
        return super.onTouchEvent(event);
    }
}
