package com.dgstore.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgstore.model.Product;
import com.dgstore.retrofit.RetrofitClient;
import com.dgstore.retrofit.StoreAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Product>> productsLiveData;
    private StoreAPI storeAPI;

    public MainViewModel() {
        productsLiveData = new MutableLiveData<>();
        storeAPI = RetrofitClient.getRetrofitInstance().create(StoreAPI.class);
    }

    public LiveData<List<Product>> getProductsLiveData() {

        return productsLiveData;
    }

    public void fetchProducts() {
        Call<List<Product>> call = storeAPI.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productsLiveData.setValue(products);
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();



            }
        });

    }

}

//        call.enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                String responseBody = response.body().toString();
//
//                try {
//                    JSONObject json = new JSONObject(responseBody);
//                    Log.d(TAG, json.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                if (response.isSuccessful()) {
//                    List<Product> products = response.body();
//                    productsLiveData.setValue(products);
//                } else {
//                    // Hata durumunu işle
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t){
//                // Hata durumunu işle
//                }
//        });



//--------------------------------------------------------------------//

//    public void loadData() {
//        StoreAPI storeAPI1 = retrofit.create(StoreAPI.class);
//
//        Call<Product> call = storeAPI.getProductById(1);
//
//        call.enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
//                if (response.isSuccessful()) {
//                    Product store = response.body();
//                    if (store != null) {
//                        storesLiveData.setValue(store);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//            }
//        });
//    }
//
//    public LiveData<Product> getStores() {
//        return storesLiveData;
//    }


