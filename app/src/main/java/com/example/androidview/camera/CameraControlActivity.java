package com.example.androidview.camera;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.databinding.ActivityCameraControlBinding;

/**
 * @author lgh on 2021/10/22 11:26
 * @description
 */
public class CameraControlActivity extends BaseActivity {



    ActivityCameraControlBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCameraControlBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }

    private void releaseCameraAndPreview() {
    }
}
