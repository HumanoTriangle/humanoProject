package com.triangle.com.humano.Network;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.triangle.com.humano.Helper.Helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    /**
     * Create an instance of Retrofit object
     * */
    public static Retrofit getRetrofitInstance(Context context) {
        Helper helper = new Helper(context);
        String baseURL = helper.getHostData();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("http://"+baseURL+"/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
