package com.example.androidview.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * author:lgh on 2019-09-02 17:10
 */
public class BitmapUtils {

    public static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final Canvas CANVAS = new Canvas();
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    public static Bitmap createBitmapFromView(View view) {
        view.clearFocus();//是view失去焦点恢复原本样式
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (CANVAS) {
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);
                CANVAS.setBitmap(null);
            }
        }
        return bitmap;
    }

    /**
     * retryCount 重试次数，发生内存溢出时回收重试
     */
    private static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
            if (retryCount>0){
                System.gc();
                return createBitmapSafely(width,height,config,-1);
            }
        }
        return null;
    }
}
