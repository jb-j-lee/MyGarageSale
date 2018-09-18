package com.myjb.dev.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myjb.dev.model.ServiceModel;
import com.myjb.dev.network.BookInfoItem;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@EBean
public class CardAdapter extends RecyclerViewAdapterBase<String, ItemView> {

    public interface OnItemClickListener {
        void onItemClick(String name, String isbn, int position);
    }

    @RootContext
    Context context;

//    @StringArrayRes(R.array.book_isbn)
//    String[] isbns;

    @NonNull
    List<BookInfoItem> itemList;

    OnItemClickListener mOnItemClickListener;

    public CardAdapter(@NonNull Context context) {
        this.context = context;
    }

    @AfterInject
    void bind() {
        itemList = new ArrayList<>();

//        for (int i = 0; i < isbns.length; i++) {
//            BookInfoItem item = new BookInfoItem(isbns[i]);
//            itemList.add(i, item);
//        }
    }

    @Override
    protected ItemView onCreateItemView(ViewGroup parent, int viewType) {
        return CardItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ItemView> viewHolder, int position) {
        Log.e("", "[onBindViewHolder] position : " + position + ", nameList : " + itemList.size());

        final BookInfoItem item = itemList.get(position);

        if (item.company == ServiceModel.Company.NONE)
            return;

        final ItemView view = viewHolder.getView();
        view.bind(item);

        //TODO
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ImageView imageView = ((CardItemView) view).image;
                final Bitmap bitmap = getBitmap(item.image);
                if (bitmap != null) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TEST", "" + bitmap.getHeight() + ", " + bitmap.getWidth());

                            imageView.setMinimumHeight(bitmap.getHeight());
                            imageView.setMinimumWidth(bitmap.getWidth());
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.isEmpty())
            return 1;

        return itemList.get(position).company;
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

    public void updateView(List<BookInfoItem> items) {
        itemList.clear();
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearView() {
        itemList.clear();
    }

    private Bitmap getBitmap(final String imageUrl) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (urlConnection != null)
                urlConnection.disconnect();
            return bitmap;
        }
    }
}