package com.example.androidview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Random;

/**
 * @author lgh on 2020/12/2 20:04
 * @description
 */
public class ViewFlippers extends ViewFlipper {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private int count = 1;

    public ViewFlippers(Context context) {
        super(context);
    }

    public ViewFlippers(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_flipper_text, null);
        mTextView1 = view.findViewById(R.id.task_flipper_text);
        mTextView1.setText(randomTips());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_flipper_text, null);
        mTextView2 = view1.findViewById(R.id.task_flipper_text);
        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.item_flipper_text, null);
        mTextView3 = view2.findViewById(R.id.task_flipper_text);
        addView(view);
        addView(view1);
        addView(view2);
    }

    @Override
    public void showNext() {
        int i = count % 3;
        if (i == 0) {
            mTextView1.setText(randomTips());
            count = 1;
        } else if (i == 2) {
            mTextView3.setText(randomTips());
            count = 3;
        } else if (i == 1) {
            mTextView2.setText(randomTips());
            count = 2;
        }
        super.showNext();
    }

    private final String[] telFirst = ("134,135,136,137,138,139,147,148,150,151,152,157,158,159,178,182,183,184,187," +
            "188,198,130,131,132,155,156,185,186,145,146,166,167,175,176,133,153,177,180,181,189,191,199").split(",");

    private final String[] moneys = {"0.1", "0.3", "0.5", "1", "5", "10", "30", "50"};

    private final Random mRandom = new Random(System.currentTimeMillis());

    private String randomTips() {
        int index = mRandom.nextInt(telFirst.length - 1);
        String head = telFirst[index];
        String tail = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        String money = moneys[mRandom.nextInt(moneys.length - 1)];
        return "用户" + head + "****" + tail + "提现" + money + "元";
    }

    private int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }


}
