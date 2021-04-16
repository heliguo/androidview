package com.example.androidview.toobar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.androidview.screenadapter.UIUtils;

/**
 * @author lgh on 2021/4/16 15:10
 * @description
 */
public class StatusBarUtils {

    //设置状态栏颜色
    public static void setStatusBarFullScreen(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    public static void setTranslucentStatusBar(Activity activity) {
        setStatusBarFullScreen(activity, Color.TRANSPARENT);
    }

    public static void setStatusBar(Activity activity, View toolbar, int color) {
        setStatusBarFullScreen(activity,color);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int count = decorView.getChildCount();
        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarVIew) {
            return;
        }
        StatusBarVIew view = new StatusBarVIew(activity);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UIUtils.getStatusBarHeight(activity.getApplicationContext()));
        view.setLayoutParams(params);
        decorView.addView(view);
        if (toolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            layoutParams.topMargin = UIUtils.getStatusBarHeight(activity.getApplicationContext());
        }
    }

    public static void setStatusBar(Activity activity, View toolbar) {
        setStatusBar(activity, toolbar, Color.argb(0, 0, 0, 0));
    }

    //设置状态栏字体颜色
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

}
