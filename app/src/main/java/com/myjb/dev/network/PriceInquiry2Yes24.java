package com.myjb.dev.network;

import android.support.annotation.NonNull;

import com.myjb.dev.model.ServiceModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PriceInquiry2Yes24 extends PriceInquiry {

    protected final static String TAG = "PriceInquiry2Yes24";

    public PriceInquiry2Yes24(@NonNull String query, @NonNull OnPriceListener listener) {
        super(query, listener);
        baseUrl = "http://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord=";
    }

    @NonNull
    @Override
    protected Elements getElements(Document doc) {
        Elements elements = super.getElements(doc);

        Element element = elements.first();

        if (element == null)
            return new Elements();

        Elements children = element.children();

        if (children == null)
            return new Elements();

        return children;
    }

    @Override
    protected int getCompany() {
        return ServiceModel.Company.YES24;
    }

    @NonNull
    @Override
    protected String getBasicFilter() {
        return "ul[class=clearfix]";
    }

    @NonNull
    @Override
    protected String getISBN(Element element) {
        return element.select("span[class=bbG_isbn13]").text().split("-")[1];
    }

    @NonNull
    @Override
    protected String getNameFilter() {
        return "strong[class=name]";
    }

    @NonNull
    @Override
    protected String getPriceFilter() {
        return "td";
    }
}
