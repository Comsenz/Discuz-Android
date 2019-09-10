package cn.tencent.DiscuzMob.request;

import android.os.Handler;
import android.os.Message;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Havorld
 */

public class HttpRequest {

    private static HttpRequest mHttpRequest = new HttpRequest();
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private HttpRequest() {

    }

    public static HttpRequest getInstance() {
        return mHttpRequest;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HttpMethod.RESULT_SUCCESS:
                    RequestManage.HandleMsgObj handleMsgObj = (RequestManage.HandleMsgObj)msg.obj;
                     OnRequestListener onRequestListener = handleMsgObj.listener;
                    onRequestListener.onResponse((String) handleMsgObj.data);
                    break;
                case HttpMethod.RESULT_FAIL:
                    RequestManage.HandleMsgObj handleMsgObj1 = (RequestManage.HandleMsgObj)msg.obj;
                    OnRequestListener onRequestListener1 = handleMsgObj1.listener;
                    onRequestListener1.onError((Exception) handleMsgObj1.e);
                    break;
            }
        }
    };


    /**
     * @Title: sortMap
     * @Description: 对集合内的数据按key的字母顺序做排序
     */
    public List<Map.Entry<String, String>> sortMap(final Map<String, String> map) {
        final List<Map.Entry<String, String>> infos = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        // 重写集合的排序方法：按字母顺序
        Collections.sort(infos, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(final Map.Entry<String, String> o1, final Map.Entry<String, String> o2) {
                return (o1.getKey().toString().compareTo(o2.getKey()));
            }
        });

        return infos;
    }

    public static String changeToJson(RequestParams requestParams) {
        Map<String, String> map = requestParams.getParams();
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        JSONObject jsonObject = new JSONObject();
        try {
            for (final Map.Entry<String, String> m : list) {
                jsonObject.put(m.getKey(), m.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * POST请求
     *
     * @param params
     * @param onRequestListener
     */
    public void okhttpPostRequest( String module, RequestParams params, final OnRequestListener onRequestListener) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            Map<String, String> requestParams = params.getParams();
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                builder = builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .addHeader("Cookie",CacheUtils.getString(RedNetApp.getInstance(),"cookie"))
                .url(module)
                .post(formBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                RequestManage.requestFailure(handler, e,onRequestListener);
            }

            @Override
            public void onResponse(Call call, Response response) {
                okhttp3.Response.Builder builder = response.newBuilder();
                okhttp3.Headers headers = builder.build().headers();
                List<String> cookies = headers.values("Set-Cookie");
                String cookie1 = headers.get("Set-Cookie");
                for (int i = 0, count = headers.size(); i < count; i++) {
                    LogUtils.i("\t" + headers.name(i) + ": " + headers.value(i));
                }
                LogUtils.i("获取到的 Cookie=   " + cookie1);
//                okhttp3.Headers headers = response.headers();
//                List<String> cookies = headers.values("Set-Cookie");
//                String cookie1 = headers.get("Set-Cookie");
//                if (cookies.size() > 0) {
//                    String session = cookies.get(0);
//                    String result = session.substring(0, session.indexOf(";"));
//                    CacheUtils.putString(mContext, "cookie1", result);
//                    LogUtils.i("获取到的 Cookie=   " + cookie1);
//                }
                RequestManage.requestSuccess(handler, response,onRequestListener);
            }
        });

    }
//    /**
//     * 图片POST请求
//     *
//     * @param params
//     * @param onRequestListener
//     */
//    public void okhttpImagePostRequest(String imgUrl, String module, RequestParams params, final OnRequestListener onRequestListener) {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(ConstantURL.getServerUrl());
//        stringBuffer.append(module);
//        String formBody = "";
//        params.addParam("source", "1002");
//        params.addParam("time", CommonUtils.getTime());
//        params.addParam("version", CommonUtils.getLocalVersion(RedMallApplication.getContext()) + "");
//        params.addParam("token", "e10adc3949ba59abbe56e057f20f883e");
//        params.addParam("sign", sortMd5(params));
//        params.deleteParam("key");
//        formBody = changeToJson(params);
//        LogUtil.i("alan1995 json请求", formBody);
//        String imageType = "multipart/form-data";
//        LogUtil.i(imgUrl);
//        File file = new File(imgUrl);
//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file", "head_image", fileBody)
//                .addPart(RequestBody.create(MultipartBody.FORM, formBody))
//                .build();
//        Request request = new Request.Builder()
//                .header("Content-Type", imageType)
//                .url(stringBuffer.toString())
//                .post(requestBody)
//                .build();
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                RequestManage.requestFailure(handler, e,onRequestListener);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//
//                RequestManage.requestSuccess(handler, response,onRequestListener);
//
//            }
//        });

//    }



    /**
     * GET请求
     *
     * @param url
     * @param onRequestListener
     */
    public void okhttpGetRequest(String url, final OnRequestListener onRequestListener) {
        LogUtils.i("Cookie",CacheUtils.getString(RedNetApp.getInstance(),"cookie"));
        Request.Builder builder = new Request.Builder().addHeader("Cookie",CacheUtils.getString(RedNetApp.getInstance(),"cookie"))
                .url(url).method(HttpMethod.GET, null);
        Request request = builder.build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                RequestManage.requestFailure(handler, e,onRequestListener);
            }

            @Override
            public void onResponse(Call call, Response response) {

                RequestManage.requestSuccess(handler, response,onRequestListener);

            }
        });

    }

//    /**
//     * 上传文件
//     *
//     * @param file
//     * @param params
//     * @param onRequestListener
//     */
//    public void okhttpUploadRequest(String module, File file, RequestParams params,
//                                    final OnRequestListener onRequestListener) {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(AppNetConfig.GETREGISTER);
//        stringBuffer.append(module);
//        params.addParam("key", "wanghong2019");
//        params.addParam("source", "1002");
//        params.addParam("time", CommonUtils.getTime());
//        params.addParam("version", CommonUtils.getLocalVersion(RedMallApplication.getContext()) + "");
//        params.addParam("sign", sortMd5(params));
//        params.deleteParam("key");
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if (params != null) {
//            Map<String, String> requestParams = params.getParams();
//            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
//                builder = builder.addFormDataPart(entry.getKey(), entry.getValue());
//            }
//        }
////        RequestBody requestBody = builder.addFormDataPart("","",RequestBody.create(Constant.MEDIA_TYPE, file)).build();
//        RequestBody requestBody = builder.addPart(RequestBody.create(HttpMethod.MEDIA_TYPE, file)).build();
//
//        Request request = new Request
//                .Builder()
//                .header("Content-Type", "application/json")
//                .url(stringBuffer.toString())
//                .post(requestBody)
//                .build();
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                RequestManage.requestFailure(handler, e,onRequestListener);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                RequestManage.requestSuccess(handler, response,onRequestListener);
//            }
//        });
//
//    }


    /**
     * 下载文件
     *
     * @param url
     * @param file
     * @param onRequestListener
     */
    public void okhttpDownloadRequest(String url, final File file, OnRequestListener onRequestListener) {

        Request request = new Request.Builder().url(url).build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();

                } catch (IOException e) {

                }

            }
        });
    }
}
