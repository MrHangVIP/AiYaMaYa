package zsx.com.aiyamaya.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @discription
 * @autor songzhihang
 * @time 2017/3/16  下午7:43
 **/
public class CommentAdapter extends BaseAdapter{
    private static final String TAG = "CommentAdapter";
    private Context context;

    public CommentAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            RelativeLayout layout=new RelativeLayout(context);

            TextView textView = new TextView(context);
            RelativeLayout.LayoutParams params =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,1000);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText("快来发表你的评论吧");
            textView.setTextSize(15);
            textView.setTextColor(Color.GRAY);
            layout.setPadding(0,200,0,0);
            layout.addView(textView,params);
            holder.view = layout;
            convertView=layout;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        RelativeLayout view;
    }
}
