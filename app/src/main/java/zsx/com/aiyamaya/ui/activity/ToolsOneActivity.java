package zsx.com.aiyamaya.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import zsx.com.aiyamaya.R;

/**
 * Created by Songzhihang on 2017/5/1.
 */
public class ToolsOneActivity  extends  BaseActivity{
    private static final String TAG = "ToolsOneActivity";
    private EditText weightET;
    private EditText heightET;
    private Button calculateBT;
    private TextView resultTV;
    private TextView adviceTV;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_tools_one);
    }

    @Override
    protected void findViews() {
        weightET=(EditText)findViewById(R.id.atone_et_weight);
        heightET=(EditText)findViewById(R.id.atone_et_height);
        calculateBT=(Button)findViewById(R.id.atone_btn_calculate);

        resultTV=(TextView)findViewById(R.id.ato_tv_BMI);
        adviceTV=(TextView)findViewById(R.id.ato_tv_advice);
    }

    @Override
    protected void initData() {
        setTitle("健康评测");
    }

    @Override
    protected void setListener() {
        final DecimalFormat   fnum  =   new DecimalFormat("##0.00");
        calculateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weightStr=weightET.getText().toString();
                if("".equals(weightStr)){
                    toast("体重不能为空~");
                }
                float weight=Float.parseFloat(weightStr);
                if(0==weight){
                    toast("体重不能为0~");
                }

                String heightStr=heightET.getText().toString();
                if("".equals(heightStr)){
                    toast("身高不能为空~");
                }
                float height=Float.parseFloat(heightStr);
                if(0==height){
                    toast("身高不能为0~");
                }

                float result=weight/((height/100)*(height/100));

                resultTV.setText(fnum.format(result)+"");

                if(result<18.5){
                adviceTV.setText("您太瘦啦~多吃点哦！");
                }else if(result<24.9){
                    adviceTV.setText("恭喜你，身材很标准，继续加油~");
                }else {
                    adviceTV.setText("体重超标了，合理饮食加锻炼哦~");
                }

            }
        });
    }
}
