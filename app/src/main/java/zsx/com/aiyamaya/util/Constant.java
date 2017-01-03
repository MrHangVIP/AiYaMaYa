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


    public static final String DEFAULT_URL="http://192.168.188.235:8080/ZSXBiShe";



    //-------本地sharedpreference的key开始----------

    public static final String MUM_STATE="mun_state";

    public static final String IS_LOGIN="is_login";

    //-------本地sharedpreference的key结束----------



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

}
