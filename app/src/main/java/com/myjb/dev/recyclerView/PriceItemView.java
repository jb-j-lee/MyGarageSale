package com.myjb.dev.recyclerView;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.myjb.dev.mygaragesale.R;
import com.myjb.dev.network.NetworkConstraint;
import com.myjb.dev.network.PriceItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_recyclerview_price)
public class PriceItemView extends ItemView {

    @ViewById(R.id.company)
    ImageView company;

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

    @Override
    protected void bind(Object item) {
        if (item instanceof PriceItem) {
            if (((PriceItem) item).company > NetworkConstraint.Company.NONE)
                company.setImageResource((((PriceItem) item).company == NetworkConstraint.Company.ALADIN) ? R.drawable.logo_aladin : R.drawable.logo_yes24);
            else
                company.setImageBitmap(null);

            price.setText(((PriceItem) item).price);
            best.setText(((PriceItem) item).best);
            high.setText(((PriceItem) item).high);
            medium.setText(((PriceItem) item).medium);
        }
    }
}