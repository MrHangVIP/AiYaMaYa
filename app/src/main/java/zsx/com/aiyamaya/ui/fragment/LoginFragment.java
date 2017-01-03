package zsx.com.aiyamaya.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.ui.activity.ForgetPassActivity;
import zsx.com.aiyamaya.ui.activity.LoginActivity;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.SpfUtil;


/**
 * Created by moram on 2016/9/21.
 */
public class LoginFragment extends BaseFragment{

    private static final String TAG = "LoginFragment";
    private View view;
    private EditText phoneNumberET;
    private EditText passwordET;
    private TextView frogetPassTV;
    private Button loginBT;



    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        view=inflater.inflate(R.layout.fragment_login,container,false);

        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        phoneNumberET=(EditText)view.findViewById(R.id.et_username);
        passwordET=(EditText)view.findViewById(R.id.et_password);
        frogetPassTV=(TextView)view.findViewById(R.id.tv_forget_pass);
        loginBT=(Button)view.findViewById(R.id.bt_login);
    }

    @Override
    protected void initEvent() {
        frogetPassTV.setOnClickListener(this);
        loginBT.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.bt_login:
                String username=phoneNumberET.getText().toString();
                String userpass=passwordET.getText().toString();
                Map<String,String> params=new HashMap<>();
                params.put("userPhone",username);
                params.put("userPass",userpass);
                OkHttpHelp<ResultItem> httpHelp= OkHttpHelp.getInstance();
                httpHelp.httpRequest("post", "LoginUser", params, new ResponseListener<ResultItem>() {
                    @Override
                    public void onSuccess(ResultItem object) {
                        if(object.getResult().equals("success")){
                            toast("登录成功！");
                            getActivity().finish();
                        }else{
                            toast("用户名或密码错误");
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


            case R.id.tv_forget_pass:
                jumpToNext(ForgetPassActivity.class);
                getActivity().finish();
                break;
        }
    }

}
