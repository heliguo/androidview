package com.example.androidview.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import java.util.Random;

/**
 * @author lgh on 2020/12/15 9:46
 * @description
 */
public class BubbleSpan extends ReplacementSpan {

    private Paint mPaint;
    static Random random = new Random();
    private int mWidth = -1;
    private final RectF mRectF = new RectF();

    private final int[] mColors = new int[20];

    public BubbleSpan() {
        initPaint();
        initColors();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        mPaint.setAntiAlias(true);
    }

    private void initColors() {
        for(int index = 0 ; index < mColors.length ; index++) {
            mColors[index] = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //return text with relative to the Paint
        mWidth = (int) paint.measureText(text, start, end);
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float charx = x;
        for(int i = start ; i<end; i++) {
            String charAt = extractText(text, i, i + 1);
            float charWidth = paint.measureText(charAt);
            mRectF.set(charx, top, charx += charWidth, bottom);
            mPaint.setColor(mColors[i % mColors.length]);
            //根据每个字的位置绘制背景
            canvas.drawOval(mRectF, mPaint);
        }
        //绘制字体，如果不掉用这个函数，就不会显示字体啦。
        canvas.drawText(text, start, end, x, y, paint);
    }

    private String extractText(CharSequence text, int start, int end) {
        return text.subSequence(start, end).toString();
    }
}
