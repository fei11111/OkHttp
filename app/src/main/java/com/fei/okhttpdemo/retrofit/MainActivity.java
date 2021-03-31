package com.fei.okhttpdemo.retrofit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fei.okhttpdemo.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitClient.getServiceApi().userLogin("Darren", "940223")
                .enqueue(new CallBack<UserLoginResult>() {
                    @Override
                    public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {

                    }

                    @Override
                    public void onFailure(Call<UserLoginResult> call, Throwable t) {

                    }
                });
    }
}