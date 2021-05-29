package com.example.androidview.bytedance;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.google.android.material.appbar.AppBarLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.reactivestreams.Subscription;

import java.util.ArrayList;

/**
 * @author lgh on 2021/5/28 19:15
 * @description
 */
public class ByteDanceActivity extends BaseActivity {

    private ImageView ivBg;
    private CircleImageView ivHead;
    private RelativeLayout rlDropdown;
    private LinearLayout llFocus;
    private LinearLayout llFans;
    private LinearLayout llGetLike;
    private ImageView ivReturn;
    private TextView tvTitle;
    private TextView tvFocus;
    private IconFontTextView ivMore;
    private Toolbar toolbar;
    private SmartTabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private TextView tvNickname;
    private TextView tvSign;
    private TextView tvGetLikeCount;
    private TextView tvFocusCount;
    private TextView tvFansCount;
    private TextView tvAddFocus;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommPagerAdapter pagerAdapter;
    private UserBean curUserBean;
    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_byte_dance);
        initView();
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        appBarLayout = findViewById(R.id.appbarlayout);
        viewPager = findViewById(R.id.viewpager);

        ivReturn = findViewById(R.id.iv_return);//toolbar 左上角返回
        tvTitle = findViewById(R.id.tv_title);//toolbar 中间 昵称
        ivMore = findViewById(R.id.iv_more);//toolbar 右上角 更多图标

        ivBg = findViewById(R.id.iv_bg);//上方背景图片
        ivHead = findViewById(R.id.iv_head);//头像 圆形
        tvAddFocus = findViewById(R.id.tv_addfocus);//添加关注
        rlDropdown = findViewById(R.id.rl_dropdown);//添加关注 右边更多

        tvFocus = findViewById(R.id.tv_focus);//关注

        tvNickname = findViewById(R.id.tv_nickname);//昵称
        tvSign = findViewById(R.id.tv_sign);//介绍内容 隐藏

        llGetLike = findViewById(R.id.ll_getlike);//获赞数量 LinearLayout
        tvGetLikeCount = findViewById(R.id.tv_getlike_count);//获赞数量

        llFocus = findViewById(R.id.ll_focus);//关注数量 LinearLayout
        tvFocusCount = findViewById(R.id.tv_focus_count);//关注数量

        llFans = findViewById(R.id.ll_fans);//粉丝数量 LinearLayout
        tvFansCount = findViewById(R.id.tv_fans_count);//粉丝数


    }
}
