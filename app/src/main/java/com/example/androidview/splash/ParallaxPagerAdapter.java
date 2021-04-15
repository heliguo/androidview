package com.example.androidview.splash;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author lgh on 2021/4/15 10:26
 * @description
 */
public class ParallaxPagerAdapter extends FragmentPagerAdapter {

    private final List<ParallaxFragment> mFragments;

    public ParallaxPagerAdapter(@NonNull FragmentManager fm, List<ParallaxFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
