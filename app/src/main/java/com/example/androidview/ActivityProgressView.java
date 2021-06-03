package com.example.androidview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/12/1 17:46
 * @description 
 */
public class ActivityProgressView extends View {

    private int stagedNum = 5;//分几段

    private int currentStage = 3;//当前进行到的阶段

    private @ColorInt
    int defaultColor = Color.parseColor("#CBCBCB");//默认未完成颜色

    private @ColorInt
    int finishColor = Color.parseColor("#ffff0000");//完成颜色

    private Paint mPaint;

    private int lineWidth = dp2px(5);

    private int gapValue = dp2px(20);//间隙

    private int lineLength;

    public ActivityProgressView(Context context) {
        this(context, null);
    }

    public ActivityProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActivityProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(defaultColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            width = (2 * stagedNum - 1) * gapValue + getPaddingLeft() + getPaddingRight() + lineWidth;
        }
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            height = lineWidth * 2 + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
        lineLength = (width - (stagedNum - 1) * gapValue - getPaddingLeft() - getPaddingRight() - lineWidth) / stagedNum;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(defaultColor);
        float startX = getLeft() + getPaddingLeft();
        float endX = startX + lineLength;
        float startY = (getTop() + lineWidth + getPaddingTop()) / 2f;
        for (int i = 0; i < stagedNum; i++) {
            canvas.save();
            canvas.translate(startX + i * (gapValue + lineLength), startY);
            canvas.drawLine(startX, startY, endX, startY, mPaint);
            canvas.restore();
        }
        if (currentStage != 0) {
            mPaint.setColor(finishColor);
            for (int i = 0; i < currentStage; i++) {
                canvas.save();
                canvas.translate(startX + i * (gapValue + lineLength), startY);
                canvas.drawLine(startX, startY, endX, startY, mPaint);
                canvas.restore();
            }

        }

    }

    private int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }

    public void setStagedNum(int stagedNum) {
        if (stagedNum == 0) {
            throw new IllegalArgumentException("the value of stage cannot be 0");
        }
        this.stagedNum = stagedNum;
        postInvalidate();
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
        postInvalidate();
    }

    public void setDefaultColor(@ColorInt int defaultColor) {
        this.defaultColor = defaultColor;
        postInvalidate();
    }

    public void setFinishColor(@ColorInt int finishColor) {
        this.finishColor = finishColor;
        postInvalidate();
    }

    public void setLineWidth(int dpValue) {
        if (dpValue == 0) {
            throw new IllegalArgumentException("the value of line's width cannot be 0");
        }
        this.lineWidth = dp2px(dpValue);
        requestLayout();
    }

    public void setGapValue(int dpValue) {
        this.gapValue = dp2px(dpValue);
        postInvalidate();
    }
}
