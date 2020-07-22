package moka.net.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String value = getIntent().getStringExtra("URLkey");

        WebView webViewOne = findViewById(R.id.webviewId);

        webViewOne.getSettings().setJavaScriptEnabled(true);

        webViewOne.loadUrl(value);
    }
}
