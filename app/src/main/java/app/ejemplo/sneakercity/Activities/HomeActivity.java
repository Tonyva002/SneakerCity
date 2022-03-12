package app.ejemplo.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import app.ejemplo.sneakercity.Adapters.ProductAdapter;
import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import app.ejemplo.sneakercity.Models.Product;


import app.ejemplo.sneakercity.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity {

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
                        assert post != null;
                        post.setIdProduct(dataSnapshot.getKey());
                        products.add(post);

                    }

                mAdapter = new ProductAdapter(HomeActivity.this, products, R.layout.product_adapter, (product, position) -> goToDetail(products.get(position).getIdProduct()));

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
        Toolbar toolbar = findViewById(R.id.toolbar);
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
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.optionShoppingCart:
                goToCart();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }


    // Metodo para configurar las opciones del navigation drawer
    private void setupNavView(){
        mNavigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
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

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.optionHome:
                goToHome();
                break;

            case R.id.optionJournal:

            case R.id.optionJordan:

            case R.id.optionNike:

            case R.id.optionAdidas:

            case R.id.optionYeezy:

            case R.id.optionWomen:

            case R.id.optionKids:

            case R.id.optionApparel:

            case R.id.optionStadium:

            case R.id.optionAllBrands:

            case R.id.optionShopBy:

                break;


            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return false;
    }
}