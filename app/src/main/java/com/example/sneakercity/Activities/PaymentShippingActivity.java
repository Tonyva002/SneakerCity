package com.example.sneakercity.Activities;

import static com.example.sneakercity.Activities.CartActivity.shopping;
import static com.example.sneakercity.Activities.CartActivity.suma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.sneakercity.Adapters.CartAdapter;
import com.example.sneakercity.Helpes.EventKeyboard.EventKeyboardInterface;
import com.example.sneakercity.Helpes.UtilsHelper;
import com.example.sneakercity.Models.Card;
import com.example.sneakercity.Models.Cart;
import com.example.sneakercity.Models.Invoice;
import com.example.sneakercity.Models.User;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class PaymentShippingActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Toolbar toolbar;
    private CartAdapter adapter;
    private TextView goToAddress, optionPayment, article, subtotalPrice,shoppingPrice, totalPrice;
    private MaterialButton makePayment;
    public static User mUser = null;
    public static Card mCard = null;
    public static Invoice mInvoice = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_shipping);

        onInit();
        setToolbar();
        setAlertDialog();
        onValidateCart();


        goToAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToUserAddress();

            }
        });

        optionPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToOptionPayment();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        setPurchaseInformation();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.payment_shipping_title);
        setSupportActionBar(toolbar);
    }

    private void onInit() {
        goToAddress = findViewById(R.id.tvGoToAddress);
        optionPayment = findViewById(R.id.tvOptionPayment);
        article = findViewById(R.id.tvArticle);
        subtotalPrice = findViewById(R.id.tvSubtotalPrice);
        shoppingPrice = findViewById(R.id.tvShoppingPrice);
        totalPrice = findViewById(R.id.tvTotalPrice);
        makePayment = findViewById(R.id.btnMakePayment);
        recyclerView = findViewById(R.id.recyclerView);



    }

    private void setAlertDialog() {
        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentShippingActivity.this);
                LayoutInflater inflater = PaymentShippingActivity.this.getLayoutInflater();
                builder.setTitle(R.string.billing_address_message)
                        .setMessage(R.string.click_add_invoice_address_message)
                        .setIcon(R.drawable.outline_shopping_cart_black_24)
                        .setPositiveButton(R.string.yes_message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(PaymentShippingActivity.this, InvoiceAddressActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no_message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void onValidateCart() {
        Cart cart = Parcels.unwrap(getIntent().getParcelableExtra(DetailActivity.ONE_PRODUCT_KEY));
        if (cart == null){
            onConnectToFirebaseCart(); // Llamada a metodo onConnectToFirebaseCart
        }else{
            ArrayList<Cart> carts = new ArrayList<>();
            carts.add(cart);
            onCreateAdapter(carts); // Llamada a metodo onCreateAdapter
        }
        
    }
    // Metodo para conectar con firebase
    private void onConnectToFirebaseCart() {
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Cart> carts = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Cart post = dataSnapshot.getValue(Cart.class);
                    carts.add(post);
                }
                onCreateAdapter(carts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    // Metodo para pasarle la informacion a CartAdpter y rellenar las vistas
    private void onCreateAdapter(ArrayList<Cart> carts) {
        adapter = new CartAdapter(PaymentShippingActivity.this,carts, R.layout.cart_adapter);
        mLinearLayoutManager = new LinearLayoutManager(PaymentShippingActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        article.setText(String.format("%s (%s)", getResources().getString(R.string.article_message), String.valueOf(carts.size())));
        subtotalPrice.setText(String.format("RD$ %s", suma));
        shoppingPrice.setText(String.format("RD$ %d", shopping));
        totalPrice.setText(String.valueOf(suma + shopping));

    }

    private void setPurchaseInformation(){
        if (mUser != null){
            goToAddress.setText(new StringBuffer()
                    .append(mUser.getName()).append(" ")
                    .append(mUser.getLastName()).append("\n")
                    .append(mUser.getCountry()).append("\n")
                    .append(mUser.getCity()).append("\n")
                    .append(mUser.getCommunity()) .append("\n")
                    .append(mUser.getPostalCode()).append("\n")
                    .append(mUser.getPhone()).append("\n")
                    .append(mUser.getAddress()).append("\n")
                    .append(mUser.getAddress2()));

        }
        if (mCard != null){
            optionPayment.setText(new StringBuffer()
                    .append(mCard.getCardNumber()).append("\n")
                    .append(mCard.getExpirationDate()).append("\n")
                    .append(mCard.getNamePrintCard()));
        }
    }

    // Metodo para navegar a direecion de envio
    private void goToUserAddress(){
        onIntent(new Intent(this, UserAddressActivity.class));
    }
    // Metodo para navegar a metodo de pago
    private void goToOptionPayment(){
        onIntent(new Intent(this, WayPayActivity.class));
    }


    private void onIntent(Intent intent){
        startActivity(intent);
    }



}