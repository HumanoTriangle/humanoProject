package com.triangle.com.humano.Interface;

import com.triangle.com.humano.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("moblie/login.php")
    Call<UserModel>getLogin(@Field("username") String username,@Field("password") String password);
}
