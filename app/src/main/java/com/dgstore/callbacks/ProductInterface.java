package com.dgstore.callbacks;

import com.dgstore.model.FavoriteProduct;
import com.dgstore.model.Product;

public interface ProductInterface {
    void addBagItems(Product product);

    void addFavouriteItems(Product product);

    void removeBagItems(Product product);

    void removeFavouriteItems(FavoriteProduct product);
}
