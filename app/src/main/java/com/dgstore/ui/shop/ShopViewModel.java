package com.dgstore.ui.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgstore.retrofit.RetrofitClient;
import com.dgstore.Store;
import com.dgstore.retrofit.StoreAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopViewModel extends ViewModel {
    private MutableLiveData<List<Store>> productsLiveData;
    private StoreAPI storeAPI;

    public ShopViewModel() {
        productsLiveData = new MutableLiveData<>();
        storeAPI = RetrofitClient.getRetrofitInstance().create(StoreAPI.class);
    }

    public LiveData<List<Store>> getProductsLiveData() {

        return productsLiveData;
    }

    public void fetchProducts() {
        Call<List<Store>> call = storeAPI.getProducts();
        call.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()) {
                    List<Store> products = response.body();

                    productsLiveData.setValue(products);
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                t.printStackTrace();



            }
        });

    }

}
