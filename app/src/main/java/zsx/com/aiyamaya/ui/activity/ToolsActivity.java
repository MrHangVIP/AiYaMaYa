package zsx.com.aiyamaya.ui.activity;

import android.content.Intent;
import android.view.View;

import zsx.com.aiyamaya.R;

/**
 * Created by Songzhihang on 2017/4/24.
 */
public class ToolsActivity extends BaseActivity {
    private static final String TAG = "ToolsActivity";


    @Override
    protected void setView() {
        setContentView(R.layout.activity_tools);
    }

    @Override
    protected void findViews() {
        findViewById(R.id.at_btn_tools1).setOnClickListener(this);
        findViewById(R.id.at_btn_tools2).setOnClickListener(this);
        findViewById(R.id.at_btn_tools3).setOnClickListener(this);
        findViewById(R.id.at_btn_tools4).setOnClickListener(this);
        findViewById(R.id.at_btn_tools5).setOnClickListener(this);
        findViewById(R.id.at_btn_tools6).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setTitle("实用工具");
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int  layoutId=R.layout.activity_tools_one;
        switch (v.getId()){
            case R.id.at_btn_tools1:
                layoutId=R.layout.activity_tools_one;
                break;
            case R.id.at_btn_tools2:
                layoutId=R.layout.activity_tools_two;
                break;
            case R.id.at_btn_tools3:
                layoutId=R.layout.activity_tools_three;
                break;
            case R.id.at_btn_tools4:
                layoutId=R.layout.activity_tools_four;
                break;
            case R.id.at_btn_tools5:
                layoutId=R.layout.activity_tools_five;
                break;
            case R.id.at_btn_tools6:
                layoutId=R.layout.activity_tools_six;
                break;
            default:
                break;
        }
        Intent intent=new Intent(mContext,ToolsDetailActivity.class);
        intent.putExtra("layoutId",layoutId);
        startActivity(intent);
    }
}
