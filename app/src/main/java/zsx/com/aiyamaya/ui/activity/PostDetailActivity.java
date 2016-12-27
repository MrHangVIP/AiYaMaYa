package zsx.com.aiyamaya.ui.activity;

import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.util.ProgressDialogUtil;

/**
 * Created by moram on 2016/9/22.
 */
public class PostDetailActivity extends BaseActivity {

    private static final String TAG = "PostDetailActivity";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_post_detail);
        ProgressDialogUtil.showProgressDialog(this,false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ProgressDialogUtil.dismissProgressdialog();
            }
        },2000);
    }

    @Override
    protected void findViews() {
        setTitle("帖子详情");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

