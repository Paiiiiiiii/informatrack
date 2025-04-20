package com.example.informatrack.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.informatrack.R;
import com.example.informatrack.viewmodel.AuthViewModel;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private AuthViewModel authViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initComponents();

        actionComponents();

    }

    private void initComponents (){
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void actionComponents (){
        tvRegister.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            String username, password;

            username = etUsername.getText().toString();
            password = etPassword.getText().toString();


            if (username.isEmpty() || password.isEmpty()){
                Log.e("Login Gagal", "aaaa ");
            } else {
                authViewModel.login(this,username,password);
            }

        });
    }
}