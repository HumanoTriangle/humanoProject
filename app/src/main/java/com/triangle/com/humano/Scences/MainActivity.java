package com.triangle.com.humano.Scences;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.triangle.com.humano.Helper.Helper;
import com.triangle.com.humano.Interface.APIInterface;
import com.triangle.com.humano.Model.UserModel;
import com.triangle.com.humano.Network.RetrofitInstance;
import com.triangle.com.humano.R;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private ImageView signInImageView;
    private ProgressBar progressBar;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupComponent();
    }

    //MARK: Setup

    private void setupComponent(){

        signInImageView = findViewById(R.id.signInImageView);
        helper = new Helper(getApplicationContext());
        initProgressBar();



        signInImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestCameraPermission();

            }
        });
    }

    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }

    //MARK: Action

    private void signInAction(String username, String password) {
        APIInterface apiInterface = RetrofitInstance
                .getRetrofitInstance(getApplicationContext())
                .create(APIInterface.class);

        Call<UserModel> call = apiInterface.getLogin(username,password);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, retrofit2.Response<UserModel> response) {
                progressBar.setVisibility(View.INVISIBLE);
                helper.setUserData(response.body());
                UserModel model = helper.getUserData();
                Toast.makeText(MainActivity.this, ""+model.getName(),Toast.LENGTH_SHORT).show();
                startNavigationDrawer();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this,"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            if(data != null){
                String message = data.getStringExtra("MESSAGE");
                if(message != null && message != ""){
                    String deCodeString = decodeBase64(message);
                    System.out.println("Message : "+deCodeString);
                    String[] separated = deCodeString.split("\\|");
                    Toast.makeText(MainActivity.this,separated[0],Toast.LENGTH_SHORT).show();
                    helper.setHostData(separated[0]);
                    signInAction(separated[1],separated[2]);
                }
            }else{

            }
        }
    }

    private void startNavigationDrawer() {
        Intent intent = new Intent(MainActivity.this,NavigationDrawerActivity.class);
        startActivity(intent);
    }

    private void startQRScanner() {
        Intent intent = new Intent(MainActivity.this,QRScannerActivity.class);
        startActivityForResult(intent,2);
    }

    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                        startQRScanner();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private String decodeBase64(String string) {
        byte[] data = Base64.decode(string, Base64.DEFAULT);
        String text = new String(data, StandardCharsets.UTF_8);
        return text;
    }
}
