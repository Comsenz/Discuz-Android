package cn.tencent.DiscuzMob.base;

import android.os.Environment;

import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.utils.Tools;

/**
 * Created by kurt on 15-5-27.
 */
public class Config {

    //    public static final String APPNAME = "rednet";
    public static final String APPNAME = "掌上论坛";
    public static String HOST = Api.getInstance().URL;
    //public static String HOST="http://rednet.pm.comsenz-service.com";
    public static String SDCARD_PATH = Tools.getSDPath() + "/" + APPNAME + "/";
    public static String LOCAL_DOWNLOAD_IMAGE_PATH = SDCARD_PATH + "img/";
    public static String LOCAL_DOWNLOAD_CACHE_PATH = SDCARD_PATH + "cache/";
    public static final String SHARE_URL = HOST + "/forum.php?mod=viewthread&tid=";
    public static final String REPORT_URL = HOST + "/forum.php?mod=viewthread&tid=";
    public static final String SHARE_ICON_RUL = "http://f3.rednet.cn/data/attachment/common/34/wxShare.png";

    public final static int ACTIONTYPE_ONLOADFINISH = 0;
    public final static int ACTIONTYPE_PRAISE = 1;
    public final static int ACTIONTYPE_SHARE = 2;
    public final static int ACTIONTYPE_DISCUSSUSER = 3;
    public final static int ACTIONTYPE_JOIN = 4;
    public final static int ACTIONTYPE_SENDPOLL = 5;
    public final static int ACTIONTYPE_CHECKVOTER = 6;
    public final static int ACTIONTYPE_LOADMORE = 7;
    public final static int ACTIONTYPE_USERINFO = 8;
    public final static int ACTIONTYOE_THUMBCLICK = 9;
    public final static int ACTIONTYOE_REPORT = 10;
    public final static int ACTIONTYOE_REPORT_COMMENT = 11;

    public static class DeviceInfo {
        public static int screenHeight = 0;
        public static int screenWidth = 0;
        public static float density = 0;
        public static float densityDpi = 0;
        public static float scaledDensity = 0.0f;
    }

    public static boolean IsCanUseSdCard() {
        try {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}