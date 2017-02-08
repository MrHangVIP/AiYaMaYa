package zsx.com.aiyamaya.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.adapter.ArticleViewPagerAdapter;
import zsx.com.aiyamaya.ui.fragment.ArticleFragment;

public class ArticleActivity extends BaseActivity {
    private static final String TAG = "ArticleActivity";

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private FloatingActionButton fab;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_article);
    }

    @Override
    protected void findViews() {
        mTabLayout = (TabLayout) findViewById(R.id.ac_tl_tab);
        mViewPager = (ViewPager) findViewById(R.id.ac_vp_viewpager);
        fab = (FloatingActionButton) findViewById(R.id.ac_fab);
    }

    @Override
    protected void initData(){
//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("经典文章");
        setupViewPager();
    }

    @Override
    protected void setListener(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:
                break;
        }
    }

    private void setupViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("备孕");
        titles.add("怀孕");
        titles.add("分娩");
        titles.add("宝宝");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ArticleFragment().setData(0));
        fragments.add(new ArticleFragment().setData(1));
        fragments.add(new ArticleFragment().setData(2));
        fragments.add(new ArticleFragment().setData(3));
        ArticleViewPagerAdapter adapter =
                new ArticleViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
