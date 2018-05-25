package com.myjb.dev.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.myjb.dev.mygaragesale.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.Arrays;

@EBean
public class PriceAdapter extends RecyclerViewAdapterBase<String, PriceItemView> {

    @RootContext
    Context context;

    @StringArrayRes(R.array.book_name)
    String[] names;

    public PriceAdapter(@NonNull Context context) {
        this.context = context;
    }

    @AfterInject
    void bind() {
        list = Arrays.asList(names);
    }

    @Override
    protected ItemView onCreateItemView(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new BookItemView(context);
        else
            return new PriceItemView(context);
    }

    @Override
    public void onBindViewHolder(final ViewWrapper<PriceItemView> viewHolder, int position) {
        String isbn = list.get(position);
        ItemView view = viewHolder.getView();
        view.bind(isbn);

//        new PriceInquiry2Yes24(isbn, new PriceInquiry.OnPriceListener() {
//            @Override
//            public void onPriceResult(List<PriceItem> priceList) {
//                PriceItemView view = viewHolder.getView();
//                view.bind(priceList.get(0));
//            }
//        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}