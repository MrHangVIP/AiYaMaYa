package zsx.com.aiyamaya.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import zsx.com.aiyamaya.adapter.ArticleRecyclerViewAdapter;
import zsx.com.aiyamaya.adapter.HomeArticleAdapter;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ArticleItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.ui.activity.BaseActivity;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.ProgressDialogUtil;

public class ArticleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "ArticleFragment";

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<ArticleItem> articleList=new ArrayList<>();

    private static final int refresh = 0x100;

    private int type;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case refresh:
                    swipeRefreshLayout.setRefreshing(false);
                    if(getActivity()!=null){
                    ((BaseActivity)getActivity()).toast("刷新成功！");
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fa_rv_recycleview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fa_sr_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        getData();
        return view;
    }

    public ArticleFragment setData(int type){
        this.type=type;
        return this;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    }

    @Override
    public void onRefresh() {
        getData();
    }


    private void getData(){
        articleList.clear();
        ProgressDialogUtil.showProgressDialog(getActivity(),true);
        Map<String,String> map=new HashMap<>();
        map.put("type",type+"");
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
                    mRecyclerView.setAdapter(new HomeArticleAdapter(getActivity(),articleList,""));
                    if(getActivity()!=null && swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
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
}
