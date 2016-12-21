package zsx.com.aiyamaya.ui.activity;

import android.view.View;
import android.widget.TextView;

import zsx.com.aiyamaya.R;

/**
 * Created by moram on 2016/9/22.
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";

    private TextView loginRegistTV;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void findViews() {
        setTitle("设置");
        loginRegistTV = (TextView) findViewById(R.id.tv_login_and_regist);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        loginRegistTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_login_and_regist:
                jumpToNext(LoginActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

