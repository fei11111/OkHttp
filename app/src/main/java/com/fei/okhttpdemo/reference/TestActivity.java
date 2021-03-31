package com.fei.okhttpdemo.reference;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.fei.okhttpdemo.R;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        for (int i = 0; i < 200; i++) {
            HttpRequest.getInstance().request("测试"+i, new HttpRequest.CallBack<String>() {
                @Override
                public void call(String s) {
                    Log.i("tag","有返回");
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("tag","TestActivity 关闭");
    }
}