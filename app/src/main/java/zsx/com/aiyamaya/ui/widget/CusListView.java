package zsx.com.aiyamaya.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import zsx.com.aiyamaya.R;

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
        contentHead.addView(myRichView);

        LinearLayout titleHead=new LinearLayout(context);
        showView= LayoutInflater.from(context).inflate(R.layout.comment_bottom_layout,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
        titleHead.addView(showView,params);

        LinearLayout headLayout=new LinearLayout(context);
        headLayout.setOrientation(LinearLayout.VERTICAL);
        headLayout.addView(contentHead);
        headLayout.addView(titleHead);

        addHeaderView(headLayout);

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
