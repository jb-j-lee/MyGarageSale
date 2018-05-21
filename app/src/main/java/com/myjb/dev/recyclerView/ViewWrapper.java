package com.myjb.dev.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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