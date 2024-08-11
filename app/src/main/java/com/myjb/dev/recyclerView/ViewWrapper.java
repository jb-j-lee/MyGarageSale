package com.myjb.dev.recyclerView;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {
    private V itemView;

    public ViewWrapper(V itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public V getView() {
        return itemView;
    }
}