package zsx.com.aiyamaya.ui.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.SpfUtil;

public class GuideActivity extends BaseActivity {
    private static final String TAG = "GuideActivity";

    private Banner banner;

    private ArrayList<Integer> imageList;

    private ImageView startIV;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_guide);
    }

    @Override
    protected void findViews() {
        startIV = (ImageView) findViewById(R.id._ag_iv_start);
        banner = (Banner) this.findViewById(R.id.ag_bn_banner);
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
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imageList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(false);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //是否允许手动滑动轮播图，默认为true
        banner.setViewPagerIsScroll(true);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    @Override
    protected void setListener() {
        startIV.setOnClickListener(this);

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    startIV.setVisibility(View.VISIBLE);
                } else {
                    startIV.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._ag_iv_start:
                if(SpfUtil.getBoolean(Constant.IS_LOGIN,false)){
                    goToNext(HomeActivity.class);
                }else{
                    goToNext(StateSelectActivity.class);
                }

                break;

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

            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//        @Override
//        public ImageView createImageView(Context context) {
//            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
