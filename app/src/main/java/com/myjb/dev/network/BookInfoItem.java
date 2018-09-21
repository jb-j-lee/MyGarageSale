package com.myjb.dev.network;

public class BookInfoItem {
    public String isbn;

    public int company;
    public String image;
    public String name;

    public PriceItem price;

    public BookInfoItem(String isbn, int company, String image, String name, PriceItem price) {
        this.isbn = isbn;
        this.company = company;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "isbn : " + this.isbn
                + ", company : " + this.company
                + ", image : " + this.image
                + ", name : " + this.name
                + ", " + this.price.toString();
    }
}