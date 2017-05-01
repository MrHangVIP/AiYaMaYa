package zsx.com.aiyamaya.ui.activity;

import android.content.Intent;
import android.view.View;

import zsx.com.aiyamaya.R;

/**
 * Created by Songzhihang on 2017/5/1.
 */
public class ToolsFiveActivity extends BaseActivity{
    private static final String TAG = "ToolsFiveActivity";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_tools_five);
    }

    @Override
    protected void findViews() {
        findViewById(R.id.shuiping).setOnClickListener(this);
        findViewById(R.id.shuangyu).setOnClickListener(this);
        findViewById(R.id.baiyang).setOnClickListener(this);
        findViewById(R.id.jinniu).setOnClickListener(this);
        findViewById(R.id.shuangzi).setOnClickListener(this);
        findViewById(R.id.juxie).setOnClickListener(this);
        findViewById(R.id.shizi).setOnClickListener(this);
        findViewById(R.id.chunv).setOnClickListener(this);
        findViewById(R.id.tiancheng).setOnClickListener(this);
        findViewById(R.id.tianxie).setOnClickListener(this);
        findViewById(R.id.shezhou).setOnClickListener(this);
        findViewById(R.id.mojie).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setTitle("星座宝宝");
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent(mContext,XinZuoActivity.class);
        intent.putExtra("layoutId",v.getId());
        startActivity(intent);

    }
}
