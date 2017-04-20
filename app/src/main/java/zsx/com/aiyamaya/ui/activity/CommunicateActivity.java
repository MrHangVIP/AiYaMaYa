package zsx.com.aiyamaya.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.adapter.CommunicateRecyclerViewAdapter;
import zsx.com.aiyamaya.adapter.IndexPagerAdapter;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ArticleItem;
import zsx.com.aiyamaya.item.PostBarItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.ProgressDialogUtil;
import zsx.com.aiyamaya.util.SpfUtil;

import static android.R.attr.width;

/**
 * Created by moram on 2016/9/21
 */
public class CommunicateActivity extends BaseActivity {
    private static final String TAG = "CommunicateActivity";

    private ViewPager viewPager;

    private ArrayList<Integer> imageList;

    private RecyclerView communicateRcyView;

    private List<PostBarItem> postBarList = new ArrayList<>();

    private Timer timer;

    private List<View> mPicViews = new ArrayList<>();

    private int index=0;

    private Handler timerHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int count = msg.what;
            int currentItem = viewPager.getCurrentItem();
            try {
                viewPager.setCurrentItem((currentItem + 1) % count, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    @Override
    protected void setView() {
        setContentView(R.layout.activity_communicate);
    }

    @Override
    protected void findViews() {
//        setTitle("分享交流");
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        communicateRcyView = (RecyclerView) findViewById(R.id.ac_ry_post);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("分享交流");
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.titleColor));
    }

    @Override
    protected void initData() {
        imageList = new ArrayList<>();
        imageList.add(R.drawable.img_wel1);
//        imageList.add(R.drawable.img_wel2);
//        imageList.add(R.drawable.img_wel3);
        communicateRcyView.setLayoutManager(new LinearLayoutManager(communicateRcyView.getContext()));
        communicateRcyView.setAdapter(new CommunicateRecyclerViewAdapter(this, postBarList));

        if (toolbar != null) {
            toolbar.setNavigationIcon(setBackIcon());
            toolbar.setNavigationOnClickListener(setBackClick());
        }
        getPostBar();
        setLiveAdapter();
    }

    @Override
    protected void setListener() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_close:
//                finish();
//                break;
        }
    }

    private void setLiveAdapter() {
        mPicViews.clear();
        for (int i = 0, l = imageList.size(); i < l; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.viewpager_item_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            TextView cursorPoint = (TextView) view.findViewById(R.id.cursor);
            loadingImgWithCommon(this, imageList.get(i), R.mipmap.ic_launcher, imageView, (int) width,
                    (int) (width * .486));
            mPicViews.add(view);
            cursorPoint.setTag(imageList.get(i));
            imageView.setTag(imageList.get(i));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        viewPager.setAdapter(new IndexPagerAdapter(mPicViews));
        viewPager.setCurrentItem(index);
        scrollPoint(index);
        change(imageList.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                index = arg0;
                scrollPoint(arg0);
                change(imageList.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    private void change(final int listSize) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                timerHandler.sendEmptyMessage(listSize);

            }
        }, 3000, 5000);
    }

    private void scrollPoint(int new_position) {
        // 设置首页的底部游标
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mPicViews.size(); i++) {
            builder.append("<font color='" + (new_position == i ? "#2f8dd4" : "#ffffff") + "'>● </font>");
        }
        if (mPicViews.size() > 1) {
            ((TextView) mPicViews.get(new_position).findViewById(R.id.cursor)).setText(Html.fromHtml(builder.toString()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_communicate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_post:
                goToNext(WritePostActivity.class);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void checkin(View view) {
        goToNext(WritePostActivity.class);
    }


    private void getPostBar() {
        ProgressDialogUtil.showProgressDialog(this, true);
        OkHttpHelp<ResultItem> okHttpHelp = OkHttpHelp.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("userPhone", SpfUtil.getString(Constant.LOGIN_USERPHONE, ""));
        okHttpHelp.httpRequest("", Constant.GET_POSTBAR, map, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                ProgressDialogUtil.dismissProgressdialog();
                if ("fail".equals(object.getResult())) {
                    toast("网络错误，请重试！");
                    return;
                }
                JSONArray jsonArray = null;
                postBarList.clear();
                try {
                    jsonArray = new JSONArray(object.getData());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        PostBarItem postBarItem = new Gson().fromJson(jsonArray.get(i).toString(), PostBarItem.class);
                        postBarList.add(postBarItem);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    communicateRcyView.post(new Runnable() {
                        @Override
                        public void run() {
                            communicateRcyView.setAdapter(new CommunicateRecyclerViewAdapter(mContext, postBarList));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String message) {
                ProgressDialogUtil.dismissProgressdialog();
                toast("网络错误，请重试！");
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });


    }

    private static void loadingImgWithCommon(final Context mContext,
                                             int  url, final int placeholder, final ImageView img, int width,
                                             int height) {
        if (null == img) {
            return;
        }
        Glide.clear(img);
        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e(TAG, "onResourceReady: ");
                        img.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        Log.e(TAG, "onLoadFailed: ");
                        super.onLoadFailed(e, errorDrawable);
                        img.setImageDrawable(errorDrawable);
                    }
                });
    }

}

