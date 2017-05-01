package zsx.com.aiyamaya.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import zsx.com.aiyamaya.R;


/**
 * Created by moram on 2016/9/21.
 * fragment 基类
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    public static final String Str_bundle = "bundle";
    public static final int request_code = 0x500;
    protected Context context;
    protected Toolbar toolbar;
    protected TextView toolbarText;
    public enum ActivityTheme {
        Light,
        Dark
    }

    ActivityTheme theme = ActivityTheme.Light;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayout(inflater, container);
        theme = setActivityTheme();
        setThemes();
        setToolbar(view);
        initView(view);
        initData();
        initEvent();
        if (view != null)
            return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public ActivityTheme setActivityTheme() {
        return ActivityTheme.Light;
    }

    private void setThemes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity() != null) {
            View decor = getActivity().getWindow().getDecorView();
            if (this.theme == ActivityTheme.Light) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getActivity().getWindow().setStatusBarColor(getColorRes(android.R.color.transparent));
            } else {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getActivity().getWindow().setStatusBarColor(getColorRes(android.R.color.black));
            }
        }
    }


    void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbarText = (TextView) view.findViewById(R.id.toolbar_title);
        if (toolbar != null && toolbarText != null) {
            toolbarText.setText(setTitle());
        }
    }

    protected String setTitle() {
        return null;
    }

    protected abstract View getLayout(LayoutInflater inflater, ViewGroup container);

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract void initEvent();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    protected void toast(String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

    protected String getStringRes(@StringRes int resId) {
        return getResources().getString(resId);
    }

    protected int getIntegerRes(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    protected int getColorRes(@ColorRes int resId) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, resId);
        } else {
            return getResources().getColor(resId);
        }
    }



    /**
     * 页面切换，含参数
     *
     * @param mClass
     * @param bundle
     */
    protected void jumpToNext(Class<?> mClass, Bundle bundle) {
        jumpToNext(mClass, bundle, 0);
    }

    /**
     * 页面切换，含参数
     *
     * @param mClass
     * @param bundle
     */
    protected void jumpToNext(Class<?> mClass, Bundle bundle, @AnimRes int resId) {
        Intent intent = new Intent(getActivity(), mClass);
        if (bundle != null)
            intent.putExtra(Str_bundle, bundle);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(resId, 0);
    }

    /**
     * 页面切换，不含参数
     *
     * @param mClass
     */
    protected void jumpToNext(Class<?> mClass) {
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
    protected void jumpToNextForResult(Class<?> mClass, Bundle bundle){
        jumpToNextForResult(mClass, bundle, 0);
    }
    protected void jumpToNextForResult(Class<?> mClass, @AnimRes int resId){
        jumpToNextForResult(mClass, null, resId);
    }
    protected void jumpToNextForResult(Class<?> mClass){
        jumpToNextForResult(mClass, null, 0);
    }
    protected void jumpToNextForResult(Class<?> mClass, Bundle bundle, @AnimRes int resId){
        Intent intent = new Intent(getActivity(), mClass);
        if (bundle != null)
            intent.putExtra(Str_bundle, bundle);
        getActivity().startActivityForResult(intent, request_code);
        getActivity().overridePendingTransition(resId, 0);
    }


}
