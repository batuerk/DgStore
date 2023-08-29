package com.dgstore.ui.bag;

import com.dgstore.Store;

public class Bag {
    public Store storeList;
    public int piece = 0;

    @Override
    public String toString() {
        return "Bag{" +
                "storeList=" + storeList +
                ", piece=" + piece +
                '}';
    }
}


