package cn.tencent.DiscuzMob.request;


import java.io.File;

/**
 * Created by Havorld
 */

public class HttpUtil {
    /**
     * 请求网络(默认为GET请求)
     *
     * @param url               请求链接地址
     * @param onRequestListener 回调
     */
    public static void sendRequest(String url, OnRequestListener onRequestListener) {

        sendRequest(url, null, onRequestListener);
    }

    /**
     * 请求网络
     *
     * @param url               请求链接地址
     * @param params            请求参数
     * @param onRequestListener 回调
     */
    public static void sendRequest(String url, RequestParams params, OnRequestListener onRequestListener) {

        sendRequest(HttpMethod.GET, url, params, onRequestListener);
    }

    /**
     * 请求网络
     *
     * @param httpMethod        请求方式  HttpMethod.GET 和 HttpMethod.POST
     * @param url               请求链接地址
     * @param params            请求参数
     * @param onRequestListener 回调
     */
    public static void sendRequest(String httpMethod, String url, RequestParams params, OnRequestListener onRequestListener) {

//        if (HttpMethod.GET.equals(httpMethod)) {
//            HttpRequest.getInstance().okhttpGetRequest(url, params, onRequestListener);
//        } else if (HttpMethod.POST.equals(httpMethod)) {
//            HttpRequest.getInstance().okhttpPostRequest(url, params, onRequestListener);
//        }
    }

    /**
     * 上传文件
     *
     * @param url               请求链接
     * @param file              要上传的文件
     * @param onRequestListener 回调
     */
    public static void uploadRequest(String url, File file, OnRequestListener onRequestListener) {

        uploadRequest(url, file, null, onRequestListener);
    }

    /**
     * 上传文件
     *
     * @param url               请求链接
     * @param file              要上传的文件
     * @param params            参数
     * @param onRequestListener 回调
     */
    public static void uploadRequest(String url, File file, RequestParams params, OnRequestListener onRequestListener) {

//        HttpRequest.getInstance().okhttpUploadRequest(url, file, params, onRequestListener);
    }

    /**
     * @param url               下载地址
     * @param filePath          下载文件到的路径
     * @param fileName          下载保存文件的文件名
     * @param onRequestListener 回调
     */
    public static void downloadRequest(String url, String filePath, String fileName, OnRequestListener onRequestListener) {

        HttpRequest.getInstance().okhttpDownloadRequest(url, new File(filePath, fileName), onRequestListener);
    }

    /**
     * @param url               下载地址
     * @param path              下载文件的完整路径
     * @param onRequestListener 回调
     */
    public static void downloadRequest(String url, String path, OnRequestListener onRequestListener) {

        HttpRequest.getInstance().okhttpDownloadRequest(url, new File(path), onRequestListener);
    }

}
