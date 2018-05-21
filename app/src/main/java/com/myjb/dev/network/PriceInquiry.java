package com.myjb.dev.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.myjb.dev.mygaragesale.BuildConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

public class PriceInquiry extends AsyncTask<Void, Void, List<PriceInquiry.Item>> {

    public interface OnPriceListener {
        void onPriceResult(List<Item> priceList);
    }

    protected final static String TAG = "PriceInquiry";

    public class Item {
        public String price;
        public String best;
        public String high;
        public String medium;

        Item(String price, String best, String high, String medium) {
            this.price = price;
            this.best = best;
            this.high = high;
            this.medium = medium;
        }

        @Override
        public String toString() {
            return "price : " + this.price
                    + ", best : " + this.best
                    + ", high : " + this.high
                    + ", medium : " + this.medium;
        }
    }

    protected String baseUrl;
    protected String query;
    private OnPriceListener listener;

    public PriceInquiry(@NonNull String query, @NonNull OnPriceListener listener) {
        this.query = query;
        this.listener = listener;
    }

    @Override
    protected List<Item> doInBackground(Void... params) {
        return basicVersion();
    }

    @Override
    protected void onPostExecute(List<Item> imageUrls) {
        if (listener != null)
            listener.onPriceResult(imageUrls);
    }

    @DebugLog
    protected List<Item> basicVersion() {
        try {
            long init = System.currentTimeMillis();

            String url = baseUrl + query;
            Log.e(TAG, "[basicVersion] url : " + url);

            Document doc = Jsoup.connect(url).get();

            long connect = System.currentTimeMillis();
            checkTime("basicVersion", "connect", init);

            String filter = getBasicFilter();
            Log.e(TAG, "[basicVersion] filter : " + filter);

            Elements elements = doc.select(filter);

            long select = System.currentTimeMillis();
            checkTime("basicVersion", "select", connect);

            List<Item> priceList = new ArrayList<>();
            for (Element element : elements) {
                String[] price = element.select(getDetailFilter()).text().split("원");
                if (price != null)
                    priceList.add(new Item(price[0], price[1], price[2], price[3]));
            }

            checkTime("basicVersion", "add", select);

            Log.e(TAG, "[basicVersion] list.size : " + priceList.size());
            return priceList;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @NonNull
    protected String getBasicFilter() {
        return null;
    }

    @NonNull
    protected String getDetailFilter() {
        return null;
    }

    protected void checkTime(String method, String name, long previousTime) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, "[" + method + "] " + name + " : " + (System.currentTimeMillis() - previousTime) + " ms");
    }
}
