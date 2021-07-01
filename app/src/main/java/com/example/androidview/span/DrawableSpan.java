package com.example.androidview.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author lgh on 2021/6/23 11:07
 * @description
 */
public class DrawableSpan extends ReplacementSpan implements ParcelableSpan {

    private static final String TAG = "DrawableSpan";
    private Drawable mDrawable;
    private int mImageId;
    private int mWidth = -1;

    public DrawableSpan(Drawable drawable) {
        mDrawable = drawable;
    }

    @Override
    public int getSpanTypeId() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        float size = paint.measureText(text, start, end);
        if (fm != null) {
            paint.getFontMetricsInt(fm);
        }
        mWidth = (int) size;
        return mWidth;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        if (mDrawable == null) {//if no backgroundImage just don't do any draw
            Log.e(TAG, "mDrawable is null draw()");
            return;
        }
        Drawable drawable = mDrawable;
        canvas.save();
        canvas.translate(x, top); // translate to the left top point
        mDrawable.setBounds(0, 0, mWidth, (bottom - top));
        drawable.draw(canvas);
        canvas.restore();
        canvas.drawText(text, start, end, x, y, paint);
    }
}
