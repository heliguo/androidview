package com.example.androidview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/5/22 9:40
 * @description 自定义view绘制流程
 */
public class TestDrawView extends View {

    //当view被设置成wrap_content时，可自定义view的默认宽高，参考Textview onMeasure方法；
    private int mWidth;
    private int mheigth;

    public TestDrawView(Context context) {
        super(context);
    }

    public TestDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //拿到view的测量值
//        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
//        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
//        setMeasuredDimension(width, height);//此方法等价于super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mheigth);//自定义默认宽高,否则此时view宽高等于父布局
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (heightMeasureSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mheigth);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }

    }
}
