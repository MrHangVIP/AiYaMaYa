package zsx.com.aiyamaya.ui.activity;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.item.UserItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.ui.dialog.ShareDialog;
import zsx.com.aiyamaya.ui.fragment.HomeArticleFragment;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.ProgressDialogUtil;
import zsx.com.aiyamaya.util.SpfUtil;

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
                if(SpfUtil.getBoolean(Constant.IS_LOGIN,false) && BaseApplication.getAPPInstance().getmUser()!=null){
                    jumpToNext(CommunicateActivity.class);
                }else{
                    toast("请先登录");
                }

                break;

            case R.id.iv_classical_article:
                jumpToNext(ArticleActivity.class);
                break;

            case R.id.iv_practical_tools:
                jumpToNext(ToolsActivity.class);
                break;

            case R.id.iv_settings:
                jumpToNext(SettingActivity.class);
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
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
//                                tokenError();
                                toast("请重新登陆");
//                                finish();
                            }
                        } else {
                            JSONObject userJson = null;
                            try {
                                userJson = new JSONObject(object.getData());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            UserItem userItem = (new Gson()).fromJson(userJson.toString(), UserItem.class);
                            BaseApplication.getAPPInstance().setmUser(userItem);
                        }
                    }

                    @Override
                    public void onFailed(String message) {

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
        super.onDestroy();
    }
}
