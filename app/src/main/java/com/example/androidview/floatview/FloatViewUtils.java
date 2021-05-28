package com.example.androidview.floatview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/5/28 10:06
 * @description
 */
public class FloatViewUtils implements Application.ActivityLifecycleCallbacks {

    private static FloatViewUtils INSTANCE;

    /**
     * 忽略 list
     */
    private final List<Class> page = new ArrayList<>();

    @LayoutRes
    private int layout;

    private FrameLayout.LayoutParams mLayoutParams = getDefaultParams();

    private View.OnClickListener mClickListener;

    private FloatViewUtils() {
    }


    public static FloatViewUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FloatViewUtils();
        }
        return INSTANCE;
    }

    public FloatViewUtils layout(int layout) {
        this.layout = layout;
        return this;
    }

    public FloatViewUtils ignore(List<Class> activities) {
        page.addAll(activities);
        return this;
    }

    public FloatViewUtils ignore(Class activities) {
        page.add(activities);
        return this;
    }

    public void needShow(Class activity) {
        if (page.isEmpty()) {
            return;
        }
        if (page.contains(activity))
            page.remove(activity);
    }

    public void needShow(List<Class> activities) {
        if (page.isEmpty()) {
            return;
        }
        for (Class activity : activities) {
            if (page.contains(activity)) {
                page.remove(activity);
            }
        }
    }

    public FloatViewUtils listener(View.OnClickListener listener) {
        mClickListener = listener;
        return this;
    }

    public FloatViewUtils layoutParams(FrameLayout.LayoutParams params) {
        mLayoutParams = params;
        return this;
    }


    public void show(Activity activity) {
        initShow(activity);
        activity.getApplication().registerActivityLifecycleCallbacks(this);
    }

    public void dismiss(Activity activity) {
        FloatViewManager.getInstance().remove();
        FloatViewManager.getInstance().detach(activity);
        activity.getApplication().unregisterActivityLifecycleCallbacks(this);
    }

    private void initShow(Activity activity) {
        if (FloatViewManager.getInstance().getView() == null) {
            FloatViewManager.getInstance().customView(new FloatView(activity, layout));
        }
        if (mLayoutParams == null) {
            mLayoutParams = getDefaultParams();
        }
        FloatViewManager.getInstance().layoutParams(mLayoutParams);
        FloatViewManager.getInstance().attach(activity);
        if (mClickListener != null) {
            FloatViewManager.getInstance().getView().setFloatViewListener(magnetView ->
                    mClickListener.onClick(magnetView));

        }
    }

    private FrameLayout.LayoutParams getDefaultParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.START;
        params.setMargins(0, params.topMargin, params.rightMargin, 500);
        return params;
    }


    @Override
    public void onActivityCreated(@NonNull @NotNull Activity activity, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull @NotNull Activity activity) {
        if (isActivityInValid(activity)) {
            return;
        }
        initShow(activity);
    }

    @Override
    public void onActivityResumed(@NonNull @NotNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull @NotNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull @NotNull Activity activity) {
        if (isActivityInValid(activity)) {
            return;
        }
        FloatViewManager.getInstance().detach(activity);
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull @NotNull Activity activity, @NonNull @NotNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull @NotNull Activity activity) {

    }

    private boolean isActivityInValid(Activity activity) {
        return page.contains(activity.getClass());
    }
}
