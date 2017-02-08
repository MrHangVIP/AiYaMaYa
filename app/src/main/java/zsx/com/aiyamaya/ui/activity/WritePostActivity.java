package zsx.com.aiyamaya.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.api.OkHttpHelp;
import zsx.com.aiyamaya.item.PostBarItem;
import zsx.com.aiyamaya.item.ResultItem;
import zsx.com.aiyamaya.item.UserItem;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.model.UploadImageModel;
import zsx.com.aiyamaya.ui.fragment.EmojiFragment;
import zsx.com.aiyamaya.ui.widget.RichTextEditor;
import zsx.com.aiyamaya.util.Constant;
import zsx.com.aiyamaya.util.MyUtil;
import zsx.com.aiyamaya.util.ProgressDialogUtil;

/**
 *
 */
public class WritePostActivity extends BaseActivity {

    private static final String TAG = "WritePostActivity";
    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1022;
    private RichTextEditor richEdit;
    private ImageView emojiIV;
    private ImageView photoIV;
    private ImageView albumIV;
    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;// 照相机拍照得到的图片
    private EditText titleET;
    private LinearLayout buttomLL;
    private FrameLayout emojiFL;
    private String imageUrl="";
    private PostBarItem postBarItem;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==Constant.IMAGE_UPLOAD_OK){
                ResultItem object=(ResultItem) msg.obj;
                if (object.getData().equals("token error")) {
                    ProgressDialogUtil.dismissProgressdialog();
                    toast("登录超时，请重新登录！");
                    tokenError();
                    finish();
                } else {
                    try {
                        JSONArray jsonArray=new JSONArray(object.getData());
                        for(int i=0;i<jsonArray.length();i++){
                            imageUrl= imageUrl+ new Gson().fromJson(jsonArray.get(i).toString(), String.class)+Constant.MY_SPLIT_STR;
                        }
                        commitPostBar();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        MyUtil.MyLogE(TAG,e.toString());
                    }
                }
            }
            if(msg.what==Constant.IMAGE_UPLOAD_FAIL){
                ProgressDialogUtil.dismissProgressdialog();
                toast("网络异常");
            }

        }
    };

    @Override
    protected void setView() {
        setContentView(R.layout.activity_write_post);
    }

    @Override
    protected void findViews() {
        titleET = (EditText) findViewById(R.id.awp_et_title);
        richEdit = (RichTextEditor) findViewById(R.id.awp_rte_richtext);
        buttomLL = (LinearLayout) findViewById(R.id.awp_ll_buttom);
        emojiIV = (ImageView) findViewById(R.id.lbe_iv_emojy);
        photoIV = (ImageView) findViewById(R.id.lbe_iv_photo);
        albumIV = (ImageView) findViewById(R.id.lbe_iv_album);
        emojiFL = (FrameLayout) findViewById(R.id.lbe_fl_emoji);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lbe_fl_emoji, new EmojiFragment())
                .commit();
    }

    @Override
    protected void initData() {
        setTitle("发帖子");
        setMenu();
        richEdit.setActivity(this);
    }



    @Override
    protected void setListener() {
        emojiIV.setOnClickListener(this);
        photoIV.setOnClickListener(this);
        albumIV.setOnClickListener(this);
        titleET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    buttomLL.setVisibility(View.GONE);
                } else {
                    buttomLL.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    void setMenu(){
        toolbar.inflateMenu(R.menu.menu_submit);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.toolbar_submit) {
                    String title=titleET.getText().toString();
                    if("".equals(title)||title.length()<10){
                        toast("请输入标题，并且紫薯不少于10个");
                        return true;
                    }
                    ProgressDialogUtil.showProgressDialog(WritePostActivity.this,true);
                    postBarItem= dealEditData(richEdit.buildEditData());
                    postBarItem.setTitle(title);
                    String content=postBarItem.getContent();
                    String imagePath=postBarItem.getImageUrl();
                    if("".equals(imagePath)&&"".equals(content)){
                        toast("内容不能为空");
                        ProgressDialogUtil.dismissProgressdialog();
                        return true;
                    }
                    if(!"".equals(imagePath)){
                        String[] images=imagePath.split(",");
                        List<String> imageList=new ArrayList<String>();
                        for(int i=0;i<images.length;i++){
                            imageList.add(images[i]);
                        }
                        imageUpload(imageList);
                    }else{
                        commitPostBar();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    protected PostBarItem dealEditData(List<RichTextEditor.EditData> editList) {
        PostBarItem postBarItem=new PostBarItem();
        String order="";
        String content="";
        String imageUrl="";
        for(RichTextEditor.EditData editData:editList){
            order=order+editData.order+",";
            if(editData.imagePath!=null && !"".equals(editData.imagePath)){
                imageUrl=imageUrl+editData.imagePath+",";
            }
            if(editData.inputStr!=null && !"".equals(editData.inputStr)){
                content=content+editData.inputStr+Constant.MY_SPLIT_STR;
            }
        }
        postBarItem.setContent(content);
        postBarItem.setOrders(order);
        postBarItem.setImageUrl(imageUrl);
        return postBarItem;
    }

    /**
     * 添加图片到富文本剪辑器
     *
     * @param imagePath
     */
    private void insertBitmap(String imagePath) {
        richEdit.insertImage(imagePath);
    }

    /**
     * 根据Uri获取图片文件的绝对路径
     */
    public String getRealFilePath(final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        hideInput(v);
        switch (v.getId()) {
            case R.id.lbe_iv_emojy:
                if (emojiFL.isShown()) {
                    emojiFL.setVisibility(View.GONE);
                } else {
                    emojiFL.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.lbe_iv_album:
                // 打开系统相册
                if (emojiFL.isShown()) {
                    emojiFL.setVisibility(View.GONE);
                }
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

                break;

            case R.id.lbe_iv_photo:
                if (emojiFL.isShown()) {
                    emojiFL.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (emojiFL.isShown()) {
            emojiFL.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            insertBitmap(getRealFilePath(uri));
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            insertBitmap(mCurrentPhotoFile.getAbsolutePath());
        }
    }

    private void hideInput(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void hideEmoji(){
        if (emojiFL.isShown()) {
            emojiFL.setVisibility(View.GONE);
        }
    }

    public void addEmoji(String emojiName, int id) {
        EditText editText = richEdit.getLastFocusEdit();
        String editStr = "";
        editStr = editStr + emojiName;
        SpannableString spannableString = new SpannableString(editStr);
        Drawable d = getDrawableRes(id);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(span, 0, editStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.append(spannableString);
        MyUtil.MyLogE(TAG, editText.getText().toString());
    }
    //1.点击上传首先获取照片并且上传，返回每张图片的url，图片最好富含位置信息。然后打包图片URl，
    //获取文本内容信息，保存在一条中，但是得有顺序，

    /**
     * 上传图片
     * @param path
     */
    private void imageUpload(List<String> path){
        UploadImageModel uploadImageModel=new UploadImageModel(path,mHandler);
        uploadImageModel.imageUpload();
    }

    /**
     * 上传帖子
     */
    private void commitPostBar(){
        OkHttpHelp<ResultItem> okHttpHelp=OkHttpHelp.getInstance();
        Map<String,String> params=new HashMap<>();
        UserItem userItem=BaseApplication.getAPPInstance().getmUser();
        params.put("userPhone",userItem.getUserPhone());
        params.put("title",postBarItem.getTitle());
        params.put("content",postBarItem.getContent());
        params.put("imageUrl",imageUrl);
        params.put("order",postBarItem.getOrders());
        okHttpHelp.httpRequest("", Constant.WRITE_POSTBAR, params, new ResponseListener<ResultItem>() {
            @Override
            public void onSuccess(ResultItem object) {
                ProgressDialogUtil.dismissProgressdialog();
                if("success".equals(object.getResult())){
                    toast("发表成功");
                    goToNext(CommunicateActivity.class);
                }else{
                    toast("发表失败");
                }
            }

            @Override
            public void onFailed(String message) {
                ProgressDialogUtil.dismissProgressdialog();
            }

            @Override
            public Class<ResultItem> getEntityClass() {
                return ResultItem.class;
            }
        });


    }
}
