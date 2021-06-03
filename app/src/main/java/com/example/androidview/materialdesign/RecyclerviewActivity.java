package com.example.androidview.materialdesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/21 9:39
 * @description
 */
public class RecyclerviewActivity extends BaseActivity {

    private LinearLayout floatLl;
    private TextView floatTv;
    private int height;
    private LinearLayoutManager mManager;
    private int currentPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_recyclerview);
        RecyclerView recyclerView = findViewById(R.id.feeds_rv);
        mManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setAdapter(new Adapter());
        recyclerView.setHasFixedSize(true);

        floatLl = findViewById(R.id.float_ll);
        floatTv = findViewById(R.id.float_title);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                height = floatLl.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = mManager.findViewByPosition(currentPosition + 1);
                if (view != null) {
                    if (view.getTop() <= height) {
                        floatLl.setY(-height + view.getTop());
                    } else {
                        floatLl.setY(0);
                    }
                }
                if (currentPosition != mManager.findFirstVisibleItemPosition()) {
                    currentPosition = mManager.findFirstVisibleItemPosition();
                    updateFloatView();
                }
            }
        });
        updateFloatView();
    }

    private void updateFloatView() {
        floatTv.setText("title: " + currentPosition);
    }

    class Adapter extends RecyclerView.Adapter<FeedsViewHolder> {

        @NonNull
        @Override
        public FeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feeds, parent, false);
            return new FeedsViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedsViewHolder holder, int position) {
            holder.title.setText("title: " + position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    class FeedsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;

        public FeedsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bar_title);
            content = itemView.findViewById(R.id.bar_content);
        }
    }
}
