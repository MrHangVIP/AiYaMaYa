package zsx.com.aiyamaya.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import zsx.com.aiyamaya.R;


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
//                EventBus.getDefault().post(new EventBusTest("hello"));
//                String url="http://192.168.188.173:8080/ZSXBiShe/AddUserServlet";
//                OkHttpClient client = new OkHttpClient();
//                try {
//                    Log.e("tag",url);
//                    String username=phoneNumberET.getText().toString();
//                    String password=passwordET.getText().toString();
//                    String age=checkCodeET.getText().toString();
//                    RequestBody formBody = new FormBody.Builder()
//                            .add("userName",username)
//                            .add("userPass",password)
//                            .add("age",age)
//                            .build();
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .post(formBody)
//                            .header("User-Agent", "OkHttp Headers.java")
//                            .addHeader("Accept", "application/json; q=0.5")
//                            .build();
//
//                    client.newCall(request).enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
////                            Toast.makeText(getActivity(),"注册成功！",Toast.LENGTH_SHORT).show();
//                            System.out.println(response.body().string());
//                        }
//                    });
//                }catch (Exception e){
//                    Log.e("tag","go");
//                }


                break;

            case R.id.bt_send:

                break;

        }
    }
}
