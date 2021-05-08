package com.example.androidview.discview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author lgh on 2021/5/7 17:07
 * @description
 */
public class BackgroundAnimationRelativeLayout extends RelativeLayout {

    public BackgroundAnimationRelativeLayout(Context context) {
        this(context,null);
    }

    public BackgroundAnimationRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackgroundAnimationRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
