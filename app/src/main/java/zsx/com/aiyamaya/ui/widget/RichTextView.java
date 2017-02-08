package zsx.com.aiyamaya.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import zsx.com.aiyamaya.R;

/**
 * Created by moram on 2017/1/20.
 */

public class RichTextView extends ScrollView {
    private static final String TAG = "RichTextView";

    private LinearLayout allLayout; // 这个是所有子view的容器，scrollView内部的唯一一个ViewGroup
    private LayoutInflater inflater;

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);

        // 1. 初始化allLayout
        allLayout = new LinearLayout(context);
        allLayout.setOrientation(LinearLayout.VERTICAL);
        allLayout.setBackgroundColor(Color.WHITE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        addView(allLayout, layoutParams);
    }

    public TextView createTextView(String text,int index){
        TextView textView = (TextView) inflater.inflate(R.layout.edit_item,
                null);
        textView.setText(text);

        allLayout.addView(textView,index);

        return textView;
    }

    public ImageView createImageView(Context context,String url,int index){
        ImageView imageView = (ImageView) inflater.inflate(R.layout.edit_imageview,
                null);
        Glide.with(context)
                .load(url)
                .into(imageView);
        allLayout.addView(imageView,index);
        return imageView;
    }
}
