package cn.tencent.DiscuzMob.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.facebook.stetho.common.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;


public class CommonUtils {


    private static Boolean mUserDragAdView = false;


    /**
     * 判断是否有可用网络链接 不管是GPRS 还是 WIFI
     */
    public static boolean hasActiveNetwork(Context c) {
        ConnectivityManager manager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            LogUtil.i("couldn't get connectivity manager");
        } else {
//        	NetworkInfo netInfo = manager.getActiveNetworkInfo();
//        	if (netInfo != null && netInfo.isConnected()) {
//				LogUtil.i( "network is available");
//        		return true;
//        	}
        }
        LogUtil.i("network is not available");
        return false;
    }
    /**
     * 判断是否WIFI连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConntection(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        return false;
//		return con.getNetworkInfo(ConnectivityManager.TYPE_WIFI) == null ? false : con.getNetworkInfo(
//				ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
    }
	 /* 获取本地软件版本号
	 */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 屏幕宽（像素，如：480px）
     *
     * @param
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高（像素，如：480px）
     *
     * @param
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getScreenWidthRadio(Activity app) {
        float radio = 1.0f;
        try {
            DisplayMetrics dm = new DisplayMetrics();
            //取得窗口属性
            app.getWindowManager().getDefaultDisplay().getMetrics(dm);
            //窗口的宽度
            int screenWidth = dm.widthPixels;
            radio = screenWidth / (float) dip2px(app, 360);
            LogUtil.i("screenWidth : " + screenWidth + " radio: " + radio);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LogUtil.e("getScreenWidthRadio Exception");
        }

        return radio;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取屏幕原始尺寸高度，包括虚拟功能键
     *
     * @param context
     * @return
     */
    public static int getTotalHeight(Context context) {

        int totalHeight = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();

        try {
            Class<?> clazz = Class.forName("android.view.Display");
            Method method = clazz.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            totalHeight = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalHeight;
    }

    /**
     * 获取底部虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomNavigationBarHeight(Context context) {
        int totalHeight = getTotalHeight(context);
        int contentHeight = getScreenHeight(context);
        return totalHeight - contentHeight;
    }

    /**
     * 获取国家代号
     * 设备
     *
     * @return String
     */
    public static String getCountry() {
        String country = null;
        country = Locale.getDefault().getCountry();

        if (country != null) {
            return country;
        }
        return "";
    }

    /**
     * 获取国家代号
     *
     * @param context 设备
     * @return String
     */
    public static String getCountry(Context context) {
        String country = null;
        country = Locale.getDefault().getCountry();

        if (country != null) {
            return country;
        }
        return "";
    }

    public static long getCurrentLocalTimeLong() {
        Calendar calendar = Calendar.getInstance();
        long localTime = calendar.getTime().getTime();
        return localTime;
    }

    public static String getLanguageCountry() {
        String language = getLauguage();
        String country = getCountry();
        return (language + "-" + country).toLowerCase();

    }

    /**
     * 获取当前系统语言，返回如"zh"
     *
     * @return String
     */
    public static String getLauguage() {
        String lag = null;
        lag = Locale.getDefault().getLanguage();

        if (lag != null) {
            return lag;
        }

        return "";
    }


    /**
     * 判断是否为空
     *
     * @return T
     */
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }


    /**
     * 字符串转int 默认为0
     *
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        int num;
        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
        return num;
    }

    public static int getDeviceDpi(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 获取当前手机/Pad 设备显示密度,用于网络图标获取显示.
     *
     * @return 当前设备显示密度
     */
    public static int getIconSystemDensity(Context context) {
        int density = 3;
        int dpi = getDeviceDpi(context);
        if (dpi >= DisplayMetrics.DENSITY_XXHIGH) {
            density = 3;
        } else if (dpi >= DisplayMetrics.DENSITY_XHIGH) {
            density = 2;
        } else {
            density = 1;
        }
        return density;
    }

    // 获取当前时间
    public static Date GetNowtime() {
        Date curDate = new Date(System.currentTimeMillis());
        return curDate;
    }
    // 获取当前时间的时间戳
    public static String getTime(){
        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        String  str=String.valueOf(time);
        return str;

    }
    // 传入string 字符串 对比时间 返回时间差 为 xx天x小时x分x秒
    public static String TimeDifferent1(String a1, String a2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null, d2 = null;
        try {
            d1 = df.parse(a1);
            d2 = df.parse(a2);
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String time = null;
        try {
            if (d1 != null && d2 != null) {
                long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
                long days = diff / 1000 / 60 / 60 / 24;
                long hours = diff / 1000 / 60 / 60 - days * 24;
                long minutes = diff / 1000 / 60 - hours * 60 - days * 24 * 60;
                long miao = diff / 1000 - minutes * 60 - hours * 3600 - days * 3600 * 24;
                time = days + "天" + hours + "小时" + minutes + "分" + miao + "秒";
            }
        } catch (Exception e) {
        }
        return time;
    }

    // 传入date数据 对比时间 返回时间差 为 xx天x小时x分x秒
    public static String TimeDifferent(Date d1, Date d2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = null;
        try {
            long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
            long days = diff / 1000 / 60 / 60 / 24;
            long hours = diff / 1000 / 60 / 60 - days * 24;
            long minutes = diff / 1000 / 60 - hours * 60 - days * 24 * 60;
            long miao = diff / 1000 - minutes * 60 - hours * 3600 - days * 3600 * 24;
            time = days + "天" + hours + "小时" + minutes + "分" + miao + "秒";
        } catch (Exception e) {
        }
        return time;
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        // if (QLog.isColorLevel()) {
                        // QLog.d(ReflecterHelper.class.getSimpleName(),
                        // QLog.CLR, "fixInputMethodManagerLeak break, context
                        // is not suitable, get_context=" + v_get.getContext()+"
                        // dest_context=" + destContext);
                        // }
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
