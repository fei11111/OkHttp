package com.fei.okhttpdemo.retrofit;


/**
 * @ClassName: CallBack
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 15:45
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 15:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface CallBack<T> {

    public void onResponse(Call<T> call, Response<T> response);

    public void onFailure(Call<T> call, Throwable t) ;

}
