package com.dgstore.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dgstore.R;
import com.dgstore.Store;
import com.dgstore.databinding.FragmentShopBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ShopFragment extends Fragment{

    private FragmentShopBinding binding;
    private ShopViewModel shopViewModel;
    RecyclerView recyclerView;
    RecyclerViewShopAdapter recyclerViewShopAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerViewShop);

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        shopViewModel.fetchProducts();

        shopViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                displayProducts(stores);
            }
        });
        return root;
    }

    private void displayProducts(List<Store> stores) {
        List<String> categoryList = new ArrayList<>();
        for (Store store : stores) {
            categoryList.add(store.getCategory());

        }
        List<String> storeList = removeDuplicates(categoryList);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewShopAdapter = new RecyclerViewShopAdapter(storeList);
        recyclerView.setAdapter(recyclerViewShopAdapter);


    }

    public static <T> List<T> removeDuplicates(List<T> list) {

        HashSet<T> uniqueElements = new HashSet<>(list);

        return new ArrayList<>(uniqueElements);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
