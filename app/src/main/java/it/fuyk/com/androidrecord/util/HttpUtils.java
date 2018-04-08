package it.fuyk.com.androidrecord.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author: senseLo
 * date: 2018/4/8
 */

public class HttpUtils {
    private static final int TIMEOUT_MILLIONS = 5000;
    public interface CallBack {
        void onRequestComplete(String result);
    }

    /*
    * 异步的GET请求
    * */
    public static void doGetAsyn(final String url, final CallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String result = doGet(url);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            }else {
                throw new RuntimeException(conn.getResponseCode() + "");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            }catch (IOException e) {

            }
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return null;
    }

    public static void doPostAsyn(final String url, final String params, final CallBack callback) throws Exception {
        new Thread() {
            @Override
            public void run() {
                try {
                    String result = doPost(url, params);
                    if (callback != null) {
                        callback.onRequestComplete(result);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static String doPost(String urlStr, String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setReadTimeout(TIMEOUT_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_MILLIONS);

            if (params != null && !params.trim().equals("")) {
                out = new PrintWriter(conn.getOutputStream());
                out.print(params);
                out.flush();
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
