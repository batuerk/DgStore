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
import com.dgstore.database.MyDatabase;
import com.dgstore.databinding.FragmentFavouriteBinding;
import com.dgstore.model.FavoriteProduct;
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
    List<FavoriteProduct> favoriteDataList = new ArrayList<>();
    MyDatabase myDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        myDatabase = MyDatabase.getMyDatabase(getActivity());

        homeViewModel.getFavouritedProducts().observe(getViewLifecycleOwner(), bagList -> {
            favoriteProductList = bagList;

            List<Product> productItem = favoriteProductList;
            for (Product item : productItem) {
                FavoriteProduct existingFavoriteProduct = myDatabase.daoFavorite().getFavoriteId(item.getId());

                if (existingFavoriteProduct == null) {
                    FavoriteProduct favoriteProductItem = new FavoriteProduct();
                    favoriteProductItem.setId(item.getId());
                    favoriteProductItem.setCategory(item.getCategory());
                    favoriteProductItem.setImage(item.getImage());
                    favoriteProductItem.setPrice(item.getPrice());
                    favoriteProductItem.setDescription(item.getDescription());
                    favoriteProductItem.setTitle(item.getTitle());
                    myDatabase.daoFavorite().addFavorite(favoriteProductItem);
                    recyclerViewFavouriteAdapter.notifyDataSetChanged();

                    System.out.println("--------");
                }else {
                    System.out.println("********");
                }
            }

            System.out.println("baglist: " + bagList);
        });
        favoriteDataList = myDatabase.daoFavorite().allSelectedFavorite();
        binding.recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFavouriteAdapter = new RecyclerViewFavouriteAdapter(favoriteDataList, this);
        binding.recyclerViewFavourite.setAdapter(recyclerViewFavouriteAdapter);

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
    public void removeFavouriteItems(FavoriteProduct product) {
        System.out.println("removebag items çalışıyor");

        for (int i = 0; i < favoriteDataList.size(); i++) {
            System.out.println("selectedremoveproductsize: " + favoriteDataList.size());
            if (favoriteDataList.get(i).getId() == product.getId()) {
                System.out.println("bura");
                int currentPiece = favoriteDataList.get(i).getPiece();
                if (currentPiece > 1) {
                    System.out.println("bura2");
                    favoriteDataList.get(i).setPiece(currentPiece - 1);
                    break;
                } else {
                    System.out.println("bura3");
                    favoriteDataList.remove(i);
                    myDatabase.daoFavorite().delete(product);
                    recyclerViewFavouriteAdapter.notifyItemRemoved(i);
                    break;
                }
            }
        }
//        homeViewModel.setFavouritedProducts(favoriteProductList);

    }
}