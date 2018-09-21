package com.myjb.dev.recyclerView;

import android.content.Context;
import android.view.View;
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

    @ViewById(R.id.save)
    ImageView save;

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
//            Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
//            startAnimation(animation);

            BookInfoItem bookInfoItem = (BookInfoItem) item;

            name.setText(bookInfoItem.name);
            save.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            price.setText(bookInfoItem.price.price);
            best.setText(bookInfoItem.price.best);
            better.setText(bookInfoItem.price.better);
            good.setText(bookInfoItem.price.good);
        }
    }
}