package zsx.com.aiyamaya.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.Util;

/**
 * @discription
 * @autor songzhihang
 * @time 2017/3/16  下午4:44
 **/
public class CusListView extends ListView {
    private static final String TAG = "CusListView";

    private Context context;

    private MyRichView myRichView;
    private View showView;
    private View titleView;


    public CusListView(Context context) {
        super(context);
        init(context);
    }

    public CusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CusListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context=context;


        LinearLayout contentHead=new LinearLayout(context);
        contentHead.setOrientation(LinearLayout.VERTICAL);
        myRichView=new MyRichView(context);
        titleView= LayoutInflater.from(context).inflate(R.layout.layout_detai_head_title,null);
        contentHead.addView(titleView);

        LinearLayout.LayoutParams richViewParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        richViewParams.setMargins(Util.toDip(5),0,Util.toDip(5),0);
        contentHead.addView(myRichView,richViewParams);

        LinearLayout titleHead=new LinearLayout(context);
        showView= LayoutInflater.from(context).inflate(R.layout.comment_bottom_layout,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) Constant.getScreenDIS(context)*50);
        titleHead.setGravity(Gravity.CENTER_VERTICAL);
        titleHead.addView(showView,params);

        LinearLayout headLayout=new LinearLayout(context);
        headLayout.setOrientation(LinearLayout.VERTICAL);
        headLayout.addView(contentHead);
        headLayout.addView(titleHead);

        addHeaderView(headLayout);

        RelativeLayout layout=new RelativeLayout(context);

        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams footParams =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                , RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText("快来发表你的评论吧");
        textView.setTextSize(15);
        textView.setTextColor(Color.GRAY);
        layout.setPadding(0,100,0,100);
        layout.addView(textView,footParams);
        addFooterView(layout);

    }

    public MyRichView getMyRichView(){
        return myRichView;
    }

    public View getShowView(){
        return showView;
    }

    public View getTitleView(){
        return titleView;
    }
}
