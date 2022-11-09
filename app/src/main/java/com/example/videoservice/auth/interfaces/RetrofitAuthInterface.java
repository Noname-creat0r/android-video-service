package com.example.videoservice.auth.interfaces;

import com.example.videoservice.auth.models.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAuthInterface {

    @POST("http://10.0.2.2:3000/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("http://10.0.2.2:3000/signup")
    Call<Void> executeSignup(@Body HashMap<String, String> map);
}
