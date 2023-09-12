package com.dgstore.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dgstore.database.MyDatabase;
import com.dgstore.databinding.FragmentOrderBinding;
import com.dgstore.model.OrderHistory;
import com.dgstore.model.OrderProduct;
import com.dgstore.model.Product;
import com.dgstore.ui.viewmodel.ProfileViewModel;
import com.dgstore.ui.adapter.RecylerViewOrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    List<OrderHistory> bagOrderList = new ArrayList<>();
    private FragmentOrderBinding binding;
    private ProfileViewModel profileViewModel;
    private RecylerViewOrderAdapter recylerViewOrderAdapter;
    MyDatabase myDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        myDatabase = MyDatabase.getMyDatabase(requireContext().getApplicationContext());
        bagOrderList = myDatabase.daoOrders().allOrderProduct();

        binding.recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        recylerViewOrderAdapter = new RecylerViewOrderAdapter(bagOrderList);
        binding.recyclerViewOrder.setAdapter(recylerViewOrderAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
