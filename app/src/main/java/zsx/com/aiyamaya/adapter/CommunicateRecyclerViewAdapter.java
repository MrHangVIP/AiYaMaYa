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

    private List<PostBarItem> postBarList = new ArrayList<>();

    public CommunicateRecyclerViewAdapter(Context mContext, List<PostBarItem> postBarList) {
        this.mContext = mContext;
        this.postBarList = postBarList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_communicate, parent, false);
        CircleImageView headIV = (CircleImageView) view.findViewById(R.id.ic_iv_headimg);
        TextView nickNameTV = (TextView) view.findViewById(R.id.ic_tv_poster);
        TextView timeTV = (TextView) view.findViewById(R.id.ic_tv_time);
        TextView titleTV = (TextView) view.findViewById(R.id.ic_tv_title);
        ImageView image1IV = (ImageView) view.findViewById(R.id.ic_iv_postimg1);
        ImageView image2IV = (ImageView) view.findViewById(R.id.ic_iv_postimg2);
        ImageView image3IV = (ImageView) view.findViewById(R.id.ic_iv_postimg3);
        return new ViewHolder(view, headIV, nickNameTV, timeTV, titleTV, image1IV, image2IV, image3IV);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(mContext, PostDetailActivity.class);
                        intent.putExtra("postdetail", postBarList.get(position));
                        mContext.startActivity(intent);
                    }
                });
                animator.start();
            }
        });
        PostBarItem postBarItem = postBarList.get(position);
        String[] images = postBarItem.getImageUrl().split(Constant.MY_SPLIT_STR);
        Glide.with(mContext)
                .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + postBarItem.getHeadUrl())
                .placeholder(R.drawable.img_loading_2)
                .into(holder.headIV);
        if (images.length > 0) {
            Glide.with(mContext)
                    .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + images[0])
                    .placeholder(R.drawable.img_loading_2)
                    .into(holder.image1IV);
            holder.image1IV.setVisibility(View.VISIBLE);
            if (images.length > 1) {
                Glide.with(mContext)
                        .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + images[1])
                        .placeholder(R.drawable.img_loading_2)
                        .into(holder.image2IV);
                holder.image2IV.setVisibility(View.VISIBLE);
            } else {
                holder.image2IV.setVisibility(View.GONE);
                holder.image3IV.setVisibility(View.GONE);
            }
            if (images.length > 2) {
                Glide.with(mContext)
                        .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + images[2])
                        .placeholder(R.drawable.img_loading_2)
                        .into(holder.image3IV);
                holder.image3IV.setVisibility(View.VISIBLE);
            } else {
                holder.image3IV.setVisibility(View.GONE);
            }
        } else {
            holder.image1IV.setVisibility(View.GONE);
            holder.image2IV.setVisibility(View.GONE);
            holder.image3IV.setVisibility(View.GONE);
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
        public final ImageView image1IV;
        public final ImageView image2IV;
        public final ImageView image3IV;

        public ViewHolder(View view, CircleImageView headIV, TextView nickNameTV,
                          TextView timeTV, TextView titleTV, ImageView image1IV, ImageView image2IV, ImageView image3IV) {
            super(view);
            mView = view;
            this.headIV = headIV;
            this.nickNameTV = nickNameTV;
            this.timeTV = timeTV;
            this.titleTV = titleTV;
            this.image1IV = image1IV;
            this.image2IV = image2IV;
            this.image3IV = image3IV;
        }
    }
}
