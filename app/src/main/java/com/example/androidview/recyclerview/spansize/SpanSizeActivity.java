package com.example.androidview.recyclerview.spansize;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.recyclerview.CustomAdapter;

/**
 * @author lgh on 2021/4/12 17:06
 * @description
 */
public class SpanSizeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span_size);
        RecyclerView recyclerView = findViewById(R.id.span_size_rv);
        CustomAdapter customAdapter = new CustomAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int span;

                switch (position % 6) {
                    case 0:
                        span = 6;
                        break;
                    case 1:
                    case 2:
                        span = 3;
                        break;
                    case 3:
                    case 4:
                    default:
                        span = 2;
                        break;
                }

                return span;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(customAdapter);
    }


}
