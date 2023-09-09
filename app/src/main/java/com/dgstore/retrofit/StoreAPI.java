package com.dgstore.retrofit;

import com.dgstore.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StoreAPI {

    @GET("products")
    Call<List<Product>> getProducts();
    //Call<Product> getProductById(@Path("id") int id);


}

