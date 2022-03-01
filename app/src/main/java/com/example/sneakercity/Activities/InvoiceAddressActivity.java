package com.example.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sneakercity.Adapters.InvoiceAddressAdapter;
import com.example.sneakercity.Helpes.UtilsHelper;
import com.example.sneakercity.Models.Invoice;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InvoiceAddressActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private InvoiceAddressAdapter adapter;
    private MaterialButton addInvoiceAdrress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_address);

        setToolbar();
        onInit();

        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<Invoice> invoices = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Invoice post = dataSnapshot.getValue(Invoice.class);
                    post.setIdInvoice(dataSnapshot.getKey());
                    invoices.add(post);
                }
                adapter = new InvoiceAddressAdapter(getApplicationContext(),invoices, R.layout.invoice_address_adapter, InvoiceAddressActivity.this );
                linearLayoutManager = new LinearLayoutManager(InvoiceAddressActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addInvoiceAdrress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInvoice();
            }
        });
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.shipping_invoice_address_message));
        setSupportActionBar(toolbar);
    }

    private void onInit() {
        recyclerView = findViewById(R.id.recyclerView);
        addInvoiceAdrress = findViewById(R.id.btnAddInvoiceAddress);

    }

    public void goToInvoice(){
        Intent intent = new Intent(this, InvoiceActivity.class);
        startActivity(intent);
    }

}