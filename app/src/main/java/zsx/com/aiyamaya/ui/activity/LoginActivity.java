package zsx.com.aiyamaya.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.adapter.LoginAdapter;
import zsx.com.aiyamaya.ui.fragment.LoginFragment;
import zsx.com.aiyamaya.ui.fragment.RegistFragment;
import zsx.com.aiyamaya.ui.widget.CustomViewPager;

/**
 *  Created by moram on 2016/9/21
 */
public class LoginActivity extends BaseActivity  {
    private static final String TAG = "LoginActivity";

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private ImageView closeIV;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private LoginAdapter homeAdapter;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findViews() {
        closeIV=(ImageView)findViewById(R.id.iv_close);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (CustomViewPager)findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegistFragment());
        titles = new ArrayList<>();
        titles.add("登录");
        titles.add("注册");

        viewPager.setPagingEnabled(false);
        homeAdapter = new LoginAdapter(getSupportFragmentManager(), fragments, titles);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for(int i = 0; i < titles.size(); i ++){
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }
        viewPager.setAdapter(homeAdapter);
        tabLayout.setupWithViewPager(viewPager);//绑定 ViewPager 和tabLayout
    }

    @Override
    protected void setListener() {
        closeIV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                goToNext(SettingActivity.class);
                break;
        }
    }

    public void tabFragment(){
        TabLayout.Tab tab= tabLayout.getTabAt(0);
        tab.select();
    }

    @Override
    public void onBackPressed() {
        goToNext(SettingActivity.class);
    }
}

