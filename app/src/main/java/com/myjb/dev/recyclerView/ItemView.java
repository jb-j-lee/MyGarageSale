package com.myjb.dev.recyclerView;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myjb.dev.mygaragesale.R;
import com.myjb.dev.network.PriceItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup
public abstract class ItemView<T> extends LinearLayout {

    public ItemView(Context context) {
        super(context);
    }

    protected void bind(T item) {
    }
}