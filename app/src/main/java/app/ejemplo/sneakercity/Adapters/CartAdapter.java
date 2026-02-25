package app.ejemplo.sneakercity.Adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import app.ejemplo.sneakercity.Models.Cart;
import app.ejemplo.sneakercity.Models.Product;
import app.ejemplo.sneakercity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

   private final ArrayList<Cart> carts;
   private final int layout;
   private final Context context;
   private final String TAG = "Consulta";

   public CartAdapter(Context context, ArrayList<Cart> carts, int layout){
       this.context = context;
       this.carts = carts;
       this.layout = layout;

   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        UtilsHelper.getDatabase().child("products").child(carts.get(holder.getAdapterPosition()).getId_product())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Product post = snapshot.getValue(Product.class);
                        assert post != null;
                        holder.model.setText(post.getModel());
                        Glide.with(context)
                                .load(post.getImage())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(holder.image);
                        Log.e(TAG, String.valueOf(post.getImage()));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.remove.setOnClickListener(view -> onDeleteFromCart(holder.getAdapterPosition()));

        holder.price.setText(String.format("US$ %s", carts.get(position).getTotalPrice()));
        holder.quantity.setText(MessageFormat.format("{0} ({1})", context.getResources().getString(R.string.quantity_message), carts.get(position).getQuantity()));



    }

    private void onDeleteFromCart(int position) {
      final  String mIdProduct = carts.get(position).getId_product();
       int quantity = carts.get(position).getQuantity();
       if (quantity == 1){
           final Query delete = UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                   .orderByChild("id_product").equalTo(mIdProduct);
           delete.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for (DataSnapshot deleteSnapshot: snapshot.getChildren()){
                       Cart post = deleteSnapshot.getValue(Cart.class);
                       assert post != null;
                       if (Objects.equals(post.getId_product(), mIdProduct)){
                           deleteSnapshot.getRef().removeValue();
                           break;
                       }
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

                   Log.e(TAG, "onCancelled", error.toException());

               }
           });
       }else {
           Cart mCart = carts.get(position);
           mCart.setQuantity(quantity - 1);
           mCart.setTotalPrice(mCart.getQuantity()*mCart.getPrice());
           mCart.decrementToCart();

       }
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {

       public ImageView image;
       public TextView model, price, quantity, remove, favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imgCart);
            model = itemView.findViewById(R.id.tvModel);
            price = itemView.findViewById(R.id.tvPrice);
            quantity = itemView.findViewById(R.id.tvQuantity);
            remove = itemView.findViewById(R.id.tvRemove);
            favorite = itemView.findViewById(R.id.tvFavorite);
        }


    }
}
