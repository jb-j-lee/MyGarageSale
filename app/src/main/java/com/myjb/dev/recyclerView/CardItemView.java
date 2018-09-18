package com.myjb.dev.recyclerView;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.myjb.dev.mygaragesale.R;
import com.myjb.dev.network.BookInfoItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_recyclerview_bookinfo)
public class CardItemView extends ItemView {

    @ViewById(R.id.image)
    ImageView image;

    @ViewById(R.id.name)
    TextView name;

    @ViewById(R.id.price)
    TextView price;

    @ViewById(R.id.best)
    TextView best;

    @ViewById(R.id.better)
    TextView better;

    @ViewById(R.id.good)
    TextView good;

    public CardItemView(Context context) {
        super(context);
    }

    @Override
    protected void bind(Object item) {
        if (item instanceof BookInfoItem) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
            startAnimation(animation);

            BookInfoItem bookInfoItem = (BookInfoItem) item;

            name.setText(bookInfoItem.name);

            price.setText(bookInfoItem.price);
            best.setText(bookInfoItem.best);
            better.setText(bookInfoItem.better);
            good.setText(bookInfoItem.good);
        }
    }
}