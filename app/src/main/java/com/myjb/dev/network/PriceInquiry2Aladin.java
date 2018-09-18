package com.myjb.dev.network;

import android.support.annotation.NonNull;

import com.myjb.dev.model.ServiceModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PriceInquiry2Aladin extends PriceInquiry {

    protected final static String TAG = "PriceInquiry2Aladin";

    public PriceInquiry2Aladin(@NonNull String query, @NonNull OnPriceListener listener) {
        super(query, listener);
        baseUrl = "http://www.aladin.co.kr/shop/usedshop/wc2b_search.aspx?ActionType=1&SearchTarget=Book&KeyWord=";
    }

    @NonNull
    @Override
    protected Elements getElements(Document doc) {
        Elements elements = super.getElements(doc);

        if (elements == null)
            return null;

        Element element = elements.first();

        if (element == null)
            return null;

        Element child0 = element.child(0);

        if (child0 == null)
            return null;

        Elements children = child0.children();

        Elements newElements = new Elements();

        for (Element child : children) {
            if (child.getElementsByAttributeValue("class", "chk").size() > 0) {
                newElements.add(child);
            }
        }

        return newElements;
    }

    @NonNull
    @Override
    protected int getCompany() {
        return ServiceModel.Company.ALADIN;
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