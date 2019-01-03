package com.example.lamine.bluetooth_20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class BaseActivity extends AppCompatActivity {
WebView webView;
String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        webView=(WebView)findViewById(R.id.web);
        Intent intent=getIntent();
        Bundle extra=intent.getExtras();
        if(extra!=null){
            str=extra.getString("sanae");
        }

        String url="http://172.20.10.6/itm/bluetooth.php?Identifiant="+str;
        webView.loadUrl(url);
    }
}
