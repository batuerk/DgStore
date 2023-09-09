package com.dgstore.ui.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dgstore.callbacks.ProductInterface;
import com.dgstore.R;
import com.dgstore.model.Product;

public class RecyclerViewBagAdapter extends RecyclerView.Adapter<RecyclerViewBagAdapter.BagViewHolder> {

    private final List<Product> productList;

    private final ProductInterface productInterface;

    public RecyclerViewBagAdapter(List<Product> productList, ProductInterface productInterface) {
        this.productList = productList;
        this.productInterface = productInterface;
    }

    @NonNull
    @Override
    public BagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bag_layout, parent, false);
        System.out.println("BagAdapterBaglist: " + productList);
        return new BagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BagViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class BagViewHolder extends RecyclerView.ViewHolder {
        private final TextView productCategoryName;
        private final TextView productTitleName;
        private final TextView productPiece;
        private final TextView productPrice;
        private final ImageView productImage;

        public BagViewHolder(@NonNull View itemView) {
            super(itemView);
            productCategoryName = itemView.findViewById(R.id.product_category_name);
            productTitleName = itemView.findViewById(R.id.product_title_name);
            productPiece = itemView.findViewById(R.id.product_piece);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            AppCompatImageButton removeButton = itemView.findViewById(R.id.removeButton);

            removeButton.setOnClickListener(view -> {
                productInterface.removeBagItems(productList.get(getBindingAdapterPosition()));
                System.out.println("remove button çalışıyor");
                Toast.makeText(view.getContext(), "Product Removed to Bag", Toast.LENGTH_SHORT).show();

            });



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

