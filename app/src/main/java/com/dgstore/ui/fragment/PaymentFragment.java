package com.dgstore.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dgstore.R;
import com.dgstore.database.MyDatabase;
import com.dgstore.databinding.FragmentPaymentBinding;
import com.dgstore.model.FavoriteProduct;
import com.dgstore.model.OrderHistory;
import com.dgstore.model.OrderProduct;
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
    EditText editText;

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

        editText = binding.paymentCardNoText;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cardNo = charSequence.toString();

                if (cardNo.startsWith("4")) {
                    binding.cardView.setBackgroundResource(R.drawable.visa);
                    binding.cardView.setVisibility(View.VISIBLE);
                } else if (cardNo.startsWith("5")) {
                    binding.cardView.setBackgroundResource(R.drawable.mastercard2);
                    binding.cardView.setVisibility(View.VISIBLE);

                }else {
                    binding.cardView.setVisibility(View.GONE);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payerName = binding.paymentNameText.getText().toString();
                payerSurname = binding.paymentSurnameText.getText().toString();
                payerAddress = binding.paymentAddressText.getText().toString();
                payerCardNo = binding.paymentCardNoText.getText().toString();

                double totalAmount = 0;

                if (!payerName.isEmpty() && !payerSurname.isEmpty() && !payerAddress.isEmpty() && !payerCardNo.isEmpty()) {
                    Toast.makeText(requireContext().getApplicationContext(), "Payment Success", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_navigation_payment_to_navigation_home);
                    OrderHistory orderHistory = new OrderHistory();
                    orderHistory.setOrderProductList(new ArrayList<>());
                    for (int i = 0; i < bagPaymentList.size(); i++) {
                        OrderProduct orderProduct = new OrderProduct();
                        orderProduct.setPiece(bagPaymentList.get(i).getPiece());
                        orderProduct.setCategory(bagPaymentList.get(i).getCategory());
                        orderProduct.setImage(bagPaymentList.get(i).getImage());
                        orderProduct.setDescription(bagPaymentList.get(i).getDescription());
                        orderProduct.setTitle(bagPaymentList.get(i).getTitle());
                        orderProduct.setPrice(bagPaymentList.get(i).getPrice());
                        double tAmount = Double.parseDouble(bagPaymentList.get(i).getPrice()) * bagPaymentList.get(i).getPiece();
                        totalAmount += tAmount * 10;
                        orderHistory.getOrderProductList().add(orderProduct);
                        orderHistory.setTotalAmount(totalAmount);



                    }

                    myDatabase.daoOrders().addOrder(orderHistory);
                    for (Product product : bagPaymentList) {
                        myDatabase.daoProduct().delete(product);
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
