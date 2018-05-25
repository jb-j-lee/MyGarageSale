package com.myjb.dev.network;

public class PriceItem {
    public String price;
    public String best;
    public String high;
    public String medium;

    public PriceItem(String price, String best, String high, String medium) {
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