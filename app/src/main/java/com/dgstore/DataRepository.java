package com.dgstore;

import com.dgstore.ui.bag.Bag;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    private List<Bag> cartItems;

    public void addToCart(Bag item) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(item);
        System.out.println("carditems: " + cartItems);
    }

    public void removeToCart(Store item) {

        cartItems.remove(item);
    }

    public List<Bag> getCartItems() {
        System.out.println("diğeri: " + cartItems);
        return cartItems;
    }
}
