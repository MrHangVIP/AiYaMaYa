package zsx.com.aiyamaya.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.EmojiItem;
import zsx.com.aiyamaya.item.PostBarItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.ui.activity.BaseActivity;
import zsx.com.aiyamaya.ui.activity.PostDetailActivity;
import zsx.com.aiyamaya.ui.widget.CircleImageView;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.ProgressDialogUtil;
import zsx.com.aiyamaya.util.SpfUtil;

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
        LinearLayout imageLayoutLL = (LinearLayout) view.findViewById(R.id.ic_ll_image_layout);
        ImageView image1IV = (ImageView) view.findViewById(R.id.ic_iv_postimg1);
        ImageView image2IV = (ImageView) view.findViewById(R.id.ic_iv_postimg2);
        ImageView image3IV = (ImageView) view.findViewById(R.id.ic_iv_postimg3);
        TextView contentTV = (TextView) view.findViewById(R.id.ic_tv_content);
        RelativeLayout likeRL = (RelativeLayout) view.findViewById(R.id.ic_rl_like);
        TextView likeTV = (TextView) view.findViewById(R.id.ic_tv_likecount);
        ImageView likeIV = (ImageView) view.findViewById(R.id.ic_iv_like);
        RelativeLayout commentRL = (RelativeLayout) view.findViewById(R.id.ic_rl_comment);
        TextView commentTV = (TextView) view.findViewById(R.id.ic_tv_commentcount);
        ImageView commentIV = (ImageView) view.findViewById(R.id.ic_iv_comment);
        return new ViewHolder(view, headIV, nickNameTV, timeTV, titleTV,
                imageLayoutLL, image1IV, image2IV, image3IV,contentTV, likeRL,likeIV, likeTV,commentRL, commentIV,commentTV);
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
        final PostBarItem postBarItem = postBarList.get(position);
        String[] images = postBarItem.getImageUrl().split(Constant.MY_SPLIT_STR);
        Glide.with(mContext)
                .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + postBarItem.getHeadUrl())
                .placeholder(R.drawable.img_loading_2)
                .into(holder.headIV);
        if (images.length > 0 && !TextUtils.isEmpty(images[0])) {
            holder.contentTV.setVisibility(View.GONE);
            holder.imageLayoutLL.setVisibility(View.VISIBLE);
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
//            holder.contentTV.setText(postBarItem.getContent());
            String str =postBarItem.getContent();
            str.replace(Constant.MY_SPLIT_STR," ");
            SpannableString spannableString = new SpannableString(str);
            if (!TextUtils.isEmpty(str)) {
                List<String> ls = new ArrayList<String>();
                Pattern pattern = Pattern.compile("(?<=\\[)(.+?)(?=\\])");
                Matcher matcher = pattern.matcher(str);

                while (matcher.find()) {
                    ls.add("[" + matcher.group() + "]");
                    Log.e("SZH", "getData: " + matcher.group());
                }
                int pos = 0;
                List<EmojiItem> emojiList = BaseApplication.getEmojiItemList();
                RESTART:  for (int j=0; j<ls.size(); j++) {
                    for (EmojiItem item : emojiList) {
                        if (item.getValue().equals(ls.get(j))) {
                            pos = str.indexOf("]", pos);
                            int id = mContext.getResources().getIdentifier(item.getName(), "drawable", mContext.getPackageName());
                            Drawable d =mContext.getResources().getDrawable(id);
                            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                            spannableString.setSpan(span, pos-ls.get(j).length() +1, pos+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            pos = pos + 1;
                            MyUtil.MyLogE(pos+item.getName());
                            continue RESTART;
                        }
                    }
                }
                holder.contentTV.setText(spannableString);
            }
            holder.contentTV.setVisibility(View.VISIBLE);
            holder.imageLayoutLL.setVisibility(View.GONE);
            holder.image1IV.setVisibility(View.GONE);
            holder.image2IV.setVisibility(View.GONE);
            holder.image3IV.setVisibility(View.GONE);
        }

        holder.nickNameTV.setText(postBarItem.getNickName());
        holder.timeTV.setText(postBarItem.getCreateTime());
        holder.titleTV.setText(postBarItem.getTitle());
        holder.commentTV.setText(postBarItem.getCommentNums());
        holder.likeTV.setText(postBarItem.getLikeNums());
        holder.likeIV.setImageResource("0".equals(postBarItem.getHasLike()) ?R.drawable.icon_like_nor :R.drawable.icon_like_selected);
        holder.likeRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("0".equals(postBarItem.getHasLike()) ){
                    postBarList.get(position).setHasLike("1");
                    postBarList.get(position).setLikeNums(Integer.getInteger(postBarList.get(position).getLikeNums(),0)+1+"");
                }else{
                    postBarList.get(position).setHasLike("0") ;
                    postBarList.get(position).setLikeNums(Integer.getInteger(postBarList.get(position).getLikeNums(),1)-1+"");
                }
                notifyDataSetChanged();
                doLikeAction(postBarItem.getPostbarId()+"");

            }
        });
    }

    @Override
    public int getItemCount() {
        return postBarList.size();
    }

    private void doLikeAction(String postBarId){
        OkHttpHelp<ResultItem> okHttpHelp = OkHttpHelp.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("userPhone", SpfUtil.getString(Constant.LOGIN_USERPHONE, ""));
        map.put("postBarId", postBarId);
        map.put("token", SpfUtil.getString(Constant.TOKEN, ""));
        okHttpHelp.httpRequest("", Constant.LIKE_POSTBAR, map, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                ((BaseActivity)mContext).toast(object.getData());
            }

            @Override
            public void onFailed(String message) {

            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CircleImageView headIV;
        public final TextView nickNameTV;
        public final TextView timeTV;
        public final TextView titleTV;
        public final LinearLayout imageLayoutLL;
        public final ImageView image1IV;
        public final ImageView image2IV;
        public final ImageView image3IV;
        public final TextView contentTV;

        //底部action
        public final RelativeLayout likeRL;
        public final ImageView likeIV;
        public final TextView likeTV;
        public final RelativeLayout commentRL;
        public final ImageView commentIV;
        public final TextView commentTV;

        public ViewHolder(View view, CircleImageView headIV, TextView nickNameTV,
                          TextView timeTV, TextView titleTV,LinearLayout imageLayoutLL,
                          ImageView image1IV, ImageView image2IV, ImageView image3IV,
                          TextView contentTV,RelativeLayout likeRL,ImageView likeIV,TextView likeTV,
                          RelativeLayout commentRL,ImageView commentIV,TextView commentTV) {
            super(view);
            mView = view;
            this.headIV = headIV;
            this.nickNameTV = nickNameTV;
            this.timeTV = timeTV;
            this.titleTV = titleTV;
            this.imageLayoutLL=imageLayoutLL;
            this.image1IV = image1IV;
            this.image2IV = image2IV;
            this.image3IV = image3IV;
            this.contentTV = contentTV;
            this.likeRL = likeRL;
            this.likeIV = likeIV;
            this.likeTV = likeTV;
            this.commentRL = commentRL;
            this.commentIV = commentIV;
            this.commentTV = commentTV;

        }
    }
}
