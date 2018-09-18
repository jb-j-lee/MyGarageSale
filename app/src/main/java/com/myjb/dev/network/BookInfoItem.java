package com.myjb.dev.network;

import com.myjb.dev.model.ServiceModel;

public class BookInfoItem {
    public String isbn;

    public int company;
    public String image;
    public String name;

    //why string???
    public String price;
    public String best;
    public String better;
    public String good;

    public BookInfoItem(String isbn) {
        this.isbn = isbn;
        company = ServiceModel.Company.NONE;
        image = name = price = best = better = good = null;
    }

    public BookInfoItem(String isbn, int company, String image, String name, String price, String best, String better, String good) {
        this.isbn = isbn;
        this.company = company;
        this.image = image;
        this.name = name;
        this.price = price;
        this.best = best;
        this.better = better;
        this.good = good;
    }

    public BookInfoItem(BookInfoItem item) {
        this.isbn = item.isbn;
        this.company = item.company;
        this.image = item.image;
        this.name = item.name;
        this.price = item.price;
        this.best = item.best;
        this.better = item.better;
        this.good = item.good;
    }

    @Override
    public String toString() {
        return "isbn : " + this.isbn
                + ", company : " + this.company
                + ", image : " + this.image
                + ", name : " + this.name
                + ", price : " + this.price
                + ", best : " + this.best
                + ", better : " + this.better
                + ", good : " + this.good;
    }
}