package com.triangle.com.humano.Scences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

import com.triangle.com.humano.Interface.APIInterface;
import com.triangle.com.humano.Model.UserModel;
import com.triangle.com.humano.Network.RetrofitInstance;
import com.triangle.com.humano.R;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button signInButton;
    private ProgressBar progressBar;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupComponent();
    }

    //MARK: Setup

    private void setupComponent(){

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        apiInterface = RetrofitInstance.getRetrofitInstance().create(APIInterface.class);
        initProgressBar();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                signInAction(username, password);
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
        Call<UserModel> call = apiInterface.getLogin(username,password);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, retrofit2.Response<UserModel> response) {
                progressBar.setVisibility(View.INVISIBLE);
                String id = response.body().getId();
                Toast.makeText(MainActivity.this,id,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this,"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
