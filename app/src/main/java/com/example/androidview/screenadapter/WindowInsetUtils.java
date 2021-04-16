package com.example.androidview.screenadapter;

import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

/**
 * @author lgh on 2021/4/16 11:37
 * @description 刘海屏工具类
 * 涉及到view 会存在view还未初始化完的问题
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class WindowInsetUtils {


    public static boolean hasWindInsets(Window window) {
        View decorView = window.getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        if (insets != null) {
            DisplayCutout displayCutout = insets.getDisplayCutout();
            return displayCutout != null && displayCutout.getBoundingRects() != null &&
                    displayCutout.getBoundingRects().size() > 0 && displayCutout.getSafeInsetTop() > 0;
        }
        return false;
    }

    public static void setFullScreen(Window window) {
        if (hasWindInsets(window)) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            /*
             * 处理刘海屏
             * #LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 全屏模式下，内容下移，非全屏不受影响
             * #LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 允许内容延伸至刘海区
             * #LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 不允许内容延伸至刘海区
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                window.setAttributes(params);
            }

            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE //来帮助你的app来维持一个稳定的布局
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //确保appUI的主要部分不会因为被系统导航栏覆盖而结束
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //确保appUI的主要部分不会因为被系统状态栏覆盖而结束
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
                            | View.SYSTEM_UI_FLAG_FULLSCREEN //表示全屏，会将状态栏隐藏，只会隐藏状态栏
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);//粘性沉浸
        }

    }

}
