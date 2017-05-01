package zsx.com.aiyamaya.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.item.ArticleItem;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.SpfUtil;

public class NewsDetailActy extends BaseActivity {

    private TextView timeTV;
    private TextView contentTV;
    private ArticleItem newsItem;

    private TextView titleTV;
    private TextView readnumTV;
    private ImageView imageIV;

    @Override
    protected void setView() {
        setContentView(R.layout.acty_news_details);
    }

    @Override
    protected void findViews() {
        imageIV = (ImageView) findViewById(R.id.and_iv_image);
        titleTV = (TextView) findViewById(R.id.and_tv_title);
        contentTV = (TextView) findViewById(R.id.and_tv_content);
        timeTV = (TextView) findViewById(R.id.and_tv_date);
        readnumTV = (TextView) findViewById(R.id.and_tv_read);
    }

    @Override
    protected void initData() {
        setTitle("文章详情");
        Intent intent = getIntent();
        newsItem = (ArticleItem) (intent.getBundleExtra("bundle").getSerializable("newsdetail"));
        boolean isRead= SpfUtil.getBoolean(newsItem.getId()+"",false);
        if(isRead){
            readnumTV.setText("阅读(" + (newsItem.getReadnum()) + ")");
        }else{
            readnumTV.setText("阅读(" + (newsItem.getReadnum() + 1) + ")");
            SpfUtil.saveBoolean(newsItem.getId()+"",true);
        }

        contentTV.setText(newsItem.getContent());
        titleTV.setText(newsItem.getTitle());
        Glide.with(this)
                .load(Constant.DEFAULT_URL + Constant.IMAGE_URL + newsItem.getImageUrl())
                .placeholder(R.drawable.img_wel1)
                .into(imageIV);

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }


}
