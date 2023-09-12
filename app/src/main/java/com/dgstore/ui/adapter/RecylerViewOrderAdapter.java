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
import com.dgstore.model.OrderHistory;
import com.dgstore.model.OrderProduct;
import com.dgstore.model.Product;

import java.text.MessageFormat;
import java.util.List;

public class RecylerViewOrderAdapter extends RecyclerView.Adapter<RecylerViewOrderAdapter.RowHolder> {
    private String[] colors = {"#3232d6","#7857a1","#f58220","#ff50a0","#065535","#bcffe3","#b8e6f2"};
    private final List<OrderHistory> productList;

    public RecylerViewOrderAdapter(List<OrderHistory> productList) {
        System.out.println("ürün listesi: " + productList.toString());
        this.productList = productList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_order_layout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        private final TextView productCategoryName;
        private final TextView productTitleName;
        private final TextView productPiece;
        private final TextView productPrice;
        private final ImageView productImage;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            productCategoryName = itemView.findViewById(R.id.order_category_name);
            productTitleName = itemView.findViewById(R.id.order_title_name);
            productPiece = itemView.findViewById(R.id.order_piece);
            productPrice = itemView.findViewById(R.id.order_price);
            productImage = itemView.findViewById(R.id.order_image);        }

        public void bind(OrderHistory product) {
//            productCategoryName.setText(product.getCategory()); // Bu kısma göre ayarlayın
//            productTitleName.setText(product.getTitle());
//            productPiece.setText(MessageFormat.format("Piece: {0}", product..getPiece()));
//            double price = product.getPiece() * Double.parseDouble(product.getPrice());
//            double sumPrice = price * 10;
//            productPrice.setText(MessageFormat.format("Price: {0}₺", sumPrice));
//
//            Glide.with(itemView.getContext())
//                    .load(Uri.parse(product.getImage()))
//                    .into(productImage);
        }
    }
}
