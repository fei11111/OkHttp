package com.fei.okhttpdemo.retrofit;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

/**
 * @ClassName: HttpCall
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 16:04
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 16:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HttpCall<T> implements Call<T> {

    private ServiceMethod serviceMethod;
    private Object[] args;

    public HttpCall(ServiceMethod serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    @Override
    public void enqueue(final CallBack<T> callBack) {
        //最后发请求来到这里
        okhttp3.Call call = serviceMethod.newCall();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                Log.i("tag","onFailure");
                if (callBack != null) {
                    callBack.onFailure(HttpCall.this, e);
                }
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
                Log.i("onResponse",response.body().string());
//                if (callBack != null) {
//                    callBack.onResponse(HttpCall.this,new Re);
//                }
            }
        });
    }
}
