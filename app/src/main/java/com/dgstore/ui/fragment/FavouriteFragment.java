package com.dgstore.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dgstore.callbacks.ProductInterface;
import com.dgstore.databinding.FragmentFavouriteBinding;
import com.dgstore.model.Product;
import com.dgstore.ui.adapter.RecyclerViewFavouriteAdapter;
import com.dgstore.ui.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements ProductInterface {
    private HomeViewModel homeViewModel;
    private RecyclerViewFavouriteAdapter recyclerViewFavouriteAdapter;
    private FragmentFavouriteBinding binding;
    List<Product> favoriteProductList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        homeViewModel.getFavouritedProducts().observe(getViewLifecycleOwner(), bagList -> {
            favoriteProductList = bagList;
            binding.recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewFavouriteAdapter = new RecyclerViewFavouriteAdapter(bagList, this);
            binding.recyclerViewFavourite.setAdapter(recyclerViewFavouriteAdapter);
            System.out.println("baglist: " + bagList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void addBagItems(Product product) {

    }

    @Override
    public void addFavouriteItems(Product product) {

    }

    @Override
    public void removeBagItems(Product product) {

    }

    @Override
    public void removeFavouriteItems(Product product) {
        System.out.println("removebag items çalışıyor");

        for (int i = 0; i < favoriteProductList.size(); i++) {
            System.out.println("selectedremoveproductsize: " + favoriteProductList.size());
            if (favoriteProductList.get(i).getId() == product.getId()) {
                System.out.println("bura");
                int currentPiece = favoriteProductList.get(i).getPiece();
                if (currentPiece > 1) {
                    System.out.println("bura2");
                    favoriteProductList.get(i).setPiece(currentPiece - 1);
                    break;
                } else {
                    System.out.println("bura3");
                    favoriteProductList.remove(i);
                    break;
                }
            }
        }
        homeViewModel.setFavouritedProducts(favoriteProductList);

    }
}