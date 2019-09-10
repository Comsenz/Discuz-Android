package cn.tencent.DiscuzMob.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Date;

public class RedNetPreferences {
    private static SharedPreferences sp;
    private static final String preferences_name = "rednetPreferences";

    public static void initPreferences(Context context) {
        try {
            if (sp != null) {
                return;
            }
            sp = context.getSharedPreferences(preferences_name, Activity.MODE_WORLD_WRITEABLE);
        } catch (Exception e) {
        }
    }

    private static synchronized String getStringValue(String key) {
        try {
            if (null == sp)
                return null;
            else
                return sp.getString(key, null);
        } catch (Exception e) {
            return "";
        }
    }

    private static synchronized Integer getIntegerValue(String key) {
        try {
            if (null == sp) {
                return 0;
            } else {
                return sp.getInt(key, 0);
            }
        } catch (Exception e) {
            return -1;
        }
    }

    private static synchronized long getLongValue(String key) {//added by luozheng in 12.2.29;
        try {
            if (null == sp) {
                return 0;
            } else {
                return sp.getLong(key, 0);
            }
        } catch (Exception e) {
            return -1;
        }
    }

    private static synchronized Integer getIntegerValueDefault(String key) {
        try {
            if (null == sp)
                return -1;
            else
                return sp.getInt(key, -1);
        } catch (Exception e) {
            return -1;
        }
    }

    private static synchronized void setStringValue(String key, String value) {
        try {
            if (null != sp) {
                Editor editor = sp.edit();
                editor.putString(key, value);
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void setIntegerValue(String key, Integer value) {
        try {
            if (null != sp) {
                Editor editor = sp.edit();
                editor.putInt(key, value);
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void setLongValue(String key, long value) {//added by luozheng in 12.2.29
        try {
            if (null != sp) {
                Editor editor = sp.edit();
                editor.putLong(key, value);
                editor.commit();
            }
        } catch (Exception e) {
        }
    }


    public static synchronized void clearAllConfig() {
        try {
            if (null != sp) {
                Editor editor = sp.edit();
                editor.clear();
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean IsFirstOpen() {
        if (getIntegerValueDefault("firstopen") == -1) {
            setIntegerValue("firstopen", 1);
            return true;
        } else {
            return false;
        }
    }

    public static void setFirstOpen(int flag) {
        setIntegerValue("firstopen", -1);
    }

    public static int queryDownloadImgMode() {
        return getIntegerValue("DownloadImgMode");
    }

    public static void setDownloadImgMode(int flag) {
        setIntegerValue("DownloadImgMode", flag);
    }

    public static int queryAutoPushMsg() {
        return getIntegerValue("AutoPushMsg");
    }

    public static void setAutoPushMsg(int flag) {
        setIntegerValue("AutoPushMsg", flag);
    }

    public static void setNavMsg(int flag) {
        setIntegerValue("NavMsg", flag);
    }

    public static int getNavMsg() {
        return getIntegerValue("NavMsg");
    }

    public static void setOpenMsg(int flag) {
        setIntegerValue("OpenMsg", flag);
    }

    public static int getOpenMsg() {
        return getIntegerValue("OpenMsg");
    }

    public static void setIsOpenDefaultPage(int flag) {
        setIntegerValue("IsOpenDefaultPage", flag);
    }

    public static int getIsOpenDefaultPage() {
        return getIntegerValue("IsOpenDefaultPage");
    }

    public static void setDefaultPage(int flag) {
        setIntegerValue("DefaultPage", flag);
    }

    public static int getDefaultPage() {
        return getIntegerValue("DefaultPage");
    }

    public static int getShowPushMsg() {
        return getIntegerValue("ShowPushMsg");
    }

    public static void setShowPushMsg(int flag) {
        setIntegerValue("ShowPushMsg", flag);
    }


    public static String getShowPushMsgType() {
        return getStringValue("ShowPushMsgType");
    }

    public static void setShowPushMsgType(String flag) {
        setStringValue("ShowPushMsgType", flag);
    }

    public static String getSmileyType() {
        return getStringValue("SmileyType");
    }

    public static void setSmileyType(String flag) {
        setStringValue("SmileyType", flag);
    }


    public static int getDownloadCss() {
        return getIntegerValue("getDownloadCss");
    }

    public static void setDownloadCss(int flag) {
        setIntegerValue("getDownloadCss", flag);
    }

    public static int getDownloadJs() {
        return getIntegerValue("getDownloadJs");
    }

    public static void setDownloadJs(int flag) {
        setIntegerValue("getDownloadJs", flag);
    }

    public static int getDownloadPic() {
        return getIntegerValue("getDownloadPic");
    }

    public static void setDownloadPic(int flag) {
        setIntegerValue("getDownloadPic", flag);
    }

    public static void setLBSType(String type) {
        setStringValue("lbstype", type);
    }

    public static String getLBSType() {
        return getStringValue("lbstype");
    }

    public static void setVcodeCookie(String vcodeCookie) {
        setStringValue("VcodeCookie", vcodeCookie);
    }

    public static String getVcodeCookie() {
        return getStringValue("VcodeCookie");
    }

    public static void setSaltkey(String saltkey) {
        setStringValue("saltkey", saltkey);
    }

    public static String getSaltkey() {
        return getStringValue("saltkey");
    }

    public static int getImageSetting() {
        return getIntegerValue("imageSetting");
    }

    public static void setImageSetting(int val) {
        setIntegerValue("imageSetting", val);
    }

    public static int getNotificationSetting() {
        return getIntegerValue("notificationSetting");
    }

    public static void setNotificationSetting(int val) {
        setIntegerValue("notificationSetting", val);
    }

    public static void setSeccode(String seccode) {
        setStringValue("seccode", seccode);
    }

    public static String getSeccodeCookie() {
        return getStringValue("seccode");
    }

    public static void setIsOpen(int type) {
        setIntegerValue("open", type);
    }

    public static int getIsOpen() {
        return getIntegerValue("open");
    }

    public static void setFontSize(int val) {
        setIntegerValue("fontsize", val);
    }

    public static int getFontSize() {
        if (getIntegerValueDefault("fontsize") == -1) {
            setFontSize(1);
        }
        return getIntegerValue("fontsize");
    }


    public static void setLastRefreshTime(int type) {
        Date date = new Date();
        setLongValue("lastRefreshTime-" + type, date.getTime());
    }

    public static Long getLastRefreshTime(int type) {
        return getLongValue("lastRefreshTime-" + type);
    }
}
