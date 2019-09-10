package cn.tencent.DiscuzMob.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast; 

import cn.tencent.DiscuzMob.base.RedNetApp;

/**
 * Created by Feng on 2017/3/20.
 */
public class ToastUtils {
    private static ToastUtils instance;
    private Toast mToast;
    private Handler mMainHandler;
    private Context mContext;

    protected ToastUtils() {
    }

    private static ToastUtils getInstance() {
        if (instance == null) {
            synchronized (ToastUtils.class) {
                if (instance == null) {
                    instance = new ToastUtils();
                    instance.mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return instance;
    }

    public static final void setContext(Context context) {
        getInstance().mContext = context;
    }

    public static Context getContext() {
//		return getInstance().mContext == null ? PluginBaseApplication.getInstance().getApplicationContext() : getInstance().mContext;
        return getInstance().mContext == null ? RedNetApp.getInstance() : getInstance().mContext;
    }

    private void showToast(final Toast _toast) {
        getInstance().mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (getInstance().mToast != null) {
                    getInstance().mToast.cancel();
                }
                getInstance().mToast = _toast;
                getInstance().mToast.show();
            }
        });
    }

    public synchronized static void cancleToast() {
        if (getInstance().mToast != null) {
            getInstance().mToast.cancel();
        }
    }

    public synchronized static final void showToast(String msg) {
        getInstance().showToast(new Builder().setMsg(msg).builder());
    }

    public static class Builder {
        String msg;

        public Builder setMsg(int msg) {
            this.msg = getContext().getString(msg);
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Toast builder() {
            return Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        }

    }

}
