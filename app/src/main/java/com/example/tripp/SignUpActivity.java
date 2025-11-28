package com.example.tripp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameE, passwordE, confirmPassE;
    private Button SignUpb;
    private SharedPreferences sharedPref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameE = findViewById(R.id.edit_username);
        passwordE = findViewById(R.id.edit_password);
        confirmPassE = findViewById(R.id.edit_confirm_password);
        SignUpb = findViewById(R.id.btn_sign_up);

        sharedPref = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        SignUpb.setOnClickListener(v -> {
            String username = usernameE.getText().toString().trim();
            String password = passwordE.getText().toString().trim();
            String confirmPassword = confirmPassE.getText().toString().trim();

            if (username.isEmpty()) {
                usernameE.setError("Please enter a username");
                return;
            }

            if (password.isEmpty()) {
                passwordE.setError("Please enter a password");
                return;
            }

            if (confirmPassword.isEmpty()) {
                confirmPassE.setError("Please confirm your password");
                return;
            }

            if (!password.equals(confirmPassword)) {
                confirmPassE.setError("Passwords do not match");
                return;
            }

             sharedPref.edit()
                    .putString("username", username)
                    .putString("password", password)
                    .putBoolean("remember", false)
                    .apply();

            Toast.makeText(this, "Sign Up successful! Please login.", Toast.LENGTH_SHORT).show();

             Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
