package com.example.sneakercity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sneakercity.Interface.OnItemClickListener;
import com.example.sneakercity.Models.Product;
import com.example.sneakercity.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> products;
    private OnItemClickListener listener;
    private int layout;
    private Context context;

    public ProductAdapter(Context context, ArrayList<Product> products,  int layout, OnItemClickListener listener){

        this.context = context;
        this.products = products;
        this.layout = layout;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(products.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.image);

        holder.name.setText(products.get(position).getName());
        holder.model.setText(products.get(position).getModel());
        holder.price.setText(String.valueOf("RD$ "+ products.get(position).getPrice()));


        holder.bind(products.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return  products.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name, model, price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imgPhoto);
            name = itemView.findViewById(R.id.tvName);
            model = itemView.findViewById(R.id.tvModel);
            price = itemView.findViewById(R.id.tvPrice);
        }

        public void bind(final Product list, final OnItemClickListener listener){

          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  listener.onItemClick(list, getAdapterPosition());

              }
          });
        }


    }
}
