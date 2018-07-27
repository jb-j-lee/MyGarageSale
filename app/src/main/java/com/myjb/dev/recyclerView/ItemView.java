package com.myjb.dev.recyclerView;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class ItemView<T> extends LinearLayout {

    public ItemView(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    protected abstract void bind(T item);
}