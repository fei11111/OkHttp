package com.fei.okhttpdemo.download;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @ClassName: DownloadDispatcher
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/15 11:32
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/15 11:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DownloadDispatcher {

    private static DownloadDispatcher downloadDispatcher = new DownloadDispatcher();

    /**
     * Ready async calls in the order they'll be run.
     */
    private final Deque<DownloadTask> readyTasks = new ArrayDeque<>();

    /**
     * Running asynchronous calls. Includes canceled calls that haven't finished yet.
     */
    private final Deque<DownloadTask> runningTasks = new ArrayDeque<>();

    /**
     * Running synchronous calls. Includes canceled calls that haven't finished yet.
     */
    private final Deque<DownloadTask> stopTasks = new ArrayDeque<>();

    private DownloadDispatcher() {

    }

    public static DownloadDispatcher getDispatcher() {
        return downloadDispatcher;
    }

    public void startDownload(final String url, final DownloadCallBack callBack) {
        OkHttpManager.getInstance().asyncCall(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onFail(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                long contentLength = response.body().contentLength();
                if (contentLength <= -1) {
                    return;
                }
                DownloadTask task = new DownloadTask(url,contentLength,callBack);
                task.init();
                runningTasks.add(task);
            }
        });
    }

    public void recyclerTask(DownloadTask downloadTask) {
        runningTasks.remove(downloadTask);
        // 参考 OkHttp 的 Dispatcher 的源码,如果还有需要下载的开始下一个的下载
    }

    public void stopDownload(String url){
        // 这个停止的是不是正在下载的
    }

    // 开个单独的线程去执行 所有下载的回调
}
