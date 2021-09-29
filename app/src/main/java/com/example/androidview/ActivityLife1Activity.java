package com.example.androidview;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2021/9/29 16:38
 * @description
 */
public class ActivityLife1Activity extends BaseActivity{

    private static final String TAG = "TAG1";
    com.example.androidview.databinding.ActivityMainBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = com.example.androidview.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Log.e(TAG, "onCreate: " );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }
}
