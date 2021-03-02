package com.fei.okhttpdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fei.okhttpdemo.okhttp.Call;
import com.fei.okhttpdemo.okhttp.Callback;
import com.fei.okhttpdemo.okhttp.OkHttpClient;
import com.fei.okhttpdemo.okhttp.Request;
import com.fei.okhttpdemo.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * {"info":{"token":"EUKlBg4FPKNRJV4iq4W0+or20KY6e0ijeXVcI3V4pFTgICQkFbtmTvjvMae\/TuO3sYTk9iC3JDgO9IRGW22IiBqFFkjVTOwZYkP\/inSKAvUvcClX\/MMopa9cTLVru+CQAqrg9Ns2NYt3Mv03qLSP3g==",
         * "sysVersion":"SM-G955F@@Android7.1.2","appVersion":"竖屏3.5.1","imei":"866174301656605"},
         * "postData":{"loginName":"18617146263","system":"1"}}
         */

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://www.baidu.com/")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}