package com.fei.okhttpdemo.reference;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: HttpRequest
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2021-03-31 19:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-03-31 19:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HttpRequest {

    private static volatile HttpRequest instance;
    private ExecutorService executorService;

    private HttpRequest() {
        //创建线程池
        executorService = Executors.newFixedThreadPool(5);
    }

    public static HttpRequest getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new HttpRequest();
                }
            }
        }
        return instance;
    }

    public synchronized <T> void request(T param, CallBack<T> callBack) {
        //封装成runnable
        RequestRunnable<T> tRequestRunnable = RequestRunnable.obtain(param, callBack);
        executorService.execute(tRequestRunnable);
    }

    private static class RequestRunnable<T> implements Runnable {

        private T param;
        private WeakReference<CallBack> callBack;

        public static final Object sPoolSync = new Object();
        private static RequestRunnable sPool;
        private static int sPoolSize = 0;
        /*package*/ RequestRunnable next;

        private static final int MAX_POOL_SIZE = 50;

        private RequestRunnable(T param, CallBack<T> callBack) {
            this.param = param;
            this.callBack = new WeakReference<CallBack>(callBack);
        }

        @Override
        public void run() {
            //假装网络请求
            try {
                Thread.sleep(5000);
                if (callBack != null && callBack.get() != null) {
                    Log.i("tag", param.toString() + " " + Thread.currentThread().getName() + " " + this.toString());
                    callBack.get().call(param + "dada");
                }else {
                    Log.i("tag","callback被回收了");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                recycleUnchecked();
            }
        }

        public static <T> RequestRunnable obtain(T param, CallBack<T> callBack) {
            synchronized (sPoolSync) {
                if (sPool != null) {
                    RequestRunnable m = sPool;
                    sPool = m.next;
                    m.next = null;
                    m.param = param;
                    m.callBack = new WeakReference(callBack);
                    sPoolSize--;
                    return m;
                }
            }
            return new RequestRunnable(param, callBack);
        }

        void recycleUnchecked() {
            // Mark the message as in use while it remains in the recycled object pool.
            // Clear out all other details.
            Log.i("tag","回收成功:"+ this.toString());
            callBack.clear();
            callBack = null;
            param = null;
            synchronized (sPoolSync) {
                if (sPoolSize < MAX_POOL_SIZE) {
                    next = sPool;
                    sPool = this;
                    sPoolSize++;
                }
            }
        }

        @Override
        public String toString() {
            return "RequestRunnable{" +
                    "param=" + param +
                    ", callBack=" + callBack +
                    ", next=" + next +
                    '}';
        }
    }

    public interface CallBack<T> {
        void call(T t);
    }

}
