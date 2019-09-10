package cn.tencent.DiscuzMob.utils;

import com.google.gson.Gson;

/**
 * Created by Havorld
 */

public class GsonUtil {
    /**
     *
     * 解析JsonObject对象
     *
     * @param clazz
     * @param result
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String result, Class<T> clazz) {
        return new Gson().fromJson(result, clazz);
    }

}
