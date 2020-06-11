package com.example.mywebviewapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.os.Build;
import java.util.List;
import java.util.ArrayList;
import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import org.xml.sax.ErrorHandler;


public class Redirect_link extends AppCompatActivity {

        private ProgressBar chromeProgressBar;
        private WebView webView;
        static final int REQUEST_CODE = 0;
        String URL;
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
            Bundle bundle = getIntent().getExtras();
            URL = bundle.getString("link");

            webSettings.setDomStorageEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                WebView.setWebContentsDebuggingEnabled(true);
                int hasCameraPermission = checkSelfPermission(android.Manifest.permission.CAMERA);

                List<String> permissions = new ArrayList<String>();

                if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(android.Manifest.permission.CAMERA);
                    permissions.add(Manifest.permission.RECORD_AUDIO);
                    permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                    permissions.add(Manifest.permission.LOCATION_HARDWARE);
                }
                if (!permissions.isEmpty()) {
                    requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
                }
            }
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setAllowFileAccess(true);
            webView.loadUrl(URL);


            webView.setWebChromeClient(new WebChromeClient() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                // Grant permissions for cam
                @Override
                public void onPermissionRequest(final PermissionRequest request) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request.grant(request.getResources());
                    }
                }
                @Override
                public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                    if (ContextCompat.checkSelfPermission(Redirect_link.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Redirect_link.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                            new AlertDialog.Builder(Redirect_link.this)
                                    .setMessage("Allow Location Access?")
                                    .setNeutralButton(R.string.alert_dialog_button, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ActivityCompat.requestPermissions(Redirect_link.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
                                        }
                                    }).show();
                        } else {
                            ActivityCompat.requestPermissions(Redirect_link.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
                        }
                    } else {
                        callback.invoke(origin, true, true);
                    }
                }
            });

//            webView.setWebViewClient(new WebViewClient() {
////                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//                // Grant permissions for cam
////                @Override
////                public void onPermissionRequest(final PermissionRequest request) {
////
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                        request.grant(request.getResources());
////                    }
////                }
//            });
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
