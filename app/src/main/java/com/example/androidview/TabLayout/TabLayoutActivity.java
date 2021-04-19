package com.example.androidview.TabLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.TabLayout.Fragment.TabFragment;
import com.example.androidview.TabLayout.Listener.CollapsedStateListener;
import com.example.androidview.screenadapter.UIUtils;
import com.example.androidview.toobar.StatusBarUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class TabLayoutActivity extends BaseActivity {

    private TabLayout mTableLayout;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"Android", "iOS", "Web", "Other", "Android", "iOS", "Web", "Other"};
    private ViewPager mViewPager;
    private int[] mColorArray;
    private int[] mImageArray;
    private ImageView mImageView;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.getInstance(getApplicationContext());
        setContentView(R.layout.activity_tablayout);
        mImageView = findViewById(R.id.imageview);
        mCollapsingToolbarLayout = findViewById(R.id.collapsingtoolbarlayout);
        initToolbar();
        initFragments();
        initViewPager();

        mImageArray = new int[]{
                R.drawable.bg_android,
                R.drawable.bg_ios,
                R.drawable.bg_js,
                R.drawable.bg_other,
                R.drawable.bg_android,
                R.drawable.bg_ios,
                R.drawable.bg_js,
                R.drawable.bg_other};
        mColorArray = new int[]{
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light};
        initTabLayout();
        initAppBarLayout();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.menu));
        StatusBarUtils.setStatusBar(this, mToolbar);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, true);
    }

    private void initAppBarLayout() {
        mAppBarLayout = findViewById(R.id.app_bar_layout);
        mAppBarLayout.addOnOffsetChangedListener(new CollapsedStateListener() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, CollapsedStateListener.State state,int offset) {
                if (state == State.EXPANDED) {
                    //展开


                } else if (state == State.COLLAPSED) {
                    //折叠


                } else {
                    //其他

                }
            }
        });
    }

    private void initTabLayout() {
        mTableLayout = findViewById(R.id.tabLayout);
        mTableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setupTabLayout();
        mTableLayout.setupWithViewPager(mViewPager);
    }

    private void setupTabLayout() {
        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mToolbar.setTitle(mTitles[tab.getPosition()]);

                mImageView.setBackgroundResource(mImageArray[tab.getPosition()]);

                mCollapsingToolbarLayout.setContentScrimColor(
                        ContextCompat.getColor(TabLayoutActivity.this, mColorArray[tab.getPosition()]));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                mImageView.startAnimation(AnimationUtils.loadAnimation(TabLayoutActivity.this, R.anim.anim_dismiss));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                mImageView.setAnimation(AnimationUtils.loadAnimation(TabLayoutActivity.this, R.anim.anim_show));
            }
        });
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        for (String title : mTitles) {
            mFragments.add(TabFragment.getInstance(title));
        }
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.ll_vv_content);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        mViewPager.setCurrentItem(3, false);//设置默认显示页
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //        if (item.getItemId() == android.R.id.home) {
        //            finish();
        //        }
        //        switch (item.getItemId()) {
        //            case R.id.action_about:
        //                break;
        //            case R.id.action_about_me:
        //                break;
        //        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
