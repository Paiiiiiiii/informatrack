package com.example.informatrack.api;

import com.example.informatrack.common.constanta.ApiConst;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDatabase {
    private static String url = ApiConst.baseUrl;
    private static Retrofit retrofit;

    public static Retrofit MyConectionApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
        }
        return retrofit;
    }
}
