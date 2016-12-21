package zsx.com.aiyamaya.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.ui.activity.ForgetPassActivity;


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


//
//                String url="http://192.168.188.173:8080/ZSXBiShe/";
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(url)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                GetUserListService service = retrofit.create(GetUserListService.class);
//
//                Map<String,Object> map=new HashMap<>();
//                map.put("userName","aaa");
//                Call<JsonArrayBean> call=service.getList("GetUserList",map);
//                Log.e("tag",call.request().url().toString());
//                //------------------------------- //同步请求，注意需要在子线程中
////                try {
////                    JsonArrayBean response = call.execute().body();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                //------------------------------//异步请求
//                call.enqueue(new Callback<JsonArrayBean>() {
//                    @Override
//                    public void onResponse(Call<JsonArrayBean> call, Response<JsonArrayBean> response) {
//                        Log.e("tag", response.body().getList().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonArrayBean> call, Throwable t) {
//
//                    }
//                });


                break;


            case R.id.tv_forget_pass:
                jumpToNext(ForgetPassActivity.class);
                break;
        }
    }

}
