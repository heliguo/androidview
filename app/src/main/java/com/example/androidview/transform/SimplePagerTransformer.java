package com.example.androidview.transform;

import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

/**
 * @author lgh on 2021/5/30 20:50
 * @description
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SimplePagerTransformer implements ViewPager.PageTransformer {

    private ViewPager mViewPager;
    private final PagerAdapter mAdapter;

    public SimplePagerTransformer(ViewPager viewPager) {
        mViewPager = viewPager;
        mAdapter = mViewPager.getAdapter();
    }

    @Override
    public void transformPage(@NonNull @NotNull View view, float position) {

    }
}
