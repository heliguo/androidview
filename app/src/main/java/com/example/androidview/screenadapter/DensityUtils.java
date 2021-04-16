package com.example.androidview.screenadapter;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * @author lgh on 2021/4/16 9:41
 * @description
 */
public class DensityUtils {

    private static float appDensity;//屏幕密度
    private static float appScaledDensity;//字体缩放比例

    public static void setDensity(Application application, Activity activity, float matchBase) {

        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (appDensity == 0) {
            appDensity = displayMetrics.density;
            appScaledDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {//字体发生更改
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        //计算目标值density、scaleDensity、densityDpi
        float targetDensity = displayMetrics.widthPixels / matchBase;
        float targetScaleDensity = targetDensity * (appScaledDensity / appDensity);
        float targetDensityDpi = targetDensity * 160;

        //替换Activity的density、scaleDensity、densityDpi
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        dm.density = targetDensity;
        dm.scaledDensity = targetScaleDensity;
        dm.densityDpi = (int) targetDensityDpi;
    }
}
