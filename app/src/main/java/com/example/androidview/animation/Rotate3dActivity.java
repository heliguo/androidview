package com.example.androidview.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import com.example.androidview.R;
import com.example.androidview.databinding.ActivityRotate3dBinding;

public class Rotate3dActivity extends AppCompatActivity {

    ActivityRotate3dBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRotate3dBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.button3.setOnClickListener(v -> {
            Rotate3dAnimation animation = new Rotate3dAnimation(
                    0, 360, 0, 0, 0, false);
            animation.setDuration(2000);
            animation.setRotateMode(Rotate3dAnimation.RotateMode.X);
            mBinding.textView.startAnimation(animation);
        });

        mBinding.button4.setOnClickListener(v -> {
            ValueAnimator animator = ObjectAnimator.ofInt(mBinding.textView,
                    "backgroundColor",/*Red*/0xFFFF8080,/*Blue*/0xFF8080FF);
            animator.setDuration(2000);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.start();
        });

        mBinding.button5.setOnClickListener(v -> {
            ObjectAnimator.ofFloat(mBinding.textView,"translationY",0,mBinding.textView.getMeasuredHeight(),0)
                    .setDuration(2000).start();

        });

        mBinding.button6.setOnClickListener(v -> {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(mBinding.textView,"rotationX",0,360,0),
                    ObjectAnimator.ofFloat(mBinding.textView,"rotationY",0,180,0),
                    ObjectAnimator.ofFloat(mBinding.textView,"rotation",0,-90,0),
                    ObjectAnimator.ofFloat(mBinding.textView,"translationX",0,90,0),
                    ObjectAnimator.ofFloat(mBinding.textView,"translationY",0,90,0),
                    ObjectAnimator.ofFloat(mBinding.textView,"scaleX",1,1.5f,1f),
                    ObjectAnimator.ofFloat(mBinding.textView,"scaleY",1,0.5f,1f),
                    ObjectAnimator.ofFloat(mBinding.textView,"alpha",1,0.25f,1)
            );
            set.setDuration(5000).start();
        });

    }
}