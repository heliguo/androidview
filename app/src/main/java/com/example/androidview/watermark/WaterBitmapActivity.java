package com.example.androidview.watermark;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/5/28 17:02
 * @description
 */
public class WaterBitmapActivity extends BaseActivity {

    private WaterMarkDrawable mDrawable;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_bitmap);
    }

    public void show(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        mDrawable = new WaterMarkDrawable(this, bitmap, -30);
        mDrawable.setAlpha(100);
        WaterMarkUtils.getInstance().waterMarker(this, mDrawable);
    }

    public void clear(View view) {
        WaterMarkUtils.getInstance().clearWaterMark(this);
    }

    public void alpha(View view) {
        if (mDrawable != null) {
            mDrawable.setAlpha(200);
            mDrawable.setRepeatHorizontalRatio(0.5f);
        }
    }
}
