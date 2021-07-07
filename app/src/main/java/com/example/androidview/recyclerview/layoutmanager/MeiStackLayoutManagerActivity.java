package com.example.androidview.recyclerview.layoutmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wenshi
 * @github
 * @Description
 * @since 2019/6/27
 */
public class MeiStackLayoutManagerActivity extends BaseActivity {

    ParallaxRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mei_stack_activity);


        mRecyclerView = findViewById(R.id.rv_layout_manager);
        // new StackLayoutManager(this,-56) 堆叠模式
        ParallaxLayoutManager3 parallaxLayoutManager = new ParallaxLayoutManager3();
        mRecyclerView.setLayoutManager(parallaxLayoutManager);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list.add(String.valueOf(i));
        }
        mRecyclerView.setAdapter(new BaseAdapter(list));
    }

    class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

        private List<String> mList;

        public BaseAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_stack_card,
                    viewGroup, false);
            view.setBackgroundColor(Color.argb(255,
                    new Random().nextInt(255),
                    new Random().nextInt(255),
                    new Random().nextInt(255)));
            return new BaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder viewHolder, int p) {
            final int i = p % mList.size();
            ((TextView) viewHolder.itemView.findViewById(R.id.tv)).setText(mList.get(i));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "" + i, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
