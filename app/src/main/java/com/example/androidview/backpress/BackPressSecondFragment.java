package com.example.androidview.backpress;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.watermark.WaterMarkDrawable;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lgh on 2021/4/1 15:48
 * @description
 */
public class BackPressSecondFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_back_press_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        com.tencent.smtt.sdk.WebView webView = view.findViewById(R.id.web);
        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持JavaScript
        webSettings.setBuiltInZoomControls(true);//放大缩小，不支持已适配的页面
        webSettings.setUseWideViewPort(true);//双击缩放
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                webView.loadUrl(webResourceRequest.getUrl().toString());
                return true;
            }
        });
        FrameLayout decorView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        SimpleDateFormat createTimeSdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List<String> labels = new ArrayList<>();
        labels.add("用户名：张三");
        labels.add("日期：" + createTimeSdf1.format(new Date()));
        labels.add("不可扩散");
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.setBackgroundDrawable(new WaterMarkDrawable(getActivity(), labels, -30, 13));
        decorView.addView(frameLayout, 0);
        webView.setBackgroundDrawable(new WaterMarkDrawable(getActivity(), labels, -30, 13));
        webView.loadUrl("https://www.baidu.com");

        if (requireActivity() instanceof BaseActivity) {
            ((BaseActivity) requireActivity()).registerBackPress(this, new BackPressObserver() {
                @Override
                public boolean onBackPress() {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                    return false;
                }
            });
        }

        getActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                Log.e("TAGTAGTAGTAGTAG", "BackPressSecondFragment handleOnBackPressed: ");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
