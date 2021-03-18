package com.fei.okhttpdemo.retrofit;

import com.fei.okhttpdemo.retrofit.annotation.FieldMap;
import com.fei.okhttpdemo.retrofit.annotation.GET;
import com.fei.okhttpdemo.retrofit.annotation.Query;

/**
 * @ClassName: ServiceApi
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/18 15:34
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/18 15:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ServiceApi {

    @GET("/xxx")
    Call<UserLoginResult> userLogin(@Query("user_name") String userName, @Query("pwd") String password);
}
