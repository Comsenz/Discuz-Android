package cn.tencent.DiscuzMob.request;

/**
 * Created by Havorld
 */

public interface OnRequestListener {

    void onResponse(String result);

    void onError(Exception error);
}
