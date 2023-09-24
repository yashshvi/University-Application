package com.example.university_application;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.os.Bundle;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    private WebView webView;

    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loading = ProgressDialog.show(this, "Loading", "Wait while loading...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);

        webView = findViewById(R.id.webViewforNewPage);

        // Retrieve the URL from the intent's extras
        String url = getIntent().getStringExtra("URL");

        try{
            // Load the URL in the WebView
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    loading.dismiss();

                }
            });
            webView.loadUrl(url);

        }catch(Exception e){
            Log.d("TAG", "onCreate: exception is "+e);

        }
    }
}
