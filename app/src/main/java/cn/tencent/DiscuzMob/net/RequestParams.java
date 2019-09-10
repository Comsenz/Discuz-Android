package cn.tencent.DiscuzMob.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Havorld
 */

public class RequestParams {


    private Map<String, String> params;

    public RequestParams() {
        params = new HashMap<String, String>();
    }

    /**
     * 添加请求参数
     *
     * @param key
     * @param value
     */
    public void addParam(String key, String value) {

        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put(key, value);
    }

    /**
     * 移除参数
     *
     * @param key
     * @param value
     */
    public void deleteParam(String key) {

        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.remove(key);
    }

    /**
     *
     * @return 获取请求参数
     */
    public Map<String, String> getParams() {

        if (params != null) {

            return params;
        } else {

            return new HashMap<String, String>();
        }
    }


}
