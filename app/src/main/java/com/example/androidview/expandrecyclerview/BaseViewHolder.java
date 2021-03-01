package com.example.androidview.expandrecyclerview;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


import java.lang.ref.WeakReference;

/**
 * @author HuaJian Jiang.
 *         Date 2017/1/20.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    WeakReference<RecyclerView> associateRv;

    /**
     * ItemView 的 childView 缓存
     * 便于根据 id 查找对应的 View
     * 如果该缓存里没有查找到该 childView 就先 findViewById 再缓存下来
     */
    private SparseArray<View> mCachedViews = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 根据 id 查找 ItemView 里 childView
     *
     * @param id ItemView 里 childView 的 id
     * @return ItemView 里的 childView
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        if (id == View.NO_ID) return null;
        final View iv = itemView;
        View v = mCachedViews.get(id);
        if (v == null) {
            if (id == iv.getId()) {
                v = iv;
            } else {
                v = iv.findViewById(id);
            }
            if (v != null) mCachedViews.put(id, v);
        }
        return (T) v;
    }
    public int getType() {
        return Packager.getClientViewType(getItemViewType());
    }

}
