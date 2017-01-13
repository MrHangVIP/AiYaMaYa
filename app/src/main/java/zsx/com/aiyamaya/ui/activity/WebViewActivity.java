package zsx.com.aiyamaya.ui.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import zsx.com.aiyamaya.R;

public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewActivity";
    private WebView webWB;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_web_view);
    }

    @Override
    protected void findViews() {
        webWB=(WebView)findViewById(R.id.awv_web);
        WebSettings webSet=webWB.getSettings();
        webSet.setJavaScriptEnabled(false);
    }

    @Override
    protected void initData() {
        Bundle bundle=getIntent().getBundleExtra("bundle");
        String url =bundle.getString("newsUrl");
        setTitle("文章详情");
        webWB.loadUrl(url);
    }

    @Override
    protected void setListener() {

    }
}
