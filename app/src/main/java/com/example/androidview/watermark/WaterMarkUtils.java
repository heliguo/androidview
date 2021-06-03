package com.example.androidview.watermark;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntRange;
import androidx.core.view.ViewCompat;

import java.util.HashMap;

/**
 * @author lgh on 2021/5/28 16:46
 * @description
 */
public class WaterMarkUtils {

    private static WaterMarkUtils INSTANCE;

    private final HashMap<Activity, FrameLayout> mActivities = new HashMap<>();

    private WaterMarkUtils() {
    }

    public static WaterMarkUtils getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new WaterMarkUtils();
        }
        return INSTANCE;
    }

    public void waterMarker(Activity activity, WaterMarkDrawable drawable) {
        if (activity.isFinishing() || mActivities.containsKey(activity)) {
            return;
        }
        FrameLayout decorView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewCompat.setBackground(frameLayout, drawable);
        mActivities.put(activity, frameLayout);
        decorView.addView(frameLayout);
    }

    public void waterMarker(Activity activity, WaterMarkDrawable drawable, @IntRange(from = 0, to = 10) int index) {
        if (activity.isFinishing() || mActivities.containsKey(activity)) {
            return;
        }
        FrameLayout decorView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewCompat.setBackground(frameLayout, drawable);
        mActivities.put(activity, frameLayout);
        decorView.addView(frameLayout, index);
    }

    public void clearWaterMark(Activity activity) {
        if (activity.isFinishing() || !mActivities.containsKey(activity)) {
            return;
        }
        FrameLayout decorView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        decorView.removeView(mActivities.get(activity));
        mActivities.remove(activity);
    }

}
