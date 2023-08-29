package com.dgstore.retrofit;

import com.dgstore.Store;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StoreAPI {

    @GET("products")
    Call<List<Store>> getProducts();
    //Call<Store> getProductById(@Path("id") int id);


}

