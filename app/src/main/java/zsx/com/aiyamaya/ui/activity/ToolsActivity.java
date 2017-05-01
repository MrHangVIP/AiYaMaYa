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
        findViewById(R.id.at_tv_jkpc).setOnClickListener(this);
        findViewById(R.id.at_tv_xzbb).setOnClickListener(this);
        findViewById(R.id.at_tv_rl).setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.at_tv_jkpc:
                Intent intent1=new Intent(mContext,ToolsOneActivity.class);
                startActivity(intent1);
                break;
            case R.id.at_tv_xzbb:
                Intent intent2=new Intent(mContext,ToolsFiveActivity.class);
                startActivity(intent2);
                break;
            case R.id.at_tv_rl:
                Intent intent3=new Intent(mContext,ToolsThreeActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }

    }
}
