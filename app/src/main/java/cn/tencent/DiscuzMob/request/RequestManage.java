package cn.tencent.DiscuzMob.request;

import android.os.Handler;
import android.os.Message;


import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Havorld
 */

public class RequestManage {

    public static void requestFailure(Handler handler, Exception e, OnRequestListener requestListener) {
        e.printStackTrace();
        Message message = Message.obtain();
        message.obj = new HandleMsgObj(e, requestListener);
        message.what = HttpMethod.RESULT_FAIL;
        handler.sendMessage(message);
    }

    public static void requestSuccess(Handler handler, Response response, OnRequestListener requestListener) {
        try {
            Message message = Message.obtain();
            message.obj =  new HandleMsgObj( response.body().string(), requestListener);
            message.what = HttpMethod.RESULT_SUCCESS;
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  class HandleMsgObj {
        Exception e;
        OnRequestListener listener;
        String data;

        public HandleMsgObj(Exception e, OnRequestListener listener) {
            this.e = e;
            this.listener = listener;
        }

        public HandleMsgObj(String data, OnRequestListener listener) {
            this.listener = listener;
            this.data = data;
        }
    }
}
