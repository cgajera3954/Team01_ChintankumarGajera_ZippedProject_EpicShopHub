package com.example.team01_chintankumargajera_zippedproject_epicshophub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.R;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.activities.DetailedActivity;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.models.PopularProductsModel;

import java.io.ObjectInputStream;
import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {

    private Context context;
    private List<PopularProductsModel> list;

    public PopularProductsAdapter(Context context, List<PopularProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopularProductsModel model = list.get(position);
        Glide.with(context).load(model.getImg_url()).into(holder.popularImg);
        holder.popularName.setText(model.getName());
        holder.popularPrice.setText("$"+ model.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailedActivity.class);

                intent.putExtra("detailed", model);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popularImg;
        TextView popularName, popularPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popularImg = itemView.findViewById(R.id.popular_img);
            popularName = itemView.findViewById(R.id.popular_product_name);
            popularPrice = itemView.findViewById(R.id.popular_price);
        }
    }
}
