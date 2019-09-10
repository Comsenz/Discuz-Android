package cn.tencent.DiscuzMob.base;

import com.squareup.okhttp.OkHttpClient;

import cn.tencent.DiscuzMob.utils.LoggingInterceptor;
import cn.tencent.DiscuzMob.manager.SmileManager;

/**
 * Created by kurt on 15-6-8.
 */
public class RedNet {

    public static volatile OkHttpClient mHttpClient = new OkHttpClient();
    public static SmileManager smileManager = new SmileManager();
    static {
        mHttpClient.interceptors().add(new LoggingInterceptor());
    }

}
