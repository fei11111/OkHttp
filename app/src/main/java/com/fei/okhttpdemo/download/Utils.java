package com.fei.okhttpdemo.download;

import java.io.Closeable;
import java.io.IOException;

/**
 * @ClassName: Utils
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/15 11:52
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/15 11:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Utils {
    public static void close(Closeable closeable) {

        if(closeable!=null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }

    }
}
