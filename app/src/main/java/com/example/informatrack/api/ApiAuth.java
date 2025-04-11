package com.example.informatrack.api;

import com.example.informatrack.model.ApiResponse;
import com.example.informatrack.model.User;
import com.example.informatrack.model.UserList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiAuth {
    @POST("register.php")
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

    @GET("get_unapproved_users.php")
    Call<UserList> getUnapprovedUsers();

    @FormUrlEncoded
    @POST("approve_user.php")
    Call<ApiResponse> approveUser(@Field("id_user") String id_user);


}
