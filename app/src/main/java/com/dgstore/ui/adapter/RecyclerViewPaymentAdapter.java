package com.dgstore.ui.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dgstore.R;
import com.dgstore.model.Product;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewPaymentAdapter extends RecyclerView.Adapter<RecyclerViewPaymentAdapter.PaymentViewHolder>{
    private final List<Product> productList;
    public RecyclerViewPaymentAdapter(List<Product> productList) {
        this.productList = productList;

    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payment_layout, parent, false);
        System.out.println("PaymentAdapterBaglist: " + productList);
        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        private final TextView productCategoryName;
        private final TextView productTitleName;
        private final TextView productPiece;
        private final TextView productPrice;
        private final ImageView productImage;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            productCategoryName = itemView.findViewById(R.id.payment_category_name);
            productTitleName = itemView.findViewById(R.id.payment_title_name);
            productPiece = itemView.findViewById(R.id.payment_piece);
            productPrice = itemView.findViewById(R.id.payment_price);
            productImage = itemView.findViewById(R.id.payment_image);

        }

        public void bind(Product product) {
            productCategoryName.setText(product.getCategory()); // Bu kısma göre ayarlayın
            productTitleName.setText(product.getTitle());
            productPiece.setText(MessageFormat.format("Piece: {0}", product.getPiece()));
            double price = product.getPiece() * Double.parseDouble(product.getPrice());
            double sumPrice = price * 10;
            productPrice.setText(MessageFormat.format("Price: {0}₺", sumPrice));

            Glide.with(itemView.getContext())
                    .load(Uri.parse(product.getImage()))
                    .into(productImage);

        }

    }
}
