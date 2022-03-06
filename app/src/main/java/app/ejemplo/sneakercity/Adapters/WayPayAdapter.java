package app.ejemplo.sneakercity.Adapters;

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

import app.ejemplo.sneakercity.Activities.CardActivity;
import app.ejemplo.sneakercity.Activities.PaymentShippingActivity;
import app.ejemplo.sneakercity.Models.Card;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;

import org.parceler.Parcels;

import java.util.ArrayList;

public class WayPayAdapter extends RecyclerView.Adapter<WayPayAdapter.ViewHolder>{

    private final ArrayList<Card> list;
    private final Context context;
    private final int layout;
    private final Activity activity;

public WayPayAdapter(Context context, ArrayList<Card> list, int layout, Activity activity){
    this.context = context;
    this.list = list;
    this.layout = layout;
    this.activity = activity;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.infoCard.setText(new StringBuffer()
    .append(list.get(position).getCardNumber()).append("\n")
    .append(list.get(position).getExpirationDate()).append("\n")
    .append(list.get(position).getNamePrintCard()));

    holder.infoCard.setOnClickListener(view -> goToPaymentShipping(holder.getAbsoluteAdapterPosition()));

    holder.edit.setOnClickListener(view -> goToEditCardInformation(list.get(holder.getAbsoluteAdapterPosition()).getIdCard()));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private void goToPaymentShipping(int position) {
        Parcelable wrapped = Parcels.wrap(list.get(position));
        Intent intent = new Intent(context, PaymentShippingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PaymentShippingActivity.mCard = list.get(position);
        intent.putExtra("value", wrapped);
        context.startActivity(intent);
        activity.finish();
    }

    private void goToEditCardInformation(String key) {
    Intent intent = new Intent(context, CardActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra("key", key);
    context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

    private final TextView infoCard;
    private final MaterialButton edit;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        infoCard = itemView.findViewById(R.id.tvInfoCard);
        edit = itemView.findViewById(R.id.btnEdit);
    }
}
}
