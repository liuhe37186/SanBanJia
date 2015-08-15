package com.txcap.sanbanjia.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.txcap.sanbanjia.R;

/**
 * information
 * Created by liuhe on 15/8/15.
 */
public class InformationDetails extends Activity {
    private int informationId;
    WebView wv_information;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        informationId = this.getIntent().getIntExtra("url",0);
        wv_information = (WebView)this.findViewById(R.id.wv_information);
        wv_information.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv_information.loadUrl("http://api.txcap.com/app/getinfocon/"+informationId);
    }
}
