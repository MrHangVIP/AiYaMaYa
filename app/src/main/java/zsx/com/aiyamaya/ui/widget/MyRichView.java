package zsx.com.aiyamaya.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import zsx.com.aiyamaya.R;

/**
 * @discription 包含文字与图片的富文本
 * @autor songzhihang
 * @time 2017/3/16  下午4:16
 **/
public class MyRichView extends LinearLayout{
    private static final String TAG = "MyRichView";

    private Context context;
    private int textSize=20;
    private LayoutInflater inflater;
    private LinearLayout allLayout;
    private int defaultPadding=10;


    public MyRichView(Context context) {
        super(context);
        init(context);
    }

    public MyRichView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyRichView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);

        // 1. 初始化allLayout
        allLayout = new LinearLayout(context);
        allLayout.setOrientation(LinearLayout.VERTICAL);
        allLayout.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        addView(allLayout, layoutParams);

    }

    public TextView createTextView(CharSequence text){
        TextView textview=new TextView(context);
        textview.setText(text);
        textview.setTextSize(textSize);
        textview.setPadding(0,defaultPadding,0,defaultPadding);
        allLayout.addView(textview);
        return textview;
    }

    public ImageView createImageView(String url){
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.img_loading_2)
                .into(imageView);
        imageView.setPadding(0,defaultPadding,0,defaultPadding);
        allLayout.addView(imageView);
        allLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        return imageView;
    }


}
