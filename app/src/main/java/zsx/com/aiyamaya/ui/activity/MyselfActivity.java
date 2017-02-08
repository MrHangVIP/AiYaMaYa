package zsx.com.aiyamaya.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.ArticleItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.item.UserItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.model.UploadImageModel;
import zsx.com.aiyamaya.ui.dialog.EditDialog;
import zsx.com.aiyamaya.ui.widget.CircleImageView;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.FileUtils;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.ProgressDialogUtil;
import zsx.com.aiyamaya.util.SelectPhotoTools;
import zsx.com.aiyamaya.util.SpfUtil;

public class MyselfActivity extends BaseActivity {

    private static final String TAG = "MyselfActivity";
    private CircleImageView headImgIV;
    private TextView stateTV;
    private TextView nickNameTV;
    private TextView cityTV;
    private TextView birthTV;
    private Uri photoUri;
    private String headUrl;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtil.dismissProgressdialog();
            if(msg.what==Constant.IMAGE_UPLOAD_OK){
                ResultItem object=(ResultItem) msg.obj;
                if (object.getResult().equals("token error")) {
                        toast("登录超时，请重新登录！");
                        tokenError();
                        finish();
                } else {
                    toast("修改成功");
                    try {
                        JSONArray jsonArray=new JSONArray(object.getData());
                        for(int i=0;i<jsonArray.length();i++){
                            headUrl=  new Gson().fromJson(jsonArray.get(i).toString(), String.class);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        MyUtil.MyLogE(TAG,e.toString());
                    }
                    BaseApplication.getAPPInstance().getmUser().setHeadUrl(headUrl);
                    updateHeadUrl();
                }
            }
            if(msg.what==Constant.IMAGE_UPLOAD_FAIL){
                toast("网络异常");
            }

        }
    };

    @Override
    protected void setView() {
        setContentView(R.layout.activity_myself);
    }

    @Override
    protected void findViews() {
        setTitle("个人中心");
        headImgIV = (CircleImageView) findViewById(R.id.am_ci_headimg);
        stateTV = (TextView) findViewById(R.id.lmi_tv_state);
        nickNameTV = (TextView) findViewById(R.id.lmi_tv_nickname);
        cityTV = (TextView) findViewById(R.id.lmi_tv_city);
        birthTV = (TextView) findViewById(R.id.lmi_tv_birth);
    }

    @Override
    protected void initData() {
        UserItem user = BaseApplication.getAPPInstance().getmUser();
        if (user != null) {
            stateTV.setText(user.getStatus());
            nickNameTV.setText(user.getNickName());
            if(!"".equals(user.getHeadUrl())){
                Glide.with(this)
                        .load(Constant.DEFAULT_URL+Constant.IMAGE_URL+user.getHeadUrl())
                        .placeholder(R.drawable.img_loading_2)
                        .into(headImgIV);
            }
        }
    }

    @Override
    protected void setListener() {
        headImgIV.setOnClickListener(this);
        ((LinearLayout) stateTV.getParent()).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtil.MyLogE(TAG, "click");
                        dialogShow(stateTV.getText().toString(), "输入状态", stateTV);
                    }
                });

        ((LinearLayout) nickNameTV.getParent()).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtil.MyLogE(TAG, "click");
                        dialogShow(nickNameTV.getText().toString(), "输入昵称", nickNameTV);
                    }
                });

        ((LinearLayout) cityTV.getParent()).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtil.MyLogE(TAG, "click");
                        dialogShow(cityTV.getText().toString(), "输入城市", cityTV);
                    }
                });

        ((LinearLayout) birthTV.getParent()).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtil.MyLogE(TAG, "click");
                        dialogShow(birthTV.getText().toString(), "输入生日", birthTV);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.am_ci_headimg:
                changePhoto();
                break;
        }

    }

    private void dialogShow(String content, String title, TextView view) {
        new MyEditDialog(MyselfActivity.this, content, view).setTitle(title).show();
    }


    /**
     * 点击头像按钮
     */
    private void changePhoto() {
        if (!FileUtils.isSdCardExist()) {
            toast("没有找到SD卡，请检查SD卡是否存在");
            return;
        }
        try {
            photoUri = FileUtils.getUriByFileDirAndFileName(Constant.SystemPicture.SAVE_DIRECTORY, Constant.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            toast("创建文件失败");
            return;
        }
        SelectPhotoTools.openDialog(this, photoUri);
    }

    private class MyEditDialog extends EditDialog {

        TextView view;

        public MyEditDialog(Context context, String content, TextView view) {
            super(context, content);
            this.view = view;
        }

        @Override
        protected void confirmListener() {
            view.setText(editContent);
            UserItem user = BaseApplication.getAPPInstance().getmUser();

            switch (view.getId()) {
                case R.id.lmi_tv_state:
                    user.setStatus(editContent);
                    break;

                case R.id.lmi_tv_nickname:
                    user.setNickName(editContent);
                    break;

                case R.id.lmi_tv_city:
                    user.setCity(editContent);
                    break;

                case R.id.lmi_tv_birth:
                    user.setBirthday(editContent);
                    break;
            }
            updateModel(user);
        }
    }

    private void updateModel(UserItem userItem) {
        ProgressDialogUtil.showProgressDialog(MyselfActivity.this, true);
        Map<String, String> params = new HashMap<>();
        params.put("userPhone", userItem.getUserPhone());
        params.put("status", userItem.getStatus());
        params.put("nickName", userItem.getNickName());
        params.put("city", userItem.getCity());
        params.put("birthday", userItem.getBirthday());
        params.put("token", SpfUtil.getString(Constant.TOKEN, ""));
        OkHttpHelp<ResultItem> okHttpHelp = OkHttpHelp.getInstance();
        okHttpHelp.httpRequest("", Constant.UPDATE_USER_URL, params, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                ProgressDialogUtil.dismissProgressdialog();
                if (object.getResult().equals("fail")) {
                    if (object.getData().equals("token error")) {
                        toast("token失效,请重新登录");
                        tokenError();
                    } else {
                        toast("更新失败");
                    }
                } else {
                    toast("修改成功");
                }
            }

            @Override
            public void onFailed(String message) {
                ProgressDialogUtil.dismissProgressdialog();
                toast("网络错误");
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });

    }

    @Override
    public void onBackPressed() {
        goToNext(SettingActivity.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                SelectPhotoTools.startPhotoZoom(MyselfActivity.this, null, photoUri, 300);
                break;
            case Constant.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data == null)
                    return;
                SelectPhotoTools.startPhotoZoom(MyselfActivity.this, null, data.getData(), 300);
                break;
            case Constant.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data == null)
                    return;
                File file = FileUtils.getFileByUri(MyselfActivity.this, photoUri);
                MyUtil.MyLogE(TAG, file.toString());
                Bitmap bit = data.getExtras().getParcelable("data");
                headImgIV.setImageBitmap(bit);
                try {
                    bit.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    MyUtil.MyLogE(TAG,e.toString());
                }
                //读取本地
//                bit= BitmapFactory.decodeFile(file.getAbsolutePath());
//                toast("设置成功");

                imageUpload(file.getAbsolutePath());
                break;
        }
    }

    private void imageUpload(String path){
        ProgressDialogUtil.showProgressDialog(this,true);
        List<String> pathList=new ArrayList<String>();
        pathList.add(path);
        UploadImageModel uploadImageModel=new UploadImageModel(pathList,mHandler);
        uploadImageModel.imageUpload();
    }

    private void updateHeadUrl(){
        Map<String,String> params=new HashMap<>();
        params.put("token",SpfUtil.getString(Constant.TOKEN,""));
        params.put("userPhone",SpfUtil.getString(Constant.LOGIN_USERPHONE,""));
        params.put("headUrl",headUrl);
        OkHttpHelp<ResultItem> httpHelp=OkHttpHelp.getInstance();
        httpHelp.httpRequest("", Constant.UPDATE_HEAD_URL, params, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                if("fail".equals(object.getResult())){
                    if("token error".equals(object.getData())){
                        toast("token失效,请重新登录");
                        tokenError();
                        finish();
                    }
                }else{
                    MyUtil.MyLogE(TAG,"插入成功！");
                }
            }
            @Override
            public void onFailed(String message) {
                MyUtil.MyLogE(TAG,"连接失败");
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });
    }

}
