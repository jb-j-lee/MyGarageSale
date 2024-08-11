package com.myjb.dev.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;

import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewPriceBinding;
import com.myjb.dev.network.PriceItem;

public class PriceItemView extends ItemView {

    ItemRecyclerviewPriceBinding binding;

    public PriceItemView(Context context) {
        super(context);
        binding = ItemRecyclerviewPriceBinding.inflate(LayoutInflater.from(context));
        addView(binding.getRoot());
    }

    @Override
    protected void bind(Object item) {
        if (item instanceof PriceItem) {
            binding.price.setText(((PriceItem) item).price);
            binding.best.setText(((PriceItem) item).best);
            binding.better.setText(((PriceItem) item).better);
            binding.good.setText(((PriceItem) item).good);
        }
    }
}