package com.example.informatrack.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.informatrack.api.ApiAuth;
import com.example.informatrack.api.GetDatabase;
import com.example.informatrack.model.ApiResponse;
import com.example.informatrack.view.Dashboard;

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
                if (response.isSuccessful()){
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")){
                        Intent move = new Intent(activity, Dashboard.class);
                        move.putExtra("login", "sucess");
                        activity.startActivity(move);
                        activity.finish();
                    } else {
                        Log.e("Username atau Password Salah", "onFailure: " + response.message());
                    }
                } else {
                    Log.e("Login Gagal", "onFailure: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Login Gagal", "onFailure: ", t);
                t.printStackTrace();
            }
        });
    }
}
