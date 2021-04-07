package com.example.androidview.dragandslideslip;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.dragandslideslip.rv.ItemHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lgh on 2021/4/7 11:07
 * @description
 */
public class ItemHelperActivity extends BaseActivity {

    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_helper);
        RecyclerView recyclerView = findViewById(R.id.item_helper_rv);
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("");
        }
        ItemHelperAdapter adapter = new ItemHelperAdapter(mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        CommonListItemTouchCallBack commonListItemTouchCallBack = new CommonListItemTouchCallBack();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(commonListItemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        commonListItemTouchCallBack.setOnItemTouchListener(new CommonListItemTouchCallBack.OnItemTouchListener() {
            @Override
            public void onMove(int fromPosition, int toPosition) {
                Collections.swap(mList, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onSwiped(int position) {
//                mList.remove(position);
//                adapter.notifyItemRemoved(position);
            }

            @Override
            public void release() {

            }
        });
    }
}
