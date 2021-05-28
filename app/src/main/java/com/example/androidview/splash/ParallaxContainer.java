package com.example.androidview.splash;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.androidview.R;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/4/15 10:27
 * @description
 */
public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private List<ParallaxFragment> fragments;

    private ImageView stableView;
    private ParallaxPagerAdapter mAdapter;

    public ParallaxContainer(@NonNull Context context) {
        this(context, null);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setStableView(ImageView stableView) {
        this.stableView = stableView;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        addView(this.stableView, 1, params);
    }

    public void setUp(int[] childId) {
        fragments = new ArrayList<>();
        for (int value : childId) {
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutId", value);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        ViewPager viewPager = new ViewPager(getContext());
        viewPager.setId(R.id.parallax_pager);
        SplashActivity activity = (SplashActivity) getContext();
        mAdapter = new ParallaxPagerAdapter(activity.getSupportFragmentManager(), fragments);
        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(this);
        addView(viewPager, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int containerWidth = getWidth();
        ParallaxFragment outFragment = null;
        if (position > 0) {
            outFragment = fragments.get(position - 1);
        }
        //获取到退出的页面
        ParallaxFragment inFragment = fragments.get(position);

        if (outFragment != null) {
            //获取Fragment上所有的视图，实现动画效果
            List<View> inViews = outFragment.getParallaxViews();
            if (inViews != null) {
                for (View view : inViews) {
                    //
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    ViewHelper.setTranslationX(view, (containerWidth - positionOffsetPixels) * tag.xIn);
                    ViewHelper.setTranslationY(view, (containerWidth - positionOffsetPixels) * tag.yIn);
                }

            }

        }
        if (inFragment != null) {
            List<View> outViews = inFragment.getParallaxViews();
            if (outViews != null) {
                for (View view : outViews) {
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    //仔细观察退出的fragment中view从原始位置开始向上移动，translationY应为负数
                    ViewHelper.setTranslationY(view, 0 - positionOffsetPixels * tag.yOut);
                    ViewHelper.setTranslationX(view, 0 - positionOffsetPixels * tag.xOut);
                }
            }
        }


    }

    @Override
    public void onPageSelected(int position) {
        if (position == mAdapter.getCount() - 1) {
            stableView.setVisibility(INVISIBLE);
        } else {
            stableView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animation = (AnimationDrawable) stableView.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                animation.start();
                break;
            default:
            case ViewPager.SCROLL_STATE_IDLE:
                animation.stop();
                break;
        }
    }
}
