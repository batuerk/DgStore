package com.dgstore.ui.bag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgstore.DataRepository;
import com.dgstore.Store;
import com.dgstore.ui.home.HomeViewModel;

import java.util.List;

public class BagViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<Bag>> cartItemsLiveData;
    private HomeViewModel homeViewModel;
    private DataRepository dataRepository;

    public LiveData<List<Store>> getCartItems() {
        return (LiveData<List<Store>>) dataRepository.getCartItems();
    }



    public BagViewModel() {
        mText = new MutableLiveData<>();

        cartItemsLiveData = new MutableLiveData<>();
        dataRepository = new DataRepository();

//        mText.setValue(String.valueOf(homeViewModel.cartItems));

    }

    public LiveData<List<Bag>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public LiveData<String> getText() {

        return mText;
    }
}