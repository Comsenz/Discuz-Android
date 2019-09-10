package cn.tencent.DiscuzMob.utils.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * Created by cg on 2016/coolmonkey010/31.
 */
public class CacheUtils {
    /**
     * 保持数据
     *
     * @param context
     * @param key
     * @param values
     */
    public static void putString(Context context, String key, String values) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CKLS", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, values).commit();
    }

    public static void putForeverString(Context context, String key, String values) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ZHLT", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, values).commit();
    }


    public static String getForeverString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ZHLT", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    /**
     * 得到缓存的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getImgString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CKLS", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "0");
    }
    /**
     * 得到缓存的数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CKLS", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("CKLS", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("CKLS", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void clean(Context context) {
        SharedPreferences userSettings = context.getSharedPreferences("CKLS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSettings.edit();
//        editor.remove("cookiepre_auth");
//        editor.remove("cookiepre_saltkey");
//        editor.remove("cookiepre_auth");
        editor.clear();
        editor.commit();
    }

    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

}
