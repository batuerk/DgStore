package com.dgstore.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.dgstore.databinding.FragmentProfileBinding;
import com.dgstore.ui.viewmodel.ProfileViewModel;
import com.dgstore.ui.adapter.RecylerViewProfileAdapter;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private RecylerViewProfileAdapter adapter;
    RecyclerView recyclerView;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
//        profileViewModel.init(requireActivity().getApplication());

//        recyclerView = root.findViewById(R.id.recyclerViewProfile);

        final TextView textView = binding.textName;
        final TextView textView1 = binding.textEmail;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        profileViewModel.getText1().observe(getViewLifecycleOwner(), textView1::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
