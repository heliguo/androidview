package com.example.androidview.htmltextview;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/29 18:31
 * @description
 */
public class HtmlTextViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_textview);
        HtmlTextView textView = findViewById(R.id.html_text);
        textView.setHtmlFromString("地尔硫<img src=\"http://static.wangxiao.cn/zhuntiku/img/lcys/caozhuo.gif\" />");
    }
}
