package com.fei.okhttpdemo.okhttp;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;

/**
 * @ClassName: RealCall
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/2 15:15
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 15:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RealCall implements Call {

    private static final String TAG = "RealCall";

    private OkHttpClient client;
    private Request request;

    public RealCall(OkHttpClient client, Request request) {
        this.client = client;
        this.request = request;
    }

    /**
     * 异步
     *
     * @param callback
     */
    @Override
    public void enqueue(Callback callback) {
        client.dispatcher.enqueue(new AsyncCall(callback));
    }

    /**
     * 同步
     *
     * @return
     * @throws IOException
     */
    @Override
    public Response execute() throws IOException {
        return null;
    }

    final class AsyncCall implements Runnable {
        private final Callback callback;

        public AsyncCall(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            Log.i(TAG, "run: ");

            //网络请求
            try {
                URL url = new URL(request.url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // https 的一些操作
                // httpsURLConnection.setHostnameVerifier();
                // urlConnection.setReadTimeout();
//                urlConnection.setConnectTimeout();
                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.setRequestProperty("Charset", "UTF-8");
                urlConnection.setRequestMethod(request.method.name());
                urlConnection.setDoOutput(request.method.doOutPut());
                urlConnection.setDoInput(true);
                RequestBody requestBody = request.requestBody;
                if (requestBody != null) {
                    urlConnection.addRequestProperty("Content-Type", requestBody.getContentType());
                    urlConnection.addRequestProperty("Content-Length", Long.toString(requestBody.getContentLength()));
                    OutputStream outputStream = urlConnection.getOutputStream();
                    requestBody.onWrite(outputStream);
                    outputStream.flush();
                    outputStream.close();
                }else {
//                    urlConnection.addRequestProperty("Content-Type", request.getContentType());
//                    urlConnection.addRequestProperty("Content-Length", Long.toString(requestBody.getContentLength()));
                }

                //连接
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    Response response = new Response(inputStream);
                    callback.onResponse(RealCall.this, response);
                }
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(RealCall.this, e);
            }
        }

        public void executeOn(ExecutorService executorService) {
            //dispatcher回调这里
            executorService.execute(this);
        }
    }
}
