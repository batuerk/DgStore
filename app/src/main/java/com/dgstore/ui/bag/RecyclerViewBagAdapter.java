package com.dgstore.ui.bag;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dgstore.R;
import com.dgstore.Store;

public class RecyclerViewBagAdapter extends RecyclerView.Adapter<RecyclerViewBagAdapter.BagViewHolder> {

    private List<Store> storeList;

    public RecyclerViewBagAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public BagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bag_layout, parent, false);
        System.out.println("baglist: " + storeList);
        return new BagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BagViewHolder holder, int position) {
        holder.bind(storeList.get(position));
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class BagViewHolder extends RecyclerView.ViewHolder {
        private TextView productCategoryName;
        private TextView productTitleName;
        private TextView productPiece;
        private TextView productPrice;
        private ImageView productImage;


        public BagViewHolder(@NonNull View itemView) {
            super(itemView);
            productCategoryName = itemView.findViewById(R.id.product_category_name);
            productTitleName = itemView.findViewById(R.id.product_title_name);
            productPiece = itemView.findViewById(R.id.product_piece);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
        }

        public void bind(Store store) {
            productCategoryName.setText(store.getCategory()); // Bu kısma göre ayarlayın
            productTitleName.setText(store.getTitle());
            productPiece.setText(MessageFormat.format("Piece: {0}", store.getPiece()));
            double price = store.getPiece() * Double.parseDouble(store.getPrice());
            productPrice.setText(MessageFormat.format("Price: {0}₺", price));

            Glide.with(itemView.getContext())
                    .load(Uri.parse(store.getImage()))
                    .into(productImage);
        }
    }
}

