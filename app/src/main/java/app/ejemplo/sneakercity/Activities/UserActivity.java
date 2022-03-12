package app.ejemplo.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import app.ejemplo.sneakercity.Models.User;
import app.ejemplo.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    
    private User mUser;
    private EditText name, lastName, country, community, city, postalCode, phone, address, address2;
    private MaterialButton save;
    private boolean isNew = true;
            

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        
        setToolbar();
        onInit();

        final String idAddress = getIntent().getStringExtra("key");
        if (idAddress != null){
            isNew = false;
            UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(idAddress).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User post = snapshot.getValue(User.class);
                    assert post != null;
                    name.setText(post.getName());
                    lastName.setText(post.getLastName());
                    country.setText(post.getCountry());
                    community.setText(post.getCommunity());
                    city.setText(post.getCity());
                    postalCode.setText(post.getPostalCode());
                    phone.setText(post.getPhone());
                    address.setText(post.getAddress());
                    address2.setText(post.getAddress2());
                    mUser.setIdAddress(snapshot.getKey());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        save.setOnClickListener(view -> saveInformation());
    }

    private void saveInformation(){
        name.setError((null));
        lastName.setError(null);
        country.setError(null);
        community.setError(null);
        city.setError(null);
        postalCode.setError(null);
        phone.setError(null);
        address.setError(null);
        address2.setError(null);

        if (name.getText().toString().equals("")){
            name.setError(getString(R.string.enter_name_message));

        }else if (lastName.getText().toString().equals("")){
            lastName.setError(getString(R.string.enter_last_name_message));

        }else if (country.getText().toString().equals("")){
            country.setError(getString(R.string.enter_country_message));

        }else if (community.getText().toString().equals("")){
            community.setError(getString(R.string.enter_community_message));

        }else if (city.getText().toString().equals("")){
            city.setError(getString(R.string.enter_city_message));

        }else if (postalCode.getText().toString().equals("")){
            postalCode.setError(getString(R.string.enter_zip_code_message));

        }else if (phone.getText().toString().equals("")){
            phone.setError(getString(R.string.enter_phone_message));

        }else if (address.getText().toString().equals("")){
            address.setError(getString(R.string.enter_address_message));

        }else {
            mUser.setName(name.getText().toString());
            mUser.setLastName(lastName.getText().toString());
            mUser.setCountry(country.getText().toString());
            mUser.setCommunity(community.getText().toString());
            mUser.setCity(city.getText().toString());
            mUser.setPostalCode(postalCode.getText().toString());
            mUser.setPhone(phone.getText().toString());
            mUser.setAddress(address.getText().toString());
            mUser.setAddress2(address2.getText().toString());
            //Guardar informacion
            mUser.saveUserInformation(isNew);
            Toast toast = Toast.makeText(UserActivity.this, getResources().getString(R.string.information_saved_correctly_message), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();

        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.user_address_message);
        setSupportActionBar(toolbar);
    }

    private void onInit() {
        name = findViewById(R.id.etName);
        lastName = findViewById(R.id.etLastName);
        country = findViewById(R.id.etCountry);
        community = findViewById(R.id.etCommunity);
        city = findViewById(R.id.etCity);
        postalCode = findViewById(R.id.etPostalCode);
        phone = findViewById(R.id.etPhone);
        address = findViewById(R.id.etAddress);
        address2 = findViewById(R.id.etAddress2);
        save = findViewById(R.id.btnSave);
        mUser = new User();

    }


}