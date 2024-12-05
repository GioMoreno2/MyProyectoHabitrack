package com.gio.myhabitrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity_register extends AppCompatActivity {

    private EditText etEmail, etPassword, etUsername, etFirstName, etLastName, etState;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        initializeFirebase();
        initializeViews();
        setupRegisterButton();
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mUserDatabase = mDatabase.getReference("users");
    }

    private void initializeViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etState = findViewById(R.id.etState);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setupRegisterButton() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String state = etState.getText().toString().trim();

        if (validateInputs(email, password, username, firstName, lastName, state)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                saveUserDataToDatabase(username, firstName, lastName, email, state);
                            } else {
                                handleRegistrationError(task.getException());
                            }
                        }
                    });
        }
    }

    private boolean validateInputs(String email, String password, String username, String firstName, String lastName, String state) {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || state.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveUserDataToDatabase(String username, String firstName, String lastName, String email, String state) {
        String userId = mAuth.getCurrentUser().getUid();
        User user = new User(username, firstName, lastName, email, state);

        mUserDatabase.child(userId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(MainActivity_register.this, "Error al guardar los datos del usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleRegistrationError(Exception exception) {
        if (exception instanceof FirebaseAuthException) {
            String errorCode = ((FirebaseAuthException) exception).getErrorCode();
            switch (errorCode) {
                case "ERROR_INVALID_EMAIL":
                    Toast.makeText(this, "El correo electrónico no es válido", Toast.LENGTH_SHORT).show();
                    break;
                case "ERROR_WEAK_PASSWORD":
                    Toast.makeText(this, "La contraseña es demasiado débil", Toast.LENGTH_SHORT).show();
                    break;
                case "ERROR_EMAIL_ALREADY_IN_USE":
                    Toast.makeText(this, "El correo electrónico ya está en uso", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "Error en el registro: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Toast.makeText(this, "Error en el registro: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(MainActivity_register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static class User {
        public String username, firstName, lastName, email, state;

        public User() {
            // Constructor vacío requerido para Firebase
        }

        public User(String username, String firstName, String lastName, String email, String state) {
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.state = state;
        }
    }
}