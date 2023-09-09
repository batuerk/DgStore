package com.dgstore.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dgstore.R;
import com.dgstore.database.MyDatabase;
import com.dgstore.databinding.FragmentPaymentBinding;
import com.dgstore.model.Product;
import com.dgstore.ui.adapter.RecyclerViewPaymentAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {
    private FragmentPaymentBinding binding;
    private RecyclerViewPaymentAdapter recyclerViewPaymentAdapter;
    MyDatabase myDatabase;
    List<Product> bagPaymentList = new ArrayList<>();
    String payerName;
    String payerSurname;
    String payerAddress;
    String payerCardNo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myDatabase = MyDatabase.getMyDatabase(requireContext().getApplicationContext());
        bagPaymentList = myDatabase.daoProduct().allSelectedProduct();

        binding.recyclerViewPayment.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPaymentAdapter = new RecyclerViewPaymentAdapter(bagPaymentList);
        binding.recyclerViewPayment.setAdapter(recyclerViewPaymentAdapter);

        productsPrice(bagPaymentList);
        paymentScreen();

    }

    public void paymentScreen() {

        binding.paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payerName = binding.paymentNameText.getText().toString();
                payerSurname = binding.paymentSurnameText.getText().toString();
                payerAddress = binding.paymentAddressText.getText().toString();
                payerCardNo = binding.paymentCardNoText.getText().toString();

                if (!payerName.isEmpty() && !payerSurname.isEmpty() && !payerAddress.isEmpty() && !payerCardNo.isEmpty()) {
                    Toast.makeText(requireContext().getApplicationContext(), "Payment Success", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_navigation_payment_to_navigation_home);
                    for (int i = 0; i < bagPaymentList.size(); i++) {
                        myDatabase.daoProduct().delete(bagPaymentList.get(i));
                    }


                }else {
                    Toast.makeText(requireContext().getApplicationContext(), "Fill area", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void productsPrice(List<Product> productList) {
        double finalPrice = 0.0;

        for (Product product : productList) {
            double price = product.getPiece() * Double.parseDouble(product.getPrice());
            double sumPrice = price * 10;
            finalPrice += sumPrice;
        }

        binding.paymentAmountText.setText(MessageFormat.format("Price: {0}₺", finalPrice));

    }
}
