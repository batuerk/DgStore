package com.dgstore.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dgstore.model.Product;
import com.dgstore.retrofit.RetrofitClient;
import com.dgstore.retrofit.StoreAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private final StoreAPI storeAPI;

    public ProductRepository() {
        storeAPI = RetrofitClient.getRetrofitInstance().create(StoreAPI.class);
    }

    public LiveData<List<Product>> storeResponse() {
        final MutableLiveData<List<Product>> data = new MutableLiveData<>();
        storeAPI.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
