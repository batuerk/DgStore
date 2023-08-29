package com.dgstore.ui.shop;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgstore.R;
import com.dgstore.Store;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewShopAdapter extends RecyclerView.Adapter<RecyclerViewShopAdapter.RowHolder> {

    private List<String> storeList;
    private String[] colors = {"#3232d6","#7857a1","#f58220","#ff50a0","#065535","#bcffe3","#b8e6f2"};
    private List<String> emptyCategory = new ArrayList();

    public RecyclerViewShopAdapter(List<String> storeList) {
        System.out.println("ürün listesi: " + storeList.toString());
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_shop_layout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(storeList.get(position), colors, position);
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        private TextView textCategory;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.text_category);
        }

        @SuppressLint("SuspiciousIndentation")
        public void bind(String category, String[] colors, Integer position) {
            emptyCategory.add(category);
            textCategory.setText(category);

        }
    }
}


