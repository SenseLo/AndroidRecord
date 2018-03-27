package it.fuyk.com.androidrecord.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Set;

import it.fuyk.com.androidrecord.R;
import it.fuyk.com.androidrecord.util.Utils;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * author: senseLo
 * date: 2018/3/26
 */

public class WebViewNote extends AppCompatActivity {

    /*
    * WebView：基于webkit引擎、展现web页面的控件。
    *
    * */

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        initView();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webview);

        /*
        * 加载网页/加载apk包中的额html页面/加载手机本地的html页面
        * */
        webView.loadUrl("url/file:///android_asset/test.html/content://com.android.htmlfileprovider/sdcard/test.html");

        /*
        * 前进后退网页
        * */
        webView.canGoBack(); //是否可以后退
        webView.canGoForward();//是否可以前进
        webView.goBack();//后退
        webView.goForward();//前进
    }

    public void setting() {
        /*
        * WebSettings：对webview进行配置和管理
        *   1：添加网络访问权限;
        *   2：生成一个webview组件
        *       a:WebView webview = new WebView(this);
        *       b:WebView webview = (WebView) findViewById(R.id.webview);
        *   3: 利用WebSettings子类进行配置
        * */

        WebSettings settings = webView.getSettings();

        /*
        * 加载的html里有js在执行动画等操作，会造成资源浪费，在onStop， onResume里把
        * setJavaScriptEnable设置成false和true；
        * */
        settings.setJavaScriptEnabled(true); //访问的页面中要与JS交互

        /*
        * 设置自适应屏幕，两者结合使用
        * */
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); //缩放至屏幕的大小

        /*
        * 缩放操作
        * */
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        /*
        * 其他
        * */
//        settings.setCacheMode(settings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        if (Utils.isNetworkConnected(this)) {
            settings.setCacheMode(settings.LOAD_DEFAULT);
        }else {
            settings.setCacheMode(settings.LOAD_CACHE_ELSE_NETWORK);
        }
        settings.setAllowFileAccess(true); // 设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过js打开新窗口
        settings.setLoadsImagesAutomatically(true); //设置支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8"); //设置编码格式

        /*
        * WebViewClient：处理各种通知请求事件
        * */
        webView.setWebViewClient(new WebViewClient(){

            /*
            * 打开网页时不调用系统浏览器，而在本webview中显示
            * */
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl("url");
//                return true;

                /*
                * 根据协议的参数，判断是否是需要的url
                * 一般根据scheme(协议格式) & authority(协议名)判断
                *
                * JS获取Android方法的返回值复杂，但是不存方法一的漏洞
                *
                * 假定传进来的url="js://webview?arg1=111&arg2=222"
                * */
                Uri uri = request.getUrl();
                if(uri.getScheme().equals("js")) {
                    if (uri.getAuthority().equals("webview")) {

                        /*
                        * 执行JS所需要调用的逻辑
                        * */

                        /*
                        * 可以在协议上带有参数并且传递到Android上
                        * */
                        HashMap<String, String> params = new HashMap<String, String>();
                        Set<String> collection = uri.getQueryParameterNames();
                    }
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //设动加载资源的操作
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                switch (error.getErrorCode()) {
                    case 0:
                        view.loadUrl("file:///android_assets/error_handle.html");
                        break;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                /*
                * 处理https请求
                * 5.1以上默认禁止了https和http混用，以下是打开方式
                * */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
            }
        });

        /*
        * WebChromeClient：辅助WebView处理JS的对话框，网站图标，网站标题等等
        * */
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                /*
                * 获得网页的加载进度并且显示
                * */
                if (newProgress < 100) {
                    //显示进度
                }else {

                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                /*
                * 获取Web页面中的标题
                * */
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                /*
                * 支持js的警告框
                * */
                new AlertDialog.Builder(WebViewNote.this)
                        .setTitle("JSAlert")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                /*
                * 支持js的确认框
                * */
                new AlertDialog.Builder(WebViewNote.this)
                        .setTitle("JSConfirm")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    /*
    * Android调用JS代码
    * */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void AndroidUseJS() {
        /*
        * 第一种方式：调用的js方法名要对应上，调用JS中的callJS()方法
        * 注意：JS代码调用一定要在onPageFinished()回调之后才能调用，否则不会调用
        * 方便简洁，效率低，获取返回值麻烦，不需要获取返回值并且对性能要求较低
        * */
        webView.loadUrl("javascript:callJS()");
        /*
        * 第二种方式：Android 4.4后才可以使用
        * 相比较与第一种方法更高效更简洁，但是向下兼容性差，
        * */
        webView.evaluateJavascript("javascript：callJS()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                /*
                * 此处为js返回的结果
                * */
            }
        });
    }

    /*
    * JS调用Android
    * */
    public void JSUseAndroid() {
        /*
        * 第一种方式：通过webview的addJavascriptInterface()进行对象映射
        *   1：定义一个与JS对象映射关系的Android类
        *
        *   优缺点：使用简单，但是在Android4.2以下存在严重的漏洞问题
        * */
        /*
        * 参数一：Javascript对象名
        * 参数二：Java对象名
        * */
        webView.addJavascriptInterface(new AndroidJs(), "test");

        /*
        * 第二种方式：通过webviewClient的方法shouldOverrideUrlLoading()回调拦截url
        * */
    }

    public class AndroidJs extends Object {
        @JavascriptInterface
        public void hello(String msg) {
            Log.d("senselo", msg);
        }
    }

    /*
    * Back键控制网页后退操作
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*
        * 清除缓存数据
        * */
        webView.clearCache(true); //清除网页访问留下的缓存
        webView.clearHistory(); //清除当前webview访问的历史记录
    }
}
