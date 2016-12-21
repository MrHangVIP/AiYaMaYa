package zsx.com.aiyamaya.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.ui.dialog.ShareDialog;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {
        this.findViewById(R.id.iv_share).setOnClickListener(this);
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

                break;

            case R.id.iv_classical_article:

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
