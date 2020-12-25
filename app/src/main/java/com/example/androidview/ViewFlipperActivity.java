package com.example.androidview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author lgh
 * @description ViewFlipper示例 渐变颜色状态栏
 */
public class ViewFlipperActivity extends AppCompatActivity {

    private static final String TAG = "ViewFlipperActivity";

    ViewFlippers mViewFlipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        initView();
    }

    private void initView() {
        mViewFlipper = findViewById(R.id.view_filpper);
        mViewFlipper.setFlipInterval(2000);
        mViewFlipper.startFlipping();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            initStatusBar();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: ");
    }

    private View statusBarView;

    /**
     * 颜色渐变
     */
    private void initStatusBar() {
        if (statusBarView == null) {
            //利用反射机制修改状态栏背景
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }

        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.bg_title_gradient);
        } else {
            Log.e(TAG, "initStatusBar: ");
        }
    }

}