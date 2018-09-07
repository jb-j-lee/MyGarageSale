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

    @ViewById(R.id.high)
    TextView high;

    @ViewById(R.id.medium)
    TextView medium;

    public CardItemView(Context context) {
        super(context);
    }

    @Override
    protected void bind(Object item) {
        if (item instanceof String) {
            name.setText((String) item);

            Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
            image.startAnimation(animation);
        }
        if (item instanceof BookInfoItem) {
            BookInfoItem bookInfoItem = (BookInfoItem) item;
//            if (((PriceItem) item).company > NONE)
//                company.setImageResource((((PriceItem) item).company == ALADIN) ? R.drawable.logo_aladin : R.drawable.logo_yes24);
//            else
//                company.setImageBitmap(null);

            name.setText(bookInfoItem.name);

            price.setText(bookInfoItem.price);
            best.setText(bookInfoItem.best);
            high.setText(bookInfoItem.high);
            medium.setText(bookInfoItem.medium);
        }
    }
}