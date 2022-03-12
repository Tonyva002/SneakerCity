package app.ejemplo.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import app.ejemplo.sneakercity.Models.Card;
import app.ejemplo.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class CardActivity extends AppCompatActivity {

    private Card mCard;
    private EditText cardNumber, expirationDate, securityCode, namePrintCard;
    private MaterialButton save;
    private boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        
        onInit();
        setToolbar();

        final String idCard = getIntent().getStringExtra("key");
        if(idCard != null){
            isNew = false;
            UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("card").child(idCard).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Card post = snapshot.getValue(Card.class);
                    assert post != null;
                    cardNumber.setText(post.getCardNumber());
                    expirationDate.setText(post.getExpirationDate());
                    securityCode.setText(post.getSecurityCode());
                    namePrintCard.setText(post.getNamePrintCard());
                    mCard.setIdCard(snapshot.getKey());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        save.setOnClickListener(view -> {
            cardNumber.setError(null);
            expirationDate.setError(null);
            securityCode.setError(null);
            namePrintCard.setError(null);

            if (cardNumber.getText().toString().equals("")){
                cardNumber.setError(getString(R.string.enter_card_number_message));

            }else if (expirationDate.getText().toString().equals("")){
                expirationDate.setError(getString(R.string.enter_expiration_date_message));

            }else if (securityCode.getText().toString().equals("")){
                securityCode.setError(getString(R.string.enter_security_code_message));

            }else if (namePrintCard.getText().toString().equals("")){
                namePrintCard.setError(getString(R.string.enter_name_printed_card_message));

            }else {
                mCard.setCardNumber(cardNumber.getText().toString());
                mCard.setExpirationDate(expirationDate.getText().toString());
                mCard.setSecurityCode(securityCode.getText().toString());
                mCard.setNamePrintCard(namePrintCard.getText().toString());
                mCard.saveCardInformation(isNew);

                Toast toast = Toast.makeText(CardActivity.this, R.string.information_saved_correctly_message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.card_message);
        setSupportActionBar(toolbar);
    }

    private void onInit() {
        cardNumber = findViewById(R.id.etCardNumber);
        expirationDate = findViewById(R.id.etExpirationDate);
        securityCode = findViewById(R.id.etSecurityCode);
        namePrintCard = findViewById(R.id.etNamePrintCard);
        save = findViewById(R.id.btnSave);
        mCard = new Card();

    }


}