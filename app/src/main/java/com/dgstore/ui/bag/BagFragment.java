package com.dgstore.ui.bag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dgstore.databinding.FragmentBagBinding;
import com.dgstore.ui.home.HomeViewModel;

public class BagFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private RecyclerViewBagAdapter recyclerViewBagAdapter;
    private FragmentBagBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBagBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        homeViewModel.getSelectedProducts().observe(getViewLifecycleOwner(), bagList -> {
            binding.recyclerViewBag.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewBagAdapter = new RecyclerViewBagAdapter(bagList);
            binding.recyclerViewBag.setAdapter(recyclerViewBagAdapter);
            System.out.println("baglist: " + bagList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
