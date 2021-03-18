package com.fei.okhttpdemo.retrofit;

import android.util.Log;

import com.fei.okhttpdemo.retrofit.annotation.FieldMap;
import com.fei.okhttpdemo.retrofit.annotation.GET;
import com.fei.okhttpdemo.retrofit.annotation.POST;
import com.fei.okhttpdemo.retrofit.annotation.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.Request;

/**
 * @ClassName: ServiceMethod
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 15:40
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 15:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ServiceMethod {

    private Retrofit retrofit;
    private Method method;
    private String httpMethod;
    private String relativeUrl;
    private ParameterHandler[] parameterHandlers;
    private Object[] args;

    public ServiceMethod(Builder builder) {
        this.retrofit = builder.retrofit;
        this.method = builder.method;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.parameterHandlers = builder.parameterHandlers;
        this.args = builder.args;
    }

    public Call newCall() {
        RequestBuilder requestBuilder = new RequestBuilder(retrofit.baseUrl,relativeUrl, httpMethod,parameterHandlers, args);
        Request request = requestBuilder.build();
        Log.e("tag",request.toString());
        return retrofit.callFactory.newCall(request);
    }

    public static class Builder {
        Retrofit retrofit;
        Method method;
        Object[] args;
        String httpMethod;
        ParameterHandler[] parameterHandlers;
        String relativeUrl;

        public Builder(Retrofit retrofit, Method method, Object[] args) {
            this.retrofit = retrofit;
            this.method = method;
            this.args = args;
        }

        public ServiceMethod build() {

            Annotation[] annotations = method.getAnnotations();//判断是get请求还是post请求等等

            parseMethod(annotations);

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();//获取参数key
            parameterHandlers = new ParameterHandler[parameterAnnotations.length];
            for (int i = 0; i < parameterAnnotations.length; i++) {
                //拿到参数key
                Annotation annotation = parameterAnnotations[i][0];
                parseParameter(i, annotation);
            }
            return new ServiceMethod(this);
        }

        private void parseParameter(int i, Annotation annotation) {
            if (annotation instanceof FieldMap) {
                parameterHandlers[i] = new ParameterHandler.FieldMap(((FieldMap) annotation).value());
            } else if (annotation instanceof Query) {
                parameterHandlers[i] = new ParameterHandler.Query<String>(((Query) annotation).value());
            }
        }

        private void parseMethod(Annotation[] annotations) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof GET) {
                    paraseMethodAndPath("GET", ((GET) annotation).value());
                } else if (annotation instanceof POST) {
                    paraseMethodAndPath("POST", ((POST) annotation).value());
                }
            }
        }

        private void paraseMethodAndPath(String httpMethod, String url) {
            this.httpMethod = httpMethod;
            this.relativeUrl = url;
        }
    }
}
