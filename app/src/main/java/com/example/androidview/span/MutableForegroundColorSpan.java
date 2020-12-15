package com.example.androidview.span;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

/**
 * @author lgh on 2020/12/15 9:44
 * @description 自定义Span
 */
public class MutableForegroundColorSpan extends ForegroundColorSpan {
    private int mAlpha = 255;
    private int mForegroundColor;
    public MutableForegroundColorSpan(int alpha,int color) {
        super(color);
        mAlpha = alpha;
        mForegroundColor = color;
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(getForegroundColor());
    }
    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }
    public void setForegroundColor(int foregroundColor) {
        mForegroundColor = foregroundColor;
    }
    public float getAlpha() {
        return mAlpha;
    }
    @Override
    public int getForegroundColor() {
        return Color.argb(mAlpha,Color.red(mForegroundColor),Color.green(mForegroundColor),Color.blue(mForegroundColor));
    }
}
