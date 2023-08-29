package com.dgstore.ui.favorite;

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
import com.dgstore.Store;

import java.text.MessageFormat;
import java.util.List;


public class RecyclerViewFavouriteAdapter extends RecyclerView.Adapter<RecyclerViewFavouriteAdapter.FavouriteViewHolder> {

//    private List<Store> storeList;
    private List<Store> favList;

    public RecyclerViewFavouriteAdapter(List<Store> favList) {
        this.favList = favList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favourite_layout, parent, false);
        return new FavouriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        holder.bind(favList.get(position));

    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private TextView productCategoryName;
        private TextView productTitleName;
        private TextView productPrice;
        private ImageView productImage;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            productCategoryName = itemView.findViewById(R.id.product_category_name);
            productTitleName = itemView.findViewById(R.id.product_title_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
        }

        public void bind(Store store) {
            productCategoryName.setText(store.getCategory()); // Bu kısma göre ayarlayın
            productTitleName.setText(store.getTitle());
            productPrice.setText(MessageFormat.format("Price: {0}₺", store.getPrice()));

            Glide.with(itemView.getContext())
                    .load(Uri.parse(store.getImage()))
                    .into(productImage);
        }
    }


}
