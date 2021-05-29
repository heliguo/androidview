package com.example.androidview.fastblur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.utils.FastBlurUtil;

/**
 * @author lgh on 2021/5/8 15:12
 * @description
 */
public class FastBlurActivity extends BaseActivity {

    ImageView mImageView;
    ImageView mImageView2;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_blur);
        mImageView = findViewById(R.id.fast_blur_iv);
        mImageView2 = findViewById(R.id.palette_iv);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.butterfly_340);
        mImageView.setImageBitmap(mBitmap);
//        mImageView.post(() -> blur(mBitmap, mImageView));
    }


    private int index;

    public void palettes(View view) {
        Palette.from(mBitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable @org.jetbrains.annotations.Nullable Palette palette) {
                if (palette == null) {
                    return;
                }
                //柔和而暗的颜色
                int darkMutedColor = palette.getDarkMutedColor(Color.BLUE);
                //鲜艳和暗的颜色
                int darkVibrantColor = palette.getDarkVibrantColor(Color.BLUE);
                //亮和鲜艳的颜色
                int lightVibrantColor = palette.getLightVibrantColor(Color.BLUE);
                //亮和柔和的颜色
                int lightMutedColor = palette.getLightMutedColor(Color.BLUE);
                //柔和颜色
                int mutedColor = palette.getMutedColor(Color.BLUE);

                int vibrantColor = palette.getVibrantColor(Color.BLUE);
                if (index == 0 || index >= 6) {
                    index = 0;
                    mImageView2.setBackgroundColor(darkMutedColor);
                    index++;
                } else if (index == 1) {
                    mImageView2.setBackgroundColor(darkVibrantColor);
                    index++;
                } else if (index == 2) {
                    mImageView2.setBackgroundColor(lightVibrantColor);
                    index++;
                } else if (index == 3) {
                    mImageView2.setBackgroundColor(lightMutedColor);//比较好
                    index++;
                } else if (index == 4) {
                    mImageView2.setBackgroundColor(mutedColor);
                    index++;
                } else if (index == 5) {
                    mImageView2.setBackgroundColor(vibrantColor);
                    index++;
                }
            }
        });

    }
}
