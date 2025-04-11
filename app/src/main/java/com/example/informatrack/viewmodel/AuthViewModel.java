package com.example.informatrack.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.informatrack.api.ApiAuth;
import com.example.informatrack.api.GetDatabase;
import com.example.informatrack.model.ApiResponse;
import com.example.informatrack.model.User;
import com.example.informatrack.view.DashboardAdmin;
import com.example.informatrack.view.Dashboard;
import com.example.informatrack.view.DashboardDosen;
import com.example.informatrack.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    public MutableLiveData<ApiResponse> userLiveData = new MutableLiveData<>();

    public void login(Activity activity, String username, String password){
        ApiAuth apiAuth = GetDatabase.MyConectionApi().create(ApiAuth.class);
        Call<ApiResponse> call = apiAuth.login(username, password);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    ApiResponse apiResponse = response.body();

                    if (apiResponse.getStatus().equals("success")) {
                        User user = apiResponse.getUser();
                        String role = user.getRole(); // Ambil role dari user

                        Intent intent;

                        switch (role.toLowerCase()) {
                            case "admin":
                                intent = new Intent(activity, DashboardAdmin.class);
                                break;
                            case "dosen":
                                intent = new Intent(activity, DashboardDosen.class);
                                break;
                            case "mahasiswa":
                            default:
                                intent = new Intent(activity, Dashboard.class);
                                break;
                        }

                        intent.putExtra("login", "success");
                        intent.putExtra("username", user.getUsername());
                        intent.putExtra("role", role);
                        activity.startActivity(intent);
                        activity.finish();

                    } else {
                        Toast.makeText(activity, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Login Gagal", "Status error: " + apiResponse.getMessage());
                    }
                } else {
                    Toast.makeText(activity, "Login gagal: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("Login Gagal", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(activity, "Koneksi gagal", Toast.LENGTH_SHORT).show();
                Log.e("Login Gagal", "onFailure: ", t);
                t.printStackTrace();
            }
        });
    }

    public void register(Activity activity, String username, String nim, String email, String password){
        ApiAuth apiAuth = GetDatabase.MyConectionApi().create(ApiAuth.class);
        Call<ApiResponse> call = apiAuth.register(username, nim, email, password);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Registrasi berhasil, tunggu persetujuan admin.", Toast.LENGTH_LONG).show();
                    Intent move = new Intent(activity, MainActivity.class);
                    move.putExtra("register", "success");
                    activity.startActivity(move);
                    activity.finish();
                } else {
                    Toast.makeText(activity, "Register gagal: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("Register Gagal", "onFailure: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(activity, "Koneksi gagal", Toast.LENGTH_SHORT).show();
                Log.e("Register Gagal", "onFailure: ", t);
                t.printStackTrace();
            }
        });
    }
}