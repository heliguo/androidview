package com.example.androidview.hencoder.draw.draw_5;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author lgh on 2020/12/1 11:43
 * @description
 */
public class DrawParent extends ViewGroup {

    public DrawParent(Context context) {
        super(context);
    }

    public DrawParent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawParent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 调用 View.setWillNotDraw(false) 这行代码来切换到完整的绘制流程（是「可能」而不是「必须」的原因是，
     * 有些 ViewGroup 是已经调用过 setWillNotDraw(false) 了
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setWillNotDraw(true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
