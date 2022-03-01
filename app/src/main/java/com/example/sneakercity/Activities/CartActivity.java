package com.example.sneakercity.Activities;

import static java.lang.String.format;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sneakercity.Adapters.CartAdapter;
import com.example.sneakercity.Helpes.UtilsHelper;
import com.example.sneakercity.Models.Cart;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CartAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    public static double suma = 0;
    public static final int shopping = 200;
    private TextView article, subtotalPrice, shoppingPrice, totalPrice;
    private MaterialButton CompleteTransation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setToolbar();
        onInit();



        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        suma = 0;
                        ArrayList<Cart> carts = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Cart post = dataSnapshot.getValue(Cart.class);
                            suma += post.getTotalPrice();
                            carts.add(post);
                        }
                        mAdapter = new CartAdapter(CartActivity.this, carts, R.layout.cart_adapter);

                        mLinearLayoutManager = new LinearLayoutManager(CartActivity.this);
                        mRecyclerView.setLayoutManager(mLinearLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setHasFixedSize(true);
                        article.setText(getResources().getString(R.string.article_message) +" (" + String.valueOf(carts.size()) + ")");
                        subtotalPrice.setText(format("RD$ %s", suma));
                        shoppingPrice.setText(format("RD$ %d", shopping));
                        totalPrice.setText(String.format("RD$ %s", String.valueOf(suma + shopping)));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        CompleteTransation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToPaymentShipping();

            }
        });
    }

    // Metodo para configurar la toolbar
    public void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.shopping_cart_message));
        setSupportActionBar(toolbar);

    }

    // Metodo para referenciar las vistas del layout
    private void onInit(){
        mRecyclerView = findViewById(R.id.recyclerView);
        article = findViewById(R.id.tvArticle);
        subtotalPrice = findViewById(R.id.tvSubtotalPrice);
        shoppingPrice = findViewById(R.id.tvShoppingPrice);
        totalPrice = findViewById(R.id.tvTotalPrice);
        CompleteTransation = findViewById(R.id.btnCompleteTransation);

    }

    // Metodo para inflar el menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Metodo para configurar las opciones del menu de opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.optionShoppingCart:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Metodo para navegar a payment shipping
    private void goToPaymentShipping(){
        onIntent(new Intent(this, PaymentShippingActivity.class));
    }


    private void onIntent(Intent intent){
        startActivity(intent);
    }
}

