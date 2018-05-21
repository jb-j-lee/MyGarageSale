package com.myjb.dev.network;

import android.support.annotation.NonNull;

public class PriceInquiry2Yes24 extends PriceInquiry {

    protected final static String TAG = "PriceInquiry2Yes24";

    public PriceInquiry2Yes24(@NonNull String query, @NonNull OnPriceListener listener) {
        super(query, listener);
        baseUrl = "http://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord=";
    }

    @NonNull
    @Override
    protected String getBasicFilter() {
        return "div[class=bbG_price]";
    }

    @NonNull
    @Override
    protected String getDetailFilter() {
        return "td";
    }
}
