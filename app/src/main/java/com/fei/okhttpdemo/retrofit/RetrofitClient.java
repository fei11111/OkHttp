package com.fei.okhttpdemo.retrofit;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @ClassName: RetrofitClient
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 15:32
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 15:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class  RetrofitClient {

    private static ServiceApi serviceApi;

    static {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NotNull String s) {
                        Log.e("tag",s);
                    }
                }))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .client(okHttpClient)
                .build();

        serviceApi = retrofit.create(ServiceApi.class);
    }

    public static ServiceApi getServiceApi() {
        return serviceApi;
    }
}
