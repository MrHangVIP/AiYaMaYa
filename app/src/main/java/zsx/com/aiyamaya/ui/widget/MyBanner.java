package zsx.com.aiyamaya.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.youth.banner.Banner;

/**
 * @discription
 * @autor songzhihang
 * @time 2017/4/1  上午9:23
 **/
public class MyBanner extends Banner implements ViewPager.OnPageChangeListener {
    private static final String TAG = "MyBanner";
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public MyBanner(Context context) {
        super(context);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener!=null){
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
//        currentItem = viewPager.getCurrentItem();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
        super.setOnPageChangeListener(onPageChangeListener);
    }
}
