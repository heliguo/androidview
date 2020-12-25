package com.example.androidview.spread;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/12/25 11:44
 * @description 水波纹动画
 */
public class SpreadView2 extends View {

    private Paint mCenterPaint;

    private Paint mSpreadPaint;

    private int mCenterRadius = 50;//中心圆半径

    private int mInterval = 50;//步长

    private int count = 0;//起始

    private int mStepInterval = 2;//值越大波动越快

    private int mSpreadMaxRadius = 200;//最大波纹半径

    private int mCenterX;

    private int mCenterY;

    private int mPaintColor = Color.RED;


    public SpreadView2(Context context) {
        this(context, null);
    }

    public SpreadView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpreadView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterPaint.setStyle(Paint.Style.FILL);
        mCenterPaint.setColor(mPaintColor);

        mSpreadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSpreadPaint.setStyle(Paint.Style.STROKE);
        mSpreadPaint.setStrokeWidth(2);
        mSpreadPaint.setColor(mPaintColor);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int step = count; step <= mSpreadMaxRadius; step += mInterval) {
            //step越大越靠外就越透明
            mSpreadPaint.setAlpha(255 * (mSpreadMaxRadius - step) / mSpreadMaxRadius);
            canvas.drawCircle(mCenterX, mCenterY, (float) (mCenterRadius + step), mSpreadPaint);
        }
        canvas.drawCircle(mCenterX, mCenterY, (float) mCenterRadius, mCenterPaint);

        //延迟运行
        postDelayed(() -> {
            count += mStepInterval;
            count %= mInterval;
            postInvalidate();//重绘
        }, 30);
    }

    public void setCenterRadius(int centerRadius) {
        mCenterRadius = centerRadius;
    }

    public void setInterval(int interval) {
        mInterval = interval;
    }

    public void setSpreadMaxRadius(int spreadMaxRadius) {
        mSpreadMaxRadius = spreadMaxRadius;
    }

    public void setPaintColor(int paintColor) {
        mPaintColor = paintColor;
    }

    public void setStepInterval(int stepInterval) {
        mStepInterval = stepInterval;
    }
}



