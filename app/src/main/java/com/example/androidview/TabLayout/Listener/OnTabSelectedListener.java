package com.example.androidview.TabLayout.Listener;

import com.google.android.material.tabs.TabLayout;

/**
 * @author hugeterry(http : / / hugeterry.cn)
 */

public interface OnTabSelectedListener {

    void onTabSelected(TabLayout.Tab tab);

    void onTabUnselected(TabLayout.Tab tab);

    void onTabReselected(TabLayout.Tab tab);
}
