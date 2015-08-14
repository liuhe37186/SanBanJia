package com.txcap.sanbanjia.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.marshalchen.common.ui.ToastUtil;
import com.txcap.sanbanjia.R;
import com.txcap.sanbanjia.fragment.HomeFragment;
import com.txcap.sanbanjia.fragment.InformationFragment;
import com.txcap.sanbanjia.fragment.StoreFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity implements View.OnClickListener{


    @InjectView(R.id.rl_home)
    RelativeLayout rl_home;
    @InjectView(R.id.rl_information)
    RelativeLayout information;
    @InjectView(R.id.rl_store)
    RelativeLayout store;
    @InjectView(R.id.rl_mine)
    RelativeLayout mine;
//    @InjectView(R.id.rl_pluse)
//    ImageButton ib_pluse;

    FragmentManager fragmentManager;
    HomeFragment homeFragment;
    InformationFragment informationFragment;
    StoreFragment storeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        ButterKnife.inject(this);
        rl_home.setOnClickListener(this);
        information.setOnClickListener(this);
        store.setOnClickListener(this);
        mine.setOnClickListener(this);
        homeFragment = new HomeFragment();
        informationFragment = new InformationFragment();
        storeFragment = new StoreFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.rl_top, homeFragment).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_home:
                ToastUtil.show(this, "首页");
                FragmentTransaction hometransaction = getFragmentManager().beginTransaction();
                hometransaction.replace(R.id.rl_top, homeFragment);
                hometransaction.addToBackStack("homeFragment");
                hometransaction.commit();
                break;
            case R.id.rl_information:
                ToastUtil.show(this, "资讯");
                FragmentTransaction informationtransaction = getFragmentManager().beginTransaction();
                informationtransaction.replace(R.id.rl_top ,informationFragment);
                informationtransaction.addToBackStack("informationFragment");
                informationtransaction.commit();
                break;
            case R.id.rl_store:
                ToastUtil.show(this, "三板库");
                FragmentTransaction storetransaction = getFragmentManager().beginTransaction();
                storetransaction.replace(R.id.rl_top, storeFragment);
                storetransaction.addToBackStack("storeFragment");
                storetransaction.commit();
                break;
//            case R.id.rl_mine:
//                FragmentTransaction minetransaction = getFragmentManager().beginTransaction();
//                minetransaction.replace(R.id.rl_top, mineFragment);
//                minetransaction.addToBackStack("storeFragment");
//                minetransaction.commit();
//                break;
        }
    }
}
