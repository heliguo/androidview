package com.example.androidview.spread;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

public class PathLoadingView2 extends View {
    //    PathMeasure
    private Path mPath;
    private Paint mPaint;
    private float mLength;
    private Path dst;
    private float mAnimatorValue;
    //    新加入的
    private PathMeasure mPathMeasure;

    @ColorInt
    private int paintColor = Color.parseColor("#FF4081");

    private float paintStroke = dp2px(3);

    private float circleRadius = dp2px(30);

    private int duration = 1500;

    public PathLoadingView2(Context context) {
        this(context, null);
    }


    public PathLoadingView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public PathLoadingView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public void setPaintStroke(float paintStroke) {
        this.paintStroke = paintStroke;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private void init() {
        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(paintColor);
        mPaint.setStrokeWidth(paintStroke);
        mPaint.setStyle(Paint.Style.STROKE);
        //Path
        mPath = new Path();
        //        0<distance<length*百分比
        // 闭合  圆     直线 非闭合  0  --length
        // pathmeasure  path的测量工具类
        mPathMeasure = new PathMeasure(mPath, true);

        dst = new Path();
        //属性动画
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        //设置动画过程的监听
        valueAnimator.addUpdateListener(animation -> {
            //
            mAnimatorValue = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.setDuration(duration);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int mWidth;
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            mWidth = Math.round(circleRadius * 3);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = Math.max(Math.round(circleRadius * 3), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mWidth = Math.max(widthSize, Math.round(circleRadius * 3));
        } else {
            mWidth = widthSize;
        }
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        float centerX = width / 2f + paddingLeft;
        float centerY = height / 2f + paddingTop;
        mPath.addCircle(centerX, centerY, circleRadius, Path.Direction.CW);//加入一个半径为100圆
        mPathMeasure.setPath(mPath,true);
        mLength = mPathMeasure.getLength();//不需要你自己算  //
        dst.reset();
        float distance = mLength * mAnimatorValue;
        //        distance     start  disant -0
        float start = (float) (distance - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        //   ( distance-0.5*mLength)   start   distance
        //mPath  1  dst  2
        mPathMeasure.getSegment(start, distance, dst, true);
        canvas.drawPath(dst, mPaint);
    }

    private int dp2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale);
    }
}
