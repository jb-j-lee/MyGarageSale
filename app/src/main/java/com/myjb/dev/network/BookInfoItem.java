package com.myjb.dev.network;

public class BookInfoItem {
    public String isbn;

    public int company;
    public String image;
    public String name;

    //why string???
    public String price;
    public String best;
    public String high;
    public String medium;

    public BookInfoItem(String isbn) {
        this.isbn = isbn;
        company = NetworkConstraint.Company.NONE;
        image = name = price = best = high = medium = null;
    }

    public BookInfoItem(String isbn, String image, String name, String price, String best, String high, String medium) {
        this.isbn = isbn;
        this.image = image;
        this.name = name;
        this.price = price;
        this.best = best;
        this.high = high;
        this.medium = medium;
    }

    public BookInfoItem(BookInfoItem item) {
        this.isbn = item.isbn;
        this.company = item.company;
        this.image = item.image;
        this.name = item.name;
        this.price = item.price;
        this.best = item.best;
        this.high = item.high;
        this.medium = item.medium;
    }

    @Override
    public String toString() {
        return "isbn : " + this.isbn
                + ", company : " + this.company
                + ", image : " + this.image
                + ", name : " + this.name
                + ", price : " + this.price
                + ", best : " + this.best
                + ", high : " + this.high
                + ", medium : " + this.medium;
    }
}