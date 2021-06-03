package com.example.androidview.spread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidview.R;

/**
 * @author lgh on 2020/12/25 11:44
 * @description 水波纹动画
 */
public class SpreadView2 extends View {

    private Paint mCenterPaint;

    private Paint mSpreadPaint;

    private int mCenterRadius = 20;//中心圆半径

    private int mInterval = 30;//步长

    private int count = 0;//起始

    private int mStepInterval = 1;//值越大波动越快

    private int mSpreadMaxRadius = 100;//最大波纹半径

    private int mCenterX;

    private int mCenterY;

    private int mPaintColor = Color.parseColor("#C0362E");

    private Bitmap mBitmap;
    private Paint bitmapPaint;
    private Matrix mMatrix;

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
        mSpreadPaint.setStyle(Paint.Style.FILL);
        mSpreadPaint.setStrokeWidth(2);
        mSpreadPaint.setColor(mPaintColor);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.music);
        mBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
        bitmap.recycle();
        mMatrix = new Matrix();
        mMatrix.setScale(0.5f, 0.5f);

        mCenterRadius = mBitmap.getWidth()/2;

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
        //        canvas.drawCircle(mCenterX, mCenterY, (float) mCenterRadius, mCenterPaint);

        canvas.drawBitmap(mBitmap, mCenterX - mBitmap.getWidth() / 2f, mCenterY - mBitmap.getHeight() / 2f, bitmapPaint);

        //延迟运行
        postDelayed(() -> {
            count += mStepInterval;
            count %= mInterval;
            postInvalidate();//重绘
        }, 30);
    }

    public void setStyle(Paint.Style style) {
        mSpreadPaint.setStyle(style);
        invalidate();
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



