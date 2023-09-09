package com.dgstore.ui.fragment;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dgstore.R;
import com.dgstore.callbacks.ProductInterface;
import com.dgstore.database.MyDatabase;
import com.dgstore.model.FavoriteProduct;
import com.dgstore.model.Product;
import com.dgstore.databinding.FragmentBagBinding;
import com.dgstore.ui.adapter.RecyclerViewBagAdapter;
import com.dgstore.ui.viewmodel.HomeViewModel;
import com.dgstore.workmanager.NotificationWorker2;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BagFragment extends Fragment implements DefaultLifecycleObserver, ProductInterface {
    private HomeViewModel homeViewModel;
    private RecyclerViewBagAdapter recyclerViewBagAdapter;
    private FragmentBagBinding binding;
    List<Product> bagProductList = new ArrayList<>();
    MyDatabase myDatabase;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBagBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel.class);

        myDatabase = MyDatabase.getMyDatabase(requireContext().getApplicationContext());
        bagProductList = myDatabase.daoProduct().allSelectedProduct();

        binding.recyclerViewBag.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBagAdapter = new RecyclerViewBagAdapter(bagProductList, this);
        binding.recyclerViewBag.setAdapter(recyclerViewBagAdapter);

        if (myDatabase.daoProduct().allSelectedProduct().size()<1){
            setupNoProducts();
        }

        if (myDatabase.daoProduct().allSelectedProduct().size() > 0) {
            productsPrice(bagProductList);
            setupProductView();

        }


        homeViewModel.getSelectedProducts().observe(getViewLifecycleOwner(), bagList -> {

            System.out.println("baglist: " + bagList);



        });

    }

    private void setupProductView() {
        // Örnek: binding.recyclerViewProducts.setAdapter(adapter);
        binding.linearNoProduct.setVisibility(View.INVISIBLE);
        binding.bagAcceptText.setVisibility(View.VISIBLE);
        binding.productsPriceText.setVisibility(View.VISIBLE);

        binding.bagAcceptText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_bag_to_navigation_payment);

            }
        });
    }

    // Ürünler yokken gösterilecek metni ayarlamak için bir örnek metod
    public void setupNoProducts() {
//        binding.noProduct.setVisibility(View.VISIBLE); // Görünür yapabilirsiniz
        binding.linearNoProduct.setVisibility(View.VISIBLE);
        binding.bagAcceptText.setVisibility(View.INVISIBLE);
        binding.productsPriceText.setVisibility(View.INVISIBLE);

    }

    public void productsPrice(List<Product> productList) {
        double finalPrice = 0.0;

        for (Product product : productList) {
            double price = product.getPiece() * Double.parseDouble(product.getPrice());
            double sumPrice = price * 10;
            finalPrice += sumPrice;
        }

        binding.productsPriceText.setText(MessageFormat.format("Price: {0}₺", finalPrice));

    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        if (bagProductList.size()>0){
            System.out.println(bagProductList.size());
            NotificationWorker2.periodicWork();
        }
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
        System.out.println("removebag items çalışıyor");

        for (int i = 0; i < bagProductList.size(); i++) {
            System.out.println("selectedremoveproductsize: " + bagProductList.size());
            if (bagProductList.get(i).getId() == product.getId()) {
                System.out.println("bura");
                int currentPiece = bagProductList.get(i).getPiece();
                if (currentPiece > 1) {
                    System.out.println("bura2");
                    bagProductList.get(i).setPiece(currentPiece - 1);
                    myDatabase.daoProduct().update(bagProductList.get(i));
                    recyclerViewBagAdapter.notifyItemChanged(i);
                } else {
                    System.out.println("bura3");
                    bagProductList.remove(i);
                    myDatabase.daoProduct().delete(product);
                    recyclerViewBagAdapter.notifyDataSetChanged();
                }
                updateProductView();
                break;


            }
        }
//        homeViewModel.setSelectedProducts(bagProductList);
    }

    @Override
    public void removeFavouriteItems(FavoriteProduct product) {

    }

    private void updateProductView() {
        // Ürünlerin durumuna göre görünümü güncelle
        if (bagProductList.isEmpty()) {
            setupNoProducts();
        } else {
            setupProductView();
        }
    }

}
