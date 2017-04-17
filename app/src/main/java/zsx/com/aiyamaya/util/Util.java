package zsx.com.aiyamaya.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import zsx.com.aiyamaya.BaseApplication;


public class Util {

    /**
     * 下载缓存
     */
    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
    public static String[] urls;
    private static long lastClickTime;

    public static float density = 0l;

    /**
     * 视频截图保存的路径
     *
     * @return
     */
    public static String getScreenShotSaveOnPath() {
        String path = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory() + "/DCIM/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            path = BaseApplication.getAPPInstance().getCacheDir() + "";
        }
        return path;
    }

    public static void deleteFile(File file) {
        if (!file.exists())
            return;
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    public static String getProgramName(Context c) {
        String applicationName = null;
        try {
            PackageManager packageManager = null;
            ApplicationInfo applicationInfo = null;
            packageManager = c.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    c.getPackageName(), 0);
            applicationName = (String) packageManager
                    .getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return applicationName;
    }


    /**
     * 打开手机浏览器
     *
     * @param mContext
     * @param url
     */
    public static void openBrowser(Context mContext, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

    /*
     * 打开网络设置
     */
    public static void openNet(Context mContext) {
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mContext.startActivity(new Intent(
                    android.provider.Settings.ACTION_SETTINGS));
        } else {
            mContext.startActivity(new Intent(
                    android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    /**
     * @param url 原图片的url
     * @param w   指定图片的宽度
     * @param h   指定图片的高度
     * @return 指定大大小的图片
     */
    public static String getImageUrlByWidthHeight(String url, int w, int h) {
        if (!TextUtils.isEmpty(url)) {
            url = url.replace("/img/", "/img/" + w + "x" + h + "/");
        }
        return url;
    }

    public static String getImageUrlByWidthHeight(String url, int w) {
        if (!TextUtils.isEmpty(url)) {
            url = url.replace("/img/", "/img/" + w + "x/");
        }
        return url;
    }

    public static String getICCID(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimSerialNumber();
    }

    public static String getIMEI(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getPhoneNum(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    public static String getPhoneIP(Context c) {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String getSystem() {
        String release = Build.VERSION.RELEASE;
        if (!release.toLowerCase().contains("android")) {
            release = "Android " + release;
        }
        return release;
    }

    public static String getTypes() {
        return Build.MODEL;
    }

    public static String getUmengChannel(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            String msg = appInfo.metaData.getString("UMENG_CHANNEL");
            return msg;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 取得版本号
    public static String getVersionName(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionName == null ? "test_version"
                    : manager.versionName;
        } catch (NameNotFoundException e) {
            return "Unknown";
        }
    }

    // 取得版本号
    public static int getVersionCode(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionCode == 0 ? 1 : manager.versionCode;// 等于0时服务器会判断
        } catch (NameNotFoundException e) {
            return -1;
        }
    }


    /**
     * 根据屏幕密度获取一个合适的像素值
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    // 将px值转换为sp值，保证文字大小不变
    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * md5密钥
     */
    public static String md5(String md5) {
        if (TextUtils.isEmpty(md5)) {
            return null;
        }
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    md5.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    public static int toDip(int value) {
        return (int) (value * density);
    }


    public static int parseColor(String txt) {
        if (isEmpty(txt)) {
            return Color.WHITE;
        }
        if (!txt.startsWith("#")) {
            txt = "#" + txt;
        }
        try {
            return Color.parseColor(txt);
        } catch (Exception e) {
            return Color.WHITE;
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected() {
        Context context = BaseApplication.getAPPInstance();// 这个context是不可能为空的，除非application停止了
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isAvailable();
    }


    /**
     * 判断当前是否使用的是 WIFI网络
     *
     * @param icontext
     * @return
     */
    public static boolean isWifiActive(Context icontext) {
        Context context = BaseApplication.getAPPInstance();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info;
        if (connectivity != null) {
            info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI")
                            && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 通过流获得Bitmap
     *
     * @param is
     * @return
     * @throws OutOfMemoryError
     */
    public static Bitmap getBitMapFromInputStream(InputStream is)
            throws OutOfMemoryError {
        return BitmapFactory.decodeStream(is, null, getBitMapOptions());
    }

    /**
     * 通过资源文件获得Bitmap
     *
     * @param mContext
     * @param rid
     * @return
     * @throws OutOfMemoryError
     */
    public static Bitmap getBitMapFromResource(Context mContext, int rid)
            throws OutOfMemoryError {
        return BitmapFactory.decodeResource(mContext.getResources(), rid,
                getBitMapOptions());
    }

    /**
     * 通过文件获得Bitmap
     *
     * @param filePath
     * @return
     * @throws OutOfMemoryError
     */
    public static Bitmap getBitMapFromFile(String filePath)
            throws OutOfMemoryError {
        return BitmapFactory.decodeFile(filePath, getBitMapOptions());
    }

    /**
     * 获得Bitmap优化解析Options
     *
     * @return BitmapFactory.Options
     */
    public static BitmapFactory.Options getBitMapOptions() {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return opt;
    }

    /**
     * 判断有无内存卡
     */
    public static boolean hasStorage() {
        String state = android.os.Environment.getExternalStorageState();
        if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * android 媒体库数据更新 http://blog.csdn.net/trent1985/article/details/23907093#
     *
     * @param mContext
     * @param filename
     */
    @SuppressLint("NewApi")
    public static void updateGallery(Context mContext, String filename) {
        try {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(filename));
            intent.setData(uri);
            mContext.sendBroadcast(intent);
        } catch (Exception e) {
        }
    }

    /**
     * 拆分List几个几个一组
     *
     * @param targe
     * @param size
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<List> splitList(List targe, int size) {
        List<List> listArr = new ArrayList<List>();
        // 获取被拆分的数组个数
        int arrSize = targe.size() % size == 0 ? targe.size() / size : targe
                .size() / size + 1;
        for (int i = 0; i < arrSize; i++) {
            List sub = new ArrayList();
            // 把指定索引数据放入到list中
            for (int j = i * size; j <= size * (i + 1) - 1; j++) {
                if (j <= targe.size() - 1) {
                    sub.add(targe.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

    /**
     * 转码
     *
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String enCodeUtf8(String data)
            throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(data)) {
            return "";
        } else {
            return URLEncoder.encode(data, "UTF-8");
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param v
     */
    public static void hideSoftInput(View v) {
        if (v == null)
            return;
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param v
     */
    public static void showSoftInput(View v) {
        if (v == null)
            return;
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) {
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取程序外部的缓存目录
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName()
                + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath()
                + cacheDir);
    }


    /**
     * 判断字符串是否为空，或则null
     */

    public static boolean isEmpty(String data) {
        if (TextUtils.isEmpty(data) || data.equalsIgnoreCase("null")
                || data.equals("[]")) {
            return true;
        }
        return false;
    }


    public static void setVisibility(View view, int flag) {
        if (view != null && view.getVisibility() != flag) {
            view.setVisibility(flag);
        }
    }

    public static void startAnimation(View view, Animation anim) {
        if (view != null && anim != null) {
            view.startAnimation(anim);
        }
    }

    /**
     * 设置webview数据
     *
     * @param webView
     * @param content 数据内容
     */
    public static void setWebViewData(WebView webView, String content) {
        content = "<style>*{background: none;} body, body.small{margin:0;padding-top:23px; " +
                "padding-left:18px; padding-right:18px; font-size:16px; line-height:1.5;" +
                "word-wrap:break-word;word-break:break-all;}body.middle{font-size:26px;}body" +
                ".big{font-size:38px;}p{padding:0;margin:0px; line-height:1.8;" +
                "color:#5a5a5a}img{display:block;margin:5px auto!important;}</style>"
                + content;
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }


    public static void sendMsg(Context c, String tel, String msg) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"
                    + tel));
            intent.putExtra("sms_body", msg);
            intent.putExtra("compose_mode", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            c.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(Context c, String email, String subject,
                                 String content) {
        Intent data = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"
                + email));
        data.putExtra(Intent.EXTRA_SUBJECT, subject);
        data.putExtra(Intent.EXTRA_TEXT, content);
        c.startActivity(data);
    }

    /**
     * 判断是否打开GPS
     *
     * @param mContext
     */
    public static void openGPSSettings(Context mContext) {
        LocationManager alm = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);
        if (!alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {

        }
    }

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%d:%02d:%02d", hours, minutes,
                seconds) : String.format("%2d:%02d", minutes, seconds);
    }

    /**
     * generateTime反向格式化
     *
     * @param time
     * @return
     */
    public static long reGenerateTime(String time) {
        long converttime = 0;
        if (time.contains(":")) {
            String[] timeArr = time.split(":");
            for (int i = timeArr.length - 1, j = 0; i >= 0; i--, j++) {
                converttime += Float.valueOf(timeArr[i]) * Math.pow(10, j);
            }
        }
        return converttime;
    }

    /**
     * 判断service是否正在运行
     *
     * @param className
     * @return
     */
    public static boolean isServiceRunning(String className) {
        Context context = BaseApplication.getAPPInstance();
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (className.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String loadUrl(String url) throws Exception {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url)
                .openConnection();
        connection.setUseCaches(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * 解压缩一个文件
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static void upZipFile(File zipFile, String folderPath)
            throws ZipException, IOException {
        long start = System.currentTimeMillis();
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            InputStream in = zf.getInputStream(entry);
            String str = folderPath + File.separator + entry.getName();
            str = new String(str.getBytes("8859_1"), "GB2312");
            File desFile = new File(str);
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
                desFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(desFile);
            byte buffer[] = new byte[BUFF_SIZE];
            int realLength;
            while ((realLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, realLength);
            }
            in.close();
            out.close();
        }
        if (zipFile.exists()) {
            zipFile.delete();
        }
        long end = System.currentTimeMillis();
    }

    /**
     * 下载获取流
     *
     * @param urlStr
     * @return
     * @throws IOException
     */
    public static InputStream getInputStream(String urlStr) throws IOException {
        URL url;
        InputStream is = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();
            is = urlConn.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return is;
    }


    /**
     * 获取asset下Html文本
     *
     * @param mContext
     * @param htmlName
     * @return
     */
    public static String getHtmlInputStream(Context mContext, String htmlName) {
        InputStream in = null;
        String htmlUrl = null;
        try {
            in = mContext.getResources().getAssets().open(htmlName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            htmlUrl = new String(buffer, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return htmlUrl;
        }


    }

    /**
     * 用来判断服务是否运行.
     *
     * @param mContext
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 判断前30个进程是不是在运行
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runTsaks = am.getRunningTasks(30);
        for (RunningTaskInfo info : runTsaks) {
            ComponentName cn = info.topActivity;
            String currentPackageName = cn.getPackageName();
            if (!TextUtils.isEmpty(currentPackageName)
                    && currentPackageName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 每分钟检查一次，判断是不是到达某个时间
     *
     * @param hour
     * @param minute
     * @return
     */
    public static boolean isThisTime(int hour, int minute) {
        Calendar cal = Calendar.getInstance();// 当前日期
        int curhour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
        int curminute = cal.get(Calendar.MINUTE);// 获取分钟
        int minuteOfDay = curhour * 60 + curminute;// 从0:00分开是到目前为止的分钟数
        int setMinuteOfDay = hour * 60 + minute;// 起始时间 17:20的分钟数
        if (minuteOfDay == setMinuteOfDay) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查是否安装改包名的程序
     *
     * @param mContext
     * @param packageName
     * @return
     */
    public static boolean isApkExist(Context mContext, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        List<String> packagesList = getInstalledPackages(mContext);
        for (String string : packagesList) {
            if (TextUtils.equals(packageName, string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得手机安装的包名列表
     *
     * @param mContext
     * @return
     */
    private static List<String> getInstalledPackages(Context mContext) {
        List<String> list = new ArrayList<String>();
        List<PackageInfo> packages = mContext.getPackageManager()
                .getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                list.add(packageInfo.packageName);
            }
        }
        return list;
    }

    /**
     * 设置下左右图片大小
     *
     * @param view
     * @param width
     * @param height
     * @param attr
     */
    public static void setCompoundDrawables(TextView view, int width,
                                            int height, int attr) {
        Drawable[] drawables = view.getCompoundDrawables();
        Drawable myImage = drawables[attr];
        if (myImage == null) {
            return;
        }
        myImage.setBounds(0, 0, width, height);
        switch (attr) {
            case 0:
                view.setCompoundDrawables(myImage, drawables[1], drawables[2],
                        drawables[3]);
                break;
            case 1:
                view.setCompoundDrawables(drawables[0], myImage, drawables[2],
                        drawables[3]);
                break;
            case 2:
                view.setCompoundDrawables(drawables[0], drawables[1], myImage,
                        drawables[3]);
                break;
            case 3:
                view.setCompoundDrawables(drawables[0], drawables[1], drawables[2],
                        myImage);
                break;

        }
    }

    /**
     * 获取电池栏高度，不可在onCreate方法执行
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight1(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取电池栏高度，可在onCreate方法执行
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight2(Activity activity) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
            return 0;
        }
    }

    /**
     * 获得App安装状态
     *
     * @param packageName
     * @param mContext
     * @return
     */
    public static boolean getAppState(String packageName, Context mContext) {
        List<String> packagesList = getInstalledPackages(mContext);
        for (String string : packagesList) {
            if (TextUtils.equals(packageName, string)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 安装程序
     *
     * @param mContext
     * @param downLoadPath
     */
    public static void InstallApp(Context mContext, String downLoadPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(downLoadPath)),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    /**
     * 卸载程序
     *
     * @param mContext
     * @param packageName
     */
    public static void uninstallApp(Context mContext, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        mContext.startActivity(uninstallIntent);
    }

    /**
     * 判断Activity是否finish
     *
     * @param mContext
     * @return
     */
    public static boolean isActivityRun(Context mContext) {
        if (mContext == null)
            return true;
        boolean isFinishing = false;
        if (mContext instanceof Activity) {
            if (Build.VERSION.SDK_INT < 17) {
                isFinishing = ((Activity) mContext).isFinishing();
            } else {
                isFinishing = ((Activity) mContext).isDestroyed();
            }
        }
        return isFinishing;
    }


    /**
     * 判断快速点击的公共方法
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 200) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取手机mac地址
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 释放图片资源
     *
     * @param imageView
     */
    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    /**
     * 更换app语言环境
     *
     * @param mContext
     */
    public static void switchLanguage(Context mContext) {
        Locale locale = Locale.ROOT;
        if (TextUtils.equals("Tibetan", "")) {
            locale = Locale.TAIWAN;
        } else if (TextUtils.equals("English", "")) {
            locale = Locale.US;
        }
        Configuration config = mContext.getResources().getConfiguration();// 获得设置对象
        Resources resources = mContext.getResources();// 获得res资源对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale; // 简体中文
        resources.updateConfiguration(config, dm);
    }


    /**
     * 获取String资源id
     *
     * @param rid
     * @return
     */
    public static String getString(int rid) {
        return BaseApplication.getAPPInstance().getString(rid);
    }

    /**
     * @param mContext
     * @param rid
     * @return
     */
    public static String getString(Context mContext, int rid) {
        if (null == mContext || mContext.getResources() == null)
            return "";
        return mContext.getResources().getString(rid);
    }


    /**
     * 根据字符串是否为空，则是否显示layout
     *
     * @param text
     * @param view
     * @param layout
     */
    public static void setText(String text, TextView view, View layout) {
        if (!isEmpty(text)) {
            layout.setVisibility(View.VISIBLE);
            view.setText(text);
        } else {
            layout.setVisibility(View.GONE);
        }
    }


    /**
     * 判断当前程序是否 是在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    System.out.print(String.format("Foreground App:", appProcess.processName));
                    return false;
                } else {
                    System.out.print("Background App:" + appProcess.processName);
                    return true;
                }
            }
        }
        return false;
    }

}
