package com.fei.okhttpdemo.okhttp;

import java.io.IOException;

/**
 * @ClassName: Call
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/2 15:14
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface Call {

    /**
     * 异步
     */
    void enqueue(Callback callback);

    /**
     * 同步
     */
    Response execute() throws IOException;

}
