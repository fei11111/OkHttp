package com.fei.okhttpdemo.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 * @ClassName: DownloadRunnable
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/15 11:27
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/15 11:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DownloadRunnable implements Runnable {
    private static final int STATUS_DOWNLOADING = 1;
    private static final int STATUS_STOP = 2;
    private String url;
    private int threadId;
    private long start;
    private long end;
    private DownloadCallBack callBack;
    private int mStatus = STATUS_DOWNLOADING;
    private long mProgress = 0;

    public DownloadRunnable(String url, int threadId, long start, long end, DownloadCallBack callBack) {

        this.url = url;
        this.threadId = threadId;
        this.start = start;
        this.end = end;
        this.callBack = callBack;
    }

    @Override
    public void run() {

        RandomAccessFile accessFile = null;
        InputStream inputStream = null;
        try {
            Response response = OkHttpManager.getInstance().execute(url, start, end);
            inputStream = response.body().byteStream();
            // 写数据
//            File file = FileManager.manager().getFile(url);
            File file = new File("xxxxx");
            accessFile = new RandomAccessFile(file, "rwd");
            // 从这里开始
            accessFile.seek(start);
            int len = 0;
            byte[] buffer = new byte[1024 * 10];

            while ((len = inputStream.read(buffer)) != -1) {
                if(mStatus == STATUS_STOP)
                    break;
                // 保存进度，做断点 , 100kb
                accessFile.write(buffer, 0, len);
            }

            callBack.onSuccess(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(inputStream);
            Utils.close(accessFile);

//            // 存到数据库，数据库怎么存？
//            mDownloadEntity.setProgress(mProgress);
//            DaoManagerHelper.getManager().addEntity(mDownloadEntity);
        }

    }

    public void stop() {
        //修改状态
        mStatus = STATUS_STOP;
    }
}
