package com.dgstore.repository;

import com.dgstore.model.Product;
import com.dgstore.model.Bag;

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

    public void removeToCart(Product item) {

        cartItems.remove(item);
    }

    public List<Bag> getCartItems() {
        System.out.println("diğeri: " + cartItems);
        return cartItems;
    }
}
