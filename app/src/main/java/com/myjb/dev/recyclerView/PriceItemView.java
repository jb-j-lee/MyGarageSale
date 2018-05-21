package com.myjb.dev.recyclerView;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myjb.dev.mygaragesale.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_recyclerview_list)
public class PriceItemView extends LinearLayout {

    @ViewById(R.id.price)
    TextView price;

    @ViewById(R.id.best)
    TextView best;

    @ViewById(R.id.high)
    TextView high;

    @ViewById(R.id.medium)
    TextView medium;

    public PriceItemView(Context context) {
        super(context);
    }

    public void bind(String item) {
        price.setText(item);
    }
}