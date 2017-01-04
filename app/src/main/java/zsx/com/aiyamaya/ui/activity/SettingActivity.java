package zsx.com.aiyamaya.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.item.UserItem;
import zsx.com.aiyamaya.ui.widget.CircleImageView;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.ProgressDialogUtil;
import zsx.com.aiyamaya.util.SpfUtil;

/**
 * Created by moram on 2016/9/22.
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";

    public static final int  RESULT_LOGIN=0x20;

    private TextView loginRegistTV;
    private ViewGroup loginVG;
    private ViewGroup myselfVG;
    private CircleImageView headImgCI;
    private TextView nickNameTV;
    private TextView stateTV;
    private LinearLayout rpUserLL;


    @Override
    protected void setView() {
        setContentView(R.layout.activity_setting);
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
        setTitle("设置");
        loginVG=(ViewGroup)findViewById(R.id.as_ly_login);
        myselfVG=(ViewGroup)findViewById(R.id.as_ly_myself);
        loginRegistTV = (TextView) findViewById(R.id.tv_login_and_regist);
        headImgCI=(CircleImageView)findViewById(R.id.lm_ci_headImg);
        nickNameTV=(TextView)findViewById(R.id.lm_tv_nickname);
        stateTV=(TextView)findViewById(R.id.lm_tv_state);
        rpUserLL=(LinearLayout)findViewById(R.id.asl_ll_replaceuser);
    }

    @Override
    protected void initData() {
        if(SpfUtil.getBoolean(Constant.IS_LOGIN,false)){
            myselfVG.setVisibility(View.VISIBLE);
            loginVG.setVisibility(View.GONE);
            setSelfData();
        }else{
            loginVG.setVisibility(View.VISIBLE);
            myselfVG.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListener() {
        loginRegistTV.setOnClickListener(this);
        myselfVG.setOnClickListener(this);
        rpUserLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_login_and_regist:
                startActivityForResult(new Intent(SettingActivity.this,LoginActivity.class),100);
                break;

            case R.id.as_ly_myself:
                jumpToNext(MyselfActivity.class);
                break;

            case R.id.asl_ll_replaceuser:
                SpfUtil.clearAll();
                startActivityForResult(new Intent(SettingActivity.this,LoginActivity.class),100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){
            case RESULT_LOGIN:
                loginVG.setVisibility(View.GONE);
                myselfVG.setVisibility(View.VISIBLE);
                setSelfData();
            break;
        }

    }

    private void  setSelfData(){
        if(BaseApplication.getAPPInstance().getmUser()!=null){
            UserItem mUser=BaseApplication.getAPPInstance().getmUser();
            nickNameTV.setText(mUser.getNickName());
            stateTV.setText(mUser.getStatus());
            if(mUser.getHeadUrl()!=null && !"".equals(mUser.getHeadUrl())){
                Glide.with(SettingActivity.this)
                        .load(mUser.getHeadUrl())
                        .into(headImgCI);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

