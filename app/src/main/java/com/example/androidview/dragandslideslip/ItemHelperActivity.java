package com.example.androidview.dragandslideslip;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;
import com.example.androidview.calendar.ViewUtils;
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
                mList.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void release() {

            }
        });
        commonListItemTouchCallBack.setOnItemSwipingListener(new CommonListItemTouchCallBack.OnItemSwipingListener() {
            @Override
            public void onItemSwiping(Canvas c, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                c.save();
                if (dX > 0) {
                    c.clipRect(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + dX, itemView.getBottom() - ViewUtils.dpToPx(itemView.getContext(), 4));
                    c.translate(itemView.getLeft(), itemView.getTop());
                } else {
                    c.clipRect(itemView.getRight() + dX, itemView.getTop(),
                            itemView.getRight(), itemView.getBottom() - ViewUtils.dpToPx(itemView.getContext(), 4));
                    c.translate(itemView.getRight() + dX, itemView.getTop());
                }
                c.drawColor(Color.RED);
                c.restore();
            }
        });
    }
}
