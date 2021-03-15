package com.fei.okhttpdemo.download;

import java.io.File;

/**
 * @ClassName: CallBack
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/15 11:26
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/15 11:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface DownloadCallBack {

    public void onSuccess(File file);

    public void onFail(Exception e);

}
