package com.dgstore.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgstore.R;
import com.dgstore.model.User;

import java.util.List;

public class RecylerViewProfileAdapter extends RecyclerView.Adapter<RecylerViewProfileAdapter.RowHolder> {
    private String[] colors = {"#3232d6","#7857a1","#f58220","#ff50a0","#065535","#bcffe3","#b8e6f2"};
    private List<User> userInfo;

    public RecylerViewProfileAdapter(List<User> infoUser) {
        System.out.println("ürün listesi: " + infoUser.toString());
        this.userInfo = infoUser;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_profile_layout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewProfileAdapter.RowHolder holder, int position) {
        holder.bind(userInfo.get(position), colors, position);
    }

    @Override
    public int getItemCount() {
        return userInfo.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        private TextView textInfo;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            textInfo = itemView.findViewById(R.id.text_info);
        }

        public void bind(User info, String[] colors, Integer position) {
            textInfo.setText("Name: " + User.class.getName() + "\nEmail: " );
        }
    }
}
