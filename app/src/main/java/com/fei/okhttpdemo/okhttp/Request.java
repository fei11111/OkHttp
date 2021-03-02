package com.fei.okhttpdemo.okhttp;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Request
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/2 15:14
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Request {

    public String url;
    public Method method;
//    public Map<String, String> params;
    public RequestBody requestBody;

    public Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
//        this.params = builder.params;
        this.requestBody = builder.requestBody;
    }


    public static class Builder {

        public Method method;
        public String url;
//        public Map<String, String> params;
        public RequestBody requestBody;

        public Builder() {
            method = Method.GET;
//            params = new HashMap<>();
        }

        public Request build() {
            return new Request(this);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            this.method = Method.GET;
            return this;
        }

        public Builder post() {
            this.method = Method.POST;
            return this;
        }

//        public Builder addParams(String key, String value) {
//            params.put(key, value);
//            return this;
//        }

        public Builder requestBody(RequestBody body) {
            this.requestBody = body;
            return this;
        }

    }
}
