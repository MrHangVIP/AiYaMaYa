package zsx.com.aiyamaya.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.SpfUtil;

/**
 * Created by moram on 2017/1/6.
 */

public class UploadImageModel {

    private static final String TAG = "UploadImageModel";
    private List<String> path;
    private Handler handler;

    public UploadImageModel(List<String>  path, Handler handler) {
        this.path = path;
        this.handler = handler;
    }

    public void imageUpload() {
        Map<String, String> params = new HashMap<>();
        params.put("userPhone",SpfUtil.getString(Constant.LOGIN_USERPHONE,""));
        params.put("token",SpfUtil.getString(Constant.TOKEN,""));
        String imagePath="";
        int i=0;
        for(String str:path){
            i++;
//          imagePath= MyUtil.fileBase64String(str)+SpfUtil.getString(Constant.TOKEN,"");
            params.put("image"+i, MyUtil.fileBase64String(str));
        }
//        params.put("image", imagePath);
        OkHttpHelp<ResultItem> httpHelp = OkHttpHelp.getInstance();
        httpHelp.httpRequest("", Constant.IMAGE_UPLOAD_URL, params, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                Message msg = Message.obtain();
                msg.what = Constant.IMAGE_UPLOAD_OK;
                msg.obj = object;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailed(String message) {
                handler.sendEmptyMessage(Constant.IMAGE_UPLOAD_FAIL);
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });
    }

    /**
     *上传文件
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     * @param callBack 回调
     * @param <T>
     */
    public <T> void upLoadFile(OkHttpClient okHttpClient,String actionUrl, HashMap<String, Object> paramsMap, final ResponseListener<T> callBack) {
        try {
            //补全请求地址
            String requestUrl = String.format("%s/%s", actionUrl);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), createProgressRequestBody(null, file, new ResponseListener() {
                        @Override
                        public void onSuccess(Object object) {

                        }

                        @Override
                        public void onFailed(String message) {

                        }

                        @Override
                        public Class getEntityClass() {
                            return null;
                        }
                    }));
                }
            }
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().url(requestUrl).post(body).build();
            final Call call = okHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, e.toString());
//                    failedCallBack("上传失败", callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e(TAG, "response ----->" + string);
//                        successCallBack((T) string, callBack);
                    } else {
//                        failedCallBack("上传失败", callBack);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 创建带进度的RequestBody
     * @param contentType MediaType
     * @param file  准备上传的文件
     * @param callBack 回调
     * @param <T>
     * @return
     */
    public <T> RequestBody createProgressRequestBody(final MediaType contentType, final File file, final ResponseListener callBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        Log.e(TAG, "current------>" + current);
//                        progressCallBack(remaining, current, callBack);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
