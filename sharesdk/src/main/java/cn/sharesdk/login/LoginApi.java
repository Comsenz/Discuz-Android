package cn.sharesdk.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static android.content.ContentValues.TAG;

public class LoginApi implements Callback {
    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    private OnLoginListener loginListener;
    private String platform;
    private Context context;
    private Handler handler;

    public LoginApi() {
        handler = new Handler(Looper.getMainLooper(), this);
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setOnLoginListener(OnLoginListener login) {
        this.loginListener = login;
    }

    public void login(Context context) {
        this.context = context.getApplicationContext();
        if (platform == null) {
            return;
        }

        //初始化SDK
        ShareSDK.initSDK(context);
        final Platform plat = ShareSDK.getPlatform(platform);
        Log.e("TAG", "plat1 =" + plat.getName() + plat.getId() + plat.getSortId() + plat.isAuthValid());
        if (plat == null) {
            Log.e("TAG", "00000000000");
            return;
        }

        if (plat.isAuthValid()) {
//            loginListener.onResult(true, plat.getName(),           getAccount(plat));
            plat.removeAccount(true);
            if (plat.getDb() != null) {
                plat.getDb().removeAccount();
            }
        }
        Log.e("TAG", "plat2 =" + plat.toString());
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
                Log.d(TAG, "onComplete: ");
//                if (action == Platform.ACTION_USER_INFOR) {
                Message msg = new Message();
                msg.what = MSG_AUTH_COMPLETE;
                msg.arg2 = action;
                msg.obj = new Object[]{plat.getName(), getAccount(plat)};
                handler.sendMessage(msg);
//                }
            }

            public void onError(Platform plat, int action, Throwable t) {
                Log.e("TAG", "onError: Platform=" + plat.getName() + " action=" + action + "Throwable=" + t.getMessage());
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_ERROR;
                    msg.arg2 = action;
                    msg.obj = t;
                    handler.sendMessage(msg);
                }
                t.printStackTrace();
            }

            public void onCancel(Platform plat, int action) {
                Log.d(TAG, "onCancel: ");
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_CANCEL;
                    msg.arg2 = action;
                    msg.obj = plat;
                    handler.sendMessage(msg);
                }
            }
        });
        plat.authorize();
    }

    Bundle getAccount(Platform platform) {
        if (platform.isAuthValid()) {
            Bundle bundle = new Bundle();
            bundle.putString("openId", platform.getDb().getUserId());
            bundle.putString("platform", platform.getName());
            bundle.putString("icon", platform.getDb().getUserIcon());
            bundle.putString("nickname", platform.getDb().getUserName());
            Platform weChat = ShareSDK.getPlatform(Wechat.NAME);

            String unionid = weChat.getDb().get("unionid");
            return bundle;
        }
        return null;
    }

    /**
     * 处理操作结果
     */
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL: { // 取消
                Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show();
                loginListener.onResult(false, null, null);
            }
            break;
            case MSG_AUTH_ERROR: {// 失败
                Throwable t = (Throwable) msg.obj;
                t.printStackTrace();
                String text = "caught error: " + t.getMessage();
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                loginListener.onResult(false, null, null);
            }
            break;
            case MSG_AUTH_COMPLETE: { // 成功
                Log.d(TAG, "MSG_AUTH_COMPLETE");
                if (loginListener != null) {
                    Object[] objs = (Object[]) msg.obj;
                    loginListener.onResult(true, (String) objs[0], (Bundle) objs[1]);
                }
            }
            break;
        }
        return false;
    }

}
