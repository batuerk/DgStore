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
import com.dgstore.callbacks.ProductInterface;
import com.dgstore.R;
import com.dgstore.model.Product;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.RowHolder> {
    private final List<Product> productList;
    private final ProductInterface productInterface;

    public RecyclerViewHomeAdapter(List<Product> productList, ProductInterface productInterface) {
        this.productList = productList;
        this.productInterface = productInterface;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_home_layout, parent, false);
        return new RowHolder(view);
        // oluşturulan row ile recycler view'i birbirine bağlama işlemi
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(productList.get(position));
        // görünümleri viewholder da tanımlayıp bağlama işlemi burada yapılıyor
    }

    @Override
    public int getItemCount() {
        return productList.size();
        // kaç tane row oluşturulacak o demek
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        private TextView textCategory;
        private TextView textPrice;
        private TextView textTitle;
        private ImageView imageView;
        private AppCompatImageButton addButton,favButton;
        Boolean newFav = false;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
//            textCategory = itemView.findViewById(R.id.home_category);
            textTitle = itemView.findViewById(R.id.home_title);
            textPrice = itemView.findViewById(R.id.home_price);
            imageView = itemView.findViewById(R.id.home_image);
            addButton = itemView.findViewById(R.id.addButton);
            favButton = itemView.findViewById(R.id.favButton);



            favButton.setOnClickListener(view -> {
                productInterface.addFavouriteItems(productList.get(getBindingAdapterPosition()));
                System.out.println(productList.get(getBindingAdapterPosition()));
                Toast.makeText(view.getContext(), "Product Added to Favorite", Toast.LENGTH_SHORT).show();
                if (newFav) {
                    favButton.setImageResource(R.drawable.ic_favorite);
                    newFav = true;
                }else {

                }

            });

            addButton.setOnClickListener(view -> {
                productInterface.addBagItems(productList.get(getBindingAdapterPosition()));
                Toast.makeText(view.getContext(), "Product Added to Bag", Toast.LENGTH_SHORT).show();
            });

        }

        public void bind(Product product) {
//            textCategory.setText(product.getCategory());
            textTitle.setText(product.getTitle());
            double price = Double.parseDouble(product.getPrice()) * 10;
            textPrice.setText(MessageFormat.format("Price: {0}₺", price));

            Glide.with(itemView.getContext())
                    .load(Uri.parse(product.getImage()))
                    .into(imageView);
        }
    }
}
