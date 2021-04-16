package com.example.androidview.screenadapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
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

    private static float project_width = STANDARD_WIDTH;

    private static float project_height = STANDARD_HEIGHT;

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
            throw new RuntimeException("You should call the constructor with the arguments firstly");
        }
        return instance;
    }

    public static void notifyInstance(Context context) {
        instance = new UIUtils(context);
    }

    public static void setProject_width(float project_width) {
        UIUtils.project_width = project_width;
    }

    public static void setProject_height(float project_height) {
        UIUtils.project_height = project_height;
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
        return (float) displayWidth / (project_width - (isHorizontal ? systemBarHeight : 0));
    }

    public float getVerticalScaleValue() {
        return (float) displayHeight / (project_height - (isHorizontal ? 0 : systemBarHeight));
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

    public static int getStatusBarHeight(Context context) {
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
    public int getNavigationHeight(Context context) {
        int resourceId;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;
    }

    public void matchView(View view, ViewGroup.MarginLayoutParams params) {
        if (displayWidth == 0) {
            UIUtils.getInstance(view.getContext().getApplicationContext());
        }
        params.width = (int) (params.width * getHorizontalScaleValue());
        params.height = (int) (params.height * getVerticalScaleValue());
        params.rightMargin = (int) (params.rightMargin * getHorizontalScaleValue());
        params.leftMargin = (int) (params.leftMargin * getHorizontalScaleValue());
        params.topMargin = (int) (params.topMargin * getVerticalScaleValue());
        params.bottomMargin = (int) (params.bottomMargin * getVerticalScaleValue());
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        view.setPadding(((int) (paddingLeft * getHorizontalScaleValue())), ((int) (paddingTop * getVerticalScaleValue())),
                ((int) (paddingRight * getHorizontalScaleValue())), ((int) (paddingBottom * getVerticalScaleValue())));
    }

}
