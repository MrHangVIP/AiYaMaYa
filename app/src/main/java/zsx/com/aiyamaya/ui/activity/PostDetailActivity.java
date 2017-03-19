package zsx.com.aiyamaya.ui.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.adapter.CommentAdapter;
import zsx.com.aiyamaya.adapter.HomeArticleAdapter;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ArticleItem;
import zsx.com.aiyamaya.item.EmojiItem;
import zsx.com.aiyamaya.item.PostBarItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.ui.widget.CircleImageView;
import zsx.com.aiyamaya.ui.widget.CusListView;
import zsx.com.aiyamaya.ui.widget.MyFullLayoutManager;
import zsx.com.aiyamaya.ui.widget.MyRichView;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.ProgressDialogUtil;

/**
 * Created by moram on 2016/9/22.
 */
public class PostDetailActivity extends BaseActivity implements AbsListView.OnScrollListener {

    private static final String TAG = "PostDetailActivity";
    private CusListView cusListView;
    private LinearLayout commentRL;
    private MyRichView myRichView;
    private PostBarItem postBarItem;
    private CircleImageView headImgCI;
    private TextView nickNameTV;
    private TextView titleTV;
    private TextView timeTV;


    @Override
    protected void setView() {
        setContentView(R.layout.activity_post_detail);
//        ProgressDialogUtil.showProgressDialog(this, false);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                ProgressDialogUtil.dismissProgressdialog();
//            }
//        }, 2000);
    }

    @Override
    protected void findViews() {
        setTitle("帖子详情");
        cusListView = (CusListView) findViewById(R.id.acd_lv_listview);
        commentRL = (LinearLayout) findViewById(R.id.comment);
        myRichView = cusListView.getMyRichView();
        headImgCI = (CircleImageView) cusListView.getTitleView().findViewById(R.id.ldht_ci_head);
        nickNameTV = (TextView) cusListView.getTitleView().findViewById(R.id.ldht_tv_nickname);
        titleTV = (TextView) cusListView.getTitleView().findViewById(R.id.ldht_tv_title);
        timeTV = (TextView) cusListView.getTitleView().findViewById(R.id.ldht_tv_time);

    }

    @Override
    protected void initData() {
        postBarItem = (PostBarItem) getIntent().getSerializableExtra("postdetail");
        getData();
        cusListView.setAdapter(new CommentAdapter(this));
        nickNameTV.setText(postBarItem.getNickName());
        titleTV.setText(postBarItem.getTitle());
        timeTV.setText(postBarItem.getCreateTime());
        Glide.with(this)
                .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + postBarItem.getHeadUrl())
                .placeholder(R.drawable.img_loading_2)
                .into(headImgCI);
    }

    @Override
    protected void setListener() {
        cusListView.setOnScrollListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    private void getData() {
        String[] orderarr = postBarItem.getOrders().split(",");
        String[] imagearr = postBarItem.getImageUrl().split(Constant.MY_SPLIT_STR);
        String[] contentarr = postBarItem.getContent().split(Constant.MY_SPLIT_STR);
        int imageCount = 0;
        int contentCount = 0;
        List<EmojiItem> emojiList = BaseApplication.getEmojiItemList();
        for (int i = 0; i < orderarr.length; i++) {
            if (orderarr[i].equals("0") && contentarr != null && contentCount < contentarr.length) {

                String str = contentarr[contentCount];
                SpannableString spannableString = new SpannableString(str);
                if (!TextUtils.isEmpty(str)) {
                    List<String> ls = new ArrayList<String>();
                    Pattern pattern = Pattern.compile("(?<=\\[)(.+?)(?=\\])");
                    Matcher matcher = pattern.matcher(str);

                    while (matcher.find()) {
                        ls.add("[" + matcher.group() + "]");
                        Log.e(TAG, "getData: " + matcher.group());
                    }
                    int pos = 0;
                    RESTART:  for (int j=0; j<ls.size(); j++) {
                        for (EmojiItem item : emojiList) {
                            if (item.getValue().equals(ls.get(j))) {
                                pos = str.indexOf("]", pos);
                                int id = getResources().getIdentifier(item.getName(), "drawable", getPackageName());
                                Drawable d = getDrawableRes(id);
                                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                                spannableString.setSpan(span, pos-ls.get(j).length() +1, pos+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                pos = pos + 1;
                                MyUtil.MyLogE(TAG,pos+item.getName());
                                continue RESTART;
                            }
                        }
                    }
                    myRichView.createTextView(spannableString);
                    contentCount++;
                }
            }
            if (orderarr[i].equals("1")) {
                String url = imagearr[imageCount];
                myRichView.createImageView(Constant.DEFAULT_URL + Constant.IMAGE_URL + url);
                imageCount++;
            }

        }

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View firstItem = view.getChildAt(0);
        if (firstItem != null && firstItem instanceof LinearLayout && cusListView.getShowView() != null) {
//            Log.e(TAG, "firstItem.getY(): " + firstItem.getY());
//            Log.e(TAG, "firstItem.getHeight(): " + firstItem.getHeight());
//            Log.e(TAG, "view.getY(): " + view.getY());
//            Log.e(TAG, "getShowView: " + cusListView.getShowView().getHeight());
//            Log.e(TAG, "padding: " + padding);
            if (-firstItem.getY() >= firstItem.getHeight() - cusListView.getShowView().getHeight()) {
                commentRL.setVisibility(View.VISIBLE);
            } else {
                commentRL.setVisibility(View.GONE);
            }

//            if(getWinHeight()-view.getY()-padding-contentTop<=firstItem.getY()+firstItem.getHeight()){
//            if(-firstItem.getY()<=680){
//                findViewById(R.id.buttom_text).setVisibility(View.VISIBLE);
//            }else{
//                findViewById(R.id.buttom_text).setVisibility(View.GONE);
//            }

        } else {
            commentRL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
}

