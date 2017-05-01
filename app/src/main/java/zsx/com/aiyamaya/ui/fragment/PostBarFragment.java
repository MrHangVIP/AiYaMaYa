package zsx.com.aiyamaya.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.adapter.CommunicateRecyclerViewAdapter;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.PostBarItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.ProgressDialogUtil;
import zsx.com.aiyamaya.util.SpfUtil;

/**
 * Created by Songzhihang on 2017/5/1.
 */
public class PostBarFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "PostBarFragment";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView communicateRcyView;

    private List<PostBarItem> postBarList = new ArrayList<>();
    public LinearLayout mainLL;


    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_postbar_layout,null);
    }

    @Override
    protected void initData() {
        communicateRcyView.setLayoutManager(new LinearLayoutManager(communicateRcyView.getContext()));
        communicateRcyView.setAdapter(new CommunicateRecyclerViewAdapter(getActivity(), postBarList));
    }

    @Override
    protected void initView(View view) {
        mainLL=(LinearLayout)view.findViewById(R.id.fpl_ll_main);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fpl_sr_swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        communicateRcyView = (RecyclerView) view.findViewById(R.id.fpl_ry_post);
    }

    @Override
    protected void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getPostBar();
    }

    @Override
    public void onRefresh() {
        getPostBar();
    }

    private void getPostBar() {
        ProgressDialogUtil.showProgressDialog(getActivity(), true);
        OkHttpHelp<ResultItem> okHttpHelp = OkHttpHelp.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("userPhone", SpfUtil.getString(Constant.LOGIN_USERPHONE, ""));
        okHttpHelp.httpRequest("", Constant.GET_POSTBAR, map, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                swipeRefreshLayout.setRefreshing(false);
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
                   communicateRcyView.setAdapter(new CommunicateRecyclerViewAdapter(getActivity(), postBarList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String message) {
                ProgressDialogUtil.dismissProgressdialog();
                swipeRefreshLayout.setRefreshing(false);
                toast("网络错误，请重试！");
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });
    }
}
