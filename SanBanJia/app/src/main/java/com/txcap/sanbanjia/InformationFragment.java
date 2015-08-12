package com.txcap.sanbanjia;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by liuhe on 15/8/12.
 */
public class InformationFragment extends Fragment {
//    @InjectView(R.id.wv_information)
//    WebView wv_informatin;
WebView wv_information;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ButterKnife.inject(this.getActivity());
        return inflater.inflate(R.layout.fragment_information,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wv_information = (WebView)this.getView().findViewById(R.id.wv_information);
        wv_information.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        wv_information.loadUrl("http://api.txcap.com/app/getinformation/%d");
        wv_information.loadUrl("http://api.txcap.com//app/getinfocon/12");

    }
}
