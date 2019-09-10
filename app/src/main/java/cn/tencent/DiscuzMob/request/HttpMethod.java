package cn.tencent.DiscuzMob.request;

import okhttp3.MediaType;

/**
 * Created by Havorld
 */

public class HttpMethod {

    public static final String GET = "GET";
    public static final String POST = "POST";
    /**
     * 访问服务器成功的标记
     */
    public static final int RESULT_SUCCESS = 1;
    /**
     *访问服务器失败的标记
     */
    public static final int RESULT_FAIL = 2;
    public static final MediaType MEDIA_TYPE = MediaType.parse("image/png");
}
