package com.fei.okhttpdemo.okhttp;

/**
 * @ClassName: OkHttpClient
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/2 15:14
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OkHttpClient {

    public Dispatcher dispatcher;

    public OkHttpClient() {
        dispatcher = new Dispatcher();
    }

    public Call newCall(Request request) {
        return new RealCall(this,request);
    }
}
