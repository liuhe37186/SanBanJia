package com.txcap.sanbanjia.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;
import com.marshalchen.common.ui.ToastUtil;
import com.txcap.sanbanjia.activity.InformationDetails;
import com.txcap.sanbanjia.adapter.ListViewAdapter;
import com.txcap.sanbanjia.R;
import com.txcap.sanbanjia.utils.ImageDownloader;
import com.txcap.sanbanjia.view.RefreshLayout;
import com.txcap.sanbanjia.bean.InformationResultBean;
import com.txcap.sanbanjia.bean.InformationTitleBean;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liuhe on 15/8/12.
 */
public class InformationFragment extends Fragment {


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

    private List<InformationTitleBean> data;

    private Integer id = 2;

    public Activity mView;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information_title, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mView = this.getActivity();
        // 获取RefreshLayout实例
        myRefreshListView = (RefreshLayout) this.getActivity().findViewById(R.id.swipe_refresh);
        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        myRefreshListView.setColorScheme(android.R.color.holo_green_light);
//        infoList = new ArrayList<InformationTitleBean>();

//        for (int i = 0; i < 10; i++) {
//            ItemInfo info = new ItemInfo();
//            info.setName("coin" + i);
//            infoList.add(info);
//        }

//        listView = (ListView) this.getActivity().findViewById(R.id.listview);
//        adapter = new ListViewAdapter(this.getActivity(), infoList);
//        listView.setAdapter(adapter);


        listView = (ListView) mView.findViewById(R.id.listview);
        getJsonStr("http://api.txcap.com/app/getinformation/1");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent details = new Intent(mView, InformationDetails.class);
                InformationTitleBean titleBean = (InformationTitleBean) listView.getItemAtPosition(position);
                int urlid = titleBean.getId();
                details.putExtra("url",urlid);
                startActivity(details);
            }
        });

        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {


                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        myRefreshListView.setRefreshing(false);
                        id = 1;
                        data.clear();
                        getJsonStr("http://api.txcap.com/app/getinformation/1");
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
//                        // 加载完后调用该方法
                        myRefreshListView.setLoading(false);
                        id++;
                        loadMore("http://api.txcap.com/app/getinformation/" + id);
                    }
                }, 1500);

            }
        });
    }

    /**
     * 获取gson字符串
     */
    public void getJsonStr(String url) {

        HttpUtilsAsync.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (i == 200) {
                    String jsonStr;
                    jsonStr = new String(bytes);
                    Log.d("xxxxx", jsonStr);
                    Gson gson = new Gson();
                    InformationResultBean result = gson.fromJson(jsonStr, InformationResultBean.class);
                    Log.i("xxxx", result.toString());
                    data = result.getData();

                    adapter = new ListViewAdapter(mView, data);
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }

        });
    }

    public void loadMore(String url){
        HttpUtilsAsync.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (i == 200) {
                    String jsonStr;
                    jsonStr = new String(bytes);
                    Log.d("xxxxx", jsonStr);
                    Gson gson = new Gson();
                    InformationResultBean result = gson.fromJson(jsonStr, InformationResultBean.class);
                    Log.i("xxxx", result.toString());
                    List<InformationTitleBean> new_data = result.getData();
                    if(new_data != null && new_data.size() > 0){
                        for (int j = 0; j < new_data.size(); j++) {
                            data.add(new_data.get(j));
                        }

                    }else {
                        ToastUtil.show(mView,"木有更多了...");
                    }

//                    listView = (ListView) mView.findViewById(R.id.listview);

                    adapter.notifyDataSetChanged();

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
            Type listType = new TypeToken<LinkedList<T>>() {
            }.getType();
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, listType);
        } catch (Exception e) {
        }
        return list;
    }
}
