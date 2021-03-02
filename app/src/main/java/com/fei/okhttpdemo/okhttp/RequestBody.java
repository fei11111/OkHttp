package com.fei.okhttpdemo.okhttp;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: RequestBody
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2021/3/2 16:03
 * @UpdateUser: Fei
 * @UpdateDate: 2021/3/2 16:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RequestBody {

    public static final String FORM = "multipart/form-data";
    private String type;
    private String boundary = createBoundary();
    private String startBoundary = "--" + boundary;
    private String endBoundary = startBoundary + "--";
    private Map<String, Object> map = null;

    public RequestBody() {
        map = new HashMap<>();
    }

    private String createBoundary() {
        return "OkHttp" + UUID.randomUUID().toString();
    }

    public RequestBody type(String type) {
        this.type = type;
        return this;
    }

    public RequestBody addParam(String key, String value) {
        map.put(key, value);
        return this;
    }

    public RequestBody addParam(String key, Bindry value) {
        map.put(key, value);
        return this;
    }

    public String getContentType() {
        return type;
    }

    public long getContentLength() {
        long length = 0;
        for (String key : map.keySet()) {
            Object o = map.get(key);
            if (o instanceof String) {
                String text = getText(key, (String) o);
                length += text.getBytes().length;
            } else if (o instanceof Bindry) {
                Bindry bindry = (Bindry) o;
                String text = getText(key, bindry);
                length += text.getBytes().length;
                length += bindry.fileLength() + "\r\n".getBytes().length;
            }

        }
        if (map.size() > 0) {
            length += endBoundary.getBytes().length;
        }
        return length;
    }

    private String getText(String key, Bindry bindry) {
        String str = startBoundary + "\r\n" + "Content-Disposition: form-data; " + "name = " + "\"" + key + "\" "
                + "filename = \"" + bindry.fileName() + "\""
                + "Content-Type: " + bindry.minType() + "\r\n\r\n" + "\r\n";
        Log.i("tag", str);
        return str;
    }

    private String getText(String key, String value) {
        String str = startBoundary + "\r\n" + "Content-Disposition: form-data; " + "name = " + "\"" + key + "\" \r\n"
                + "Content-Type: text/plain" + "\r\n\r\n" + value + "\r\n";
        Log.i("tag", str);
        return str;
    }

    public void onWrite(OutputStream outputStream) throws IOException {
        for (String key : map.keySet()) {
            Object o = map.get(key);
            if (o instanceof String) {
                String text = getText(key, (String) o);
                outputStream.write(text.getBytes());
            } else if (o instanceof Bindry) {
                Bindry bindry = (Bindry) o;
                String text = getText(key, bindry);
                outputStream.write(text.getBytes());
                bindry.onWrite(outputStream);
                outputStream.write("\r\n".getBytes());
            }

        }
    }

    public static Bindry createBindry(final File file) {
        return new Bindry() {
            @Override
            public long fileLength() {
                return file.length();
            }

            @Override
            public String minType() {
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                String contentType = fileNameMap.getContentTypeFor(file.getAbsolutePath());
                return contentType;
            }

            @Override
            public String fileName() {
                return file.getName();
            }

            @Override
            public void onWrite(OutputStream outputStream) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }


}
