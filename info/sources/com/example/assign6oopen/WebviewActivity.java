package com.example.assign6oopen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebviewActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_webview);
        String value = getIntent().getStringExtra("URLkey");
        WebView webViewOne = (WebView) findViewById(R.id.webview1);
        webViewOne.getSettings().setJavaScriptEnabled(true);
        webViewOne.loadUrl(value);
    }
}
