package com.dgstore.model;

public class Bag {
    public Product productList;
    public int piece = 0;

    @Override
    public String toString() {
        return "Bag{" +
                "productList=" + productList +
                ", piece=" + piece +
                '}';
    }
}


