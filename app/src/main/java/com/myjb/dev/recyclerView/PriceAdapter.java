package com.myjb.dev.recyclerView;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class PriceAdapter extends RecyclerViewAdapterBase<String, PriceItemView> {

    @RootContext
    Context context;

    @Override
    protected PriceItemView onCreateItemView(ViewGroup parent, int viewType) {
        return PriceItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<PriceItemView> viewHolder, int position) {
        PriceItemView view = viewHolder.getView();
        String person = list.get(position);

        view.bind(person);
    }
}