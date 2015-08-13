package com.txcap.sanbanjia;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;
import com.txcap.sanbanjia.bean.InformationResultBean;
import com.txcap.sanbanjia.bean.InformationTitleBean;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liuhe on 15/8/12.
 */
public class InformationFragment extends Fragment {
//    @InjectView(R.id.wv_information)
//    WebView wv_informatin;

WebView wv_information;


    /**
     * 给ListView添加下拉刷新
     */
   RefreshLayout myRefreshListView;

    /**
     * ListView
     */
    private ListView listView;

    /**
     * ListView适配器
     */
    private ListViewAdapter adapter;

    private List<ItemInfo> infoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ButterKnife.inject(this.getActivity());
        return inflater.inflate(R.layout.fragment_information_title,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 获取RefreshLayout实例
//        final RefreshLayout myRefreshListView = (RefreshLayout)this.getActivity().findViewById(R.id.swipe_refresh);

        myRefreshListView = (RefreshLayout) this.getActivity().findViewById(R.id.swipe_refresh);
// 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {


                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        /*datas.add(new Date().toGMTString());
                        adapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        myRefreshListView.setRefreshing(false);*/
                        myRefreshListView.setRefreshing(false);
                        ItemInfo info = new ItemInfo();
                        info.setName("coin-refresh");
                        infoList.add(info);
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });


        // 加载监听器
        myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {


                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        ItemInfo info = new ItemInfo();
                        info.setName("coin-add");
                        infoList.add(info);
                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        myRefreshListView.setLoading(false);
                    }
                }, 1500);

            }
        });
//        // 顶部刷新的样式
//        myRefreshListView.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,
//                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        infoList = new ArrayList<ItemInfo>();
        ItemInfo info = new ItemInfo();
        info.setName("coin");
        infoList.add(info);
        listView = (ListView) this.getActivity().findViewById(R.id.listview);
        adapter = new ListViewAdapter(this.getActivity(), infoList);
        listView.setAdapter(adapter);

//        wv_information = (WebView)this.getView().findViewById(R.id.wv_information);
//        wv_information.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
////        wv_information.loadUrl("http://api.txcap.com/app/getinformation/%d");
//        wv_information.loadUrl("http://api.txcap.com//app/getinfocon/12");
//        getJsonStr("http://api.txcap.com/app/getinformation/2");


    }

    /**
     获取gson字符串
     */
    public static void getJsonStr(String url){

        HttpUtilsAsync.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (i == 200) {
                    String jsonStr; jsonStr = new String(bytes);
                    Log.d("xxxxx", jsonStr);
                    Gson gson = new Gson();
                    InformationResultBean result = gson.fromJson(jsonStr,InformationResultBean.class);
                    Log.i("xxxx",result.toString());
                    List<InformationTitleBean> data = result.getData();

//                    List<InformationResultBean> list = getInformation(jsonStr,InformationResultBean.class);
                    for(int j = 0;j < data.size();j++){
                        Log.i("xxxxx",data.get(j).toString());
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }

        });
    }

    public static <T> List<T> getInformation(String jsonString, final Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Type listType = new TypeToken<LinkedList<T>>(){}.getType();
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, listType);
        } catch (Exception e) {
        }
        return list;
    }

    /*@Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                myRefreshListView.setRefreshing(false);
                ItemInfo info = new ItemInfo();
                info.setName("coin-refresh");
                infoList.add(info);
                adapter.notifyDataSetChanged();
            }
        }, 500);
    }*/
}
