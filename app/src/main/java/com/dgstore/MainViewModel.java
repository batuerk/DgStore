package com.dgstore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgstore.retrofit.RetrofitClient;
import com.dgstore.retrofit.StoreAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Store>> productsLiveData;
    private StoreAPI storeAPI;

    public MainViewModel() {
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

//        call.enqueue(new Callback<List<Store>>() {
//            @Override
//            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
//                String responseBody = response.body().toString();
//
//                try {
//                    JSONObject json = new JSONObject(responseBody);
//                    Log.d(TAG, json.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                if (response.isSuccessful()) {
//                    List<Store> products = response.body();
//                    productsLiveData.setValue(products);
//                } else {
//                    // Hata durumunu işle
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Store>> call, Throwable t){
//                // Hata durumunu işle
//                }
//        });



//--------------------------------------------------------------------//

//    public void loadData() {
//        StoreAPI storeAPI1 = retrofit.create(StoreAPI.class);
//
//        Call<Store> call = storeAPI.getProductById(1);
//
//        call.enqueue(new Callback<Store>() {
//            @Override
//            public void onResponse(Call<Store> call, Response<Store> response) {
//                if (response.isSuccessful()) {
//                    Store store = response.body();
//                    if (store != null) {
//                        storesLiveData.setValue(store);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Store> call, Throwable t) {
//            }
//        });
//    }
//
//    public LiveData<Store> getStores() {
//        return storesLiveData;
//    }


