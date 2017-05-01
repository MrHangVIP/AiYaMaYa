package zsx.com.aiyamaya.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.item.UserItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.ui.widget.CircleImageView;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.ProgressDialogUtil;
import zsx.com.aiyamaya.util.SpfUtil;

/**
 * Created by moram on 2016/9/22.
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";

    public static final int RESULT_LOGIN = 0x20;

    private TextView loginRegistTV;
    private RelativeLayout loginVG;
    private RelativeLayout myselfVG;
    private CircleImageView headImgCI;
    private TextView nickNameTV;
    private TextView stateTV;
    private LinearLayout rpUserLL;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void findViews() {
        setTitle("设置");
        loginVG = (RelativeLayout) findViewById(R.id.as_ly_login);
        myselfVG = (RelativeLayout) findViewById(R.id.as_ly_myself);
        loginRegistTV = (TextView) findViewById(R.id.tv_login_and_regist);
        headImgCI = (CircleImageView) findViewById(R.id.lm_ci_headImg);
        nickNameTV = (TextView) findViewById(R.id.lm_tv_nickname);
        stateTV = (TextView) findViewById(R.id.lm_tv_state);
        rpUserLL = (LinearLayout) findViewById(R.id.asl_ll_replaceuser);
    }

    @Override
    protected void initData() {
        if (SpfUtil.getBoolean(Constant.IS_LOGIN, false)) {
            myselfVG.setVisibility(View.VISIBLE);
            loginVG.setVisibility(View.GONE);
            setSelfData();
        } else {
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
//                startActivityForResult(new Intent(SettingActivity.this,LoginActivity.class),100);
                goToNext(LoginActivity.class);
                break;

            case R.id.as_ly_myself:
                goToNext(MyselfActivity.class);
                break;

            case R.id.asl_ll_replaceuser:
                SpfUtil.clearAll();
                BaseApplication.getAPPInstance().setmUser(null);
                goToNext(LoginActivity.class);
//                startActivityForResult(new Intent(SettingActivity.this,LoginActivity.class),100);
                break;

            case R.id.asl_ll_update:
                toast("当前已是最新版本");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_LOGIN:
                loginVG.setVisibility(View.GONE);
                myselfVG.setVisibility(View.VISIBLE);
                setSelfData();
                break;
        }

    }

    private void setSelfData() {
        ProgressDialogUtil.showProgressDialog(SettingActivity.this, true);
        if (BaseApplication.getAPPInstance().getmUser() != null) {
            UserItem mUser = BaseApplication.getAPPInstance().getmUser();
            nickNameTV.setText(mUser.getNickName());
            stateTV.setText(mUser.getStatus());
            if (mUser.getHeadUrl() != null && !"".equals(mUser.getHeadUrl())) {
                Glide.with(SettingActivity.this)
                        .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + mUser.getHeadUrl())
                        .placeholder(R.drawable.img_loading_2)
                        .into(headImgCI);
            }
            ProgressDialogUtil.dismissProgressdialog();
        }
        getUserData();
    }

    private void getUserData() {
        Map<String, String> params = new HashMap<>();
        params.put("token", SpfUtil.getString(Constant.TOKEN, ""));
        params.put("userPhone", SpfUtil.getString(Constant.LOGIN_USERPHONE, ""));
        OkHttpHelp<ResultItem> httpHelp = OkHttpHelp.getInstance();
        httpHelp.httpRequest("", Constant.GET_USER_URL, params, new ResponseListener<ResultItem>() {
                    @Override
                    public void onSuccess(ResultItem object) {
                        if ("fail".equals(object.getResult())) {
                            if ("token error".equals(object.getData())) {
                                toast("token失效,请重新登录");
                                tokenError();
                                finish();
                            }
                        } else {
                            JSONObject userJson = null;
                            try {
                                userJson = new JSONObject(object.getData());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            UserItem userItem = (new Gson()).fromJson(userJson.toString(), UserItem.class);
                            if (BaseApplication.getAPPInstance().getmUser() == null) {
                                UserItem mUser = userItem;
                                nickNameTV.setText(mUser.getNickName());
                                stateTV.setText(mUser.getStatus());
                                if (mUser.getHeadUrl() != null && !"".equals(mUser.getHeadUrl())) {
                                    Glide.with(SettingActivity.this)
                                            .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + mUser.getHeadUrl())
                                            .placeholder(R.drawable.img_loading_2)
                                            .into(headImgCI);
                                }
                                ProgressDialogUtil.dismissProgressdialog();
                            }
                            BaseApplication.getAPPInstance().setmUser(userItem);
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        ProgressDialogUtil.dismissProgressdialog();
                    }

                    @Override
                    public Class getEntityClass() {
                        return ResultItem.class;
                    }
                }

        );
    }

    @Override
    protected void onDestroy() {
        MyUtil.MyLogE(TAG, "onDestroy");
        super.onDestroy();
    }
}

