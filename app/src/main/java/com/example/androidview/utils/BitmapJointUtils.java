package com.example.androidview.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.LruCache;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

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
            if (view instanceof RecyclerView) {
                Bitmap bitmap = shotRecyclerView(((RecyclerView) view));
                bitmaps.add(bitmap);
            }else {
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                bitmaps.add(view.getDrawingCache());
            }

        }
        if (hasHide) {
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
            if (view instanceof RecyclerView) {
                Bitmap bitmap = shotRecyclerView(((RecyclerView) view));
                bitmaps.add(bitmap);
            } else {
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                bitmaps.add(view.getDrawingCache());
            }

        }
        if (hasHide) {
            for (View view : hideView) {
                view.setVisibility(View.VISIBLE);
            }
        }
        return mosaicBitmapHorizontal(bgColor, bitmaps);
    }

    private static Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory/8;
            LruCache<String, Bitmap> bitmapCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    bitmapCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmapCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
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
