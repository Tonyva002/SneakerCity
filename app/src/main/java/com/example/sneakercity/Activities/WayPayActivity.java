package com.example.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sneakercity.Adapters.WayPayAdapter;
import com.example.sneakercity.Helpes.UtilsHelper;
import com.example.sneakercity.Models.Card;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WayPayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WayPayAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private MaterialButton wayPay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_pay);

        onInit();
        setToolbar();

        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("card")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final ArrayList<Card> cards = new ArrayList<>();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Card post = dataSnapshot.getValue(Card.class);
                            assert post != null;
                            post.setIdCard(dataSnapshot.getKey());
                            cards.add(post);
                        }
                        adapter = new WayPayAdapter(getApplicationContext(), cards, R.layout.way_pay_adapter, WayPayActivity.this);
                        linearLayoutManager = new LinearLayoutManager(WayPayActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        wayPay.setOnClickListener(view -> goToCard());
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.way_pay_message);
        setSupportActionBar(toolbar);
    }

    private void onInit() {
        recyclerView = findViewById(R.id.recyclerView);
        wayPay = findViewById(R.id.btnWayPay);
    }

    private void goToCard(){
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }
}