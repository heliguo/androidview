package com.example.androidview.event;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/13 14:38
 * @description
 */
public class EventActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_event);
        MyView myView = findViewById(R.id.myview);
        myView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("TagTagTagTagTagTag", "onLongClick: child ");
                return true;
            }
        });
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TagTagTagTagTagTag", "onClick: child ");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TagTagTagTagTagTag", "onTouchEvent: activity");
        return super.onTouchEvent(event);
    }
}
