package zsx.com.aiyamaya.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.adapter.HomeArticleAdapter;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ArticleItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.ProgressDialogUtil;

/**
 * Created by moram on 2016/9/22.
 */
public class PostDetailActivity extends BaseActivity {

    private static final String TAG = "PostDetailActivity";
    private RecyclerView mRecyclerView;

    private List<ArticleItem> articleList=new ArrayList<>();

    @Override
    protected void setView() {
        setContentView(R.layout.activity_post_detail);
        ProgressDialogUtil.showProgressDialog(this,false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ProgressDialogUtil.dismissProgressdialog();
            }
        },2000);
    }

    @Override
    protected void findViews() {
        setTitle("帖子详情");
        mRecyclerView = (RecyclerView)findViewById(R.id.apd_cycle);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        getData();
    }

    @Override
    protected void setListener() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    private void getData(){
        articleList.clear();
        ProgressDialogUtil.showProgressDialog(this,true);
        Map<String,String> map=new HashMap<>();
        map.put("type",0+"");
        OkHttpHelp<ResultItem> httpHelp=OkHttpHelp.getInstance();
        httpHelp.httpRequest("", Constant.GET_TYPE_ARTICLE, map, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                ProgressDialogUtil.dismissProgressdialog();
                if(object.getResult().equals("success")){
                    try {
                        JSONArray jsonArray=new JSONArray(object.getData());
                        for(int i=0;i<jsonArray.length();i++){
                            ArticleItem articleItem=  new Gson().fromJson(jsonArray.get(i).toString(), ArticleItem.class);
                            articleList.add(articleItem);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        MyUtil.MyLogE(TAG,e.toString());
                    }
                    mRecyclerView.setAdapter(new HomeArticleAdapter(PostDetailActivity.this,articleList,""));
                }
            }

            @Override
            public void onFailed(String message) {
                ProgressDialogUtil.dismissProgressdialog();
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

