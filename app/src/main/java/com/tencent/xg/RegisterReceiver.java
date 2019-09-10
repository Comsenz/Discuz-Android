package com.tencent.xg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cn.tencent.DiscuzMob.utils.LogUtils;

/**
 * Created by AiWei on 2016/6/17.
 */
public class RegisterReceiver extends BroadcastReceiver {

    public static final String ACTION_REGIST = "com.qq.xg.ACTION_REGIST";
    public static final String ACTION_UNREGIST = "com.qq.xg.ACTION_UNREGIST";
    static final String KEY_ACCOUNT = "com.qq.xg.KEY_ACCOUNT";
    private static final Set<String> sBingSet = Collections.synchronizedSet(new HashSet<String>());//避免重复发送绑定uid请求
    private SharedPreferences mSP;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mSP == null)
            mSP = context.getSharedPreferences("Rendet_xg_configs", Context.MODE_PRIVATE);
        if (ACTION_REGIST.equals(intent.getAction())) {
            onRegister(context, intent.getStringExtra("uid"));
        } else {
            onUnregister(context);
        }
    }

    void onRegister(Context context, String suid) {
        LogUtils.d("start register push service.");
        final String uid = TextUtils.isEmpty(suid) || "0".equals(suid) ? "*" : (suid.length() == 1 ? ("0" + suid) : suid);
        if ("*".equals(uid)) {
            if (TextUtils.isEmpty(mSP.getString(KEY_ACCOUNT, null))) {//当前没有绑定的账号,注册默认信息
                XGPushManager.registerPush(context.getApplicationContext());
            } else {//解除绑定的账号,并注册默认信息
                XGPushManager.registerPush(context.getApplicationContext(), "*", new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object o, int i) {
                        LogUtils.d(" XGPushManager registerPush success.");
                        mSP.edit().clear().commit();
                    }

                    @Override
                    public void onFail(Object o, int i, String s) {
                    }
                });
            }
        } else {
            if (!TextUtils.isEmpty(uid) && sBingSet.add(uid)) {
                XGPushManager.registerPush(context.getApplicationContext(), uid, new XGIOperateCallback() {

                    @Override
                    public void onSuccess(Object token, int errorCode) {
                        LogUtils.d(" XGPushManager registerPush success.");
                        mSP.edit().putString(KEY_ACCOUNT, (String) token).commit();
                        sBingSet.remove(uid);
                    }

                    @Override
                    public void onFail(Object data, int i, String s) {
                        sBingSet.remove(uid);
                    }
                });
            }
        }
    }

    void onUnregister(Context context) {
        LogUtils.d("start unregister push service.");
        XGPushManager.unregisterPush(context.getApplicationContext());
    }

}
