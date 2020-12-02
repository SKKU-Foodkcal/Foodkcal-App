package com.example.foodkcal_app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TestService {
    @GET("/")
    Call<Object> getTest();

    @POST("/prediction")
    Call<Object> postImage();

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
