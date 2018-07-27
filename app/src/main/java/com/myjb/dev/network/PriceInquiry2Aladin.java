package com.myjb.dev.network;

import android.support.annotation.NonNull;

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
