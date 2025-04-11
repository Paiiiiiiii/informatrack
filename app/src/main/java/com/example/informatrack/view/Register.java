package com.example.informatrack.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.informatrack.R;
import com.example.informatrack.viewmodel.AuthViewModel;

public class Register extends AppCompatActivity {
    private EditText etUsername, etNim, etEmail, etPassword;
    private Button btnRegister;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();

        actionComponents ();
    }

    private void initComponents (){
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        etUsername = findViewById(R.id.etUsername);
        etNim = findViewById(R.id.etNim);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void actionComponents (){
        btnRegister.setOnClickListener(view -> {
            String username, nim, email, password;

            username = etUsername.getText().toString();
            nim = etNim.getText().toString();
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty() || nim.isEmpty() || email.isEmpty()){
                Log.e("Pastikan Semua Data Terisi", "aaaa ");
            } else {
                authViewModel.register(this,username,nim,email,password);
            }
        });
    }
}