package com.example.androidview.smarttablayout;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * @创建者 lgh
 * @创建时间 2019-07-10 10:55
 * @描述 dp与px转换 屏幕尺寸
 */
public class ScreenUtil {

    /**
     * 屏幕size
     */
    public static Point getSizeNew(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        Point size = new Point();
        size.x = (int) (dm.widthPixels * dm.density);
        size.y = (int) (dm.heightPixels * dm.density);
        return size;

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)  。
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 。
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param context 上下文
     * @param px      int类型
     * @return int dp
     */
    public static int px2dp(Context context, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }

    //获取手机屏幕宽度
    public static int getScreenWidth(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    //获取手机屏幕高度
    public static int getScreenHeight(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }


}
