package zsx.com.aiyamaya.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zsx.com.aiyamaya.adapter.HomeArticleAdapter;
import zsx.com.aiyamaya.item.NewsDataItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;

/**
 * Created by moram on 2016/12/30.
 */

public class OkHttpNewsHelp<T> {

    private static final String TAG = "OkHttpHelp";

    private static OkHttpNewsHelp mInstance;
    private OkHttpClient mOkHttpClient;
    private Gson mGson;

    private OkHttpNewsHelp() {
        mOkHttpClient = new OkHttpClient();
        //cookie enabled
//        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mGson = new Gson();
    }

    public static OkHttpNewsHelp getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpNewsHelp.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpNewsHelp();
                }
            }
        }
        return mInstance;
    }


    public void httpRequest(String method, String url, Map<String, String> params, final ResponseListener<T> listener) {
        if ("".equals(url)) {
            url = "http://v.juhe.cn/toutiao/index?type=top&key=29f7ddf45497b0382427bf8759b9f1a1";
        } else {
            if (url == null) {
                url = Constant.DEFAULT_URL;
            } else {
                url = Constant.DEFAULT_URL + url;
            }
        }

        RequestBody formBody = null;
        if (params != null) {
            FormBody.Builder builder = new FormBody.Builder();
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                builder.add(entry.getKey(), entry.getValue());
            }
            formBody = builder.build();
        }

        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .build();

        MyUtil.runInBackground(new Runnable() {
            @Override
            public void run() {
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        listener.onFailed(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            listener.onFailed(response.toString());
                            throw new IOException("Unexpected code " + response);
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            MyUtil.MyLogI(TAG, jsonObject.toString());
                            if (jsonObject.getJSONObject("result") != null) {
                                JSONObject dataJO = jsonObject.getJSONObject("result");
                                if (dataJO != null && dataJO.getJSONArray("data") != null) {
                                    try {
                                        JSONArray jsonArray = dataJO.getJSONArray("data");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            Class<T> entityClass = listener.getEntityClass();
                                            final T t = mGson.fromJson(jsonArray.get(i).toString(), entityClass);
                                            MyUtil.runOnUI(new Runnable() {
                                                @Override
                                                public void run() {
                                                    listener.onSuccess(t);
                                                }
                                            });
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        MyUtil.MyLogE(TAG, e.toString());
                                    }
                                }
                            }

                        } catch (final JSONException e) {
                            e.printStackTrace();
                            listener.onFailed(e.toString());
                        }
                    }
                });
            }
        });


    }

}
