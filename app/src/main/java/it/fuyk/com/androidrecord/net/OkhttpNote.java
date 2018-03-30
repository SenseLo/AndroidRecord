package it.fuyk.com.androidrecord.net;

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * author: senseLo
 * date: 2018/3/27
 */

public class OkhttpNote {

    /*
    * 总李写代码博客： http://www.cnblogs.com/whoislcj/p/5526431.html
    * OkHttp：开源的轻量级的网络请求框架
    * android平台已经不推荐使用HttpClient了，所以android-async-http不适合android平台了；
    * 官网地址：http://square.github.io/okhttp/
    * 官方API地址：http://m.blog.csdn.net/article/details?id=50747352
    * github源码地址：https://github.com/square/okhttp
    *
    * 鸿洋大神关于OkHttp的封装（已经停止维护）
    * https://github.com/hongyangAndroid/okhttputils
    * */

    private static final String BASE_URL = "";
    private static OkhttpNote instance;
    private OkHttpClient okHttpClient;
    private Handler handler; //全局处理子线程和主线程通信
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final int TYPE_POST_FORM = 2;//post请求参数为表单

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype 这个需要和服务端保持一致

    public OkhttpNote(Context context) {
        /*
        * 初始化OkHttpClient
        * */
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        /*
        * 初始化Handler
        * */
        handler = new Handler(context.getMainLooper());
    }

    public static OkhttpNote getInstance(Context context) {
        if (instance == null) {
            synchronized (OkhttpNote.class) {
                if (instance == null) {
                    instance = new OkhttpNote(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /*
    * 实现Okhhtp同步请求入口
    * actionUrl 接口地址
    * requestType 请求类型
    * paramsMap 请求参数
    * */
    public void requestSyn(String actionUrl, int requestType, HashMap<String, String> paramsMap) {
        switch (requestType) {
            case TYPE_GET:
                break;
            case TYPE_POST_JSON:
                break;
            case TYPE_POST_FORM:
                break;
        }
    }

    /*
    * 添加请求头
    * */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("", "")
                .addHeader("", "");
        return builder;
    }

    /*
    * get同步请求
    * */
    private void requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        /*
        * 处理请求参数
        * */
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key),"utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s/%s?%s", BASE_URL, actionUrl, paramsMap.toString());
            Request request = addHeaders().url(requestUrl).build();
            final Call call = okHttpClient.newCall(request);
            final Response response = call.execute();
            if (response.isSuccessful()) {
                response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * POST同步请求
    * */
    private void requestPostBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(String.format("%s%s", BASE_URL, actionUrl));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            //生成参数
            String params = stringBuilder.toString();
            //创建一个请求实体对象
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON ,params);
            //创建一个请求
            final Request request = addHeaders().url(requestUrl).post(body).build();
            //创建Call对象
            final Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful()) {
                response.body().string();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * POST同步请求表单提交
    * */
    private void requestPostBySynWithForm(String actionUrl, HashMap<String, String> paramMap) {
        try {
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramMap.keySet()) {
                //追加表单信息
                builder.add(key, paramMap.get(key));
            }
            //生成表单实体对象
            RequestBody body = builder.build();
            //补全请求地址
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            //创建一个请求
            Request request = addHeaders().url(requestUrl).post(body).build();
            //创建Call对象
            Call call = okHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            if (response.isSuccessful()) {
                response.body().string();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    *   实现OkHttp异步请求入口
    *   actionUrl：接口地址
    *   requestType： 请求类型
    *   paramsMap：请求参数
    *   callBack：请求返回数据回调
    *   <T>： 数据泛型
    * */
    public <T> Call requestAsyn(String actionUrl, int requestType, HashMap<String, String> paramsMap, ReqCallBack<T> callBack) {
        Call call = null;
        switch (requestType) {
            case TYPE_GET:
                break;
            case TYPE_POST_JSON:
                break;
            case TYPE_POST_FORM:
                break;
        }
        return call;
    }

    /*
    * get异步请求
    * */
    private <T> Call requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            /*
            * 补全请求地址
            * */
            String requestUrl = String.format("%s/%s?%s", BASE_URL, actionUrl, stringBuilder.toString());
            Request request = addHeaders().url(requestUrl).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(e.getMessage(), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        successCallBack((T)response.body().string(), callBack);
                    }else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * post异步请求
    * */
    private <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
              if (pos > 0) {
                  stringBuilder.append("&");
              }
                stringBuilder.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = stringBuilder.toString();
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            Request request = addHeaders().url(requestUrl).post(body).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(e.getMessage(), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        successCallBack((T)response.body().string(), callBack);
                    }else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 异步请求表单提交
    * */
    private <T> Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key));
            }
            RequestBody body = builder.build();
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack(e.getMessage(), callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        successCallBack((T)response.body().string(), callBack);
                    }else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 不带参数上传文件
    * */
    private <T> void upLoadFile(String actionUrl, String filePath, final ReqCallBack<T> callBack) {
        String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
        final File file = new File(filePath);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
        final Request request = new Request.Builder().url(requestUrl).post(body).build();
        Call call = okHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack(e.getMessage(), callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    successCallBack((T)response.body().string(), callBack);
                }else {
                    failedCallBack("上传失败", callBack);
                }
            }
        });
    }

    /**
     *上传文件
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     * @param callBack 回调
     * @param <T>
     */
    public <T>void upLoadFile(String actionUrl, HashMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {
        try {
            //补全请求地址
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                }
            }
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().url(requestUrl).post(body).build();
            //单独设置参数 比如读取超时时间
            final Call call = okHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("上传失败", callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("上传失败", callBack);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 带参数带进度上传文件
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     * @param callBack 回调
     * @param <T>
     */
    public <T> void upLoadFile(String actionUrl, HashMap<String, Object> paramsMap, final ReqProgressCallBack<T> callBack) {
        try {
            //补全请求地址
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_TYPE_MARKDOWN, file, callBack));
                }
            }
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().url(requestUrl).post(body).build();
            final Call call = okHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("上传失败", callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("上传失败", callBack);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建带进度的RequestBody
     * @param contentType MediaType
     * @param file  准备上传的文件
     * @param callBack 回调
     * @param <T>
     * @return
     */
    public <T> RequestBody createProgressRequestBody(final MediaType contentType, final File file, final ReqProgressCallBack<T> callBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        progressCallBack(remaining, current, callBack);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 下载文件
     * @param fileUrl 文件url
     * @param destFileDir 存储目标目录
     */
    public <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqCallBack<T> callBack) {
//        final String fileName = MD5.encode(fileUrl);
        final File file = new File(destFileDir, fileUrl);
        if (file.exists()) {
            successCallBack((T) file, callBack);
            return;
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("下载失败", callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    successCallBack((T) file, callBack);
                } catch (IOException e) {
                    failedCallBack("下载失败", callBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 下载文件
     * @param fileUrl 文件url
     * @param destFileDir 存储目标目录
     */
    public <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqProgressCallBack<T> callBack) {
//        final String fileName = MD5.encode(fileUrl);
        final File file = new File(destFileDir, fileUrl);
        if (file.exists()) {
            successCallBack((T) file, callBack);
            return;
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("下载失败", callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        progressCallBack(total, current, callBack);
                    }
                    fos.flush();
                    successCallBack((T) file, callBack);
                } catch (IOException e) {
                    failedCallBack("下载失败", callBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public interface ReqCallBack<T> {
        /*
        * 响应成功
        * */
        void onSuccess(T result);

        /*
        * 响应失败
        * */
        void onFailure(String errorMsg);
    }

    public interface ReqProgressCallBack<T>  extends ReqCallBack<T>{
        /**
         * 响应进度更新
         */
        void onProgress(long total, long current);
    }

    /*
    * 统一处理成功信息
    * */
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onSuccess(result);
                }
            }
        });
    }

    /*
    * 统一处理失败信息
    * */
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFailure(errorMsg);
                }
            }
        });
    }

    /**
     * 统一处理进度信息
     * @param total    总计大小
     * @param current  当前进度
     * @param callBack
     * @param <T>
     */
    private <T> void progressCallBack(final long total, final long current, final ReqProgressCallBack<T> callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onProgress(total, current);
                }
            }
        });
    }

}
