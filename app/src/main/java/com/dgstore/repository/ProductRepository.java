package com.dgstore.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dgstore.Store;
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

    public LiveData<List<Store>> storeResponse() {
        final MutableLiveData<List<Store>> data = new MutableLiveData<>();
        storeAPI.getProducts().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
