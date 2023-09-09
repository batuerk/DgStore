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
import com.dgstore.model.Product;
import com.dgstore.databinding.FragmentHomeBinding;
import com.dgstore.ui.viewmodel.HomeViewModel;
import com.dgstore.ui.adapter.RecyclerViewHomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductInterface {

    private Handler handler;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private RecyclerViewHomeAdapter recyclerViewHomeAdapter;
    List<Product> selectedProducts = new ArrayList<>();
    List<Product> favouritedProducts = new ArrayList<>();
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
            if (stores != null) {
                displayProducts(stores);
                animationView.setVisibility(View.INVISIBLE);

            }
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
        for (int i = 0; i < favouritedProducts.size(); i++) {
            System.out.println("favouritedproductsize: " + favouritedProducts.size());
            if (favouritedProducts.get(i).getId() == product.getId()) {
                isProductAlreadyFavourited = true;
                break; // Ürün zaten favori olarak işaretlendiği için döngüden çık
            }
        }
        if (!isProductAlreadyFavourited) {
            favouritedProducts.add(product); // Ürünü favorilere ekle
        } else {
            Toast.makeText(getContext(), "Ürün zaten favori olarak işaretlendi.", Toast.LENGTH_SHORT).show();
        }
        homeViewModel.setFavouritedProducts(favouritedProducts);
    }

    @Override
    public void removeBagItems(Product product) {

    }

    @Override
    public void removeFavouriteItems(Product product) {

    }


}
