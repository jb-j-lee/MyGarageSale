package com.myjb.dev.network;

public class PriceItem {
    public String query;
    public int company;
    public String price;
    public String best;
    public String high;
    public String medium;

    public PriceItem(String query, String price, String best, String high, String medium) {
        this.query = query;
        this.price = price;
        this.best = best;
        this.high = high;
        this.medium = medium;
    }

    public PriceItem(PriceItem item) {
        this.query = item.query;
        this.company = item.company;
        this.price = item.price;
        this.best = item.best;
        this.high = item.high;
        this.medium = item.medium;
    }

    @Override
    public String toString() {
        return "query : " + this.query
                + ", company : " + this.company
                + ", price : " + this.price
                + ", best : " + this.best
                + ", high : " + this.high
                + ", medium : " + this.medium;
    }
}