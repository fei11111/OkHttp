package com.fei.okhttpdemo.download;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName: OkHttpManager
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/15 11:23
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/15 11:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OkHttpManager {

    private static volatile OkHttpManager instance;
    private OkHttpClient okHttpClient;

    private OkHttpManager() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                if (instance == null) {
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    public void asyncCall(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public Response execute(String url, long start, long end) throws IOException {
        Request request = new Request.Builder().url(url).addHeader("Range", "bytes=" + start + "-" + end).build();
        Call call = okHttpClient.newCall(request);
        return call.execute();
    }
}
