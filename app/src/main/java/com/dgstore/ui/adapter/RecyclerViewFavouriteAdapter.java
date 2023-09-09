package com.dgstore.ui.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dgstore.R;
import com.dgstore.callbacks.ProductInterface;
import com.dgstore.model.FavoriteProduct;
import com.dgstore.model.Product;

import java.text.MessageFormat;
import java.util.List;


public class RecyclerViewFavouriteAdapter extends RecyclerView.Adapter<RecyclerViewFavouriteAdapter.FavouriteViewHolder> {

    private List<FavoriteProduct> favList;

    private final ProductInterface productInterface;

    public RecyclerViewFavouriteAdapter(List<FavoriteProduct> favList, ProductInterface productInterface) {
        this.favList = favList;
        this.productInterface = productInterface;
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
        private AppCompatImageButton removeButton;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            productCategoryName = itemView.findViewById(R.id.product_category_name);
            productTitleName = itemView.findViewById(R.id.product_title_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            removeButton = itemView.findViewById(R.id.removeButton);

            removeButton.setOnClickListener(view -> {
                productInterface.removeFavouriteItems(favList.get(getBindingAdapterPosition()));
                System.out.println("remove button çalışıyor");
                Toast.makeText(view.getContext(), "Product Removed to Favorite", Toast.LENGTH_SHORT).show();

            });
        }

        public void bind(FavoriteProduct product) {
            productCategoryName.setText(product.getCategory()); // Bu kısma göre ayarlayın
            productTitleName.setText(product.getTitle());
            double sumPrice = Double.parseDouble(product.getPrice()) * 10;
            productPrice.setText(MessageFormat.format("Price: {0}₺", sumPrice));

            Glide.with(itemView.getContext())
                    .load(Uri.parse(product.getImage()))
                    .into(productImage);
        }
    }


}
