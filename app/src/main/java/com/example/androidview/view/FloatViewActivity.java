package com.example.androidview.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.R;

/**
 * @author lgh on 2020/12/17 17:01
 * @description
 */
public class FloatViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_view);
        FloatView2 floatView = findViewById(R.id.float_view);
        floatView.setOnClickListener(v -> Toast.makeText(FloatViewActivity.this, "click", Toast.LENGTH_SHORT).show());
        floatView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(FloatViewActivity.this, "long", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
