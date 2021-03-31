package com.example.androidview.dragandslideslip;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.R;

import java.util.ArrayList;

public class SnapHelperActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<String> mData;
    LinearLayoutManager mLayoutManager;
    GallerySnapHelper mGallerySnapHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_helper);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initData();
        mRecyclerView.setAdapter(new SnapHelperAdapter(this, mData));
        mGallerySnapHelper = new GallerySnapHelper();
        mGallerySnapHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i= 0;i<60;i++){
            mData.add("i="+i);
        }
    }

}
