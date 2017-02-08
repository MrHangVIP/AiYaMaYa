package zsx.com.aiyamaya.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.item.PostBarItem;
import zsx.com.aiyamaya.ui.activity.PostDetailActivity;
import zsx.com.aiyamaya.ui.widget.CircleImageView;
import zsx.com.aiyamaya.util.Constant;

public class CommunicateRecyclerViewAdapter extends RecyclerView.Adapter<CommunicateRecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    private List<PostBarItem> postBarList=new ArrayList<>();

    public CommunicateRecyclerViewAdapter(Context mContext,List<PostBarItem> postBarList) {
        this.mContext = mContext;
        this.postBarList=postBarList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_communicate, parent, false);
        CircleImageView headIV=(CircleImageView)view.findViewById(R.id.ic_iv_headimg);
        TextView nickNameTV=(TextView)view.findViewById(R.id.ic_tv_poster);
        TextView timeTV=(TextView)view.findViewById(R.id.ic_tv_time);
        TextView titleTV=(TextView)view.findViewById(R.id.ic_tv_title);
        ImageView imageIV=(ImageView)view.findViewById(R.id.ic_iv_postimg);
        return new ViewHolder(view,headIV,nickNameTV,timeTV,titleTV,imageIV);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mContext.startActivity(new Intent(mContext, PostDetailActivity.class));
                    }
                });
                animator.start();
            }
        });
        PostBarItem postBarItem=postBarList.get(position);
        String[] images=postBarItem.getImageUrl().split(Constant.MY_SPLIT_STR);
        Glide.with(mContext)
                .load(Constant.DEFAULT_URL+Constant.IMAGE_URL+postBarItem.getHeadUrl())
                .into(holder.headIV);
        if(images.length>0){
            Glide.with(mContext)
                    .load(Constant.DEFAULT_URL+Constant.IMAGE_URL+images[0])
                    .into(holder.imageIV);
        }else{
            holder.imageIV.setVisibility(View.GONE);
        }

        holder.nickNameTV.setText(postBarItem.getNickName());
        holder.timeTV.setText(postBarItem.getCreateTime());
        holder.titleTV.setText(postBarItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return postBarList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CircleImageView headIV;
        public final TextView nickNameTV;
        public final TextView timeTV;
        public final TextView titleTV;
        public final ImageView imageIV;

        public ViewHolder(View view,CircleImageView headIV,TextView nickNameTV,
                          TextView timeTV,TextView titleTV,ImageView imageIV) {
            super(view);
            mView = view;
            this.headIV=headIV;
            this.nickNameTV=nickNameTV;
            this.timeTV=timeTV;
            this.titleTV=titleTV;
            this.imageIV=imageIV;
        }
    }
}
