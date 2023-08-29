package com.dgstore.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgstore.Store;
import com.dgstore.repository.ProductRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Store>> selectedProducts = new MutableLiveData<>();
    private MutableLiveData<List<Store>> favouritedProducts = new MutableLiveData<>();
    private ProductRepository productRepository;
    LiveData<List<Store>> listLiveData;

    public HomeViewModel() {
        productRepository = new ProductRepository();
        this.listLiveData = productRepository.storeResponse();
    }

    public LiveData<List<Store>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<List<Store>> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Store> selectedProducts) {
        this.selectedProducts.setValue(selectedProducts);
    }

    public LiveData<List<Store>> getFavouritedProducts() {
        return favouritedProducts;
    }

    public void setFavouritedProducts(List<Store> favouritedProducts) {
        this.favouritedProducts.setValue(favouritedProducts);
    }

}
