package com.example.androidview.floatview;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.core.view.ViewCompat;

import java.lang.ref.WeakReference;

/**
 * @author lgh on 2021/5/28 10:46
 * @description
 */
public class FloatViewManager {

    private FloatView mFloatView;
    private static FloatViewManager INSTANCE;

    private WeakReference<FrameLayout> mContainer;

    @LayoutRes
    private int mLayoutId;

    private ViewGroup.LayoutParams mLayoutParams;


    private FloatViewManager() {
    }

    public static FloatViewManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FloatViewManager();
        }
        return INSTANCE;
    }

    public FloatViewManager attach(Activity activity) {
        attach(getActivityRoot(activity));
        return this;
    }

    public FloatViewManager attach(FrameLayout container) {
        if (container == null || mFloatView == null) {
            mContainer = new WeakReference<>(container);
            return this;
        }
        if (mFloatView.getParent() == container) {
            return this;
        }
        if (mFloatView.getParent() != null) {
            ((ViewGroup) mFloatView.getParent()).removeView(mFloatView);
        }
        mContainer = new WeakReference<>(container);
        container.addView(mFloatView);
        return this;
    }

    public FloatViewManager detach(Activity activity) {
        detach(getActivityRoot(activity));
        return this;
    }

    public FloatViewManager detach(FrameLayout container) {
        if (mFloatView != null && container != null && ViewCompat.isAttachedToWindow(mFloatView)) {
            container.removeView(mFloatView);
        }
        if (getContainer() == container) {
            mContainer = null;
        }
        return this;
    }
//
//    public FloatViewManager add(Context context) {
//        ensureFloatingView(context);
//        return this;
//    }

//    private void ensureFloatingView(Context context) {
//        synchronized (this) {
//            if (mFloatingView != null) {
//                return;
//            }
//            mFloatingView = new FloatingView(context, mLayoutId);
//            mFloatingView.setLayoutParams(mLayoutParams);
//            addViewToWindow(mFloatingView);
//        }
//    }

    public FloatView getView() {
        return mFloatView;
    }

    public FloatViewManager customView(FloatView viewGroup) {
        mFloatView = viewGroup;
        return this;
    }

    public FloatViewManager customView(@LayoutRes int resource) {
        mLayoutId = resource;
        return this;
    }

    public FloatViewManager layoutParams(ViewGroup.LayoutParams params) {
        mLayoutParams = params;
        if (mFloatView != null) {
            mFloatView.setLayoutParams(params);
        }
        return this;
    }

    public FloatViewManager listener(FloatView.FloatViewListener floatViewListener) {
        if (mFloatView != null) {
            mFloatView.setFloatViewListener(floatViewListener);
        }
        return this;
    }


    public FloatViewManager remove() {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (mFloatView == null) {
                return;
            }
            if (ViewCompat.isAttachedToWindow(mFloatView) && getContainer() != null) {
                getContainer().removeView(mFloatView);
            }
            mFloatView = null;
        });
        return this;
    }


    private FrameLayout getContainer() {
        if (mContainer == null) {
            return null;
        }
        return mContainer.get();
    }

    private FrameLayout getActivityRoot(Activity activity) {
        if (activity == null) {
            return null;
        }
        try {
            return activity.getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FrameLayout.LayoutParams getDefaultParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.START;
        params.setMargins(13, params.topMargin, params.rightMargin, 500);
        return params;
    }
}
