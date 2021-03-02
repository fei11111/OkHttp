package com.fei.okhttpdemo.okhttp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ClassName: Response
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/2 15:19
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 15:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Response {
    public Response(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String str = sb.toString();
        Log.i("TAG", "Response: " + sb.toString());
    }
}
