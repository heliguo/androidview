package com.example.androidview.banner;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidview.R;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * @author lgh on 2021/5/29 14:29
 * @description
 */
public class HomeBannerAdapter extends BannerAdapter<BannerBean, HomeBannerAdapter.ImageHolder> {

    public HomeBannerAdapter(List<BannerBean> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {

        ImageView imageView = (ImageView) BannerUtils.getView(parent, R.layout.banner_image);
//        //通过裁剪实现圆角
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            BannerUtils.setBannerRound(imageView, 20);
//        }
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, BannerBean data, int position, int size) {
        Glide.with(holder.itemView)
                .load(data.getUrl())
                .thumbnail(Glide.with(holder.itemView).load(data.getUrl()).thumbnail(0.3f))
                .into(holder.imageView);
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view;
        }
    }
}


