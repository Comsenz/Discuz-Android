package cn.tencent.DiscuzMob.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.widget.IFooter;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.BaseVariables;
import cn.tencent.DiscuzMob.utils.RednetUtils;

/**
 * Created by AiWei on 2016/4/19.
 */
public class ApiVersion5 {

    public static final ApiVersion5 INSTANCE = new ApiVersion5();
    private static final CacheControl NO_STORE = new CacheControl.Builder().noStore().noCache().build();
    private static final Handler sInternalHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj instanceof Result) {
                Result resultCallback = (Result) msg.obj;
                if (msg.what == 0) {
                    resultCallback.onResult(msg.arg1, null);
                    if (resultCallback.toast && !TextUtils.isEmpty((String) resultCallback.result)) {
                        RednetUtils.toast(resultCallback.context, (String) resultCallback.result);
                    }
                    if (resultCallback.footer != null) {
                        resultCallback.footer.onFaile();
                    }
                } else {
                    if (resultCallback.result instanceof BaseModel) {
                        BaseModel baseModel = (BaseModel) resultCallback.result;
                        if (!resultCallback.ignoreFormhash
                                && baseModel.getVariables() instanceof BaseVariables
                                && RedNetApp.getInstance().isLogin()) {
                            RedNetApp.setFormHash(((BaseVariables) baseModel.getVariables()).getFormhash());
                        }
                        if (baseModel.getMessage() != null && resultCallback.toast) {
                            RednetUtils.toast(resultCallback.context, baseModel.getMessage().getMessagestr());
                        }
                    }
                    resultCallback.onResult(msg.arg1, resultCallback.result);
                    if (resultCallback.footer != null) {
                        resultCallback.footer.finish();
                    }
                }
            }
        }
    };
    //    private MODE mCurrentMode = MODE.DEBUG;
    private MODE mCurrentMode = MODE.RELEASE;

    enum MODE {
        DEBUG(AppNetConfig.BASE_ADDRESS, "api/mobile/index.php?"),
        RELEASE(AppNetConfig.BASE_ADDRESS, "api/mobile/index.php?");
        String host, url;

        MODE(String host, String path) {
            this.host = host;
            this.url = new StringBuilder(host).append(path).toString();
        }
    }

    private ApiVersion5() {
    }

    public String getHost() {
        return mCurrentMode.host;
    }

    public String getUrl() {
        return mCurrentMode.url;
    }

    public String url1 = "http://10.0.6.58/api/mobile/";

    public StringBuilder getStringBuilder() {
//        return new StringBuilder(url1).append("version=5&debug=1&android=1&");
        return new StringBuilder(mCurrentMode.url).append("version=5&debug=1&android=1&");
    }

    public StringBuilder getStringBuilder(String version) {
        return new StringBuilder(mCurrentMode.url).append("version=").append(version).append("&android=1&debug=1&");
    }

    public StringBuilder getAllForum(String version) {
        return new StringBuilder(mCurrentMode.url).append("version=").append(version).append("&android=1&debug=1&");
    }

    CacheControl getCacheControl(boolean readCache, int page) {
        return page > 1 ? NO_STORE : readCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK;
    }

    /**
     * 请求开机启动图
     *
     * @param readCache      if no cache 504 will return so request network
     * @param resultCallback
     */
    public void requestSplash(boolean readCache, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder().append("module=openimage").toString())
                .cacheControl(readCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 验证(码)请求
     *
     * @param type           "register" 为注册页面模块验证码
     *                       "login" 为登录页面模块验证码
     *                       "post" 为发布主题和回帖模块验证码
     * @param resultCallback
     */
    public void requestSecure(String type, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4").append("module=secure&type=").append(type).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 发布(帖子,回复)权限验证
     *
     * @param fid
     * @param resultCallback
     */
    public void requestCheckingPost(String fid, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4").append("module=newthread&submodule=checkpost&fid=").append(fid).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * @param mime
     * @param file
     * @param uploadHash
     * @param fid
     * @param resultCallback
     */
    public void requestUploadFile(String mime, File file, String uploadHash, String fid, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4").append("module=forumupload&simple=1&fid=").append(fid).toString())
                .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("hash", uploadHash)
                        .addFormDataPart("uid", RedNetApp.getInstance().getUserLogin(false).getMember_uid())
                        .addFormDataPart("Filedata", file.getName(), RequestBody.create(MediaType.parse(mime), file))
                        .build())
                .tag(file.getAbsolutePath())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    //安全提问,验证码 需要获取响应cookie,saltkey(RequestHeader形式提交,?:cookie中的saltkey仅限当前登录用户)
    public void login(String cookie, String username, String password, String questionid, String answer, String sechash, String seccodeverify
            , Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder("1").append("module=login&loginsubmit=yes")
                        .append("&sechash=").append(sechash)
                        .append("&seccodeverify=").append(seccodeverify).toString())
                .post(new MultipartBuilder().type(MultipartBuilder.FORM)
                        .addFormDataPart("username", URLEncoder.encode(username))
                        .addFormDataPart("password", URLEncoder.encode(password))
                        .addFormDataPart("questionid", URLEncoder.encode(questionid))
                        .addFormDataPart("answer", URLEncoder.encode(answer)).build())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * @param platform
     * @param openid
     * @param nickname
     * @param resultCallback
     */
    public void requestLoginOtherPart(String platform, String openid, String nickname, Result<?> resultCallback) {
        try {
            Log.e("TAG", "qq登录=" + getStringBuilder()
                    .append("module=weiqqconnect&type=").append(platform)
                    .append("&openid=").append(openid)
                    .append("&username=").append(URLEncoder.encode(nickname, "GBK")).toString());

            RedNet.mHttpClient.newCall(new Request.Builder()
                    .url(getStringBuilder()
                            .append("module=weiqqconnect&type=").append(platform)
                            .append("&openid=").append(openid)
                            .append("&username=").append(URLEncoder.encode(nickname, "GBK")).toString())
                    .cacheControl(NO_STORE).build())
                    .enqueue(new ExtCallback(resultCallback));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解除第三方账号绑定
     *
     * @param member_loginstatus
     * @param resultCallback
     */
    public void requestUnbinding(String member_loginstatus, Result<?> resultCallback) {
        Log.e("TAG", "解除第三方账号绑定 =" + getStringBuilder("5").append("module=unbind&type=").append(member_loginstatus).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder("5").append("module=unbind&type=").append(member_loginstatus).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * @param key
     * @param page
     * @param resultCallback
     */
    public void requestSearch(String key, int page, Result<?> resultCallback) {
        try {
            Log.e("TAG", "搜索url = " + getStringBuilder().append("module=threadsearch&page=").append(page)
                    .append("&subject=").append(URLEncoder.encode(key, "GBK")).toString());
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .url(getStringBuilder().append("module=threadsearch&page=").append(page)
                            .append("&subject=").append(URLEncoder.encode(key, "GBK")).toString())
                    .cacheControl(NO_STORE).build())
                    .enqueue(new ExtCallback(resultCallback));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type           1加好友 2同意好友请求
     * @param uid
     * @param resultCallback
     */
    public void requestMakeFriend(String type, String uid, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder().append("module=addfriend&uid=").append(uid).append("&type=").append(type).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 获取用户信息
     *
     * @param readCache
     * @param uid
     * @param resultCallback
     */
    public void requestUserProfile(boolean readCache, String uid, Result<?> resultCallback) {
        Log.e("个人信息", "url=" + getStringBuilder("4").append("module=profile&uid=").append(uid).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
//                .url(getStringBuilder("4").append("module=profile&uid=").append(uid).toString())
                .url(getStringBuilder("4").append("&uid=").append(uid).toString())
                .cacheControl(readCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * @param readCache
     * @param uid            用于生成但前登录账号缓存key
     * @param resultCallback
     */
    public void requestUserFriends(boolean readCache, String uid, Result<?> resultCallback) {
        Log.e("TAG", "AppNetConfig.MYFRIEND+uid=" + AppNetConfig.MYFRIEND + uid);
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(AppNetConfig.MYFRIEND + uid)
                .cacheControl(readCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * @param readCache
     * @param uid            用于生成但前登录账号缓存key
     * @param page
     * @param resultCallback
     */
    /*我的主题*/
    public void requestMyThread(boolean readCache, String uid, int page, Result<?> resultCallback) {
        Log.e("我的主题", "我的主题 =" + getStringBuilder("4").append("module=mythread&page=").append(page).append("&uid=").append(uid).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4").append("module=mythread&page=").append(page).append("&uid=").append(uid).toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /*他的主题*/
    public void requestUserThread(boolean readCache, String uid, int page, Result<?> resultCallback) {
        Log.e("TAG", "他的主题 url =" + getStringBuilder("4")
                .append("module=userthread&uid=").append(uid)
                .append("&page=").append(page).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder("4")
                        .append("module=userthread&uid=").append(uid)
                        .append("&page=").append(page).toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /*我的回复*/
    public void requestUserReplies(boolean readCache, String uid, int page, Result<?> resultCallback) {
        Log.e("TAG", "我的回复 =" + getStringBuilder("4")
                .append("module=userpost&uid=").append(uid)
                .append("&page=").append(page).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder("4")
                        .append("module=userpost&uid=").append(uid)
                        .append("&page=").append(page).toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    //用户会话消息
    public void requestUserMessage(int page, Result<?> resultCallback) {
        Log.e("用户会话消息", "用户会话消息 =" + getStringBuilder("1")
                .append("module=mypm&checkavatar=0&page=").append(page).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("1")
                        .append("module=mypm&checkavatar=0&page=").append(page).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    //用户会话消息详情
    public void requestUserMessageInfo(String touid, int page, Result<?> resultCallback) {
        Log.e("用户会话消息详情", "用户会话消息详情 =" + getStringBuilder("4")
                .append("module=mypm&subop=view&touid=").append(touid)
                .append("&page=").append(page)
                .append("&smiley=no&convimg=2&checkavatar=1&android=1").toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4")
                        .append("module=mypm&subop=view&touid=").append(touid)
                        .append("&page=").append(page)
                        .append("&smiley=no&convimg=2&checkavatar=1&android=1").toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    //会话消息回复
    public void requestSendMessage(String touid, String username, String message, Result<?> resultCallback) {
        Log.e("TAG", "会话消息回复url=" + getStringBuilder("5").append("module=sendpm&pmsubmit=yes&pmid=0&touid=").append(touid).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("5").append("module=sendpm&pmsubmit=yes&pmid=0&touid=").append(touid).toString())
                .post(new FormEncodingBuilder().add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash())
                        .addEncoded("username", username)
                        .addEncoded("message", message).build())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    //(我的)系统消息
    public void requestCommonMessage(int page, Result<?> resultCallback) {
        Log.e("TAG", "系统消息url = " + getStringBuilder("1")
                .append("module=publicpm&page=").append(page).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("1")
                        .append("module=publicpm&page=").append(page).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    //(我的通知)帖子相关
    public void requestNotice(int page, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4")
                        .append("module=mynotelist&page=").append(page).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    //(我的通知)好友相关
    public void requestFriendNotice(int page, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4")
                        .append("module=mynotelist&&view=interactive&type=friend&page=").append(page).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * @param readCache
     * @param type           index(首页导航)|nextaddHeader（其他头部导航）| nextfooter（其他底部导航）
     * @param resultCallback
     */
    public void requestNavigation(boolean readCache, String type, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder().append("module=banner").append("&type=").append(type).toString())
                .cacheControl(readCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 导航内容列表 ---  非首页
     *
     * @param fid
     * @param page
     * @param resultCallback
     */
    public void requestNavigationCotentList(boolean readCache, String fid, int page, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder()
                        .append("module=bannerthreadlist")
                        .append("&fid=").append(fid)
                        .append("&page=").append(page).toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 导航内容列表 ---  首頁
     *
     * @param page
     * @param resultCallback
     */
    public void requestNavigationHomePageList(boolean readCache, int page, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder()
                        .append("module=indexthreadlist")
                        .append("&page=").append(page).toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));

    }

    /**
     * @param fid
     * @param page
     * @param checkpost
     * @param resultCallback
     */
    public void requestPostsOfForum(boolean readCache, String fid, int page, boolean checkpost, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4")
                        .append("module=forumdisplay&fid=").append(fid)
                        .append("&page=").append(page)
                        .append(checkpost ? "&submodule=checkpost&smiley=no&convimg=2" : "&smiley=no&convimg=2")
                        .append("&width=160&height=120")
                        .toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * @param page
     * @param tid
     * @param authorId
     * @param resultCallback
     */
    public void requestThreadDetails(int page, String tid, String authorId, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4").append("?module=viewthread&submodule=checkpost&width=360&height=480&checkavatar=1&tid=").append(tid)
                        .append("&page=").append(page)
                        .append(TextUtils.isEmpty(authorId) ? "" : ("&authorid=" + authorId)).toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 请求全部版块
     *
     * @param readCache
     * @param resultCallback
     */
    public void requestForum(boolean readCache, Result<?> resultCallback) {
        Log.e("全部版块", "url =" + getStringBuilder("4").append("module=forumindex").toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder("4").append("module=forumindex").toString())
//                .url("http://10.0.6.58/api/mobile/?module=forumindex&version=4&debug=1&mobiletype=IOS")
                .cacheControl(readCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 收藏版块
     *
     * @param fid
     * @param resultCallback
     */
    public void requestFavouriteForum(String fid, Result<?> resultCallback) {
        Log.e("TAG", "收藏版块url = " + getStringBuilder("4").append("module=favforum&android=1&id=").append(fid)
                .append("&favoritesubmit=true").toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
//                .url(getStringBuilder("4").append("module=favforum&android=1&id=").append(fid)
//                        .append("&favoritesubmit=true").toString())
                .url(AppNetConfig.COLLECTION_BANKUAI + "&id=" + fid)
                .post(new FormEncodingBuilder().add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash()).build())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 取消收藏板块
     *
     * @param favId
     * @param resultCallback
     */
    public void requestUnFavouriteForum(String favId, Result<?> resultCallback) {
        Log.e("TAG", "取消收藏板块 =" + getStringBuilder("4").append("module=unfavthread&android=1&favid=")
                .append(favId)
                .append("&favoritesubmit=true")
                .append("&formhash=").append(RedNetApp.getInstance().getUserLogin(false).getFormhash())
                .toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4").append("module=unfavthread&android=1&favid=")
                        .append(favId)
                        .append("&favoritesubmit=true")
                        .append("&formhash=").append(RedNetApp.getInstance().getUserLogin(false).getFormhash())
                        .toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    //收藏帖子
    public void requestFavouriteTThread(String tid, Result<?> resultCallback) {
        Log.e("TAG", "收藏帖子url =" + getStringBuilder("4").append("module=favthread&id=").append(tid).append("&favoritesubmit=true").toString());
        Request request = new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4").append("module=favthread&id=").append(tid).append("&favoritesubmit=true").toString())
                .post(new FormEncodingBuilder()
                        .add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash())
                        .build()).cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(new ExtCallback(resultCallback));
    }

    //取消收藏帖子
    public void requestUnFavouriteTThread(String favId, Result<?> resultCallback) {
        Log.e("TAG", "取消收藏帖子url=" + getStringBuilder("4")
                .append("&module=unfavthread&favid=").append(favId)
                .append("&op=delete&favoritesubmit=true")
                .append("&formhash=").append(RedNetApp.getInstance().getUserLogin(false).getFormhash()).toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4")
                        .append("&module=unfavthread&favid=").append(favId)
                        .append("&op=delete&favoritesubmit=true")
                        .append("&formhash=").append(RedNetApp.getInstance().getUserLogin(false).getFormhash()).toString())
                .cacheControl(NO_STORE).build()).enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 新的注册
     *
     * @param resultCallback
     */
    public void requestNewRegister(String openid, String type, String username, String password, String password2, String email, Result<?> resultCallback) {

        try {
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .url(getStringBuilder()
                            .append("&module=weiqqregister")
                            .append("&openid=").append(openid)
                            .append("&type=").append(type)
                            .append("&username=").append(URLEncoder.encode(username, "GBK"))
                            .append("&password=").append(password)
                            .append("&password2=").append(password2)
                            .append("&email=").append(email).toString())
                    .cacheControl(NO_STORE).build())
                    .enqueue(new ExtCallback(resultCallback));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定
     *
     * @param resultCallback
     */
    public void requestBinding(String openid, String type, String username, String password, Result<?> resultCallback) {
        try {
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .url(getStringBuilder()
                            .append("&module=weiqqlogin")
                            .append("&openid=").append(openid)
                            .append("&type=").append(type)
                            .append("&username=").append(URLEncoder.encode(username, "GBK"))
                            .append("&password=").append(password)
                            .toString())
                    .cacheControl(NO_STORE).build())
                    .enqueue(new ExtCallback(resultCallback));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 推荐板块
     *
     * @param readCache
     * @param resultCallback
     */
    public void requestForumHot(boolean readCache, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder("4").append("module=hotforum").toString())
                .cacheControl(readCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * (我)收藏的版块
     *
     * @param resultCallback
     */
    public void requstForumFav(Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(AppNetConfig.MYFAV)
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * (我)收藏的帖子
     *
     * @param resultCallback
     */
    public void requestThreadFav(Result<?> resultCallback) {
        Log.e("TAG", "(我)收藏的帖子 =" + getStringBuilder("4").append("module=myfavthread").toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4").append("module=myfavthread").toString())
                .cacheControl(NO_STORE).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 社区
     *
     * @param readCache
     * @param resultCallback
     */
    public void requestCommunity(boolean readCache, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder()
                        .append("module=portalbanner")
                        .append("&shownum=4").toString())
                .cacheControl(readCache ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 阅读
     *
     * @param readCache
     * @param view           hot人气社区|digest社区精华(取消)|new最新回复(取消)
     * @param page
     * @param resultCallback
     */
    public void requestReadingGuide(boolean readCache, String view, int page, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder()
                        .append("module=banread")
                        .append("&view=").append(view)
                        .append("&page=").append(page)
                        .toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /**
     * 阅读板块 -- 联盟  alliance
     * http://bbs.rednet.cn/plugin.php?id=wq_wechatcollecting&mod=api
     *
     * @param resultCallback
     */
    public void requestReadingAllianceGuide(boolean readCache, int page, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url("http://bbs.rednet.cn/plugin.php?id=wq_wechatcollecting&mod=api&page=" + page)
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie("http://bbs.rednet.cn/plugin.php?id=wq_wechatcollecting&mod=api&page=1");
        System.out.println("cookieStr:    " + CookieStr);
    }

    /**
     * 阅读板块 -- 微湖南
     * http://bbs.rednet.cn/plugin.php?id=wq_wechatcollecting&mod=api
     *
     * @param resultCallback
     */
    public void requestReadingMiniHNGuide(boolean readCache, int page, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url("http://bbs.rednet.cn/api/mobile/index.php?module=newpost&version=5&mobiletype=Android&onedigest=1&page=" + page)
//            .url("http://rednet.pm.comsenz-service.com/api/mobile/index.php?module=newpost&version=5&mobiletype=Android&onedigest=1")
//            .url(getStringBuilder()
//                .append("module=newpost")
//                .append("onedigest=1")
//                .append("&page=").append(page)
//                .toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));

    }

    /**
     * 红网红人
     *
     * @param readCache
     * @param page
     * @param resultCallback
     */
    public void requestPopularStars(boolean readCache, int page, Result<?> resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(getStringBuilder()
                        .append("module=diyuser")
                        .append("&page=").append(page)
                        .toString())
                .cacheControl(getCacheControl(readCache, page)).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    public void requestReplyThread(String tid, String message, String sechash, String seccodeverify, Result<?> resultCallback) {
        Log.e("TAG", "跟帖url = " + getStringBuilder("4")
                .append("module=sendreply&replysubmit=yes&tid=").append(tid)
                .append("&sechash=").append(sechash)
                .append("&seccodeverify=").append(seccodeverify)
                .toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
//                .url(getStringBuilder("4")
//                        .append("module=sendreply&replysubmit=yes&tid=").append(tid)
//                        .append("&sechash=").append(sechash)
//                        .append("&seccodeverify=").append(seccodeverify)
//                        .toString())
                .url(AppNetConfig.COMMENT)
                .post(new FormEncodingBuilder()
                        .add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash())
                        .add("mobiletype", "2")
                        .add("allownoticeauthor", "1")
                        .add("message", URLEncoder.encode(message)).build())
                .build())
                .enqueue(new ExtCallback(resultCallback));
    }

    public void postReport(String reportSelect,String threadUrl, String rid, String tid, String fid, Callback resultCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(new StringBuilder().append(AppNetConfig.BASEURL).append("?version=5&rtype=post&module=report&inajax=1")
                        .append("&formhash=").append(RedNetApp.getInstance().getUserLogin(false).getFormhash())
                        .append("&report_select=").append(reportSelect)
                        .append("&referer=").append(threadUrl) //帖子链接
                        .append("&rid=").append(rid) //帖子pid
                        .append("&tid=").append(tid) //帖子pid
                        .append("&fid=").append(fid) //帖子pid
                        .append("&reportsubmit = true")
                        .toString())
                .build())
                .enqueue(resultCallback);
    }

    /*引用跟帖*/
    public void requestMultiReplyThread(String tid, String reppid, String message, String noticetrimstr, String sechash, String seccodeverify
            , Result<?> resultCallback) {
        Log.e("TAG", "跟帖url = " + getStringBuilder("4")
                .append("module=sendreply&replysubmit=yes&tid=").append(tid)
                .append("&sechash=").append(sechash)
                .append("&seccodeverify=").append(seccodeverify)
                .toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4")
                        .append("module=sendreply&replysubmit=yes&tid=").append(tid)
                        .append("&sechash=").append(sechash)
                        .append("&seccodeverify=").append(seccodeverify)
                        .toString())
                .post(new FormEncodingBuilder()
                        .add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash())
                        .add("reppid", reppid)
                        .add("mobiletype", "2")
                        .add("allownoticeauthor", "1")
                        .add("message", URLEncoder.encode(message))
                        .add("noticetrimstr", URLEncoder.encode(noticetrimstr)).build())
                .build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /*发普通帖*/
    public void requestSendPost(String fid, String subject, String message, String typeid, String sechash, String seccodeverify, ArrayList<String> attachParams
            , Result<?> resultCallback) {
        Log.e("TAG", "普通帖url = " + getStringBuilder("4")
                .append("module=newthread")
                .append("&fid=").append(fid)
                .append("&topicsubmit=yes")
                .append(TextUtils.isEmpty(sechash) ? "" : ("&sechash=" + sechash))
                .append(TextUtils.isEmpty(seccodeverify) ? "" : ("&seccodeverify=" + seccodeverify))
                .toString());
        FormEncodingBuilder builder = new FormEncodingBuilder()
                .add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash())
                .add("subject", URLEncoder.encode(subject))
                .add("message", URLEncoder.encode(message))
                .add("mobiletype", "2")
                .add("allownoticeauthor", "1")
                .add("typeid", typeid);
        for (int i = 0, size = attachParams != null ? attachParams.size() : 0; i < size; i++) {
            builder.add(attachParams.get(i), "");
        }
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(new StringBuilder(AppNetConfig.THREAD + fid)
                        .append(TextUtils.isEmpty(sechash) ? "" : ("&sechash=" + sechash))
                        .append(TextUtils.isEmpty(seccodeverify) ? "" : ("&seccodeverify=" + seccodeverify))
                        .toString())
                .post(builder.build()).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /*发送活动帖*/
    public void requestSendActivityPost(String fid, String subject, String message, String attachId, String activityclass
            , String starttimefrom, String starttimeto, String activityplace, String activitynumber
            , String sechash, String seccodeverify, Result<?> resultCallback) {
        Log.e("TAG", "发活动帖url = " + getStringBuilder("4")
                .append("module=newactivity")
                .append("&fid=").append(fid)
                .append("&topicsubmit=yes")
                .append(TextUtils.isEmpty(sechash) ? "" : ("&sechash=" + sechash))
                .append(TextUtils.isEmpty(seccodeverify) ? "" : ("&seccodeverify=" + seccodeverify))
                .toString());
        FormEncodingBuilder builder = new FormEncodingBuilder()
                .add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash())
                .add("subject", URLEncoder.encode(subject))
                .add("message", URLEncoder.encode(message))
                .add("special", "4")
                .add("activityclass", URLEncoder.encode(activityclass))
                .add("activitytime", "1")
                .add("starttimefrom[1]", starttimefrom)
                .add("starttimeto", starttimeto)
                .add("activityplace", URLEncoder.encode(activityplace))
                .add("activitynumber", activitynumber)
                .add("mobiletype", "2")
                .add("allownoticeauthor", "1")
                .add("special", "4");
        if (!TextUtils.isEmpty(attachId))
            builder.add("attachId", attachId);
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4")
                        .append("module=newactivity")
                        .append("&fid=").append(fid)
                        .append("&topicsubmit=yes")
                        .append(TextUtils.isEmpty(sechash) ? "" : ("&sechash=" + sechash))
                        .append(TextUtils.isEmpty(seccodeverify) ? "" : ("&seccodeverify=" + seccodeverify))
                        .toString())
                .post(builder.build()).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    /*发送投票贴*/
    public void requestSendVotePost(String fid, String subject, String maxchoices
            , String visibilitypoll, String overt, String expiration, String[] polloption
            , String sechash, String seccodeverify, Result<?> resultCallback) {
        Log.e("TAG", "发送投票贴 url =" + getStringBuilder("4")
                .append("module=newpoll")
                .append("&fid=").append(fid)
                .append("&topicsubmit=yes")
                .append(TextUtils.isEmpty(sechash) ? "" : ("&sechash=" + sechash))
                .append(TextUtils.isEmpty(seccodeverify) ? "" : ("&seccodeverify=" + seccodeverify)));
        FormEncodingBuilder builder = new FormEncodingBuilder()
                .add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash())
                .add("subject", URLEncoder.encode(subject))
                .add("message", "")
                .add("maxchoices", maxchoices)
                .add("visibilitypoll", visibilitypoll)
                .add("overt", overt)
                .add("expiration", expiration)
                .add("mobiletype", "2")
                .add("allownoticeauthor", "1")
                .add("special", "1");
        for (int i = 0, length = polloption != null ? polloption.length : 0; i < length; i++) {
            builder.add("polloption[" + i + "]", URLEncoder.encode(polloption[i]));
        }
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
                .url(getStringBuilder("4")
                        .append("module=newpoll")
                        .append("&fid=").append(fid)
                        .append("&topicsubmit=yes")
                        .append(TextUtils.isEmpty(sechash) ? "" : ("&sechash=" + sechash))
                        .append(TextUtils.isEmpty(seccodeverify) ? "" : ("&seccodeverify=" + seccodeverify))
                        .toString())
                .post(builder.build()).build())
                .enqueue(new ExtCallback(resultCallback));
    }

    public static abstract class Result<V> {

        Context context;
        IFooter footer;//加载更多的脚部  有加载更多需要传此参数
        Class claz;
        boolean toast;//是否有吐司提示  只有失败才有   读缓存时传false
        boolean ignoreFormhash;  //只有读缓存时不刷新  传false
        V result;

        public Result(Context context, Class claz, boolean ignoreFormhash) {
            this.context = context;
            this.claz = claz;
            this.ignoreFormhash = ignoreFormhash;
        }

        public Result(Context context, Class claz, boolean ignoreFormhash, IFooter footer, boolean toast) {
            this.context = context;
            this.claz = claz;
            this.ignoreFormhash = ignoreFormhash;
            this.footer = footer;
            this.toast = toast;
        }

        /**
         * @param code  0,200,504
         * @param value IOException,String|Object,null
         */
        public abstract void onResult(int code, V value);

    }

    private static final class ExtCallback implements Callback {

        Result resultCallback;

        public ExtCallback(Result resultCallback) {
            this.resultCallback = resultCallback;
        }

        void sendMessage(int what, int arg1, int arg2, Object result) {
            resultCallback.result = result;
            ApiVersion5.sInternalHandler.sendMessage(Message.obtain(ApiVersion5.sInternalHandler
                    , what, arg1, arg2, resultCallback));
        }

        @Override
        public void onFailure(Request request, IOException e) {
            sendMessage(0, 0, 0, "请求失败,网络异常");
        }

        @Override
        public void onResponse(Response response) throws IOException {
            Log.e("TAG", "response=" + response.body().toString());
            if (resultCallback != null) {
                if (response.code() == 504) {//504表示请求成功 但是没有缓存
                    sendMessage(0, 504, 0, null);
                } else {
                    if (response.isSuccessful()) {
                        try {
                            String jsonResult = response.body().string();
                            LogUtils.i(jsonResult);
                            JSONObject jsonResultObj = new JSONObject(jsonResult);
                            if (jsonResultObj.has("error")) {
//                                sendMessage(0, 0, 0, jsonResultObj.optString("error"));
                            } else {
                                if (resultCallback.claz != null) {
                                    Method[] methods = resultCallback.claz.getMethods();
                                    for (Method method : methods) {
                                        if (method.getAnnotation(JSONParseMethod.class) != null) {
                                            sendMessage(1, response.code(), 0, method.invoke(resultCallback.claz, jsonResult));
                                            break;
                                        }
                                    }
                                } else {
//                                    sendMessage(1, response.code(), 0, jsonResult);
                                }
                            }
                        } catch (Exception e) {
                            sendMessage(0, 0, 0, "请求异常");
                        }
                    } else {
                        sendMessage(0, 0, 0, "请求失败" + response.code());
                    }
                }
            }
        }

    }

}
