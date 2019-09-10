package cn.tencent.DiscuzMob.receiver;

import java.util.Collection;
import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 监听网络的变化
 */
public class AppBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = AppBroadcastReceiver.class.getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            /** 当前设备网络是否连接 */
            boolean isConnected = (networkInfo != null) && (networkInfo.isConnected() == true);

            // 通知界面网络发生变化
            Collection<NetworkChangedListener> listeners = mListener.values();
            if (isConnected) {
                for (NetworkChangedListener listener : listeners) {
                    listener.isConnected();
                }
            } else {
                for (NetworkChangedListener listener : listeners) {
                    listener.isDisconnected();
                }
            }
        }
    }

    private static HashMap<String, NetworkChangedListener> mListener = new HashMap<>(); // 监控网络变化

    public static void register(String key, NetworkChangedListener listener) {
        mListener.put(key, listener);
    }

    public static void unregister(String key) {
        mListener.remove(key);
    }

    public static void clear() {
        mListener.clear();
    }

    public static class NetworkChangedListener {

        public void isConnected() {
        }

        public void isDisconnected() {
        }
    }

}
