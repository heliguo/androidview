package com.example.androidview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

/**
 * @author lgh
 */
public class ViewFlipperActivity extends AppCompatActivity {

    ViewFlipper mViewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        initView();
    }

    private void initView() {
        mViewFlipper = findViewById(R.id.view_filpper);
        int a = 3;
        for (int i = 0; i < a; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_viewflipper, null);
            mViewFlipper.addView(view);
        }
        mViewFlipper.setFlipInterval(2000);
        mViewFlipper.startFlipping();
    }
}