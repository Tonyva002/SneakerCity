package app.ejemplo.sneakercity.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import app.ejemplo.sneakercity.Helpes.EventKeyboard.EventKeyboard;
import app.ejemplo.sneakercity.Helpes.EventKeyboard.EventKeyboardInterface;
import app.ejemplo.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements EventKeyboardInterface {

    private MaterialButton createAccount, signIn;
    private TextInputEditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onInit();
        setupCreateAccount();
    }



    // Metodo para configurar las vistas del layout
    private void onInit(){
        EventKeyboard mKeyboard = new EventKeyboard(this);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        createAccount = findViewById(R.id.btnCreateAccount);
        signIn = findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();
        password.setOnEditorActionListener(mKeyboard);

    }


    // Metodo para navegar a la pantalla de registro
    private void setupCreateAccount(){

        createAccount.setOnClickListener(view -> goToRegister());
    }

    // Metodo para navegar a home activity
    @Override
    protected void onStart() {
        super.onStart();
        signIn.setOnClickListener(view -> autenticar());
    }


   // Metodo para autenticar con firebase
    private void autenticar() {

        if (Objects.requireNonNull(email.getText()).toString().equals("") || Objects.requireNonNull(password.getText()).toString().equals("")){
            Toast toast = Toast.makeText(this, R.string.username_password_message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }else{
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()){
                            onIntent(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                    });



        }

        InputMethodManager keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(signIn.getWindowToken(), 0);
    }



    // Metodo para navegar a la pantalla de registro
    private void goToRegister(){
        onIntent(new Intent(this, RegisterActivity.class));
    }


    private void onIntent(Intent intent){
        startActivity(intent);

    }

    @Override
    public void onButtonPressed() {
        autenticar();
    }

}