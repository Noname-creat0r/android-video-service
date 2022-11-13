package com.example.videoservice.auth.interfaces;

import com.example.videoservice.auth.models.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAuthInterface {

    @POST("login/")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("signup/")
    Call<Void> executeSignup(@Body HashMap<String, String> map);
}
