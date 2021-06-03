package com.example.androidview.transform;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.util.BannerUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author lgh on 2021/5/30 17:35
 * @description
 */
public class SimplePagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private List<String> data;
    private final RequestOptions mOptions;

    public SimplePagerAdapter(List<String> data) {
        this.data = data;
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(16);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        mOptions = RequestOptions.bitmapTransform(roundedCorners);
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        container.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(imageView).load(data.get(position)).apply(mOptions).into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView(((View) object));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }
}
