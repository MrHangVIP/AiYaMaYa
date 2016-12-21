/******************************************************************************
 * @project kanfangbao2
 * @brief
 * @author fmh19
 * @module com.yipai.realestate.ui.widget
 * @date 2016/5/25
 * @version 0.1
 * @history v0.1, 2016/5/25, by fmh19
 * <p/>
 * Copyright (C) 2016 Yipai.
 ******************************************************************************/
package zsx.com.aiyamaya.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zsx.com.aiyamaya.R;


/**
 * 分享
 */
public class ShareItem extends LinearLayout implements View.OnClickListener {


    public enum JumpTO {
        WEIXIN,
        WE_FREND,
        QQ_ZONE,
        QQ,
        WEIBO,
    }

    JumpTO mType = JumpTO.WEIXIN;

    LayoutInflater mInflater;
    ImageView imageView;
    TextView textView;

    OnShareClickListener mListener;

    public ShareItem(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public ShareItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public ShareItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        mInflater.inflate(R.layout.view_item_share, this, true);
        imageView = (ImageView) findViewById(R.id.iv_item_share);
        textView = (TextView) findViewById(R.id.tv_item_share);
        setOnClickListener(this);
    }

    public void setType(JumpTO type) {
        mType = type;
        switch (type) {
            case WEIXIN:
                imageView.setImageResource(R.drawable.icon_share_weixin);
                textView.setText(R.string.WEIXIN);
                break;
            case WE_FREND:
                imageView.setImageResource(R.drawable.icon_share_frend);
                textView.setText(R.string.WE_FREND);
                break;
            case QQ_ZONE:
                imageView.setImageResource(R.drawable.icon_share_zone);
                textView.setText(R.string.QQ_ZONE);
                break;
            case QQ:
                imageView.setImageResource(R.drawable.icon_share_qq);
                textView.setText(R.string.QQ);
                break;
            case WEIBO:
                imageView.setImageResource(R.drawable.icon_share_weibo);
                textView.setText(R.string.WEIBO);
                break;
        }
    }

    public void setOnShareListener(OnShareClickListener listener) {
        mListener = listener;
    }

    public interface OnShareClickListener {
        void onShareClick(View view, JumpTO type);
    }

    @Override
    public void onClick(View view) {
        mListener.onShareClick(view, mType);
    }

}
