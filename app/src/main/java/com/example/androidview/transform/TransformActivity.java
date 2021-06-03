package com.example.androidview.transform;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.utils.JsonUtils;
import com.google.gson.Gson;
import com.youth.banner.util.BannerUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgh on 2021/5/30 17:13
 * @description
 */
public class TransformActivity extends BaseActivity {

    private float scaleBaseProportion = 0.99f;
    private float offsetX = 0.01f;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_transform);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BannerUtils.setBannerRound(findViewById(R.id.ll_roots),10);
        }
        ViewPager viewPager = findViewById(R.id.view_pager_transform);
        List<String> list = new ArrayList<>();
        TopBannerBean bean = new Gson().fromJson(JsonUtils.getJson(this, "syone.json"), TopBannerBean.class);
        TopBannerBean.DataBean data1 = bean.getData();
        TopBannerBean.DataBean.LblistBean lblist = data1.getLblist();
        List<TopBannerBean.DataBean.LblistBean.FocusesBean> focuses = lblist.getFocuses();
        if (focuses.size() == 0) {
            return;
        }
        for (TopBannerBean.DataBean.LblistBean.FocusesBean dataBean : focuses) {
            list.add(dataBean.getFocusImageUrl().get(0));
        }
        viewPager.setAdapter(new SimplePagerAdapter(list));
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull @NotNull View view, float position) {
                float translation = -position * view.getWidth() + position * offsetX * view.getWidth();
                view.setTranslationX(translation);

                float scaleX = (float) Math.pow(scaleBaseProportion,position);
                float scaleY = (float) Math.pow(scaleBaseProportion,position);
                view.setScaleX(scaleX);
                view.setScaleY(scaleY);
                if (position<0){
                    Log.e("TAGTAGTAGTAGTAG", "-------: "+position );
                    view.setAlpha(position+1);
                    view.setTranslationX(view.getTranslationX()+position*view.getWidth());
                }else {
                    Log.e("TAGTAGTAGTAGTAG", "++++++++: "+position );
                }
            }
        });

    }
}
