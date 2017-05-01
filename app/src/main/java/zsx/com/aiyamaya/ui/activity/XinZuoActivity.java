package zsx.com.aiyamaya.ui.activity;

import zsx.com.aiyamaya.R;

/**
 * Created by Songzhihang on 2017/5/1.
 */
public class XinZuoActivity extends BaseActivity{
    private static final String TAG = "XinZuoActivity";
    @Override
    protected void setView() {
        int id=getIntent().getIntExtra("layoutId", R.id.shuiping);
        switch (id){
            case R.id.shuiping:
                setContentView(R.layout.activity_tools_five_01);
                break;
            case R.id.shuangyu:
                setContentView(R.layout.activity_tools_five_02);
                break;
            case R.id.baiyang:
                setContentView(R.layout.activity_tools_five_03);
                break;
            case R.id.jinniu:
                setContentView(R.layout.activity_tools_five_04);
                break;
            case R.id.shuangzi:
                setContentView(R.layout.activity_tools_five_05);
                break;
            case R.id.juxie:
                setContentView(R.layout.activity_tools_five_06);
                break;
            case R.id.shizi:
                setContentView(R.layout.activity_tools_five_07);
                break;
            case R.id.chunv:
                setContentView(R.layout.activity_tools_five_08);
                break;
            case R.id.tiancheng:
                setContentView(R.layout.activity_tools_five_09);
                break;
            case R.id.tianxie:
                setContentView(R.layout.activity_tools_five_10);
                break;
            case R.id.shezhou:
                setContentView(R.layout.activity_tools_five_11);
                break;
            case R.id.mojie:
                setContentView(R.layout.activity_tools_five_12);
                break;
        }
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
