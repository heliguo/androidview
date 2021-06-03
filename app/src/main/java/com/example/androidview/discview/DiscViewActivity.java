package com.example.androidview.discview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/5/7 16:12
 * @description
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class DiscViewActivity extends BaseActivity {

    private BackgroundAnimationRelativeLayout mBackgroundLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disc_view);
        mBackgroundLayout = findViewById(R.id.rootLayout);
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.ic_back)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
