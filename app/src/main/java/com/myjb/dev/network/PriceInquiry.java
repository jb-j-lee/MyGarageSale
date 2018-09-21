package com.myjb.dev.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.myjb.dev.model.ServiceModel;
import com.myjb.dev.mygaragesale.BuildConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class PriceInquiry extends AsyncTask<Void, Void, List<BookInfoItem>> {

    public interface OnPriceListener {
        void onPriceResult(List<BookInfoItem> priceList);
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
    protected List<BookInfoItem> doInBackground(Void... params) {
        String url = null;
        try {
            url = baseUrl + URLEncoder.encode(query, "EUC-KR");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (BuildConfig.DEBUG)
            Log.e(TAG, "[basicVersion] url : " + url);

        return getPriceInfo(url);
    }

    @Override
    protected void onPostExecute(List<BookInfoItem> imageUrls) {
        if (listener != null)
            listener.onPriceResult(imageUrls);
    }

    protected List<BookInfoItem> getPriceInfo(String url) {
        Document doc = null;
        Elements elements = new Elements();

        try {
            long init = System.currentTimeMillis();

            doc = getDocument(url);

            long connect = System.currentTimeMillis();

            elements = getElements(doc);

            long select = System.currentTimeMillis();

            List<BookInfoItem> bookInfoList = getItems(elements);

            if (BuildConfig.DEBUG)
                Log.e(TAG, "[basicVersion] connect : " + (connect - init) + ", select : " + (select - connect) + ", add : " + (System.currentTimeMillis() - select));

            return bookInfoList;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (elements != null)
                elements = null;
            if (doc != null)
                doc = null;
        }

        return null;
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    @NonNull
    protected Elements getElements(Document doc) {
        String filter = getBasicFilter();

        if (BuildConfig.DEBUG)
            Log.e(TAG, "[basicVersion] filter : " + filter);

        return doc.select(filter);
    }

    @NonNull
    private List<BookInfoItem> getItems(Elements elements) {
        List<BookInfoItem> bookInfoList = new ArrayList<>();
        for (Element element : elements) {
            String isbn = getISBN(element);

            if (BuildConfig.DEBUG)
                Log.e(TAG, "[basicVersion] isbn : " + isbn);

            String image = element.select(getImageFilter()).attr("src");

            if (BuildConfig.DEBUG)
                Log.e(TAG, "[basicVersion] getImageFilter() : " + getImageFilter() + ", image : " + image);

            String name = element.select(getNameFilter()).text();

            if (BuildConfig.DEBUG)
                Log.e(TAG, "[basicVersion] getNameFilter() : " + getNameFilter() + ", name : " + name);

            String[] price = element.select(getPriceFilter()).text().split("원");
            if (price.length > 1)
                bookInfoList.add(new BookInfoItem(isbn, getCompany(), image, name, new PriceItem(price)));
        }

        if (BuildConfig.DEBUG)
            Log.e(TAG, "[basicVersion] list.size : " + bookInfoList.size());

        return bookInfoList;
    }

    protected int getCompany() {
        return ServiceModel.Company.NONE;
    }

    @NonNull
    protected String getBasicFilter() {
        return "";
    }

    @NonNull
    protected String getISBN(Element element) {
        return "";
    }

    @NonNull
    protected String getImageFilter() {
        return "img[abs:src]";
    }

    @NonNull
    protected String getNameFilter() {
        return "";
    }

    @NonNull
    protected String getPriceFilter() {
        return "";
    }
}
