package com.example.androidview.discview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.androidview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/5/7 17:05
 * @description
 */
public class DiscView extends RelativeLayout {

    private List<View> mDiscLayouts = new ArrayList<>();
    private List<Integer> mMusicDatas = new ArrayList<>();
    private List<ObjectAnimator> mDiscAnimators = new ArrayList<>();
    ImageView musicNeedle;
    ImageView musicCircle;
    private ObjectAnimator mNeedleAnimator;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    public DiscView(Context context) {
        this(context, null);
    }

    public DiscView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMusicDataList(@NonNull List<Integer> musicDataList) {
        if (musicDataList.isEmpty())
            return;
        mDiscLayouts.clear();
        mMusicDatas.clear();
        mDiscAnimators.clear();
        mMusicDatas.addAll(musicDataList);
        for (Integer musicData : mMusicDatas) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_disc, mViewPager, false);
            mDiscLayouts.add(inflate);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        mViewPager.setAdapter(new ViewPagerAdapter(mDiscLayouts));
    }

    private void initView() {
        musicNeedle = findViewById(R.id.ivNeedle);
        mViewPager = findViewById(R.id.vpDiscContain);
        musicCircle = findViewById(R.id.ivDiscBlackgound);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_disc_blackground);
        Bitmap bitmap = Bitmap.createScaledBitmap(bitmap1, 813, 813, false);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        musicCircle.setImageDrawable(roundedBitmapDrawable);
//        musicNeedle.setPivotX(2);//缩放中心

    }

    class ViewPagerAdapter extends PagerAdapter {

        public ViewPagerAdapter(List<View> discLayouts) {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View discLayout = mDiscLayouts.get(position);
            container.addView(discLayout);
            return discLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mDiscLayouts.get(position));
        }

        @Override
        public int getCount() {
            return mDiscLayouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
