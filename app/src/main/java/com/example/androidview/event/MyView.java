package com.example.androidview.event;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2021/4/13 15:46
 * @description
 */
public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }

    /**
     * @return 返回true 事件结束 不会回调任何onTouchEvent事件；返回false 由上级viewGroup 执行onTouchEvent
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("TagTagTagTagTagTag", "dispatchTouchEvent: child ");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TagTagTagTagTagTag", "onTouchEvent: child ");
        return super.onTouchEvent(event);
    }
}
