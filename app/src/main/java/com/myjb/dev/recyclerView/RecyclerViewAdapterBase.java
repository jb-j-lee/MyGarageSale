package com.myjb.dev.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.myjb.dev.mygaragesale.R;

import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> list = new ArrayList<T>();

    @NonNull
    @Override
    public ViewWrapper<V> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewWrapper(onCreateItemView(parent, viewType));
    }

    protected ItemView onCreateItemView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}