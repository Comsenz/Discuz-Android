package cn.tencent.DiscuzMob.net;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.manager.DataLoaderCallback;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by AiWei on 2015/5/6.
 */
public class Api {

    private static Api sInstance;
    //正式环境
//    public final String URL = "http://bbs.rednet.cn/api/mobile/";
    public final String URL = AppNetConfig.BASEURL;
    //测试环境
//    public final String URL = "http://rednet.pm.comsenz-service.com/api/mobile/";

    private final String API_VERSION = "&version=4";
    private static final CacheControl NO_STORE = new CacheControl.Builder().noStore().noCache().build();

    //请求帖子详情2
    public void requestThreadDetails2(String tid, int page, boolean checkpost, String authorId, boolean onlyhost, DataLoaderCallback dataLoaderCallback) {
        String url;
        if (onlyhost) {
            url = URL + "?module=viewthread&tid=" + tid + (checkpost ? "&submodule=checkpost" : "") + API_VERSION + "&page=" + page + "&authorid=" + authorId + "&width=360&height=480";
            Log.e("TAG", "请求帖子详情1 =" + url);
        } else {
            url = URL + "?module=viewthread&tid=" + tid + (checkpost ? "&submodule=checkpost" : "") + API_VERSION + "&page=" + page + "&width=360&height=480&checkavatar=1";
            Log.e("TAG", "请求帖子详情2 =" + url);
        }
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey") + ";")
                .url(url).build())
                .enqueue(dataLoaderCallback);
    }

    String optString(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    //检查发帖权限
    public void requestCheckPost(String fid, DataLoaderCallback dataLoaderCallback) {
        Log.e("TAG", "检查发帖权限 =" + URL + "?module=newthread&fid=" + fid + "&submodule=checkpost&debug=1" + API_VERSION);
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(URL + "?module=newthread&fid=" + fid + "&submodule=checkpost&debug=1" + API_VERSION)
                .cacheControl(NO_STORE).build())
                .enqueue(dataLoaderCallback);
    }

    //验证码请求
    public void requestSecure(String type, DataLoaderCallback dataLoaderCallback) {
        LogUtils.e("TAG", "验证码请求接口=" + URL + "?module=secure&type=" + type + API_VERSION);
        final Request request = new Request.Builder()
                .url(AppNetConfig.BASEURL + "?module=secure&type=" + type + "&version=5")
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    public void requestForumThreadType(String fid, DataLoaderCallback dataLoaderCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(URL + "?module=newthread&fid=" + fid + API_VERSION)
                .cacheControl(NO_STORE).build())
                .enqueue(dataLoaderCallback);
    }

    //请求我的好友
    public void requestMyFriends(DataLoaderCallback dataLoaderCallback) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .url(URL + "?module=friend" + API_VERSION + "&checkavatar=1")
                .cacheControl(NO_STORE).build())
                .enqueue(dataLoaderCallback);
    }

    //请求我的短消息
    public void requestMyMessage(DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder()
                .url(URL + "?module=mypm&version=4&android=1&checkavatar=1")
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //请求我的系统短消息
    public void requestMyPublicMessage(DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder()
                .url(URL + "?module=publicpm&version=1")
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    public void requestPmInfo(String touid, DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder()
                .url(URL + "?module=mypm&version=1&subop=view&touid=" + touid)
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //请求跟某人的短消息列表
    public void requestPM(String touid, int page, DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder()
                .url(URL + "?module=mypm&version=4&subop=view&perpage=10&touid=" + touid + "&page=" + page + "&smiley=no&convimg=2&checkavatar=1&android=1")
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //请求通知详情
    public void requestNoticeLists(DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder()
                .url(URL + "?module=mynotelist" + API_VERSION)
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //请求我的好友
    public void requestSmiley(DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder().url(URL + "?module=smiley" + API_VERSION).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //请求我的帖子
    public void requestMyThread(String uid, int page, DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder().url(URL + "?module=mythread&uid=" + uid + API_VERSION + "&page=" + page).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //给某人发送短消息
    public void reuestSendMessage(String username, String content, DataLoaderCallback dataLoaderCallback) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash())
                .add("username", username)
                .add("message", content)
                .build();
        Request request = new Request.Builder().url(URL + "?module=sendpm&touid=0&pmid=0&pmsubmit=yes" + API_VERSION + "&android=1").post(formBody).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //举报帖子
    public void requestReport(HashMap<String, Object> data, DataLoaderCallback dataLoaderCallback) {
        Log.e("TAG", "举报帖子=" + URL + "?module=report" + API_VERSION);
        RequestBody formBody = new FormEncodingBuilder()
//                .add("formhash", CacheUtils.getString(RedNetApp.getInstance(), "formhash"))
                .add("message", optString((String) data.get("report_select")))
                .add("referer", optString((String) data.get("referer")))
                .add("rid", optString((String) data.get("rid")))
                .add("tid", optString((String) data.get("tid")))
                .add("fid", optString((String) data.get("fid")))
                .add("rtype", optString((String) data.get("rtype")))
                .add("inajax", optString((String) data.get("rtype")))
                .add("reportsubmit", "true")
                .add("formhash",optString((String) data.get("formhash")))
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey") + ";").url(URL + "?module=report" + "&version=5").post(formBody).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }


    // 对主题点赞
    public void requestRecommendThread(String tid, DataLoaderCallback dataLoaderCallback) {
        Log.e("TAG", "帖子点赞 url =" + URL + "?module=recommend&do=add&tid=" + tid + "&hash=" + RedNetApp.getInstance().getUserLogin(false).getFormhash() + API_VERSION);
        Request request = new Request.Builder()
                .url(URL + "?module=recommend&do=add&tid=" + tid + "&hash=" + RedNetApp.getInstance().getUserLogin(false).getFormhash() + API_VERSION)
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //引用回复时获取引用内容
    public void requestGetReplyString(String tid, String reppid, DataLoaderCallback dataLoaderCallback) {
        Log.e("TAG", "引用回复时获取引用内容 =" + URL + "?module=sendreply&tid=" + tid + "&repquote=" + reppid + "&version=3");
        Request request = new Request.Builder()
                .url(URL + "?module=sendreply&tid=" + tid + "&repquote=" + reppid + "&version=3")
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    // 参加活动
    public void requestJoinActivity(String fid, String tid, HashMap<String, String> data, String message, DataLoaderCallback dataLoaderCallback) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("formhash", RedNetApp.getInstance().getUserLogin(false).getFormhash());
        builder.add("apply[message]", message);
        builder.add("tid", tid);
        Iterator iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            builder.add("userfield[" + key + "]", val);
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey") + ";").url(URL + "?module=activityapply&topicsubmit=yes&fid=" + fid + "tid=" + tid + "&version=5").post(formBody).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    // 取消参加活动
    public void requestDelapplyeActivity(String tid, DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey") + ";")
                .url(URL + "?module=activityapply&y&op=delapply&topicsubmit=yes&tid=" + tid + "&version=5")
                .cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //投票
    public void requestVotePoll(String[] value, String fid, String tid, DataLoaderCallback dataLoaderCallback) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            sb.append(value[i]).append("|");
        }
        String result = sb.toString().substring(0, sb.toString().length() - 1);
        builder.add("pollanswers", result);
        builder.add("type", "poll");
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey") + ";")
                .url(URL + "module=pollpotion&topicsubmit=yes&fid=" + fid + "&tid=" + tid + "&version=5").post(formBody).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //查看投票选项
    public void requestVoterPollpotion(String tid, DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder().url(URL + "?module=forummisc&action=viewvote&version=5&tid=" + tid ).cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //查看投票人
    public void requestVoter(String tid, String pollid, DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder().url(URL + "?module=forummisc&action=viewvote&version=5&tid=" + tid + "&polloptionid=" + pollid + "&version=5").cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //查看用户的回复
    public void requestUserReply(String uid, int page, DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder().url(URL + "?module=userpost&uid=" + uid + "&page=" + page + API_VERSION).cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    //查看帖子单条回复
    public void requestSingleReply(String tid, String pid, DataLoaderCallback dataLoaderCallback) {
        Request request = new Request.Builder().url(URL + "?module=viewthread&tid=" + tid + "&viewpid=" + pid + API_VERSION).cacheControl(NO_STORE).build();
        RedNet.mHttpClient.newCall(request).enqueue(dataLoaderCallback);
    }

    public static final Api getInstance() {
        if (sInstance == null) {
            sInstance = new Api();
            RedNet.mHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
            RedNet.mHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
        }
        return sInstance;
    }

}
