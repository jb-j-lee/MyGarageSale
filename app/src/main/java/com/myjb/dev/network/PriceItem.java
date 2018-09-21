package com.myjb.dev.network;

public class PriceItem {
    public String price;
    public String best;
    public String better;
    public String good;

    public PriceItem(String[] price) {
        this.price = price[0];
        this.best = price[1];
        this.better = price[2];
        this.good = price[3];
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