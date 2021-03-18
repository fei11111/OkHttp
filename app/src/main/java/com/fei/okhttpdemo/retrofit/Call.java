package com.fei.okhttpdemo.retrofit;

/**
 * @ClassName: Call
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 15:44
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 15:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface Call<T> {

    void enqueue(CallBack<T> callBack);

}
