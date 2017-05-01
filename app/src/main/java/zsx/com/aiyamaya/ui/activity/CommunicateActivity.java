package zsx.com.aiyamaya.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.ui.fragment.PostBarFragment;

/**
 * Created by moram on 2016/9/21
 */
public class CommunicateActivity extends BaseActivity {
    private static final String TAG = "CommunicateActivity";

    private ViewPager mViewPager;
    private List<Fragment> fragments=new ArrayList<>();
    private View mainTitle;
    private PostBarFragment postbarFragment;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_communicate);
    }

    @Override
    protected void findViews() {
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("分享交流");
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.titleColor));
        mViewPager = (ViewPager) findViewById(R.id.ac_vp_viewpager);
        mainTitle=(View)findViewById(R.id.main_content);

    }

    @Override
    protected void initData() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(setBackIcon());
            toolbar.setNavigationOnClickListener(setBackClick());
        }
        postbarFragment=new PostBarFragment();
        fragments.add(postbarFragment);
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void setListener() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                jumpToNext(WritePostActivity.class);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void checkin(View view) {
        jumpToNext(WritePostActivity.class);
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

    class FragmentAdapter extends FragmentPagerAdapter{


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int state=event.getAction();
        if(state==MotionEvent.ACTION_MOVE){
            Log.e(TAG, "view.getY(): " + mainTitle.getY());
            Log.e(TAG, "view.getY(): " + mainTitle.getHeight());
            if(mainTitle.getY()<0){
                postbarFragment.mainLL.requestDisallowInterceptTouchEvent(false);
            }else{
                postbarFragment.mainLL.requestDisallowInterceptTouchEvent(true);
            }
        }
        return super.onTouchEvent(event);
    }

}

