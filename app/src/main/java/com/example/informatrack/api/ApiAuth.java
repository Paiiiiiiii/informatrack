package com.example.informatrack.api;

import com.example.informatrack.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiAuth {
    @POST("registrasi.php")
    @FormUrlEncoded
    Call<ApiResponse> register(
            @Field("username") String username,
            @Field("nim") String nim,
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("login.php")
    @FormUrlEncoded
    Call<ApiResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
