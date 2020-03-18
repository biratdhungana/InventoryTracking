package com.example.birat.c2e3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DoorActivity extends AppCompatActivity {

    private WebView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the title bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_door);

        mView = (WebView)findViewById(R.id.activity_web);

        //Enable Javascript
        WebSettings webSettings = mView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mView.loadUrl("http://192.168.1.101:8080");

        //Force links and redirects to open in WebView
        mView.setWebViewClient(new WebViewClient());

    }
}
