package com.myjb.dev.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.myjb.dev.mygaragesale.R;
import com.myjb.dev.network.BookInfoItem;
import com.myjb.dev.network.NetworkConstraint;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;

@EBean
public class CardAdapter extends RecyclerViewAdapterBase<String, ItemView> {

    public interface OnItemClickListener {
        void onItemClick(String name, String isbn, int position);
    }

    @RootContext
    Context context;

    @StringArrayRes(R.array.book_isbn)
    String[] isbns;

    @NonNull
    List<BookInfoItem> itemList;

    OnItemClickListener mOnItemClickListener;

    public CardAdapter(@NonNull Context context) {
        this.context = context;
    }

    @AfterInject
    void bind() {
        itemList = new ArrayList<>();

        for (int i = 0; i < isbns.length; i++) {
            BookInfoItem item = new BookInfoItem(isbns[i]);
            itemList.add(i, item);
        }
    }

    @Override
    protected ItemView onCreateItemView(ViewGroup parent, int viewType) {
        return CardItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ItemView> viewHolder, final int position) {
        Log.e("", "[onBindViewHolder] position : " + position + ", nameList : " + itemList.size());

        if (itemList.get(position).company == NetworkConstraint.Company.NONE) {
            String name = isbns[position];

            ItemView view = viewHolder.getView();
            view.bind(name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(isbns[position], isbns[position], position);
                }
            });
        } else {
            ItemView view = viewHolder.getView();
            view.bind(itemList.get(position));

            view.setOnClickListener(null);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        if (itemList == null)
            return 0;

        return itemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void updateView(BookInfoItem item, int position) {
        itemList.set(position, new BookInfoItem(item));
    }
}