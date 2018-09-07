package com.myjb.dev.network;

import android.support.annotation.NonNull;

import org.jsoup.nodes.Element;

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
    protected String getISBN(Element element) {
        return element.select("input").attr("isbn");
    }

    @NonNull
    @Override
    protected String getNameFilter() {
        return "a[class=c2b_b]";
    }

    @NonNull
    @Override
    protected String getPriceFilter() {
        return "td[class=c2b_tablet3]";
    }
}