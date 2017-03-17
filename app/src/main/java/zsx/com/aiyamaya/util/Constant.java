package zsx.com.aiyamaya.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import zsx.com.aiyamaya.BaseApplication;

/**
 * Created by moram on 2016/12/23.
 */

public class Constant {


    public static final String DEFAULT_URL="http://10.0.5.201:8080/zsxbishe";

    public static final String IMAGE_URL="/image/";

    public static final String REGIST_URL="/RegistUser";
    public static final String LOGIN_URL="/LoginUser";
    public static final String GET_USER_URL="/GetUserInfo";
    public static final String UPDATE_USER_URL="/UpdateUserInfo";
    public static final String IMAGE_UPLOAD_URL="/ImageUpload";
    public static final String UPDATE_HEAD_URL="/UpdateHeadUrl";

    public static final String GET_RANDOM_ARTICLE="/GetRandomArticle";
    public static final String GET_TYPE_ARTICLE="/GetTypeArticle";
    public static final String WRITE_POSTBAR="/WritePostBar";
    public static final String GET_POSTBAR="/GetPostBar";



    public static final int IMAGE_UPLOAD_OK=0x1000;
    public static final int IMAGE_UPLOAD_FAIL=0x1001;

    //-------本地sharedpreference的key开始----------

    public static final String MUM_STATE="mum_state";

    public static final String IS_LOGIN="is_login";

    public static final String LOGIN_USERPHONE="login_userphone";

    public static final String TOKEN="token";

    //-------本地sharedpreference的key结束----------


    //分隔符
    public static final String MY_SPLIT_STR="<&;&>";

    public static final String MacAddress=MyUtil.getMac();

    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /*****
     * 系统相册（包含有 照相、选择本地图片）
     */
    public class SystemPicture{
        /***
         * 保存到本地的目录
         */
        public static final String SAVE_DIRECTORY = "/AIYAMAYA";
        /***
         * 保存到本地图片的名字
         */
        public static final String SAVE_PIC_NAME="head.jpeg";
        /***
         *标记用户点击了从照相机获取图片  即拍照
         */
        public static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
        /***
         *标记用户点击了从图库中获取图片  即从相册中取
         */
        public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
        /***
         * 返回处理后的图片
         */
        public static final int PHOTO_REQUEST_CUT = 3;// 结果
    }

}
