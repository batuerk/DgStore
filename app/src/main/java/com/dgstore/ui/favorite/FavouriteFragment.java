package com.dgstore.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dgstore.databinding.FragmentFavouriteBinding;
import com.dgstore.ui.home.HomeViewModel;

public class FavouriteFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private RecyclerViewFavouriteAdapter recyclerViewFavouriteAdapter;
    private FragmentFavouriteBinding binding;

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
            binding.recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewFavouriteAdapter = new RecyclerViewFavouriteAdapter(bagList);
            binding.recyclerViewFavourite.setAdapter(recyclerViewFavouriteAdapter);
            System.out.println("baglist: " + bagList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}