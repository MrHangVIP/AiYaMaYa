package zsx.com.aiyamaya.ui.activity;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ArticleItem;
import zsx.com.aiyamaya.item.PostBarItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.ProgressDialogUtil;
import zsx.com.aiyamaya.util.SpfUtil;

/**
 * Created by moram on 2016/9/21
 */
public class CommunicateActivity extends BaseActivity {
    private static final String TAG = "CommunicateActivity";

    private Banner banner;

    private ArrayList<Integer> imageList;

    private RecyclerView communicateRcyView;

    private Toolbar toolbar;

    private List<PostBarItem> postBarList = new ArrayList<>();

    @Override
    protected void setView() {
        setContentView(R.layout.activity_communicate);
    }

    @Override
    protected void findViews() {
//        setTitle("分享交流");
        banner = (Banner) this.findViewById(R.id.ac_bar);
        communicateRcyView = (RecyclerView) findViewById(R.id.ac_ry_post);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("分享交流");
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.titleColor));
    }

    @Override
    protected void initData() {
        imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.img_wel1);
        imageList.add(R.drawable.img_wel2);
        imageList.add(R.drawable.img_wel3);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new CommunicateActivity.GlideImageLoader());
        //设置图片集合
        banner.setImages(imageList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //是否允许手动滑动轮播图，默认为true
        banner.setViewPagerIsScroll(true);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        communicateRcyView.setLayoutManager(new LinearLayoutManager(communicateRcyView.getContext()));
        communicateRcyView.setAdapter(new CommunicateRecyclerViewAdapter(this,postBarList));

        if (toolbar != null) {
            toolbar.setNavigationIcon(setBackIcon());
            toolbar.setNavigationOnClickListener(setBackClick());
        }
        getPostBar();
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

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
//            eg：
//            Glide 加载图片简单用法
            Glide.with(context).load((int) path).into(imageView);
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
        banner.startAutoPlay();
        super.onResume();
    }

    @Override
    protected void onPause() {
        banner.stopAutoPlay();
        super.onPause();
    }

    public void checkin(View view) {
        goToNext(WritePostActivity.class);
    }


    private void getPostBar() {
        ProgressDialogUtil.showProgressDialog(this, true);
        OkHttpHelp<ResultItem> okHttpHelp = OkHttpHelp.getInstance();
        Map<String,String> map=new HashMap<>();
        map.put("userPhone", SpfUtil.getString(Constant.LOGIN_USERPHONE,""));
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

}

