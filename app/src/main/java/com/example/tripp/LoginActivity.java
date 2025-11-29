package com.example.tripp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameE, passwordE;
    private Button Blogin;
    private CheckBox rememberMe;
    private TextView tvSignup;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameE = findViewById(R.id.edit_username);
        passwordE = findViewById(R.id.edit_password);
        Blogin = findViewById(R.id.btn_login);
        rememberMe = findViewById(R.id.checkbox_remember);
        tvSignup = findViewById(R.id.tv_signup);

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

         String savedUser = sharedPreferences.getString("username", "");
        String savedPass = sharedPreferences.getString("password", "");
        boolean savedRemember = sharedPreferences.getBoolean("remember", false);

        usernameE.setText(savedUser);
        passwordE.setText(savedPass);
        rememberMe.setChecked(savedRemember);

         Blogin.setOnClickListener(v -> {
            String username = usernameE.getText().toString().trim();
            String password = passwordE.getText().toString().trim();

            if (username.equals("leqaa") && password.equals("1111")) {

                 if (rememberMe.isChecked()) {
                    sharedPreferences.edit()
                            .putString("username", username)
                            .putString("password", password)
                            .putBoolean("remember", true)
                            .apply();
                } else {
                    sharedPreferences.edit().clear().apply();
                }

                 Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid credential", Toast.LENGTH_SHORT).show();
            }
        });

         tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}