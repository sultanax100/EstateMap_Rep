package com.example.estatemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private Button btnSignIn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Bind UI elements
        userEmail = findViewById(R.id.sign_in_email);
        userPassword = findViewById(R.id.sign_in_password);
        btnSignIn = findViewById(R.id.sign_in_btn);

        btnSignIn.setOnClickListener(this::signInUser); // Set the click listener
    }

    private void signInUser(View view) {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        // Check for empty fields
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(SignIn.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sign in the user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(SignIn.this, "Sign-in successful: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                         Intent intent = new Intent(this, HomePage.class);
                         startActivity(intent);
                         finish();
                    } else {
                        // Sign-in failed
                        Toast.makeText(SignIn.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}