package com.dgstore.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dgstore.model.Product;

import java.util.List;

@Dao
public interface DaoProduct {

    @Insert
    void addProduct(Product selectedProduct);

    @Insert
    void addMoreProduct(Product  selectedProducts);

    @Insert
    void addMoreListProduct(List<Product> selectedListProducts);

    @Query("SELECT * FROM selectedProductData")
    List<Product> allSelectedProduct();

    @Update
    void update(Product selectedListProducts);

    @Delete
    void delete(Product selectedListProducts);

}
