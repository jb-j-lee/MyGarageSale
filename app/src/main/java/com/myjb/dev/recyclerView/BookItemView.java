package com.myjb.dev.recyclerView;

import android.content.Context;
import android.widget.TextView;

import com.myjb.dev.mygaragesale.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_recyclerview_book)
public class BookItemView extends ItemView {

    @ViewById(R.id.book)
    TextView book;

    public BookItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(Object item) {
        if (item instanceof String) {
            book.setText((String) item);
        }
    }
}