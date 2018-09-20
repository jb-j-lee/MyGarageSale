package com.myjb.dev.recyclerView;

import android.content.Context;
import android.widget.TextView;

import com.myjb.dev.mygaragesale.R;
import com.myjb.dev.network.PriceItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_recyclerview_price)
public class PriceItemView extends ItemView {

    @ViewById(R.id.price)
    TextView price;

    @ViewById(R.id.best)
    TextView best;

    @ViewById(R.id.better)
    TextView better;

    @ViewById(R.id.good)
    TextView good;

    public PriceItemView(Context context) {
        super(context);
    }

    @Override
    protected void bind(Object item) {
        if (item instanceof PriceItem) {
            price.setText(((PriceItem) item).price);
            best.setText(((PriceItem) item).best);
            better.setText(((PriceItem) item).better);
            good.setText(((PriceItem) item).good);
        }
    }
}