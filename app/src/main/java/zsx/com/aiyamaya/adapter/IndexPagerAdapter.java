package zsx.com.aiyamaya.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @discription 图片的Adapter
 * @autor songzhihang
 * @time 2017/3/14  下午4:44
 **/
public class IndexPagerAdapter extends PagerAdapter {

    private static final String TAG = "IndexPagerAdapter";

    private List<View> mPicViews;

    public IndexPagerAdapter(List<View> mPicViews){
        this.mPicViews=mPicViews;
    }

    @Override
    public int getCount() {
        return mPicViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position < mPicViews.size()) {
            ((ViewPager) container).removeView(mPicViews.get(position));
        } else {
            ((ViewPager) container).removeView((View) object);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mPicViews.get(position);
        if (null != view.getParent()) {
            Log.e("debug", "v.getParent():" + view.getParent().getClass().getSimpleName());
        } else {
            ((ViewPager) container).addView(view, 0);
        }

        return view;
    }

}
