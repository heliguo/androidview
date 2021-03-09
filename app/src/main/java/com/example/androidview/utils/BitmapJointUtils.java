package com.example.androidview.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/3/9 16:05
 * @description bitmap 拼接工具类
 */
public class BitmapJointUtils {

    public static Bitmap mosaicBitmapVertical(List<View> showView, List<View> hideView, @ColorInt int bgColor) {
        boolean hasHide = false;
        if (showView == null || showView.size() == 0) {
            return null;
        }
        if (hideView != null && hideView.size() > 0) {
            hasHide = true;
            for (View view : hideView) {
                view.setVisibility(View.GONE);
            }
        }
        List<Bitmap> bitmaps = new ArrayList<>();
        for (View view : showView) {
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            bitmaps.add(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
        }
        if (hasHide){
            for (View view : hideView) {
                view.setVisibility(View.VISIBLE);
            }
        }
        return mosaicBitmapVertical(bgColor, bitmaps);
    }

    public static Bitmap mosaicBitmapHorizontal(List<View> showView, List<View> hideView, @ColorInt int bgColor) {
        boolean hasHide = false;
        if (showView == null || showView.size() == 0) {
            return null;
        }
        if (hideView != null && hideView.size() > 0) {
            hasHide = true;
            for (View view : hideView) {
                view.setVisibility(View.GONE);
            }
        }
        List<Bitmap> bitmaps = new ArrayList<>();
        for (View view : showView) {
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            bitmaps.add(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
        }
        if (hasHide){
            for (View view : hideView) {
                view.setVisibility(View.VISIBLE);
            }
        }
        return mosaicBitmapHorizontal(bgColor, bitmaps);
    }


    /**
     * 垂直拼接
     *
     * @param bitmaps
     * @return
     */
    private static Bitmap mosaicBitmapVertical(@ColorInt int bgColor, List<Bitmap> bitmaps) {

        if (bitmaps == null)
            return null;

        int height = 0;

        for (Bitmap item : bitmaps) {
            height += item.getHeight();
        }
        int width = 0;
        for (Bitmap value : bitmaps) {
            if (width < value.getWidth()) {
                width = value.getWidth();
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        if (bgColor != 0) {
            canvas.drawColor(bgColor);
        }
        int drawHeight = 0;
        for (Bitmap bitmap : bitmaps) {
            canvas.drawBitmap(bitmap, 0, drawHeight, null);
            drawHeight += bitmap.getHeight();
        }
        return result;
    }

    /**
     * 水平拼接
     *
     * @param bitmaps
     * @return
     */
    private static Bitmap mosaicBitmapHorizontal(@ColorInt int bgColor, List<Bitmap> bitmaps) {

        if (bitmaps == null)
            return null;

        int width = 0;

        for (Bitmap item : bitmaps) {
            width += item.getWidth();
        }
        int height = 0;
        for (Bitmap value : bitmaps) {
            if (height < value.getHeight()) {
                height = value.getHeight();
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        int drawWidth = 0;
        for (Bitmap bitmap : bitmaps) {
            canvas.drawBitmap(bitmap, drawWidth, 0, null);
            drawWidth += bitmap.getWidth();
        }
        return result;
    }

}
