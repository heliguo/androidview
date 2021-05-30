package com.example.androidview.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;

/**
 * @author lgh on 2021/5/29 15:04
 * @description
 */
public class BannerView extends FrameLayout {

    private Banner mBanner;

    public BannerView(@NonNull Context context) {
        this(context, null);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBanner = new Banner(context, attrs, defStyleAttr);
        addView(mBanner);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BannerUtils.setBannerRound(this,20);
        }
    }

    public void show() {
        ArrayList<BannerBean> arrayList = new ArrayList<>();
        arrayList.add(new BannerBean("https://cdn.pixabay.com/photo/2014/08/15/11/29/beach-418742__340.jpg"));
        arrayList.add(new BannerBean("https://cdn.pixabay.com/photo/2021/04/18/13/35/flowers-6188414__340.jpg"));
        arrayList.add(new BannerBean("https://cdn.pixabay.com/photo/2021/03/25/09/10/fog-6122490__340.jpg"));
        arrayList.add(new BannerBean("https://cdn.pixabay.com/photo/2021/03/07/19/38/forest-6077348__340.jpg"));
        arrayList.add(new BannerBean("https://cdn.pixabay.com/photo/2018/03/28/16/11/snow-3269681__340.jpg"));
        arrayList.add(new BannerBean("https://cdn.pixabay.com/photo/2021/03/25/09/10/fog-6122490__340.jpg"));

        HomeBannerAdapter adapter = new HomeBannerAdapter(arrayList);
        CircleIndicator circleIndicator = new CircleIndicator(getContext());
        IndicatorConfig indicatorConfig = circleIndicator.getIndicatorConfig();
        indicatorConfig.setSelectedColor(Color.BLUE);

        mBanner.setAdapter(adapter)
                .setIndicator(circleIndicator, true)
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                .setIndicatorSelectedColor(Color.GREEN)
                .setIndicatorMargins(new IndicatorConfig.Margins());


        mBanner.getViewPager2().registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

            }
        });

        mBanner.setOnBannerListener(new OnBannerListener<BannerBean>() {
            @Override
            public void OnBannerClick(BannerBean data, int position) {

            }
        });
    }
}
