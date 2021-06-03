package com.example.androidview.calendar;

import android.content.Context;

/**
 * @author lgh on 2021/1/6 16:18
 * @description
 */
public class ViewUtils {

    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
