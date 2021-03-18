package com.fei.okhttpdemo.retrofit;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @ClassName: RequestBuilder
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 16:21
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 16:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RequestBuilder {

    private String baseUrl;
    private String relativeUrl;
    private String httpMethod;
    private ParameterHandler[] parameterHandlers;
    private Object[] args;
    private HttpUrl.Builder httpUrl;

    public RequestBuilder(String baseUrl, String relativeUrl,String httpMethod,  ParameterHandler[] parameterHandlers, Object[] args) {
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;
        this.httpMethod = httpMethod;
        this.parameterHandlers = parameterHandlers;
        this.args = args;
        httpUrl = HttpUrl.parse(baseUrl + relativeUrl).newBuilder();
    }


    public Request build() {
        for (int i = 0; i < args.length; i++) {
            parameterHandlers[i].apply(this, args[i]);
        }
        //需要判断请求方式
//        if(httpMethod) {
//
//        }
        Request request = new Request.Builder().url(httpUrl.build()).build();
        return request;
    }

    public void addQueryName(String key, String value) {
        httpUrl.addQueryParameter(key, value);
    }
}
