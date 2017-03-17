package zsx.com.aiyamaya.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.item.EmojiItem;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.SpfUtil;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BaseActivity";

    protected Context mContext;

    private Toast toast;

    /**
     * 管理activity栈空间
     */
    private LinkedList<BaseActivity> activityList = new LinkedList<BaseActivity>();

    protected Toolbar toolbar;

    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        activityList.add(this);

        setView();

        setToolbar();

        findViews();

        initData();

        setListener();

    }

    public void cancelAction(View v){
//        dismiss();
//        cancel();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        if (toolbar != null && toolbarTitle != null) {
            toolbar.setNavigationIcon(setBackIcon());
            toolbar.setNavigationOnClickListener(setBackClick());
        }
    }

    /**
     *
     * @param Res 资源id
     */
    protected void setRight(int Res){
        toolbar.setRight(Res);
    }

    /**
     * 设置返回按钮图标
     *
     * @return 按钮图标
     */
    public Drawable setBackIcon() {
        return getDrawableRes(R.drawable.back_toolbar);
    }

    /**
     * 设置返回按钮监听
     *
     * @return 按钮监听
     */
    public View.OnClickListener setBackClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }


    protected Drawable getDrawableRes(@DrawableRes int resId) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getDrawable(this, resId);
        } else {
            return getResources().getDrawable(resId);
        }
    }

    public void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(toast==null){
                    toast=Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                }else{
                    toast.setText(msg);
                }
                toast.show();
            }
        });
    }

    public void clearAll() {
        activityList.clear();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }


    /**
     * 页面切换，含参数
     *
     * @param mClass
     * @param bundle
     */
    public void jumpToNext(Class<?> mClass, Bundle bundle) {
        jumpToNext(mClass, bundle, 0);
    }

    /**
     * 页面切换，含参数
     *
     * @param mClass
     * @param bundle
     */
    protected void jumpToNext(Class<?> mClass, Bundle bundle, @AnimRes int resId) {
        Intent intent = new Intent(this, mClass);
        if (bundle != null)
            intent.putExtra("bundle", bundle);
        startActivity(intent);
        overridePendingTransition(resId, 0);
    }

    /**
     * 页面切换，不含参数
     *
     * @param mClass
     */
    public void jumpToNext(Class<?> mClass) {
        jumpToNext(mClass, null);
    }

    /**
     * 页面切换，不含参数
     *
     * @param mClass
     */
    protected void jumpToNext(Class<?> mClass, @AnimRes int resId) {
        jumpToNext(mClass, null, resId);
    }

    /**
     * 页面切换，含参数，并且将当前页面关闭
     *
     * @param mClass
     * @param bundle
     */
    protected void goToNext(Class<?> mClass, Bundle bundle) {
        jumpToNext(mClass, bundle);
        finish();
    }

    /**
     * 页面切换，含参数，并且将当前页面关闭
     *
     * @param mClass
     * @param bundle
     */
    protected void goToNext(Class<?> mClass, Bundle bundle, @AnimRes int resId) {
        jumpToNext(mClass, bundle, resId);
        finish();
    }

    /**
     * 页面切换，不含参数，并且将原页面关闭
     *
     * @param mClass
     */
    public void goToNext(Class<?> mClass) {
        goToNext(mClass, null, 0);
    }

    /**
     * 页面切换，不含参数，并且将原页面关闭
     *
     * @param mClass
     */
    public void goToNext(Class<?> mClass, @AnimRes int resId) {
        goToNext(mClass, null, resId);
    }

    /**
     * token失效
     */
    public void tokenError(){
        BaseApplication.getAPPInstance().setmUser(null);
        SpfUtil.clearAll();
        goToNext(LoginActivity.class);
    }

    public SpannableString setEmoji(String text) {
        SpannableString spannableString = new SpannableString(text);
        boolean lookend = false;
        int end = 0;
        for (int i = text.length()-1; i>=0; i--) {
            MyUtil.MyLogE(TAG,String.valueOf(text.charAt(i)));
            if ("]".equals(String.valueOf(text.charAt(i)))) {
                end = i;
                lookend = true;
            }
            if (lookend && "[".equals(String.valueOf(text.charAt(i)))) {
                int start = i;
                String value = text;
                for (EmojiItem emojiItem : BaseApplication.getEmojiItemList()) {
                    String str=value.substring(start, end+1);
                    MyUtil.MyLogE(TAG,str);
                    if (str.equals(emojiItem.getValue())) {
                        Drawable d = getDrawableRes(emojiItem.getResId());
                        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                        spannableString.setSpan(span, start, end+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                lookend = false;
            }
        }
        return spannableString;
    }

    @Override
    public void onClick(View v) {

    }

    protected abstract void setView();

    protected abstract void findViews();

    protected abstract void initData();

    protected abstract void setListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyUtil.MyLogE(TAG,"onDestroy");
        activityList.remove(this);
    }
}
