package zsx.com.aiyamaya.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.item.CommentItem;
import zsx.com.aiyamaya.item.PostBarItem;
import zsx.com.aiyamaya.ui.widget.CircleImageView;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;

/**
 * @discription
 * @autor songzhihang
 * @time 2017/3/16  下午7:43
 **/
public class CommentAdapter extends BaseAdapter{
    private static final String TAG = "CommentAdapter";
    private Context context;
    private List<CommentItem> commentList;

    public CommentAdapter(Context context, List<CommentItem> commentList){
        this.context=context;
        this.commentList=commentList;
    }

    public void appendData(List<CommentItem> commentList){
        if(commentList!=null){
            this.commentList.addAll(commentList);
        }
        notifyDataSetChanged();
    }

    public void appendData(CommentItem commentList){
        if(commentList!=null){
            this.commentList.add(0,commentList);
    }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return commentList==null ? 0 : commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null ){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_comment_layout,null);
            holder.headCV=(CircleImageView) convertView.findViewById(R.id.icl_cv_headimg);
            holder.nickNameTV=(TextView) convertView.findViewById(R.id.icl_tv_nickname);
            holder.timeTV=(TextView) convertView.findViewById(R.id.icl_tv_time);
            holder.contentTV=(TextView) convertView.findViewById(R.id.icl_tv_content);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        CommentItem commentItem=commentList.get(position);
        Glide.with(context)
                .load(Constant.DEFAULT_URL+Constant.IMAGE_URL
                +commentItem.getHeadUrl())
                .placeholder(R.drawable.img_loading_2)
                .into(holder.headCV);
        holder.nickNameTV.setText(commentItem.getNickName());
        holder.timeTV.setText(commentItem.getCreateTime());
        holder.contentTV.setText(commentItem.getContent());

        return convertView;
    }

    class ViewHolder {
        RelativeLayout view;

        CircleImageView headCV;
        TextView nickNameTV;
        TextView timeTV;
        TextView contentTV;
    }
}
