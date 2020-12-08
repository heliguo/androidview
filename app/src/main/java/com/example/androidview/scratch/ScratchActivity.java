package com.example.androidview.scratch;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.R;

/**
 * @author lgh on 2020/12/7 16:57
 * @description
 */
public class ScratchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        ScratchFrameLayout frameLayout = findViewById(R.id.scratch_frame_layout);
//        frameLayout.setCompleted(true);//不可擦除
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.clear();
            }
        });
        frameLayout.setOnEraseStatusListener(ScratchView::clear);

    }
}
