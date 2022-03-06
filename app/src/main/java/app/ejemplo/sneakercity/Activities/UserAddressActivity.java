package app.ejemplo.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import app.ejemplo.sneakercity.Adapters.UserAddressAdapter;
import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import app.ejemplo.sneakercity.Models.User;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAddressActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private UserAddressAdapter adapter;
    private MaterialButton addUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        setToolbar();
        onInit();

        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User post = dataSnapshot.getValue(User.class);
                    assert post != null;
                    post.setIdAddress(dataSnapshot.getKey());
                    users.add(post);
                }
                adapter = new UserAddressAdapter(getApplicationContext(), users, R.layout.user_address_adapter, UserAddressActivity.this );
                linearLayoutManager = new LinearLayoutManager(UserAddressActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addUser.setOnClickListener(view -> goToUser());
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.shipping_user_address_message));
        setSupportActionBar(toolbar);
    }

    private void onInit() {
        recyclerView = findViewById(R.id.recyclerView);
        addUser = findViewById(R.id.btnAddUser);

    }

    public void goToUser(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }


}