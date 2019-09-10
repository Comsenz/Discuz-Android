package cn.tencent.DiscuzMob.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.facebook.stetho.Stetho;
import com.squareup.okhttp.Cache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.tencent.xg.RegisterReceiver;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.sharesdk.framework.ShareSDK;
import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.db.User;
import cn.tencent.DiscuzMob.receiver.AppBroadcastReceiver;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.Tools;

/**
 * Created by cg on 2017/7/26.
 */

public class RedNetApp extends Application {

    public static final String TAG_PICASSO = "http://wsq.demo.comsenz-service.com";
    public static volatile Handler INTERNAL_HANDLER = new Handler();
    private static RedNetApp instance;
    public ConnectivityManager CM;
    public InputMethodManager IMM;
    private User mLoginUser;
    private ContentObserver mLoginUserObserver = new ContentObserver(INTERNAL_HANDLER) {
        @Override
        public void onChange(boolean selfChange) {
            mLoginUser = User.newInstance(getContentResolver().query(User.URI, null, null, null, null));
            registPusher();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (null == Modal.getInstance().getUserAccountDao()) {
            Modal.getInstance().init(this);
        }
//        Modal.getInstance().init(this);
        CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mLoginUser = User.newInstance(getContentResolver().query(User.URI, null, null, null, null));
        Tools.initDeviceInfo(this);
        RedNetPreferences.initPreferences(this);
        getContentResolver().registerContentObserver(User.URI, true, mLoginUserObserver);
        RedNet.mHttpClient.setCache(new Cache(RedNetApp.getInstance().getStorageDir("otto"), 125 * 1024 * 1024));
        RedNet.mHttpClient.setConnectTimeout(12 * 1000, TimeUnit.MILLISECONDS);
        RedNet.mHttpClient.setReadTimeout(12 * 1000, TimeUnit.MILLISECONDS);
        RedNet.mHttpClient.setWriteTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        RedNet.mHttpClient.setCookieHandler(new AppendCookieManager(this));
        Picasso.setSingletonInstance(new Picasso.Builder(getApplicationContext())
                .downloader(new OkHttpDownloader(getCacheDir("bitmaps"), 512 * 1024 * 1024))
                .build());//静态变量驻留
//        XGPushConfig.enableDebug(this,true);
        if (isMainProcess()) {
            XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback() {
                @Override
                public void handleNotify(XGNotifaction xGNotifaction) {
                    xGNotifaction.doNotify();
                }
            });
        }
        registPusher();

        AppBroadcastReceiver.register("RedNetApp", new AppBroadcastReceiver.NetworkChangedListener() {
            @Override
            public void isConnected() {
                registPusher();
            }
        });

        MobclickAgent.enableEncrypt(true);//6.0.0版本及以后
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式
        Stetho.initializeWithDefaults(this);
    }

    /**
     * 注册信鸽推送
     */
    public void registPusher() {
        LogUtils.d("registPusher: " + (RedNetPreferences.getNotificationSetting() == 0 ? "Register" : "Unregister"));
        if (RedNetPreferences.getNotificationSetting() == 0) {
            sendBroadcast(new Intent(RegisterReceiver.ACTION_REGIST).putExtra("uid", getUid()));
        } else {
            sendBroadcast(new Intent(RegisterReceiver.ACTION_UNREGIST));
        }
    }

    public boolean networkIsOk() {
        return CM.getActiveNetworkInfo() == null ? false : CM.getActiveNetworkInfo().isConnected();
    }

    public boolean isLogin() {
        return mLoginUser != null;
    }

    public synchronized User getUserLogin(boolean readDB) {
        return readDB && mLoginUser == null ? mLoginUser = User.newInstance(getContentResolver().query(User.URI, null, null, null, null)) : mLoginUser;
    }

    public String getUid() {
        return isLogin() ? mLoginUser.getMember_uid() : "0";
    }

    public String getCookie() {
        return mLoginUser != null ? mLoginUser.getCookie() : "";
    }

    public String getCookie2() {
        return mLoginUser != null ? mLoginUser.getCookie2() : "";
    }

    public String getCookie3() {
        return mLoginUser != null ? mLoginUser.getCookie3() : "";
    }

    public String getCookie4() {
        return mLoginUser != null ? mLoginUser.getCookie4() : "";
    }

    public void logout() {
        getContentResolver().delete(User.URI, null, null);
        ((CookieManager) RedNet.mHttpClient.getCookieHandler()).getCookieStore().removeAll();
        mLoginUser = null;
        MobclickAgent.onProfileSignOff();
        try {
            RedNet.mHttpClient.getCache().evictAll();//清除所有接口数据缓存
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setFormHash(String formHash) {
        User user = RedNetApp.getInstance().getUserLogin(false);
        if (user != null && !TextUtils.isEmpty(formHash) && !formHash.equals(user.getFormhash())) {
            ContentValues values = new ContentValues();
            values.put("formhash", formHash);
            RedNetApp.getInstance().getContentResolver().update(User.URI, values, null, null);
        }
    }

    public File getCacheDir(String type) {
        File cacheDir = new File(getExternalCacheDir(), type);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

    public File getStorageDir(String directory) {
        File dir = new File(Environment.getExternalStorageDirectory(), "Rednet/" + directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir != null && dir.exists() ? dir : getCacheDir(directory);
    }

    public void exit() {
        getContentResolver().unregisterContentObserver(mLoginUserObserver);
        ShareSDK.stopSDK();
        mLoginUser = null;
        instance = null;
        //MobclickAgent.onKillProcess(this);
        //android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 在主进程设置信鸽相关的内容, 为保证弹出通知前一定调用本方法，需要在application的onCreate注册收到通知时，会调用本回调函数。
     * 相当于这个回调会拦截在信鸽的弹出通知之前被截取一般上针对需要获取通知内容、标题，设置通知点击的跳转逻辑等等
     */
    public boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static RedNetApp getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

}

