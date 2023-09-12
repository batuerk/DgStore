package com.dgstore.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.dgstore.callbacks.ProductInterface;
import com.dgstore.R;
import com.dgstore.database.MyDatabase;
import com.dgstore.model.FavoriteProduct;
import com.dgstore.model.Product;
import com.dgstore.databinding.FragmentHomeBinding;
import com.dgstore.ui.adapter.RecyclerViewFavouriteAdapter;
import com.dgstore.ui.viewmodel.HomeViewModel;
import com.dgstore.ui.adapter.RecyclerViewHomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductInterface {

    private Handler handler;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private RecyclerViewHomeAdapter recyclerViewHomeAdapter;
    private RecyclerViewFavouriteAdapter recyclerViewFavouriteAdapter;
    List<Product> selectedProducts = new ArrayList<>();
    List<Product> favoriteProducts = new ArrayList<>();
    List<Product> productList;
    MyDatabase myDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myDatabase = MyDatabase.getMyDatabase(getActivity());
        selectedProducts = myDatabase.daoProduct().allSelectedProduct();
        System.out.println("liste: " + myDatabase.daoProduct().allSelectedProduct().toString());

        LottieAnimationView animationView = binding.lottieAnimationView;
        animationView.setAnimation(R.raw.downloading_animation); // Animasyon dosyasının adını doğru şekilde ayarlayın

        homeViewModel.getListLiveData().observe(requireActivity(), stores -> {
            if (stores != null && !stores.isEmpty()) {
                productList = new ArrayList<>(stores);
                System.out.println("stores: " + stores.toString());
                System.out.println("data: " + myDatabase.daoFavorite().allSelectedFavorite().toString());

                for (FavoriteProduct favoriteProduct : myDatabase.daoFavorite().allSelectedFavorite()) {
                    if (favoriteProduct != null) {
                        for (int i = 0 ; i < productList.size() ; i ++) {
                            if (productList.get(i).getId() == favoriteProduct.getId()) {
                                productList.get(i).setIsAddedFavorite(true);
                                System.out.println("?????????");

                            }

                        }


                    }
                }
                    displayProducts(productList);
            }else {
                Toast.makeText(requireContext(),"ürün listesi çekilemedi",Toast.LENGTH_SHORT).show();
            }
            animationView.setVisibility(View.INVISIBLE);
        });

    }

    public void displayProducts(List<Product> products) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        binding.recyclerViewHome.setLayoutManager(gridLayoutManager);
        recyclerViewHomeAdapter = new RecyclerViewHomeAdapter(products,this);
        binding.recyclerViewHome.setAdapter(recyclerViewHomeAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void addBagItems(Product product) {
        boolean isNewProduct = true;
        System.out.println("add bag selected product size" + selectedProducts.size());
        for (int i = 0; i < selectedProducts.size(); i++) {
            System.out.println("selectedproductsize: " + selectedProducts.size());
            if (selectedProducts.get(i).getId() == product.getId()) {
                selectedProducts.get(i).setPiece(selectedProducts.get(i).getPiece() + 1);
                myDatabase.daoProduct().update(selectedProducts.get(i));
                isNewProduct = false;
                break;
            }
        }
        if (isNewProduct) {
            product.setPiece(1);
            System.out.println("selectedproductsizeNew: " + selectedProducts.size());
            selectedProducts.add(product);
            myDatabase.daoProduct().addProduct(product);
        }

//        homeViewModel.setSelectedProducts(selectedProducts);

    }

    @Override
    public void addFavouriteItems(Product product) {
        boolean isProductAlreadyFavourited = false;
        for (int i = 0; i < favoriteProducts.size(); i++) {
            System.out.println("favouritedproductsize: " + favoriteProducts.size());
            if (favoriteProducts.get(i).getId() == product.getId()) {
                isProductAlreadyFavourited = true;
                break; // Ürün zaten favori olarak işaretlendiği için döngüden çık
            }
        }
        if (!isProductAlreadyFavourited) {
            favoriteProducts.add(product); // Ürünü favorilere ekle
            Toast.makeText(getContext(), "Product Added to Favorite", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Ürün zaten favori olarak işaretlendi.", Toast.LENGTH_SHORT).show();
        }

        for (Product item : favoriteProducts) {
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

                System.out.println("--------");
            }else {
                System.out.println("********");
            }
        }
    }

    @Override
    public void removeBagItems(Product product) {

    }

    @Override
    public void removeFavouriteItems(FavoriteProduct product) {

    }


}
