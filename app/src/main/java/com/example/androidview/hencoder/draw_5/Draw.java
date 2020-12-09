package com.example.androidview.hencoder.draw_5;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/12/1 10:55
 * @description
 *
 * 一个完整的绘制过程会依次绘制以下几个内容：
 *
 * 背景 drawBackground
 * 主体（onDraw()）
 * 子 View（dispatchDraw()）
 * 滑动边缘渐变和滑动条
 * 前景
 */
public class Draw extends View {

    public Draw(Context context) {
        super(context);
    }

    public Draw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Draw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//继承自view ，空实现
    }

    /**
     * 子view绘制
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 滑动边缘渐变和滑动条以及前景
     * @param canvas
     */
    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }
}
