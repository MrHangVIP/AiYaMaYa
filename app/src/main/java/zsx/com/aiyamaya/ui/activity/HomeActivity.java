package zsx.com.aiyamaya.ui.activity;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.ui.dialog.ShareDialog;
import zsx.com.aiyamaya.ui.fragment.HomeArticleFragment;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.ProgressDialogUtil;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private FragmentManager fragmentManger;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void findViews() {
        FrameLayout articleFL=(FrameLayout)findViewById(R.id.ah_fl_article);

    }

    @Override
    protected void initData() {
        HomeArticleFragment articleFragment=new HomeArticleFragment();
        fragmentManger = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManger.beginTransaction();
        transaction.replace(R.id.ah_fl_article, articleFragment);
        transaction.commit();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_share).setOnClickListener(this);
        findViewById(R.id.iv_setting).setOnClickListener(this);
        findViewById(R.id.iv_home_page).setOnClickListener(this);
        findViewById(R.id.iv_shares).setOnClickListener(this);
        findViewById(R.id.iv_classical_article).setOnClickListener(this);
        findViewById(R.id.iv_practical_tools).setOnClickListener(this);
        findViewById(R.id.iv_settings).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share:
                new ShareDialog((Activity)HomeActivity.this ).show();
                break;

            case R.id.iv_setting:
                jumpToNext(SettingActivity.class);
                break;

            case R.id.iv_home_page:

                break;

            case R.id.iv_shares:
                jumpToNext(CommunicateActivity.class);
                break;

            case R.id.iv_classical_article:
                jumpToNext(ArticleActivity.class);
                break;

            case R.id.iv_practical_tools:

                break;

            case R.id.iv_settings:

                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
