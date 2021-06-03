package com.example.androidview.recyclerview.cardscale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidview.BaseActivity;
import com.example.androidview.R;

/**
 * @author lgh on 2021/4/12 16:26
 * @description
 */
public class CardScaleActivity extends BaseActivity {

    private int[] imgs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_scale);
        RecyclerView recyclerView = findViewById(R.id.card_scale_rv);
        imgs = new int[]{R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs, R.drawable.dlzs, R.drawable.dfh, R.drawable.jdzz, R.drawable.dfh};

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(new CardScaleAdapter());

        CardScaleHelper cardScaleHelper = new CardScaleHelper();
        cardScaleHelper.setCurrentItemPos(2);
        cardScaleHelper.attachToRecyclerView(recyclerView);
    }


    class CardScaleAdapter extends RecyclerView.Adapter<CardScaleViewHolder> {

        @NonNull
        @Override
        public CardScaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(CardScaleActivity.this).inflate(R.layout.item_card_scale, parent, false);
            CardAdapterHelper.onCreateViewHolder(parent, inflate);
            return new CardScaleViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull CardScaleViewHolder holder, int position) {
            CardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
            holder.itemView.setBackgroundResource(imgs[position]);
        }

        @Override
        public int getItemCount() {
            return imgs.length;
        }
    }

    static class CardScaleViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public CardScaleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.card_iv);
        }
    }

}
