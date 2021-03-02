package com.fei.okhttpdemo.okhttp;

/**
 * @ClassName: Method
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/2 15:52
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 15:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public enum Method {

    POST("POST"), GET("GET"), HEAD("HEAD"), DELETE("DELETE"), PUT("PUT"), PATCH("PATCH");

    private String name;

    Method(String name) {
        this.name = name;
    }

    public boolean doOutPut() {
        switch (this) {
            case POST:
            case PUT:
                return true;
        }
        return false;
    }
}
