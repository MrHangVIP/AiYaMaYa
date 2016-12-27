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

import java.util.Timer;
import java.util.TimerTask;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.adapter.ArticleRecyclerViewAdapter;
import zsx.com.aiyamaya.ui.activity.BaseActivity;
import zsx.com.aiyamaya.util.ProgressDialogUtil;

public class ArticleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "ArticleFragment";

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private static final int refresh = 0x100;

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
        ProgressDialogUtil.showProgressDialog(getActivity(), false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ProgressDialogUtil.dismissProgressdialog();
            }
        }, 2000);
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fa_rv_recycleview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fa_sr_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new ArticleRecyclerViewAdapter(getActivity()));
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(refresh, 3000);
    }
}
