package cn.tencent.DiscuzMob.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tencent.DiscuzMob.base.Config;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.enums.TimeStyle;
import cn.tencent.DiscuzMob.ui.activity.LoginActivity;
import cn.tencent.DiscuzMob.ui.activity.SimpleWebActivity;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2015/5/7.
 */
public class RednetUtils {


    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }


    public static final int REQUEST_CODE_LOGIN = 1;

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static void fixPostList(JSONArray postList) throws JSONException {
        for (int i = 0; i < postList.length(); i++) {
            JSONObject attachments = postList.optJSONObject(i).optJSONObject("attachments");
            postList.optJSONObject(i).remove("attchlist");
            postList.optJSONObject(i).put("attchlist", buildNewAttchList(attachments));
        }
    }

    private static JSONArray buildNewAttchList(JSONObject attachments) throws JSONException {
        JSONArray newAttachList = new JSONArray();
        if (attachments != null) {
            Iterator<String> keys = attachments.keys();
            while (keys.hasNext()) {
                String attKey = keys.next();
                JSONObject att = attachments.getJSONObject(attKey);
                JSONObject newAtt = new JSONObject();
                newAtt.put("aid", att.getString("aid"));
                newAtt.put("attachurl", AppNetConfig.IMGURL + att.optString("url") + att.optString("attachment"));
                newAttachList.put(newAtt);
            }
        }
        return newAttachList;
    }

    public enum DateFormat {
        MILLI("yyyy-MM-dd HH:mm"), MILLI2("yyyy-MM-dd"), SECONDS("yyyy-MM-dd HH:mm:ss"), FILETIMESTAMP("yyyyMMdd_HHmmss");
        public SimpleDateFormat dateFormat;

        DateFormat(String pattern) {
            this.dateFormat = new SimpleDateFormat(pattern);
        }//1464537779
    }

    public static long covertMillSeconds(String minutes) {
        return TextUtils.isEmpty(minutes) ? 0L : TimeUnit.MILLISECONDS.convert(Long.valueOf(minutes), TimeUnit.SECONDS);
    }

    public static int dp(Context context, float piexl) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, piexl, context.getResources().getDisplayMetrics());
    }

    public static boolean networkIsOk(Context context) {
        if (RedNetApp.getInstance().networkIsOk()) {
            return true;
        } else {
            if (context != null) {
                toast(context, context.getString(R.string.toast_network));
            }
            return false;
        }
    }

    public static void hideSoftInput(IBinder binder) {
        if (RedNetApp.getInstance().IMM.isActive()) {
            RedNetApp.getInstance().IMM.hideSoftInputFromWindow(binder, 0);
        }
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean hasLogin(Activity activity) {
        if (!RedNetApp.getInstance().isLogin()) {
            login(activity);
            toast(activity, "请先登录");
            return false;
        }
        return true;
    }

    public static void login(Activity activity) {
        if (activity != null) {
            activity.startActivityForResult(new Intent(activity, LoginActivity.class), REQUEST_CODE_LOGIN);
        }
    }

    public static String[] findParams(Uri uri, String... keys) {
        if (uri != null && keys != null && keys.length > 0) {
            String[] values = new String[keys.length];
            for (int i = 0; i < keys.length; i++) {
                values[i] = uri.getQueryParameter(keys[i]);
            }
            return values;
        }
        return null;
    }

    public static Intent newWebIntent(Context context, String url) {
        if (url != null) {
            Uri uri = Uri.parse(url);
            if ("cn.rednet.bbs".equalsIgnoreCase(uri.getScheme()))
                return new Intent(Intent.ACTION_VIEW, uri);
            else
                return new Intent(context, SimpleWebActivity.class).putExtra("url", url);
        }
        return null;
    }

    public static String getGMTString(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss 'GMT'", Locale.US);
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        sdf.setTimeZone(gmtZone);
        return sdf.format(new Date(milliseconds));
    }

    public static String getMimeTypeOfImageFile(String pathName) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, opt);
        return opt.outMimeType;
    }
 
    public static void toast(Context context, String text) {
        ToastUtils.showToast(text);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getRightNowTime() {
        return getRightNowTime("yyyyMMddhhmmss");
    }

    public static String getRightNowTime(String format) {
        if (TextUtils.isEmpty(format)) return null;
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        String sysDatetime = fmt.format(rightNow.getTime());
        return sysDatetime;
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTimeStamp(long timestamp, String pattern) {
        long time;
        if (timestamp < 10000000000L) {
            time = timestamp * 1000;
        } else {
            time = timestamp;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String str = format.format(new Date(time));
        return str;
    }

    /**
     * 通过时间戳转换UTC形式日期时间
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeStampToUTC(long timeStamp) {
        return convertTimeStamp(timeStamp, "yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    public static String convertTimeStampToDate(long timeStamp) {
        return convertTimeStamp(timeStamp, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 通过UTC形式日期时间转换成10位数时间戳
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long convertUTCToTimeStamp(String utc) {
        if (utc == null || utc.length() == 0)
            return 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = utc.substring(0, 10) + " " + utc.substring(11, 19); // 2013-12-23T09:39:24Z
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return date.getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static long conertToTimeStamp(String dateString) {
        if (dateString == null || dateString.equals("")) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(dateString);
            return date.getTime() > 0 ? date.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long conertTimeToTimeStamp(String timeString) {
        if (TextUtils.isEmpty(timeString)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(timeString);
            return date.getTime() > 0 ? date.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String conertTimeToTimeStamp2(String timeString) {
        if (TextUtils.isEmpty(timeString)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date date = sdf.parse(timeString);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d = format.format(date.getTime());
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String conertTimeToTimeStamp3(String timeString) {
        if (TextUtils.isEmpty(timeString)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date date = sdf.parse(timeString);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String d = format.format(date.getTime());
            Date date2 = format.parse(d);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long conertTimeToTimeStamp(String timeString, String pattern) {
        if (TextUtils.isEmpty(timeString)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date = sdf.parse(timeString);
            return date.getTime() > 0 ? date.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    static final int TIME_JUST_NOW = 0;        // 刚刚(小于一分钟)
    static final int TIME_MINUTE = 1;        // 1分钟前
    static final int TIME_MINUTES = 2;        // XX分钟前
    static final int TIME_TODAY = 3;        // 今天以内
    static final int TIME_YESTERDAY = 4;    // 昨天以内
    static final int TIME_YEAR = 5;            // 今年以内
    static final int TIME_DATE = 6;            // 更早
//	static final int TIME_WEEK = 7;			// 一周以内

    /**
     * 时间戳转换人性化时间
     *
     * @param timeLong
     * @param form     0:正常形式     1:简短形式（默认为0）
     * @return
     */
    public static String getDateSub(long timeLong, int form) {
        if (timeLong == 0) {
            return "";
        }
        return getDateSub(String.valueOf(timeLong), form);
    }

    /**
     * 时间戳转换人性化时间
     *
     * @param timeLong
     * @return
     */
    public static String getDateSub(long timeLong) {
        return getDateSub(timeLong, 0);
    }

    /**
     * 时间戳转换人性化时间
     *
     * @param timeString
     * @param form       0:正常形式     1:简短形式（默认为0） 2:非常简短形式...
     * @return
     */
    public static String getDateSub(String timeString, int form) {
        if (timeString == null) {
            return "";
        }
        long currentTime = System.currentTimeMillis() / 1000;                    // 当前的时间戳(秒)
        long remainderTime = currentTime % 86400;                                // 当前时间对86400（秒/天）取余 (24 * 60 * 60 = 86400)
        long startOfTadayTime = currentTime - remainderTime;                    // 当天的零点
        long startOfYesterdayTime = startOfTadayTime - 86400;                    // 昨天的零点
//		long startOfWeekTime = startOfTadayTime - 518400;						// 一周之前的零点(86400*6)
        long time = Long.parseLong(timeString);
        if (time >= 10000000000L) {
            time = time / 1000;
        }
        long deltaTime = currentTime - time;
        if (time >= startOfTadayTime) {                                            // 今天
            if (deltaTime >= 0 && deltaTime < 60) {                                    // 回复时间少于60秒
                return timeStamp2Date(String.valueOf(deltaTime), TIME_JUST_NOW, form);
            } else if (deltaTime >= 60 && deltaTime < 120) {                        // 回复时间大于60秒，但小于120秒
                return timeStamp2Date(String.valueOf(deltaTime), TIME_MINUTE, form);
            } else if (deltaTime >= 120 && deltaTime < 3600) {                        //回复时间大于120秒，但小于1小时
                return timeStamp2Date(String.valueOf(deltaTime), TIME_MINUTES, form);
            } else {                                                                // 其他时间都应该是在今天之内的1小时之前的时间
                return timeStamp2Date(timeString, TIME_TODAY, form);
            }
        } else if (time < startOfTadayTime && time >= startOfYesterdayTime) {    // 昨天
            return timeStamp2Date(timeString, TIME_YESTERDAY, form);
        } else if (time < startOfYesterdayTime && (convertTimeStamp(currentTime, "yyyy")
                .equals(convertTimeStamp(time, "yyyy")))) {                        // 今年之内
            return timeStamp2Date(timeString, TIME_YEAR, form);
        } else {                                                                // 更早
            return timeStamp2Date(timeString, TIME_DATE, form);
        }
    }

    /**
     * 时间戳转人性化时间
     *
     * @param time
     * @return
     */
    public static String getDateSub(long time, TimeStyle timeStyle) {
        String currTime = convertTimeStamp(time, "yyyy年MM月dd日");
        String today = convertTimeStamp(System.currentTimeMillis(), "yyyy年MM月dd日");
        String yesterday = convertTimeStamp(System.currentTimeMillis() / 1000 - 86400, "yyyy年MM月dd日");
        Resources resources = RedNetApp.getAppContext().getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        if (today.equals(currTime)) {
            if (timeStyle.getValue() == 0 || timeStyle.getValue() == 3) {
                return RedNetApp.getInstance().getResources().getString(R.string.today);
            } else if (timeStyle.getValue() == 1) {
                return convertTimeStamp(time, "HH:mm");
            } else if (timeStyle.getValue() == 2) {
                return RedNetApp.getInstance().getResources().getString(R.string.today) + convertTimeStamp(time, "HH:mm");
            }
        } else if (yesterday.equals(currTime)) {
            if (timeStyle.getValue() == 0 || timeStyle.getValue() == 3) {
                return RedNetApp.getInstance().getResources().getString(R.string.yesterday);
            } else if (timeStyle.getValue() == 1) {
                return RedNetApp.getInstance().getResources().getString(R.string.yesterday) + convertTimeStamp(time, "HH:mm");
            } else if (timeStyle.getValue() == 2) {
                return RedNetApp.getInstance().getResources().getString(R.string.yesterday) + convertTimeStamp(time, "HH:mm");
            }
        } else if (today.substring(0, 4).equals(currTime.substring(0, 4))) {
            if (timeStyle.getValue() == 0 || timeStyle.getValue() == 3) {
                if ("CN".equals(config.locale.getCountry())) {
                    return convertTimeStamp(time, "M月d日");
                } else {
                    return convertTimeStamp(time, "MM/dd");
                }
            } else if (timeStyle.getValue() == 1) {
                return convertTimeStamp(time, "M月d日 HH:mm");
            } else if (timeStyle.getValue() == 2) {
                return convertTimeStamp(time, "M月d日 HH:mm");
            }
        }
        if ("CN".equals(config.locale.getCountry())) {
            if (timeStyle.getValue() == 3) {
                return convertTimeStamp(time, "yyyy年M月d日");
            }
            return convertTimeStamp(time, "yyyy年M月d日 HH:mm");
        }
        return convertTimeStamp(time, "MM/dd/yyyy HH:mm");
    }

    /**
     * 将时间格式字符串转换为时间 yyyy/MM/dd HH:mm，并人性化展示
     *
     * @param timestampString
     * @param type
     * @param form
     * @return
     */
    private static String timeStamp2Date(String timestampString, int type, int form) {
        Long timestamp = Long.parseLong(timestampString);
        String date = null;
        Resources resources = RedNetApp.getAppContext().getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        switch (type) {
            case -1:
                date = convertTimeStamp(timestamp, "yyyy/MM/dd HH:mm");
                break;
            case TIME_JUST_NOW:
                if (form == 0) {
                    date = RedNetApp.getInstance().getResources().getString(R.string.just);
                } else {
                    date = RedNetApp.getInstance().getResources().getString(R.string.just_simple);
                }
                break;
            case TIME_MINUTE:
                if (form == 0) {
                    date = RedNetApp.getInstance().getResources().getString(R.string.minute_ago);
                } else {
                    date = RedNetApp.getInstance().getResources().getString(R.string.minute_ago_simple);
                }
                break;
            case TIME_MINUTES:
                if (form == 0) {
                    if ("CN".equals(config.locale.getCountry())) {
                        date = (timestamp / 60) + RedNetApp.getInstance().getResources().getString(R.string.minutes_ago);
                    } else {
                        date = (timestamp / 60) + " " + RedNetApp.getInstance().getResources().getString(R.string.minutes_ago);
                    }
                } else {
                    if ("CN".equals(config.locale.getCountry())) {
                        date = (timestamp / 60) + RedNetApp.getInstance().getResources().getString(R.string.minutes_ago_simple);
                    } else {
                        date = (timestamp / 60) + " " + RedNetApp.getInstance().getResources().getString(R.string.minutes_ago_simple);
                    }
                }
                break;
            case TIME_TODAY:
                if (form == 2) {
                    date = convertTimeStamp(timestamp, " HH:mm");
                } else {
                    date = RedNetApp.getAppContext().getResources().getString(R.string.time_today) + convertTimeStamp(timestamp, " HH:mm");
                }
                break;
            case TIME_YESTERDAY:
                StringBuilder yesterday = new StringBuilder();
                if (form == 0) {
                    yesterday.append(RedNetApp.getAppContext().getResources().getString(R.string.yesterday)).append(" ").append(convertTimeStamp(timestamp, "HH:mm"));
                } else {
                    yesterday.append(RedNetApp.getAppContext().getResources().getString(R.string.yesterday));
                }
                date = String.valueOf(yesterday);
//			date = date + " " + App.getInstance().getResources().getString(R.string.minutesAgo);
                break;
            case TIME_YEAR:
                if (form == 0) {
                    if ("CN".equals(config.locale.getCountry())) {//已对语言进行判定
                        date = convertTimeStamp(timestamp, "M月d日 HH:mm");
                    } else {
                        date = convertTimeStamp(timestamp, "MM/dd HH:mm");
                    }
                } else if (form == 2) {
                    if ("CN".equals(config.locale.getCountry())) {
                        date = convertTimeStamp(timestamp, "yy/M/d");
                    } else {
                        date = convertTimeStamp(timestamp, "M/d/yy");
                    }
                } else {
                    if ("CN".equals(config.locale.getCountry())) {
                        date = convertTimeStamp(timestamp, "M月d日");
                    } else {
                        date = convertTimeStamp(timestamp, "MM/dd");
                    }
                }
                break;
            case TIME_DATE:
                if (form == 0) {//正常形式
                    if ("CN".equals(config.locale.getCountry())) {//已对语言进行判定
                        date = convertTimeStamp(timestamp, "yyyy年M月d日 HH:mm");
                    } else {
                        date = convertTimeStamp(timestamp, "MM/dd/yyyy HH:mm");
                    }
                } else if (form == 2) {
                    if ("CN".equals(config.locale.getCountry())) {
                        date = convertTimeStamp(timestamp, "yy/M/d");
                    } else {
                        date = convertTimeStamp(timestamp, "M/d/yy");
                    }
                } else {//简短形式
                    if ("CN".equals(config.locale.getCountry())) {
                        date = convertTimeStamp(timestamp, "yyyy年M月d日");
                    } else {
                        date = convertTimeStamp(timestamp, "MM/dd/yyyy");
                    }
                }
                break;
            default:
                break;
        }
        return date;
    }

    /**
     * 判断是否是今天以前
     *
     * @param time
     * @return
     */
    public static boolean isTheDayBeforeToday(long time) {
        long tempTime = time;
        if (tempTime >= 10000000000L) {
            tempTime = tempTime / 1000;
        }
        long currentTime = System.currentTimeMillis() / 1000;                    // 当前的时间戳(秒)
        long remainderTime = currentTime % 86400;                                // 当前时间对86400（秒/天）取余 (24 * 60 * 60 = 86400)
        long startOfTadayTime = currentTime - remainderTime;                    // 当天的零点
        if (tempTime < startOfTadayTime) {
            return true;
        }
        return false;
    }

    /**
     * 获得星期
     * @param milliseconds
     * @return
     */
//	public static String getWeekOfDate(long milliseconds) {
//	    String[] weekDays = {
//	    		App.getAppContext().getResources().getString(R.string.sunday),
//	    		App.getAppContext().getResources().getString(R.string.monday),
//	    		App.getAppContext().getResources().getString(R.string.tuesday),
//	    		App.getAppContext().getResources().getString(R.string.wednesday),
//	    		App.getAppContext().getResources().getString(R.string.thursday),
//	    		App.getAppContext().getResources().getString(R.string.friday),
//	    		App.getAppContext().getResources().getString(R.string.saturday)};
//	    Calendar cal = Calendar.getInstance();
//	    Date curDate = new Date(milliseconds);
//	    cal.setTime(curDate);
//	    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
//	    if (w < 0)
//	        w = 0;
//	    return weekDays[w];
//	}

    /**
     *  获得时区偏移量
     *
     * @return
     */
    public static int timeOffset() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        return zoneOffset;
    }

    public static String stringToMD5(String sourceString) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        char[] charArray = sourceString.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

//	/**
//	 * 将String字符串强转成int型数字
//	 * @param inStr
//	 * @return
//	 */
//	public static int convertStringToInt(String inStr) {
//		String s = "";
//		char[] a = inStr.toCharArray();
//		for(int i=0; i<a.length; i++) {
//			if(a[i] == 0) {
//				continue;
//			}
//			s += a[i];
//		}
//		if(s.length() == 0) s = "0";
//		return Integer.parseInt(s);
//	}

    public static String convertString(String inStr) {

        char[] a = inStr.toCharArray();
        // for (int i = 0; i < a.length; i++){
        // a[i] = (char) (a[i] ^ 't');
        // }
        String s = new String(a);
        return s;
    }

    /**
     * 在进制表示中的字符集合
     */
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    /**
     * 将十进制的数字转换为指定进制的字符串
     *
     * @param n    十进制的数字
     * @param base 指定的进制
     * @return
     */
    public static String toOtherBaseString(long n, int base) {
        long num = 0;
        if (n < 0) {
            num = ((long) 2 * 0x7fffffff) + n + 2;
        } else {
            num = n;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((num / base) > 0) {
            buf[--charPos] = digits[(int) (num % base)];
            num /= base;
        }
        buf[--charPos] = digits[(int) (num % base)];
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * 将其它进制的数字（字符串形式）转换为十进制的数字
     *
     * @param str  其它进制的数字（字符串形式）
     * @param base 指定的进制
     * @return
     */
    public static long toDecimalism(String str, int base) {
        char[] buf = new char[str.length()];
        str.getChars(0, str.length(), buf, 0);
        long num = 0;
        for (int i = 0; i < buf.length; i++) {
            for (int j = 0; j < digits.length; j++) {
                if (digits[j] == buf[i]) {
                    num += j * Math.pow(base, buf.length - i - 1);
                    break;
                }
            }
        }
        return num;
    }

    public static String html(String content) {
        if (content == null) return "";
        String html = content;
        html = html.replace("'", "&apos;");
        html = html.replaceAll("&", "&amp;");
        html = html.replace("\"", "&quot;");  //"
        html = html.replace("\t", "&nbsp;&nbsp;");// 替换跳格
        html = html.replace(" ", "&nbsp;");// 替换空格
        html = html.replace("<", "&lt;");
        html = html.replaceAll(">", "&gt;");
        return html;
    }

    public static String initWebViewData(String data) {
        return data.replace("%", "%25").replace("#", "%23").replace("?", "%3f").replace("\\", "%27");
    }

    /**
     * 对象转Map
     *
     * @param obj
     * @return
     */
    public static Map convertObjToMap(Object obj) {
        Map<String, Object> reMap = new HashMap<String, Object>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(fields[i].getName(), o);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return reMap;
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static int dipToPx(float dip) {
        return (int) (dip * Config.DeviceInfo.density + 0.5f);
    }

    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }
}
