package com.myjb.dev.network;

public class PriceItem {
    public String price;
    public String best;
    public String better;
    public String good;

    public PriceItem(String price, String best, String better, String good) {
        this.price = price;
        this.best = best;
        this.better = better;
        this.good = good;
    }

    public PriceItem(PriceItem item) {
        this.price = item.price;
        this.best = item.best;
        this.better = item.better;
        this.good = item.good;
    }

    @Override
    public String toString() {
        return "price : " + this.price
                + ", best : " + this.best
                + ", better : " + this.better
                + ", good : " + this.good;
    }
}