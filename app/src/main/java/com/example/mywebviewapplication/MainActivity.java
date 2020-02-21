package com.example.mywebviewapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.os.Build;
import java.util.List;
import java.util.ArrayList;
import android.content.pm.PackageManager;
import android.widget.ProgressBar;
import android.graphics.Bitmap;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ProgressBar chromeProgressBar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);

        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            WebView.setWebContentsDebuggingEnabled(true);
            int hasCameraPermission = checkSelfPermission(android.Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.CAMERA);
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
            }
        }
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);


        webView.loadUrl("<<insert_capture_link_here>>");


        webView.setWebChromeClient(new WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            // Grant permissions for cam
            @Override
            public void onPermissionRequest(final PermissionRequest request) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }
        });
//
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onPermissionRequest(final PermissionRequest request) {
//
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @TargetApi(Build.VERSION_CODES.M)
//                    @Override
//                    public void run() {
//                        if(request.getOrigin().toString().equals("file:///")) {
//                            request.grant(request.getResources());
//                        } else {
//                            request.deny();
//                        }
//                    }
//                });
//            }
//        });


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

    }
    public void onBackPressed(){
        if (webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
