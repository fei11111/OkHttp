package com.fei.okhttpdemo.okhttp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: Dispatcher
 * @Description: 线程池
 * @Author: Fei
 * @CreateDate: 2021/3/2 15:16
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 15:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Dispatcher {

    ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
            new SynchronousQueue(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(false);
            return thread;
        }
    });

    public void enqueue(RealCall.AsyncCall call) {
        call.executeOn(executorService);
    }
}
