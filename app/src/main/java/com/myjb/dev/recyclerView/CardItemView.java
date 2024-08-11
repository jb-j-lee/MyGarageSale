package com.myjb.dev.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewBookinfoBinding;
import com.myjb.dev.network.BookInfoItem;

public class CardItemView extends ItemView {

    ItemRecyclerviewBookinfoBinding binding;

    public CardItemView(Context context) {
        super(context);
        binding = ItemRecyclerviewBookinfoBinding.inflate(LayoutInflater.from(context));
        addView(binding.getRoot());
    }

    @Override
    protected void bind(Object item) {
        if (item instanceof BookInfoItem) {
//            Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
//            startAnimation(animation);

            BookInfoItem bookInfoItem = (BookInfoItem) item;

            binding.name.setText(bookInfoItem.name);
            binding.save.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            binding.price.setText(bookInfoItem.price.price);
            binding.best.setText(bookInfoItem.price.best);
            binding.better.setText(bookInfoItem.price.better);
            binding.good.setText(bookInfoItem.price.good);
        }
    }
}