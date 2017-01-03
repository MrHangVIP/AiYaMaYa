package zsx.com.aiyamaya.ui.activity;

import android.view.View;
import android.widget.TextView;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.SpfUtil;

public class StateSelectActivity extends BaseActivity {

    private static final String TAG = "StateSelectActivity";

    private TextView prepareIV;

    private TextView pregnantIV;

    private TextView babyIV;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_state_select);
    }

    @Override
    protected void findViews() {
        prepareIV=(TextView)findViewById(R.id.ass_tv_prepared);
        pregnantIV=(TextView)findViewById(R.id.ass_tv_pregnant);
        babyIV=(TextView)findViewById(R.id.ass_tv_baby);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        prepareIV.setOnClickListener(this);
        pregnantIV.setOnClickListener(this);
        babyIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ass_tv_prepared:
                SpfUtil.saveString(Constant.MUM_STATE,"prepared");
                break;

            case R.id.ass_tv_pregnant:
                SpfUtil.saveString(Constant.MUM_STATE,"pregnant");
                break;

            case R.id.ass_tv_baby:
                SpfUtil.saveString(Constant.MUM_STATE,"baby");
                break;
        }
        jumpToNext(HomeActivity.class);
        finish();
    }
}
