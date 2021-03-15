package com.fei.okhttpdemo.download;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: DonwloadTask
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/15 11:26
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/15 11:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DownloadTask {


    private String url;
    private long contentLength;
    private DownloadCallBack callBack;
    ThreadPoolExecutor executorServiceOrNull;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int THREAD_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private int successCount = 0;
    private List<DownloadRunnable> mRunnables;

    ThreadPoolExecutor executorService() {
        if (executorServiceOrNull == null) {
            executorServiceOrNull = new ThreadPoolExecutor(0, THREAD_SIZE, 30, TimeUnit.SECONDS,
                    new SynchronousQueue(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            });
        }
        return executorServiceOrNull;
    }

    public DownloadTask(String url, long contentLength, DownloadCallBack callBack) {
        this.url = url;
        this.contentLength = contentLength;
        this.callBack = callBack;
        mRunnables = new ArrayList<>(THREAD_SIZE);
    }

    public void init() {

        for (int i = 0; i < THREAD_SIZE; i++) {

            long threadSize = contentLength / THREAD_SIZE;

            // 初始化的时候 这里要去读取数据库


            long start = i * threadSize;
            long end = (i + threadSize) - 1;

            if (i == THREAD_SIZE - 1) {
                end = contentLength - 1;
            }

            DownloadRunnable runnable = new DownloadRunnable(url, i, start, end, new DownloadCallBack() {
                @Override
                public void onSuccess(File file) {
                    synchronized (DownloadTask.this) {
                        successCount++;
                        if (successCount == THREAD_SIZE) {
                            callBack.onSuccess(file);
                        }
                    }
                }

                @Override
                public void onFail(Exception e) {
                    callBack.onFail(e);
                }
            });

            executorService().submit(runnable);
        }
    }

    public void stop() {
        for (DownloadRunnable runnable : mRunnables) {
            runnable.stop();
        }
    }
}
