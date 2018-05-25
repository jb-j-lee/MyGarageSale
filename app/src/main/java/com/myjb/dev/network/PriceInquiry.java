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

public class PriceInquiry extends AsyncTask<Void, Void, List<PriceItem>> {

    public interface OnPriceListener {
        void onPriceResult(List<PriceItem> priceList);
    }

    protected final static String TAG = "PriceInquiry";

    protected String baseUrl;
    private String query;
    private OnPriceListener listener;

    public PriceInquiry(@NonNull String query, @NonNull OnPriceListener listener) {
        this.query = query;
        this.listener = listener;
    }

    @Override
    protected List<PriceItem> doInBackground(Void... params) {
        String url = baseUrl + query;
        if (BuildConfig.DEBUG)
            Log.e(TAG, "[basicVersion] url : " + url);

        return getPriceInfo(url);
    }

    @Override
    protected void onPostExecute(List<PriceItem> imageUrls) {
        if (listener != null)
            listener.onPriceResult(imageUrls);
    }

    protected List<PriceItem> getPriceInfo(String url) {
        try {
            long init = System.currentTimeMillis();

            Document doc = getDocument(url);

            long connect = System.currentTimeMillis();

            Elements elements = getElements(doc);

            long select = System.currentTimeMillis();

            List<PriceItem> priceList = getItems(elements);

            if (BuildConfig.DEBUG)
                Log.e(TAG, "[basicVersion] connect : " + (connect - init) + ", select : " + (select - connect) + ", add : " + (System.currentTimeMillis() - select));

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

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private Elements getElements(Document doc) {
        String filter = getBasicFilter();
        if (BuildConfig.DEBUG)
            Log.e(TAG, "[basicVersion] filter : " + filter);

        return doc.select(filter);
    }

    @NonNull
    private List<PriceItem> getItems(Elements elements) {
        List<PriceItem> priceList = new ArrayList<>();
        for (Element element : elements) {
            String[] price = element.select(getDetailFilter()).text().split("원");
            if (price != null)
                priceList.add(new PriceItem(price[0], price[1], price[2], price[3]));
        }

        if (BuildConfig.DEBUG)
            Log.e(TAG, "[basicVersion] list.size : " + priceList.size());
        return priceList;
    }

    @NonNull
    protected String getBasicFilter() {
        return null;
    }

    @NonNull
    protected String getDetailFilter() {
        return null;
    }
}
