package com.rowsen.jdcoupon;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    CookieManager manager;
    public String cookie;
    public String sid;
    Handler handler;

    MyWebViewClient() {
        manager = CookieManager.getInstance();
    }

    MyWebViewClient(Handler handler) {
        this();
        this.handler = handler;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        //Log.e("should_url", url);
        if (url.startsWith("http"))
            return false;
        else
            return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        //Log.e("start_url", url);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        //Log.e("finish_url", url);
        if (url.equals("https://m.jd.com/")) {
            cookie = manager.getCookie(url);
            //Log.e("Cookie", cookie);
            view.loadUrl("https://home.m.jd.com/myJd/newhome.action?");
            Message msg = Message.obtain();
            if (cookie.contains("Rowsen-cool"))
                msg.what = 0;
            else if (cookie.contains("4974148341"))
                msg.what = 1;
            else if (cookie.contains("jd_ZlOwGxDdzHIR"))
                msg.what = 2;
            else
                msg.what = 3;//不属于任何用户的cookie情况
            msg.obj = cookie;
            handler.sendMessage(msg);
        }
    }
}
