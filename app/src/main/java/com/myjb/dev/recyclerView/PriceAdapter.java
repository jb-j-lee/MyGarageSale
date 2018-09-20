package com.myjb.dev.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.myjb.dev.mygaragesale.R;
import com.myjb.dev.network.PriceItem;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;

@EBean
public class PriceAdapter extends RecyclerViewAdapterBase<String, ItemView> {

    interface OnItemClickListener {
        void onItemClick(String name, String isbn, int position);
    }

    enum VIEW_TYPE {
        BOOK, PRICE
    }

    @RootContext
    Context context;

    @StringArrayRes(R.array.book_name)
    String[] names;

    @StringArrayRes(R.array.book_isbn)
    String[] isbn;

    @NonNull
    List<PriceItem> itemList = new ArrayList<>();

    OnItemClickListener mOnItemClickListener;

    public PriceAdapter(@NonNull Context context) {
        this.context = context;
    }

    @AfterInject
    void bind() {
        itemList = new ArrayList<>(names.length * 2);

        //TODO
        for (int i = 0; i < names.length; i++) {
            PriceItem item = new PriceItem(null, null, null, null);
            itemList.add(2 * i, item);
            itemList.add(2 * i + 1, item);
        }
    }

    @Override
    protected ItemView onCreateItemView(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE.BOOK.ordinal())
            return BookItemView_.build(context);
        else
            return PriceItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<ItemView> viewHolder, final int position) {
        Log.e("", "[onBindViewHolder] position : " + position + ", nameList : " + itemList.size());

        if (position % 3 == 0) {
            final int offset = (position > 0) ? position / 3 : 0;
            String name = names[offset];

            ItemView view = viewHolder.getView();
            view.bind(name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(names[offset], isbn[offset], offset);
                }
            });
        } else {
            final int offset = (position > 0) ? position / 3 : 0;
            PriceItem item = itemList.get(2 * offset + position % 3 - 1);

            ItemView view = viewHolder.getView();
            view.bind(item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0)
            return VIEW_TYPE.BOOK.ordinal();
        return VIEW_TYPE.PRICE.ordinal();
    }

    @Override
    public int getItemCount() {
        return names.length + itemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void updateView(PriceItem item, int position) {
        itemList.remove(position);
        itemList.add(position, new PriceItem(item));
    }
}