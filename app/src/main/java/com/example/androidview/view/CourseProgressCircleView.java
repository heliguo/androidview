package com.example.androidview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * @author lgh on 2021/6/29 16:18
 * @description
 */
public class CourseProgressCircleView extends View {

    private static final String TAG = "CourseProgressCircleVie";

    private Paint mDefaultPaint;//底色画笔

    @ColorInt
    private int mDefaultColor = Color.parseColor("#E6E6E6");

    private Paint mRenderPaint;//进度色画笔

    @ColorInt
    private int mRenderColor =  Color.parseColor("#FF9900");

    @ColorInt
    private int mProgressColor =  Color.parseColor("#FF9900");

    private Paint mProgressPaint;//进度文字画笔

    private float mTextSize = sp2px(14);

    private Paint mFillPaint;

    @ColorInt
    private int mCircleFillColor = Color.WHITE;//填充颜色

    private int mCircleRadius = dp2px(18);//圆半径

    private int mProgressWidth = dp2px(6);//进度宽，线宽

    private int mCurrentProgress;//当前进度

    private int mTotalProgress = 100;//总进度

    private float mDefaultRatio = 1.2f;

    private RectF mRectF;


    public CourseProgressCircleView(Context context) {
        this(context, null);
    }

    public CourseProgressCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseProgressCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDefaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultPaint.setColor(mDefaultColor);
        mDefaultPaint.setStyle(Paint.Style.STROKE);
        mDefaultPaint.setStrokeWidth(mProgressWidth);

        mRenderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRenderPaint.setColor(mRenderColor);
        mRenderPaint.setStyle(Paint.Style.STROKE);
        mRenderPaint.setStrokeWidth(mProgressWidth);
        //        mRenderPaint.setStrokeCap(Paint.Cap.ROUND);

        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillPaint.setColor(mCircleFillColor);
        mFillPaint.setStyle(Paint.Style.FILL);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setTextSize(mTextSize);

        mRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int mWidth;
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            mWidth = Math.round(mDefaultRatio * mCircleRadius * 2);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = Math.max(Math.round(mDefaultRatio * mCircleRadius * 2), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mWidth = Math.max(widthSize, Math.round(mDefaultRatio * mCircleRadius * 2));
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
        canvas.drawCircle(centerX, centerY, mCircleRadius, mFillPaint);
        canvas.drawCircle(centerX, centerY, mCircleRadius, mDefaultPaint);
        mRectF.left = centerX - mCircleRadius;
        mRectF.top = centerY - mCircleRadius;
        mRectF.right = centerX + mCircleRadius;
        mRectF.bottom = centerY + mCircleRadius;
        canvas.drawArc(mRectF, -90, getProgressDegree(), false, mRenderPaint);
        String text = String.valueOf(mCurrentProgress);
        canvas.drawText(text, centerX - getTextWidth(text) / 2,
                centerY + getTextHeight(), mProgressPaint);
    }

    public CourseProgressCircleView setDefaultPaint(Paint defaultPaint) {
        mDefaultPaint = defaultPaint;
        return this;
    }

    public CourseProgressCircleView setDefaultColor(int defaultColor) {
        mDefaultColor = defaultColor;
        return this;
    }

    public CourseProgressCircleView setRenderPaint(Paint renderPaint) {
        mRenderPaint = renderPaint;
        return this;
    }

    public CourseProgressCircleView setRenderColor(int renderColor) {
        mRenderColor = renderColor;
        return this;
    }

    public CourseProgressCircleView setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        return this;
    }

    public CourseProgressCircleView setProgressPaint(Paint progressPaint) {
        mProgressPaint = progressPaint;
        return this;
    }

    public CourseProgressCircleView setTextSize(float textSize) {
        mTextSize = sp2px(textSize);
        return this;
    }

    public CourseProgressCircleView setFillPaint(Paint fillPaint) {
        mFillPaint = fillPaint;
        return this;
    }

    public CourseProgressCircleView setCircleFillColor(int circleFillColor) {
        mCircleFillColor = circleFillColor;
        return this;
    }

    public CourseProgressCircleView setCircleRadius(int circleRadius) {
        mCircleRadius = dp2px(circleRadius);
        return this;
    }

    public CourseProgressCircleView setProgressWidth(int progressWidth) {
        mProgressWidth = dp2px(progressWidth);
        return this;
    }

    public CourseProgressCircleView setCurrentProgress(int currentProgress) {
        mCurrentProgress = currentProgress;
        return this;
    }

    public CourseProgressCircleView setTotalProgress(int totalProgress) {
        mTotalProgress = totalProgress;
        return this;
    }

    public CourseProgressCircleView setDefaultRatio(float defaultRatio) {
        mDefaultRatio = defaultRatio;
        return this;
    }

    private float getProgressDegree() {
        if (mCurrentProgress <= 0) {
            return 1;
        }
        return mCurrentProgress * 1.0f / mTotalProgress * 360;
    }

    private float getTextWidth(String text) {
        return mProgressPaint.measureText(text);
    }

    private float getTextHeight() {
        Paint.FontMetrics fontMetrics = mProgressPaint.getFontMetrics();
        return (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
    }

    private int dp2px(float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics()) + 0.5f);
    }

    private int sp2px(float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics()) + 0.5f);
    }

}
