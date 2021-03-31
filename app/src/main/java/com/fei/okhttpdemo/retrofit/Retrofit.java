package com.fei.okhttpdemo.retrofit;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: Retrofit
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 15:37
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 15:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Retrofit {

    final String baseUrl;
    final okhttp3.Call.Factory callFactory;
    private Map<Method, ServiceMethod> serviceMethodMapCache = new ConcurrentHashMap<>();


    public Retrofit(Builder builder) {
        this.baseUrl = builder.baseUrl;
        callFactory = builder.callFactory;
    }

    /**
     * 动态代理
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }

                //封装方法参数
                ServiceMethod serviceMethod = loadServiceMethod(method,args);

                HttpCall httpCall = new HttpCall(serviceMethod);
                return httpCall;
            }
        });
    }

    private ServiceMethod loadServiceMethod(Method method,Object[] args) {
        // 享元设计模式
        ServiceMethod serviceMethod = serviceMethodMapCache.get(method);
        if(serviceMethod == null){
            serviceMethod = new ServiceMethod.Builder(this,method,args).build();
            serviceMethodMapCache.put(method,serviceMethod);
        }
        return serviceMethod;
    }

    public static class Builder {

        String baseUrl;
        okhttp3.Call.Factory callFactory;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder client(okhttp3.Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        public Retrofit build() {
            return new Retrofit(this);
        }
    }
}
