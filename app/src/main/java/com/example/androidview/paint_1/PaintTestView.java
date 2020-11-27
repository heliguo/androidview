package com.example.androidview.paint_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/11/27 16:54
 * @description
 */
public class PaintTestView extends View {

    private Paint mPaint;

    public PaintTestView(Context context) {
        this(context, null);
    }

    public PaintTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 设置底色或者半透明蒙版
         */
        canvas.drawColor(Color.BLACK);//填充颜色
        canvas.drawRGB(255, 255, 255);
        canvas.drawARGB(120, 255, 255, 255);

        canvas.drawCircle(300, 300, 200, mPaint);

        canvas.drawRect(100, 100, 500, 500, mPaint);

        /**
         * 圆头 (ROUND)、平头 (BUTT) 和方头 (SQUARE)
         */
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(50, 50, mPaint);

        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        // 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
                8 /* 一共绘制 8 个数（4 个点）*/, mPaint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawOval(50, 50, 350, 200, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawOval(400, 50, 700, 200, mPaint);
        }

        canvas.drawLine(200, 200, 800, 500, mPaint);

        float[] lines = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        canvas.drawLines(lines, mPaint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(100, 100, 500, 300, 50, 50, mPaint);
        }
    }
}
