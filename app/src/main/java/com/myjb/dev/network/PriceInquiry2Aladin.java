package com.myjb.dev.network;

import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriceInquiry2Aladin extends PriceInquiry {

    protected final static String TAG = "PriceInquiry2Aladin";

    public PriceInquiry2Aladin(@NonNull String query, @NonNull OnPriceListener listener) {
        super(query, listener);
        baseUrl = "http://www.aladin.co.kr/shop/usedshop/wc2b_search.aspx?ActionType=1&SearchTarget=Book&KeyWord=";
    }

    @NonNull
    @Override
    protected String getBasicFilter() {
        return "table[id=searchResult]";
    }

    @NonNull
    @Override
    protected String getDetailFilter() {
        return "td[class=c2b_tablet3]";
    }
}
