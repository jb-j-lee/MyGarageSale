package com.myjb.dev.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> list = new ArrayList<T>();

    @NonNull
    @Override
    public ViewWrapper<V> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewWrapper(onCreateItemView(parent, viewType));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected PriceItemView onCreateItemView(ViewGroup parent, int viewType) {
        return null;
    }
}