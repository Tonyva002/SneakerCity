package app.ejemplo.sneakercity.Activities;

import static app.ejemplo.sneakercity.Activities.CartActivity.shopping;
import static app.ejemplo.sneakercity.Activities.CartActivity.suma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import app.ejemplo.sneakercity.Adapters.CartAdapter;
import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import app.ejemplo.sneakercity.Models.Card;
import app.ejemplo.sneakercity.Models.Cart;
import app.ejemplo.sneakercity.Models.Invoice;
import app.ejemplo.sneakercity.Models.User;
import app.ejemplo.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import android.view.View;

import org.parceler.Parcels;

import java.util.ArrayList;

public class PaymentShippingActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private TextView goToAddress, optionPayment, article, subtotalPrice,shoppingPrice, totalPrice;
    private MaterialButton makePayment;
    public static User mUser = null;
    public static Card mCard = null;
    public static Invoice mInvoice = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_shipping);

        View mainView = findViewById(android.R.id.content); // O el ID de tu ConstraintLayout raíz
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        onInit();
        setToolbar();
        setAlertDialog();
        onValidateCart();


        goToAddress.setOnClickListener(view -> goToUserAddress());

        optionPayment.setOnClickListener(view -> goToOptionPayment());
    }


    @Override
    protected void onStart() {
        super.onStart();
        setPurchaseInformation();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        makePayment.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentShippingActivity.this);
            builder.setTitle(R.string.billing_address_message)
                    .setMessage(R.string.click_add_invoice_address_message)
                    .setIcon(R.drawable.outline_shopping_cart_black_24)
                    .setPositiveButton(R.string.yes_message, (dialogInterface, i) -> {

                        Intent intent = new Intent(PaymentShippingActivity.this, InvoiceAddressActivity.class);
                        startActivity(intent);
                    })
                    .setNegativeButton(R.string.no_message, (dialogInterface, i) -> {

                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    private void onValidateCart() {
        Cart cart = Parcels.unwrap(getIntent().getParcelableExtra(ProductDetailsActivity.ONE_PRODUCT_KEY));
        if (cart == null){
            onConnectToFirebaseCart(); // Llamada al metodo onConnectToFirebaseCart
        }else{
            ArrayList<Cart> carts = new ArrayList<>();
            carts.add(cart);
            onCreateAdapter(carts); // Llamada al metodo onCreateAdapter
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
    @SuppressLint("DefaultLocale")
    private void onCreateAdapter(ArrayList<Cart> carts) {
        CartAdapter adapter = new CartAdapter(PaymentShippingActivity.this, carts, R.layout.cart_adapter);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(PaymentShippingActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        article.setText(String.format("%s (%s)", getResources().getString(R.string.article_message), carts.size()));

        if (!carts.isEmpty()) {

            String currency = carts.get(0).getCurrency();

            subtotalPrice.setText(String.format("%s %s", currency, suma));
            shoppingPrice.setText(String.format("%s %d", currency, shopping));
            totalPrice.setText(String.format("%s %s", currency, suma + shopping));

        } else {

            subtotalPrice.setText("0");
            shoppingPrice.setText("0");
            totalPrice.setText("0");

        }

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