package zsx.com.aiyamaya.util;

import android.content.Context;
import android.content.SharedPreferences;

import zsx.com.aiyamaya.BaseApplication;

/**
 * Created by moram on 2017/1/3.
 */

public class SpfUtil {
    private static SharedPreferences spf = null;
    private final static String SHARE_NAME = "share_name";

    public static void init(Context context) {
        spf = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
    }


    /**
     * 向share中添加字符串
     *
     * @param key
     *            需要保存的字符串对应的key值
     * @param value
     *            需要保存的字符串
     */
    public static void saveString(String key, String value) {
        spf.edit().putString(key, value).commit();
    }

    /**
     *
     * 向share添加int 型数字
     *
     * @param key
     *            需要保存的数字对应的key值
     * @param value
     *            需要保存的数字
     */
    public static void saveInteger(String key, int value) {
        spf.edit().putInt(key, value).commit();
    }

    /**
     * 在share保存布尔型
     *
     * @param key
     *            需要保存布尔型对应的KEY值
     * @param value
     *            需要保存的布尔型
     */
    public static void saveBoolean(String key, boolean value) {
        spf.edit().putBoolean(key, value).commit();
    }

    /**
     * 在share中保存Float 数字类型
     *
     * @param key
     *            需要保存Float 数字类型的KEY值
     * @param value
     *            需要保存的Float数字值
     */
    public static void saveFloat(String key, float value) {
        spf.edit().putFloat(key, value).commit();
    }

    /**
     * 在share中保存 Long 数字类型
     *
     * @param key
     *            需要保存的Long数字类型对应的KEY值
     * @param value
     *            需要保存的Long数字值
     */
    public static void saveLong(String key, long value) {
        spf.edit().putLong(key, value).commit();
    }

    /**
     * 从share中获取整型数字
     *
     * @param key
     *            需要获取整型数字所对应的KEY 值
     * @param defValue
     *            如果没有对应key的值，返回默认的数字
     * @return 从share中获取到的整型数字
     */
    public static int getInt(String key, int defValue) {
        return spf.getInt(key, defValue);
    }

    /**
     * 从share中获取字符串
     *
     * @param key
     *            需要获取字符串对应的KEY值
     * @param defValue
     *            如果没有获取到对应的默认值
     * @return 最终获取到字符串的结果
     */
    public static String getString(String key, String defValue) {
        return spf.getString(key, defValue);
    }

    /**
     * 从share中获取Float值
     *
     * @param key
     *            需要获取Float值所对应的KEY值
     * @param defValue
     *            如果没有获取到对应Float值得默认值
     * @return 最终获取到的Float值
     */
    public static float getFloat(String key, float defValue) {
        return spf.getFloat(key, defValue);
    }

    /**
     * 从share中获取boolean值
     *
     * @param key
     *            需要获取boolean值所对应的KEY值
     * @param defValue
     *            如果没有获取到KEY对应的值，设置一个默认值
     * @return 最终获取到的boolean的结果
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return spf.getBoolean(key, defValue);
    }

    /**
     * 获取share中的long值
     *
     * @param key
     *            需要获取long值所对应的KEY值
     * @param defValue
     *            如果对应的KEY值没有对应的值，需要设置一个默认的返回值
     * @return 最终获取到long 值对应的结果
     */
    public static long getLong(String key, long defValue) {
        return spf.getLong(key, defValue);
    }

    /**
     * 移除key值所对应的Share值
     *
     * @param key
     *            需要移除KEY值所对应的值以及key值
     */
    public static void remove(String key) {
        spf.edit().remove(key).commit();
    }

    /**
     * 删除所有数据
     * @param key
     */
    public static void clearAll() {
        spf.edit().clear().commit();
    }

}
