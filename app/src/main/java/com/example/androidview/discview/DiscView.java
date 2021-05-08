package com.example.androidview.discview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author lgh on 2021/5/7 17:05
 * @description
 */
public class DiscView extends RelativeLayout {

    public DiscView(Context context) {
        this(context,null);
    }

    public DiscView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
