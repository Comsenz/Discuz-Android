package cn.tencent.DiscuzMob.utils;

import android.util.Log;

import cn.tencent.DiscuzMob.utils.StringUtil;


/**
 * Created by Havorld
 */

/**
 * Log打印日志工具类
 *
 * @author Havorld Meng
 */
public class LogUtils {
    //
    private static String TAG = "alan1995";
    //每段log最大长度
    private static int LOG_MAXLENGTH = 2000;

    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static boolean isDebug = true ;

    public static void debug(boolean isEnable) {
        debug(TAG, isEnable);
    }

    public static void debug(String logTag, boolean isEnable) {
        TAG = logTag;
        isDebug = isEnable;
    }

    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, "---> " + msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.i(TAG, "---> " + msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, "---> " + msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, "---> " + msg);
    }

    public static void e(String msg) {
        if (isDebug)
//            Log.e(TAG, "---> " + msg);
            longLog(TAG, msg);
    }


    public static void e(String tag, String msg) {
        if (isDebug)
//            Log.e(tag, "---> " + msg);
            longLog(tag, msg);
    }
    public static void d( String msg) {
        if (isDebug)
//            Log.e(tag, "---> " + msg);
            longLog("alan", msg);
    }

    public static class SubLogUtil {
        public static final boolean NEED_SHOW = isDebug;
        public static final int MAXLENG = 3000;
        public static String TAG = "FansLog";

        public static final boolean NEED_SHOW_I = true;
        public static final boolean NEED_SHOW_E = true;
        public static final boolean NEED_SHOW_W = false;

        public static int TAG_INDEX = 1;
        public static String TAGI1 = "show-->i";
        public static String TAGI2 = "show-->i" + TAG_INDEX;

        public static String TAG_E = "show-->e";
        public static String TAG_W = "show-->w";

        public static void i(String msg, boolean save) {
            msg = StringUtil.getString(msg, true);
            if (!NEED_SHOW || !NEED_SHOW_I)
                return;
            if (save) {
                String _msg = getStackTrack() + StringUtil.getString(msg);
                Log.i(TAGI1, _msg);
            } else {
                Log.i(TAGI1, StringUtil.getString(msg));
            }
            if (msg.length() > MAXLENG) {

                int i = 0;
                do {
                    Log.i(TAGI2, msg.substring(i, Math.min(i + MAXLENG, msg.length())));
                    i += MAXLENG;
                } while (i < msg.length());
            }
        }

        public static void i(String msg) {
            i(msg, false);
        }

        public static void e(String msg) {
            if (!NEED_SHOW || !NEED_SHOW_E)
                return;
            Log.e(TAG_W + TAG_INDEX, msg);
        }

        public static void e(String msg, Throwable e) {
            if (!NEED_SHOW || !NEED_SHOW_E)
                return;
            Log.e(TAG_W + TAG_INDEX, msg, e);
        }

        public static void w(String msg) {
            if (!NEED_SHOW || !NEED_SHOW_W)
                return;
            Log.e(TAG_E + TAG_INDEX, msg);

        }

        /**
         * Formats the caller's provided message and prepends useful info like
         * calling thread ID and method name.
         */


        private static final String getStackTrack() {
            int MIN_STACK_TRACE_NUM = 5;
            int MAX_STACK_TRACE_NUM = 10;
            return getStackTrack(Thread.currentThread().getStackTrace(), MIN_STACK_TRACE_NUM, MAX_STACK_TRACE_NUM);
        }

        private static final String getStackTrack(StackTraceElement[] stackTraceElements, int min, int max) {
            String split = "\t\t\t";
            String msg = "\t";
            if (stackTraceElements != null) {
                int minLenght = Math.min(max, stackTraceElements.length);
                // msg += MAX_STACK_TRACE_NUM + " " + stackTraceElements.length +
                // "\n";
                for (int i = min; i < minLenght; i++) {
                    StackTraceElement stackTraceElement = stackTraceElements[i];
                    msg += i == min ? split : "";
                    msg += stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ":<"
                            + stackTraceElement.getMethodName() + "()>";
                    msg += split;
                }
            }
            return msg;
        }

        public static void printStackTrace(Throwable t) {
            StackTraceElement[] stacks = t.getStackTrace();
            i(getStackTrack(stacks, 0, stacks.length));
        }
    }


    public static void printStackTrace(Throwable t) {
        if (isDebug && t != null) t.printStackTrace();
    }

    public static void longLog(String tag, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(tag, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(tag, msg.substring(start, strLength));
                break;
            }
        }
    }
}


