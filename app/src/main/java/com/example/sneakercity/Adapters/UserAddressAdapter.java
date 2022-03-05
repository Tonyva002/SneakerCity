package com.example.sneakercity.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakercity.Activities.PaymentShippingActivity;
import com.example.sneakercity.Activities.UserActivity;
import com.example.sneakercity.Models.User;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;

import org.parceler.Parcels;

import java.util.ArrayList;



public class UserAddressAdapter extends RecyclerView.Adapter<UserAddressAdapter.ViewHolder> {

    private final ArrayList<User> list;
    private final Context context;
    private final int layout;
    private final Activity activity;

    public UserAddressAdapter(Context context, ArrayList<User> list, int layout, Activity activity){
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.user.setText(list.get(position).getName() + " " +
                list.get(position).getLastName() + "\n" +
                list.get(position).getAddress() + "\n" +
                list.get(position).getCountry() + "\n" +
                list.get(position).getCommunity() + "\n" +
                list.get(position).getCity() + "\n" +
                list.get(position).getPostalCode() + "\n" +
                list.get(position).getPhone());

        holder.user.setOnClickListener(view -> goToPaymentShipping(holder.getAbsoluteAdapterPosition()));

        holder.edit.setOnClickListener(view -> goToEditUser(list.get(holder.getAbsoluteAdapterPosition()).getIdAddress()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void goToEditUser(String key){
        Intent intent = new Intent(context, UserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }


    private void goToPaymentShipping(int position) {
        Parcelable wrapped = Parcels.wrap(list.get(position));
        Intent intent = new Intent(context, PaymentShippingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PaymentShippingActivity.mUser = list.get(position);
        intent.putExtra("value", wrapped);
        context.startActivity(intent);
        activity.finish();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView user;
        private final MaterialButton edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.tvShippingAddress);
            edit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
