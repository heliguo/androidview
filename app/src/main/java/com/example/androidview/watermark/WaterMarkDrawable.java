package com.example.androidview.watermark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseIntArray;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * 文字水印
 */
public class WaterMarkDrawable extends Drawable {

    public enum DrawableType {
        TEXT,
        BITMAP
    }

    private Paint mPaint;
    private List<String> mLabels;
    private Context mContext;
    private int mDegree;//角度
    private int mFontSize;//字体大小 单位sp

    private int mCanvasColor = Color.parseColor("#40F3F5F9");
    private int mPaintColor = Color.parseColor("#50AEAEAE");

    //重复 间距 水平
    @FloatRange(from = 0f, to = 1f)
    private float repeatHorizontalRatio = 0.3f;

    //重复 间距 垂直
    @FloatRange(from = 0f, to = 1f)
    private float repeatVerticalRatio = 0.5f;

    //文字 间距
    @FloatRange(from = 0f, to = 1f)
    private float textLineRatio = 0.2f;

    private DrawableType mDrawableType;

    private Bitmap mBitmap;

    public WaterMarkDrawable(Context context, List<String> labels, @IntRange(from = -50, to = 50) int degree, int fontSize) {
        this.mLabels = labels;
        this.mContext = context;
        this.mDegree = degree;
        this.mFontSize = fontSize;
        this.mDrawableType = DrawableType.TEXT;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mPaintColor);
        mPaint.setTextSize(sp2px(mContext, mFontSize));
    }



    public WaterMarkDrawable(Context context, List<String> labels, @IntRange(from = -50, to = 50) int degree,
                             int fontSize, int mCanvasColor, int paintColor) {
        this.mLabels = labels;
        this.mContext = context;
        this.mDegree = degree;
        this.mFontSize = fontSize;
        this.mCanvasColor = mCanvasColor;
        this.mPaintColor = paintColor;
        this.mDrawableType = DrawableType.TEXT;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mPaintColor);
        mPaint.setTextSize(sp2px(mContext, mFontSize));
    }

    public WaterMarkDrawable(Context context, Bitmap bitmap, @IntRange(from = -50, to = 50) int degree) {
        this.mBitmap = bitmap;
        this.mContext = context;
        this.mDegree = degree;
        this.mDrawableType = DrawableType.BITMAP;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
    }

    public WaterMarkDrawable(Context context, Bitmap bitmap, @IntRange(from = -50, to = 50) int degree, int mCanvasColor) {
        this.mBitmap = bitmap;
        this.mContext = context;
        this.mDegree = degree;
        this.mCanvasColor = mCanvasColor;
        this.mDrawableType = DrawableType.BITMAP;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mPaintColor);
        mPaint.setFilterBitmap(true);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mDrawableType == DrawableType.TEXT && mLabels.size() == 0)
            return;
        if (mDrawableType == DrawableType.BITMAP && mBitmap == null) {
            return;
        }
        performDraw(canvas);

    }

    private void performDraw(Canvas canvas) {

        int width = getBounds().right - getBounds().left;
        int height = getBounds().bottom - getBounds().top;
        canvas.drawColor(mCanvasColor);

        int save = canvas.save();
        canvas.rotate(mDegree);

        int index = 0;
        float fromX;
        int k;

        if (mDrawableType == DrawableType.TEXT) {
            canvas.rotate(mDegree);
            float textWidth = mPaint.measureText(getText(mLabels));
            float textLineHeight = getTextHeight() * textLineRatio;
            int textHeights = (int) (mLabels.size() * (textLineHeight + getTextHeight()));//文字整体高度
            float horizontalStep = textWidth * repeatHorizontalRatio;
            float verticalStep = textHeights * repeatVerticalRatio;

            if (mDegree > 0) {
                for (int positionY = -width; positionY <= height * Math.atan(mDegree);
                     positionY += (textHeights + verticalStep)) {
                    index++;
                    k = index % 2;
                    fromX = -width + k * textWidth;
                    for (float positionX = fromX; positionX < width * 3; positionX += (textWidth + horizontalStep)) {
                        float spacing = 0;//间距
                        for (String label : mLabels) {
                            canvas.drawText(label, positionX, positionY + spacing, mPaint);
                            spacing = textLineHeight + getTextHeight() + spacing;
                        }
                    }

                }
            } else {
                for (int positionY = textHeights; positionY <= height * Math.tan(mDegree); positionY += (textHeights + verticalStep)) {
                    index++;
                    k = index % 2;
                    fromX = -width * 2 + k * textWidth;
                    for (float positionX = fromX; positionX < width; positionX += (textWidth + horizontalStep)) {
                        float spacing = 0;//间距
                        for (String label : mLabels) {
                            canvas.drawText(label, positionX, positionY + spacing, mPaint);
                            spacing = textLineHeight + getTextHeight() + spacing;
                        }

                    }
                }
            }
        }

        if (mDrawableType == DrawableType.BITMAP) {

            int bitmapHeight = mBitmap.getHeight();
            int bitmapWidth = mBitmap.getWidth();
            float verticalStep = bitmapHeight * repeatVerticalRatio;
            float horizontalStep = bitmapWidth * repeatHorizontalRatio;
            if (mDegree > 0) {
                for (int positionY = -width; positionY <= height * Math.atan(mDegree);
                     positionY += (bitmapHeight + verticalStep)) {
                    index++;
                    k = index % 2;
                    fromX = -width + k * bitmapWidth;
                    for (float positionX = fromX; positionX < width * 3; positionX += (bitmapWidth + horizontalStep)) {
                        canvas.drawBitmap(mBitmap, positionX, positionY, mPaint);
                    }
                }
            } else {
                for (int positionY = bitmapHeight; positionY <= height * Math.tan(mDegree); positionY += (bitmapHeight + verticalStep)) {
                    index++;
                    k = index % 2;
                    fromX = -width * 2 + k * bitmapWidth;
                    for (float positionX = fromX; positionX < width; positionX += (bitmapWidth + horizontalStep)) {
                        canvas.drawBitmap(mBitmap, positionX, positionY, mPaint);
                    }
                }
            }

        }
        canvas.restoreToCount(save);
    }

    private String getText(List<String> labels) {
        if (labels.size() == 1) {
            return labels.get(0);
        }
        SparseIntArray array = new SparseIntArray(1);
        array.put(0, labels.get(0).length());
        for (int i = 1; i < labels.size(); i++) {
            if (labels.get(i).length() > array.valueAt(0)) {
                array.clear();
                array.put(i, labels.get(i).length());
            }
        }
        return labels.get(array.keyAt(0));
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        if (mDrawableType == DrawableType.BITMAP) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }


    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private float getTextHeight() {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }
}
