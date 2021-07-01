package com.example.androidview.hencoder.draw.animation_6_7;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.ViewPropertyAnimator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.databinding.ActivityAnimationBinding;

import static android.view.View.LAYER_TYPE_HARDWARE;

/**
 * @author lgh on 2020/12/9 10:22
 * @description ViewPropertyAnimator、ObjectAnimator、ValueAnimator、AnimatorSet
 */
public class AnimationActivity extends AppCompatActivity {

    ActivityAnimationBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAnimationBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.view.setTranslationX(100);//借助RenderNode刷新界面

        ////////////////////////////////////////////////////////////////////////////////////////////
        ViewPropertyAnimator animate = mBinding.view.animate();
        animate.translationX(100);

        ////////////////////////////////////////////////////////////////////////////////////////////
        ObjectAnimator animator = ObjectAnimator.ofInt(mBinding.imageC, "backgroundResource", 0xffff0000, 0xff00ff00);
        animator.setEvaluator(new HsvEvaluator());

        ////////////////////////////////////////////////////////////////////////////////////////////
        ObjectAnimator animator1 = ObjectAnimator.ofObject(mBinding.view, "position",
                new PointFEvaluator(), new PointF(0, 0), new PointF(1, 1));
        animator1.start();

        ////////////////////////////////////////////////////////////////////////////////////////////
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 1);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 2);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 1);

        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(mBinding.view, holder1, holder2, holder3);
        animator2.start();

        ////////////////////////////////////////////////////////////////////////////////////////////
        // 在 0% 处开始
        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
        // 时间经过 50% 的时候，动画完成度 100%
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100);
        // 时间见过 100% 的时候，动画完成度倒退到 80%，即反弹 20%
        Keyframe keyframe3 = Keyframe.ofFloat(1, 80);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);

        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(mBinding.view, holder);
        animator3.setDuration(3000);
        animator3.start();

        //view 黑白处理
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        mBinding.view.setLayerType(LAYER_TYPE_HARDWARE, paint);//关闭硬件加速
    }
}
