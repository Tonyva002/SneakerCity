package com.example.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sneakercity.Helpes.UtilsHelper;
import com.example.sneakercity.Models.Cart;
import com.example.sneakercity.Models.Product;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageView imgPhoto;
    private TextView description, name, price, quantity;
    private MaterialButton addToCart, goToPay, decrease, increment;
    private int counter = 1;
    private double priceToPay;
    Cart mCart;
    private Product product;
    public static final String ONE_PRODUCT_KEY = "ONE_PRODUCT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setToolbar();
        onInit();
        setupNavView();


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCart.saveToCart();
                Toast toast = Toast.makeText(DetailActivity.this, R.string.add_cart_message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0,0);
                toast.show();
            }
        });

        goToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goPaymentShipping();

            }
        });

        final String mIdProduct = getIntent().getStringExtra("value");
        UtilsHelper.getDatabase().child("product").child(mIdProduct).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onSetProduct(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Muestra el producto con la cantidad ya existente en el carrito de compras
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Cart post = dataSnapshot.getValue(Cart.class);
                            if (post.getId_product() != null){
                                if (post.getId_product().equals(mIdProduct)){
                                    counter = post.getQuantity();
                                    showResult();
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementCounter();
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseCounter();
            }
        });
    }

    // Metodo para configurar la toolbar
    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.detail_title_message);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    // Metodo para referenciar las vistas del layout
    private void onInit(){
        imgPhoto = findViewById(R.id.imgPhoto);
        description = findViewById(R.id.tvDescription);
        name = findViewById(R.id.tvName);
        price = findViewById(R.id.tvPrice);
        quantity = findViewById(R.id.tvQuantity);
        addToCart = findViewById(R.id.btnCart);
        goToPay = findViewById(R.id.btnGoToPay);
        decrease = findViewById(R.id.btnDecrease);
        increment = findViewById(R.id.btnIncrement);
        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mCart = new Cart();
    }


    // Metodo para navegar a pago y envio
    private void goPaymentShipping() {
        Intent intent = new Intent(this, PaymentShippingActivity.class);
        Parcelable cart = Parcels.wrap(onCreateCartForProduct());
        intent.putExtra(ONE_PRODUCT_KEY, cart);
        onIntent(intent);
    }



    private Cart onCreateCartForProduct() {
        Cart cart = new Cart();
        cart.setId_product(product.getIdProduct());
        cart.setId_user(FirebaseAuth.getInstance().getUid());
        cart.setTotalPrice(priceToPay*counter);
        cart.setQuantity(counter);

        return cart;
    }


    private void onSetProduct(DataSnapshot snapshot) {
        product = snapshot.getValue(Product.class);
        Glide.with(getApplicationContext())
                .load(product.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imgPhoto);
        description.setText(product.getDescription());
        name.setText(product.getName());
        price.setText(String.valueOf("RD$ " + product.getPrice()));
        product.setIdProduct(snapshot.getKey());
        mCart.setId_product(snapshot.getKey());
        priceToPay = product.getPrice();
        showResult();


    }



   // Metodo para incrementar la cantidad de articulo
    public void incrementCounter(){

        counter++;
        showResult();
    }

    // Metodo para descrementar la cantidad de articulo
    public void decreaseCounter(){
        if (counter > 1) {
            counter--;
        }
        showResult();
    }

    private void showResult() {
        quantity.setText(String.valueOf(counter));
        mCart.setTotalPrice(priceToPay*counter);
        mCart.setQuantity(counter);
    }



    // Metodo para inflar el menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Metodo para configurar las opciones del menu de opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.optionShoppingCart:
                  goToCart();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // Metodo para configurar las opciones del navigation drawer
    private void setupNavView(){
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.optionHome:
                        goToHome();
                        break;

                    case R.id.optionJournal:

                        break;

                    case R.id.optionJordan:

                        break;

                    case R.id.optionNike:

                        break;

                    case R.id.optionYeezy:

                        break;

                    case R.id.optionAdidas:

                        break;

                    case R.id.optionWomen:

                        break;

                    case R.id.optionKids:

                        break;

                    case R.id.optionApparel:

                        break;

                    case R.id.optionStadium:

                        break;

                    case R.id.optionAllBrands:

                        break;

                    case R.id.optionShopBy:

                        break;


                }
                return false;
            }
        });
    }


    // Metodo para navegar a Cart Activity
    public void goToCart(){
        onIntent(new Intent(this, CartActivity.class));
    }

    // Metodo para navegar a Home Activity
    private void goToHome() {
        onIntent(new Intent(this, HomeActivity.class));
    }

    private void onIntent(Intent intent){
        startActivity(intent);
    }

}