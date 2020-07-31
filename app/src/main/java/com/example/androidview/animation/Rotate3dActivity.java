package com.example.androidview.animation;

import androidx.appcompat.app.AppCompatActivity;

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
                    0,360,100,0,0,false);
            animation.setDuration(2000);
            animation.setRotateMode(Rotate3dAnimation.RotateMode.X);
            mBinding.textView.startAnimation(animation);
        });

    }
}