package com.example.androidview.discview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.androidview.R;

/**
 * @author lgh on 2021/5/7 17:07
 * @description layer drawable 图层叠加
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BackgroundAnimationRelativeLayout extends RelativeLayout {

    private LayerDrawable mLayerDrawable;
    private ObjectAnimator mObjectAnimator;

    public BackgroundAnimationRelativeLayout(Context context) {
        this(context, null);
    }

    public BackgroundAnimationRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundAnimationRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_background);
        Drawable[] drawables = new Drawable[2];
        /*初始化时先将前景与背景设置为一致*/
        drawables[0] = drawable;
        drawables[1] = drawable;
        mLayerDrawable = new LayerDrawable(drawables);
        mObjectAnimator = ObjectAnimator.ofFloat(this, "number", 0f, 1f);
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setInterpolator(new AccelerateInterpolator());
        mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue() * 255;
                mLayerDrawable.getDrawable(1).setAlpha(alpha);
                setBackground(mLayerDrawable);
            }
        });
        mObjectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }


            @Override
            public void onAnimationEnd(Animator animation) {
                mLayerDrawable.setDrawable(0, mLayerDrawable.getDrawable(1));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void setForeground(Drawable drawable) {
        mLayerDrawable.setDrawable(1, drawable);
        mObjectAnimator.start();
    }
}
