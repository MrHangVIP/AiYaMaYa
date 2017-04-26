package zsx.com.aiyamaya.ui.activity;

import android.os.Bundle;

import zsx.com.aiyamaya.R;

/**
 * Created by Songzhihang on 2017/4/24.
 */
public class ToolsDetailActivity  extends BaseActivity{
    private static final String TAG = "ToolsDetailActivity";

    @Override
    protected void setView() {
        int layoutId=getIntent().getIntExtra("layoutId", R.layout.activity_tools_one);
        setContentView(layoutId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
