package com.fei.okhttpdemo.okhttp;

import java.io.OutputStream;

/**
 * @ClassName: Bindry
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/2 16:46
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 16:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface Bindry {

    public long fileLength();

    public String minType();

    public String fileName();

    public void onWrite(OutputStream outputStream);
}
