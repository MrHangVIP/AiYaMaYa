package zsx.com.aiyamaya.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.List;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.ui.widget.RichTextEditor;

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

    @Override
    protected void setView() {
        setContentView(R.layout.activity_write_post);
    }

    @Override
    protected void findViews() {
        titleET = (EditText) findViewById(R.id.awp_et_title);
        richEdit = (RichTextEditor) findViewById(R.id.awp_rte_richtext);
        buttomLL=(LinearLayout)findViewById(R.id.awp_ll_buttom);
        emojiIV = (ImageView) findViewById(R.id.lbe_iv_emojy);
        photoIV = (ImageView) findViewById(R.id.lbe_iv_photo);
        albumIV = (ImageView) findViewById(R.id.lbe_iv_album);

    }

    @Override
    protected void initData() {
        setTitle("发帖子");
    }

    @Override
    protected void setListener() {
        emojiIV.setOnClickListener(this);
        photoIV.setOnClickListener(this);
        albumIV.setOnClickListener(this);
        titleET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    buttomLL.setVisibility(View.VISIBLE);
                }else{
                    buttomLL.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
//    protected void dealEditData(List<EditData> editList) {
//            if (itemData.inputStr != null) {
//                Log.d("RichEditor", "commit inputStr=" + itemData.inputStr);
//            } else if (itemData.imagePath != null) {
//                Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);
//            }
//
//        }
//    }

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
        super.onClick(v);

        switch (v.getId()) {
            case R.id.lbe_iv_emojy:

                break;

            case R.id.lbe_iv_album:
                // 打开系统相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

                break;

            case R.id.lbe_iv_photo:

                break;
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

}
