package com.example.mywebviewapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.graphics.Bitmap;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect_link);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = (EditText) findViewById(R.id.editText);
                String link = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, Redirect_link.class);
                intent.putExtra("link", link);
                startActivity(intent);
                finish();
            }

        });
    }
}
