package com.dgstore.ui.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dgstore.ProductInterface;
import com.dgstore.R;
import com.dgstore.Store;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.RowHolder> {
    private final List<Store> storeList;
    private final ProductInterface productInterface;

    public RecyclerViewHomeAdapter(List<Store> storeList, ProductInterface productInterface) {
        this.storeList = storeList;
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
        holder.bind(storeList.get(position));
        // görünümleri viewholder da tanımlayıp bağlama işlemi burada yapılıyor
    }

    @Override
    public int getItemCount() {
        return storeList.size();
        // kaç tane row oluşturulacak o demek
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        private TextView textCategory;
        private TextView textPrice;
        private TextView textTitle;
        private ImageView imageView;
        private AppCompatImageButton addButton,favButton;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.home_category);
            textTitle = itemView.findViewById(R.id.home_title);
            textPrice = itemView.findViewById(R.id.home_price);
            imageView = itemView.findViewById(R.id.home_image);
            addButton = itemView.findViewById(R.id.addButton);
            favButton = itemView.findViewById(R.id.favButton);

            favButton.setOnClickListener(view -> {
                productInterface.addFavouriteItems(storeList.get(getBindingAdapterPosition()));
                System.out.println(storeList.get(getBindingAdapterPosition()));
            });

            addButton.setOnClickListener(view -> {
                productInterface.addBagItems(storeList.get(getBindingAdapterPosition()));
            });

        }

        public void bind(Store store) {
            textCategory.setText(store.getCategory());
            textTitle.setText(store.getTitle());
            textPrice.setText(MessageFormat.format("Price: {0}₺", store.getPrice()));

            Glide.with(itemView.getContext())
                    .load(Uri.parse(store.getImage()))
                    .into(imageView);
        }
    }
}
