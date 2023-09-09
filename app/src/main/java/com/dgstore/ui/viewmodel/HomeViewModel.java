package com.dgstore.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgstore.model.Product;
import com.dgstore.repository.ProductRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Product>> selectedProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> favouritedProducts = new MutableLiveData<>();
    private ProductRepository productRepository;
    LiveData<List<Product>> listLiveData;

    public HomeViewModel() {
        productRepository = new ProductRepository();
        this.listLiveData = productRepository.storeResponse();
    }

    public LiveData<List<Product>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<List<Product>> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts.setValue(selectedProducts);
    }

    public LiveData<List<Product>> getFavouritedProducts() {
        return favouritedProducts;
    }

    public void setFavouritedProducts(List<Product> favouritedProducts) {
        this.favouritedProducts.setValue(favouritedProducts);
    }

}
