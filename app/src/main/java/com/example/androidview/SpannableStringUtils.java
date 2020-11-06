package com.example.androidview;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;

public class SpannableStringUtils {

    public static SpannableString getSpannableString(Context context, String source, int color, int size) {
        SpannableString mSpannableString = new SpannableString(source);
        int dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, context.getResources().getDisplayMetrics());
        String firstLine = source.contains("\n") ? source.substring(0, source.indexOf("\n")) : source;

        //第一行的样式
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);//颜色
        mSpannableString.setSpan(colorSpan, 0, firstLine.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dpValue);//大小
        mSpannableString.setSpan(absoluteSizeSpan, 0, firstLine.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //其他行的样式
        if (source.contains("\n")) {
            String otherLine = source.substring(source.indexOf("\n"));
            if (otherLine.length() > 0) {
                ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.YELLOW);//颜色
                mSpannableString.setSpan(colorSpan2, firstLine.length(), source.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan((int) (0.8f * dpValue));//大小
                mSpannableString.setSpan(absoluteSizeSpan2, firstLine.length(), source.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return mSpannableString;
    }
}
