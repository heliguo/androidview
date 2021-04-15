package com.example.androidview.screenadapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * @author lgh on 2021/4/15 16:04
 * @description
 */
public class UIUtils {

    private static UIUtils instance;

    public static final float STANDARD_WIDTH = 1080f;

    public static final float STANDARD_HEIGHT = 1920f;

    public static float displayWidth;

    public static float displayHeight;

    public static float systemBarHeight;

    public static boolean isHorizontal;//是否是横屏


    public static UIUtils getInstance(Context context) {
        if (instance == null) {
            instance = new UIUtils(context);
        }
        return instance;
    }

    public static UIUtils getInstance() {
        if (instance == null) {
            throw new RuntimeException("");
        }
        return instance;
    }

    public static UIUtils notifyInstance(Context context) {
        instance = new UIUtils(context);
        return instance;
    }

    private UIUtils(Context context) {

        //计算缩放系数
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (displayWidth == 0 || displayHeight == 0) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            displayWidth = displayMetrics.widthPixels;
            displayHeight = displayMetrics.heightPixels;
            systemBarHeight = getSystemBarHeight(context);
            if (displayHeight > displayWidth) {
                displayHeight -= systemBarHeight;
            } else {
                isHorizontal = true;
                displayWidth -= systemBarHeight;
            }
        }

    }

    public float getHorizontalScaleValue() {
        return (float) displayWidth / (STANDARD_WIDTH - (isHorizontal ? systemBarHeight : 0));
    }

    public float getVerticalScaleValue() {
        return (float) displayHeight / (STANDARD_HEIGHT - (isHorizontal ? 0 : systemBarHeight));
    }

    private int getSystemBarHeight(Context context) {
        //        return getValue(context, "com.android.internal.R$dimen", "system_bar_height", 0);
        return getStatusBarHeight(context);
    }

    private int getValue(Context context, String dimeClass, String system_bar_height, int defaultValue) {

        try {
            Class<?> clazz = Class.forName(dimeClass);
            Object o = clazz.newInstance();
            Field field = clazz.getField(system_bar_height);
            int id = Integer.parseInt(field.get(o).toString());
            return context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏高度
     */
    public int getDaoHangHeight(Context context) {
        int resourceId;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;
    }
}
