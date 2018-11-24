package com.triangle.com.humano.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triangle.com.humano.Model.UserModel;
import com.triangle.com.humano.Scences.MainActivity;

public class Helper {

    private static Context prefContext = null;
    public static final String STORAGE_NAME = "ApplicationPrefs";

    public void init(Context context)
    {
        prefContext = context;
    }

    public void setUserData(UserModel model) {

        SharedPreferences mPrefs = prefContext.getSharedPreferences(STORAGE_NAME,prefContext.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        prefsEditor.putString("userPref", json);
        prefsEditor.commit();

    }

    public UserModel getUserData() {

        SharedPreferences mPrefs = prefContext.getSharedPreferences(STORAGE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userPref", "");
        UserModel userModel = gson.fromJson(json, UserModel.class);

        return userModel;
    }
}
