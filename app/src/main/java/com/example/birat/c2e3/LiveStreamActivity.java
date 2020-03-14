package com.example.birat.c2e3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LiveStreamActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the title bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_live_stream);

        mWebView = (WebView)findViewById(R.id.activity_webview);

        //Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("http://192.168.1.104/html/");

        //Force links and redirects to open in WebView
        mWebView.setWebViewClient(new WebViewClient());

    }
}
