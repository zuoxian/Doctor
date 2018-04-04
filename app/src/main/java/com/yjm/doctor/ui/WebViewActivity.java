package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yjm.doctor.R;
import com.yjm.doctor.ui.base.BaseActivity;

/**
 * Created by zx on 2018/2/2.
 */

public class WebViewActivity extends BaseActivity {
    @Override
    public int initView() {
        return R.layout.activity_webview;
    }

    @Override
    public void finishButton() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = (String)getIntent().getSerializableExtra("object");
        WebView web=(WebView) findViewById(R.id.webkit);
        web.loadUrl(path);

        web.getSettings().setJavaScriptEnabled(true);  //加上这一行网页为响应式的
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;   //返回true， 立即跳转，返回false,打开网页有延时
            }
        });
    }
}
