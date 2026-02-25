package app.ejemplo.sneakercity.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import app.ejemplo.sneakercity.Helpes.EventKeyboard.EventKeyboard;
import app.ejemplo.sneakercity.Helpes.EventKeyboard.EventKeyboardInterface;
import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import app.ejemplo.sneakercity.R;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements EventKeyboardInterface {

    private FirebaseAuth mAuth;
    private TextInputEditText email, password, name, lastName, phone;
    private MaterialButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View mainView = findViewById(android.R.id.content); // O el ID de tu ConstraintLayout raíz
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        onInit();
    }

    private void onInit() {
        EventKeyboard mKeyboard = new EventKeyboard(this);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        name = findViewById(R.id.etName);
        lastName = findViewById(R.id.etLastName);
        phone = findViewById(R.id.etPhone);
        save = findViewById(R.id.btnSave);
        mAuth = FirebaseAuth.getInstance();
        phone.setOnEditorActionListener(mKeyboard);
    }

    private void registerUser(String name, String lastName, String phone) {
        UtilsHelper.getDatabase().child("user").child(UtilsHelper.getUserId()).child("name").setValue(name);
        UtilsHelper.getDatabase().child("user").child(UtilsHelper.getUserId()).child("lastName").setValue(lastName);
        UtilsHelper.getDatabase().child("user").child(UtilsHelper.getUserId()).child("phone").setValue(phone);
    }


    @Override
    protected void onStart() {
        super.onStart();
        save.setOnClickListener(view -> registrarUsuario());
    }

    private void registrarUsuario() {
        if (Objects.requireNonNull(email.getText()).toString().equals("") || Objects.requireNonNull(password.getText()).toString().equals("")) {

            Toast toast = Toast.makeText(RegisterActivity.this, R.string.username_password_message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        } else {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            registerUser(Objects.requireNonNull(name.getText()).toString(), Objects.requireNonNull(lastName.getText()).toString(), Objects.requireNonNull(phone.getText()).toString());

                            Toast toast = Toast.makeText(RegisterActivity.this, R.string.user_success_message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {

                            Toast toast = Toast.makeText(RegisterActivity.this, R.string.user_failed_message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });

        }
        InputMethodManager keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(save.getWindowToken(), 0);

    }

    @Override
    public void onButtonPressed() {
        registrarUsuario();
    }
}



