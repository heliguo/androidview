package com.example.androidview.backpress;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;

/**
 * @author lgh on 2021/4/1 15:48
 * @description
 */
public class BackPressSecondFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_back_press_second, container,false);
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
        webView.loadUrl("http://www.baidu.com");

        if (requireActivity() instanceof BaseActivity) {
            ((BaseActivity) requireActivity()).registerBackPress(this, new BackPressObserver() {
                @Override
                public boolean onBackPress() {
                    if (webView.canGoBack()){
                        webView.goBack();
                        return true;
                    }
                    return false;
                }
            });
        }
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
