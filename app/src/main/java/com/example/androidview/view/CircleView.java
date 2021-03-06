package com.example.androidview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidview.R;

/**
 * @author lgh on 2020/5/22 11:35
 * @description
 */
public class CircleView extends View {

    private int   mCircleColor = Color.RED;
    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //当view被设置成wrap_content时，可自定义view的默认宽高，参考Textview onMeasure方法；
    private int mWidth  = 200;
    private int mheigth = 200;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();

    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mCircleColor = array.getColor(R.styleable.CircleView_circle_color, Color.RED);
        array.recycle();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    private void init() {
        mCirclePaint.setColor(mCircleColor);
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //手动处理padding值，而margin值不需要处理其由父容器处理
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int radius = Math.max(width, height) / 2;
        canvas.drawCircle(width >> 1 + paddingLeft, height >> 1 + paddingTop, radius, mCirclePaint);
    }
}
