package zsx.com.aiyamaya.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.ui.activity.LoginActivity;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.SpfUtil;


/**
 * Created by moram on 2016/9/21.
 */
public class RegistFragment extends BaseFragment{
    private static final String TAG = "RegistFragment";
    private View view;
    private EditText phoneNumberET;
    private EditText passwordET;
    private EditText checkCodeET;
    private Button registBT;

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        view=inflater.inflate(R.layout.fragment_regist,container,false);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        phoneNumberET=(EditText)view.findViewById(R.id.et_username);
        checkCodeET=(EditText)view.findViewById(R.id.et_check_code);
        passwordET=(EditText)view.findViewById(R.id.et_password);
        registBT=(Button)view.findViewById(R.id.bt_regist);
    }

    @Override
    protected void initEvent() {
        registBT.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.bt_regist:
                String username=phoneNumberET.getText().toString();
                String userpass=passwordET.getText().toString();
                Map<String,String> params=new HashMap<>();
                params.put("userPhone",username);
                params.put("userPass",userpass);
                params.put("status", SpfUtil.getString(Constant.MUM_STATE,"prepared"));
                OkHttpHelp<ResultItem> httpHelp= OkHttpHelp.getInstance();
                httpHelp.httpRequest("post", "RegistUser", params, new ResponseListener<ResultItem>() {
                    @Override
                    public void onSuccess(ResultItem object) {
                        if(object.getResult().equals("success")){
                            toast("注册成功,请登录！");
                            ((LoginActivity)getActivity()).tabFragment();
                        }else{
                            toast("注册失败");
                            phoneNumberET.setText("");
                            passwordET.setText("");
                        }
                    }

                    @Override
                    public void onFailed(String message) {

                    }

                    @Override
                    public Class<ResultItem> getEntityClass() {
                        return ResultItem.class;
                    }
                });

                break;

            case R.id.bt_send:

                break;

        }
    }
}
