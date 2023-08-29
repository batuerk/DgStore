package com.dgstore.ui.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.dgstore.ProductInterface;
import com.dgstore.R;
import com.dgstore.Store;
import com.dgstore.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductInterface {

    private Handler handler;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private RecyclerViewHomeAdapter recyclerViewHomeAdapter;
    List<Store> selectedProducts = new ArrayList<>();
    List<Store> favouritedProducts = new ArrayList<>();

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

        LottieAnimationView animationView = binding.lottieAnimationView;
        animationView.setAnimation(R.raw.downloading_animation); // Animasyon dosyasının adını doğru şekilde ayarlayın

        homeViewModel.getListLiveData().observe(requireActivity(), stores -> {
            if (stores != null) {
                displayProducts(stores);
                animationView.setVisibility(View.INVISIBLE);

            }
        });
        // Servisten cevap geldiğinde

    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // Handler'ı temizle
//        if (handler != null) {
//            handler.removeCallbacksAndMessages(null);
//        }
//    }

    public void displayProducts(List<Store> stores) {
        binding.recyclerViewHome.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewHomeAdapter = new RecyclerViewHomeAdapter(stores, this);
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
    public void addBagItems(Store store) {
        boolean isNewProduct = true;
        for (int i = 0; i < selectedProducts.size(); i++) {
            System.out.println("selectedproductsize: " + selectedProducts.size());
            if (selectedProducts.get(i).getId() == store.getId()) {
                selectedProducts.get(i).setPiece(selectedProducts.get(i).getPiece() + 1);
                isNewProduct = false;
                break;
            }
        }
        if (isNewProduct) {
            store.setPiece(1);
            selectedProducts.add(store);
        }
        homeViewModel.setSelectedProducts(selectedProducts);
    }
//    @Override
//    public void addFavouriteItems(Store store) {
//        for (int i = 0; i< favouritedProducts.size(); i++) {
//            System.out.println("favouritedprodutsize: " + favouritedProducts.size());
//            if (favouritedProducts.get(i).getId() != store.getId()) {
//                favouritedProducts.get(i);
//
//            }else {
//                Toast.makeText(getContext(),"Ürün zaten var", Toast.LENGTH_SHORT).show();
//            }
//        }
//        homeViewModel.setFavouritedProducts(favouritedProducts);
//    }
    @Override
    public void addFavouriteItems(Store store) {
        boolean isProductAlreadyFavourited = false;
        for (int i = 0; i < favouritedProducts.size(); i++) {
            System.out.println("favouritedproductsize: " + favouritedProducts.size());
            if (favouritedProducts.get(i).getId() == store.getId()) {
                isProductAlreadyFavourited = true;
                break; // Ürün zaten favori olarak işaretlendiği için döngüden çık
            }
        }
        if (!isProductAlreadyFavourited) {
            favouritedProducts.add(store); // Ürünü favorilere ekle
        } else {
            Toast.makeText(getContext(), "Ürün zaten favori olarak işaretlendi.", Toast.LENGTH_SHORT).show();
        }
        homeViewModel.setFavouritedProducts(favouritedProducts);
    }
}
