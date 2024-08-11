package com.myjb.dev.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;

import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewBookBinding;

public class BookItemView extends ItemView {

    ItemRecyclerviewBookBinding binding;

    public BookItemView(Context context) {
        super(context);
        binding = ItemRecyclerviewBookBinding.inflate(LayoutInflater.from(context));
        addView(binding.getRoot());
    }

    @Override
    public void bind(Object item) {
        if (item instanceof String) {
            binding.book.setText((String) item);
        }
    }
}