package zsx.com.aiyamaya.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import zsx.com.aiyamaya.ui.dialog.ActionSheetDialog;


/**
 * Created by dawn on 2016/5/2.
 */
public class SelectPhotoTools {
    /*****
     * 打开选择框
     * @param context Context  Activity上下文对象
     * @param uri  Uri
     */
    public static void openDialog(final Activity context, final Uri uri){
        new ActionSheetDialog(context)
                .builder()
                .setTitle("选择图片")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Gray, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        startCamearPicCut(context,null, uri);
                    }
                })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Gray, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        startImageCaptrue(context, null);
                    }
                })
                .show();
    }

    /****
     * 调用系统的拍照功能
     * @param context Activity上下文对象
     * @param uri  Uri
     */
    private static void startCamearPicCut(Activity context, Fragment fragment, Uri uri) {
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", true);// 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if(fragment != null){
            fragment.startActivityForResult(intent, Constant.SystemPicture.PHOTO_REQUEST_TAKEPHOTO);
        }else{
            context.startActivityForResult(intent, Constant.SystemPicture.PHOTO_REQUEST_TAKEPHOTO);
        }
    }
    /***
     * 调用系统的图库
     * @param context Activity上下文对象
     */
    private static void startImageCaptrue(Activity context, Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if(fragment != null){
            fragment.startActivityForResult(intent, Constant.SystemPicture.PHOTO_REQUEST_GALLERY);
        }else{
            context.startActivityForResult(intent, Constant.SystemPicture.PHOTO_REQUEST_GALLERY);
        }
    }


    /*****
     * 进行截图
     * @param context Activity上下文对象
     * @param uri  Uri
     * @param size  大小
     */
    public static void startPhotoZoom(Activity context, Fragment fragment, Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        if(fragment != null){
            fragment.startActivityForResult(intent, Constant.SystemPicture.PHOTO_REQUEST_CUT);
        }else{
            context.startActivityForResult(intent, Constant.SystemPicture.PHOTO_REQUEST_CUT);
        }
    }

}
