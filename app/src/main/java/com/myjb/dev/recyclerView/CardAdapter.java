package com.myjb.dev.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myjb.dev.model.ServiceModel;
import com.myjb.dev.network.BookInfoItem;

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

    final int BOOK_WIDTH = 200;

    public interface OnItemClickListener {
        void onItemClick(String name, String isbn, int position);
    }

    @RootContext
    Context context;

    @NonNull
    List<BookInfoItem> itemList = new ArrayList<>();

    OnItemClickListener mOnItemClickListener;

    public CardAdapter(@NonNull Context context) {
        this.context = context;
    }

    @Override
    protected ItemView onCreateItemView(@NonNull ViewGroup parent, int viewType) {
        return CardItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<ItemView> viewHolder, int position) {
        final BookInfoItem item = itemList.get(position);

        if (item.company == ServiceModel.Company.NONE)
            return;

        final ItemView view = viewHolder.getView();
        view.bind(item);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final ImageView imageView = ((CardItemView) view).image;
                final Bitmap bitmap = getBitmap(item.image);
                if (bitmap != null) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int width = BOOK_WIDTH;
                            int height = BOOK_WIDTH * bitmap.getHeight() / bitmap.getWidth();

                            imageView.setMinimumWidth(width);
                            imageView.setMinimumHeight(height);

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
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(imageUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            inputStream = urlConnection.getInputStream();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inScaled = false;


            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }
}