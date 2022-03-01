package com.example.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sneakercity.Adapters.ProductAdapter;
import com.example.sneakercity.Helpes.UtilsHelper;
import com.example.sneakercity.Interface.OnItemClickListener;
import com.example.sneakercity.Models.Cart;
import com.example.sneakercity.Models.Product;
import com.example.sneakercity.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private ProductAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setToolbar();
        onInit();
        setupNavView();



        UtilsHelper.getDatabase().child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<Product> products = new ArrayList<>();

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        Product post = dataSnapshot.getValue(Product.class);
                        post.setIdProduct(dataSnapshot.getKey());
                        products.add(post);

                    }

                mAdapter = new ProductAdapter(HomeActivity.this, products, R.layout.product_adapter, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Product product, int position) {

                        goToDetail(products.get(position).getIdProduct());

                    }

                });

                mGridLayoutManager = new GridLayoutManager(HomeActivity.this, 2);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(mGridLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    // Metodo para configurar la toolbar
    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Metodo para referenciar las vistas del layout
    private void onInit(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mRecyclerView = findViewById(R.id.recyclerView);
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


    // Metodo para navegar a detail activity
    private void goToDetail(String key) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("value", key);
        onIntent(intent);
    }


    // Metodo para navegar a Cart Activity
    private void goToCart() {
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