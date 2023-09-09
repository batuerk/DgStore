package com.dgstore.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.dgstore.model.Product;
import com.dgstore.ui.viewmodel.MainViewModel;
import com.dgstore.databinding.ActivityMainBinding;
import com.dgstore.ui.adapter.RecyclerViewHomeAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        RecyclerViewHomeAdapter.class.getMethods();
        mainViewModel.getProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                displayProducts(products);

            }
        });

        mainViewModel.fetchProducts();

    }

    private void displayProducts(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(product.getId()).append("\n");

        }
        binding.textView.setText(sb.toString());

    }

}



