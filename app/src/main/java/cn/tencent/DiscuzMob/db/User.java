package cn.tencent.DiscuzMob.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.MyFavForumBean;
import cn.tencent.DiscuzMob.model.MyFavThreadBean;
import cn.tencent.DiscuzMob.model.UserInfoBean;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by AiWei on 2016/5/27.
 */
public class User implements Parcelable {

    public static final Uri URI = new Uri.Builder().scheme("content").authority(RednetContentProvider.authority).path("user").build();

    private String cookiepre;
    private String auth;
    private String saltkey;
    private String member_uid;
    private String member_username;
    private String formhash;
    private String member_loginstatus;//当前绑定的第三方账号,member_loginstatus={0,qq,weixin}

    public static final class Table {
        public static final String SQL_CREATE_TABLE = "create table user(_id INTEGER PRIMARY KEY,member_uid text not null unique,member_username text,cookiepre text,auth text,saltkey text,member_loginstatus text,formhash text)";
        public static final String SQL_DROP_TABLE = "drop table user";
    }

    public User() {
    }

    private User(Cursor cursor) {
        cookiepre = cursor.getString(cursor.getColumnIndex("cookiepre"));
        auth = cursor.getString(cursor.getColumnIndex("auth"));
        saltkey = cursor.getString(cursor.getColumnIndex("saltkey"));
        member_uid = cursor.getString(cursor.getColumnIndex("member_uid"));
        member_username = cursor.getString(cursor.getColumnIndex("member_username"));
//        member_loginstatus = cursor.getString(cursor.getColumnIndex("member_loginstatus"));
        member_loginstatus = cursor.getString(cursor.getColumnIndex("member_loginstatus"));
        formhash = cursor.getString(cursor.getColumnIndex("formhash"));
    }

    private User(Parcel source) {
        cookiepre = source.readString();
        auth = source.readString();
        saltkey = source.readString();
        member_uid = source.readString();
        member_username = source.readString();
        member_loginstatus = source.readString();
        formhash = source.readString();
    }

    public static ContentValues getContentValues(UserInfoBean.VariablesBean variables){
        if (variables != null) {
            ContentValues values = new ContentValues();
//            values.put("cookiepre", variables.getString("cookiepre"));
            values.put("cookiepre", variables.getCookiepre());
//            values.put("auth", variables.getString("auth"));
            values.put("auth", variables.getAuth());
//            values.put("saltkey", variables.getString("saltkey"));
            values.put("saltkey", variables.getSaltkey());
//            values.put("member_uid", variables.getString("member_uid"));
            values.put("member_uid", variables.getMember_uid());
//            values.put("member_username", variables.getString("member_username"));
            values.put("member_username", variables.getMember_username());
//            values.put("member_loginstatus", variables.getString("member_loginstatus"));
            values.put("member_loginstatus", "1");
//            values.put("formhash", variables.getString("formhash"));
            values.put("formhash", variables.getFormhash());
            return values;
        } else
            return null;
    }

    public static User newInstance(Cursor cursor) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                User user = new User(cursor);
                cursor.close();
                return user;
            } else {
                return null;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void prepareForum(final Context context, final Callback callback) {
        String cookiepre_auth = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth");
        String cookiepre_saltkey = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey");
        String formhash = CacheUtils.getString(RedNetApp.getInstance(), "formhash");
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth+";"+cookiepre_saltkey+";")
                .url(AppNetConfig.MYFAV)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Toast.makeText(RedNetApp.getInstance(), "我收藏的版块请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        MyFavForumBean myFavForumBean = new Gson().fromJson(response.body().string(), MyFavForumBean.class);
                        MyFavForumBean.VariablesBean variables = myFavForumBean.getVariables();
                        if (variables != null) {
                            List<MyFavForumBean.VariablesBean.ListBean> list = variables.getList();

                            int count;
                            if (list != null && !list.isEmpty()) {
                                Log.e("TAG", "list2="+list.size());
                                ContentValues[] values = new ContentValues[list.size()];
                                int i = 0;
                                for (MyFavForumBean.VariablesBean.ListBean model : list) {
                                    values[i++] = UserForum.getContentValues(model);
                                }
                                count = context.getContentResolver().bulkInsert(UserForum.URI, values);
                            } else {
                                count = context.getContentResolver().delete(UserForum.URI, null, null);
                            }
                            if (callback != null) {
                                callback.onCallback(count);
                            }
                        } else {
                            if (callback != null) {
                                callback.onCallback(null);
                            }
                        }

                    }
                });
//        ApiVersion5.INSTANCE.requstForumFav(new ApiVersion5.Result<Object>(context, ForumFavVariables.class, false, null, true) {
//            @Override
//            public void onResult(int code, Object value) {
//                if (value instanceof BaseModel) {
//                    ForumFavVariables variables = ((BaseModel<ForumFavVariables>) value).getVariables();
//                    if (variables != null) {
//                        List<FavModel> list = variables.getList();
//                        int count;
//                        if (list != null && !list.isEmpty()) {
//                            ContentValues[] values = new ContentValues[list.size()];
//                            int i = 0;
//                            for (FavModel model : list) {
//                                values[i++] = UserForum.getContentValues(model);
//                            }
//                            count = context.getContentResolver().bulkInsert(UserForum.URI, values);
//                        } else {
//                            count = context.getContentResolver().delete(UserForum.URI, null, null);
//                        }
//                        if (callback != null) {
//                            callback.onCallback(count);
//                        }
//                    }
//                } else {
//                    if (callback != null) {
//                        callback.onCallback(null);
//                    }
//                }
//            }
//        });
    }

    public static void prepareThread(final Context context, final Callback callback) {
        String cookiepre_auth = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth");
        String cookiepre_saltkey = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey");
        String formhash = CacheUtils.getString(RedNetApp.getInstance(), "formhash");
        OkHttpUtils
                .get()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";" + formhash + ";")
                .url(AppNetConfig.MYFAVTHREAD)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(RedNetApp.getInstance(), "我收藏的帖子请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
//                        Log.e("TAG", "我收藏的帖子 = " + response);
                        MyFavThreadBean myFavThreadBean = new Gson().fromJson(response, MyFavThreadBean.class);
                        MyFavThreadBean.VariablesBean variables = myFavThreadBean.getVariables();
                        if (variables != null) {
                            List<MyFavThreadBean.VariablesBean.ListBean> list = variables.getList();
                            int count;
                            if (list != null && !list.isEmpty()) {
                                ContentValues[] values = new ContentValues[list.size()];
                                int i = 0;
                                for (MyFavThreadBean.VariablesBean.ListBean model : list) {
                                    values[i++] = UserThread.getContentValues(model);
                                }
                                count = context.getContentResolver().bulkInsert(UserThread.URI, values);
                            } else {
                                count = context.getContentResolver().delete(UserThread.URI, null, null);
                            }
                            if (callback != null) {
                                callback.onCallback(count);
                            }
                        } else {
                            if (callback != null) {
                                callback.onCallback(null);
                            }
                        }
                    }
                });
//        ApiVersion5.INSTANCE.requestThreadFav(new ApiVersion5.Result<Object>(context, ThreadFavVariables.class, false, null, true) {
//            @Override
//            public void onResult(int code, Object value) {
//                if (value instanceof BaseModel) {
//                    ThreadFavVariables variables = ((BaseModel<ThreadFavVariables>) value).getVariables();
//                    if (variables != null) {
//                        List<FavModel> list = variables.getList();
//                        int count;
//                        if (list != null && !list.isEmpty()) {
//                            ContentValues[] values = new ContentValues[list.size()];
//                            int i = 0;
//                            for (FavModel model : list) {
//                                values[i++] = UserThread.getContentValues(model);
//                            }
//                            count = context.getContentResolver().bulkInsert(UserThread.URI, values);
//                        } else {
//                            count = context.getContentResolver().delete(UserThread.URI, null, null);
//                        }
//                        if (callback != null) {
//                            callback.onCallback(count);
//                        }
//                    }
//                } else {
//                    if (callback != null) {
//                        callback.onCallback(null);
//                    }
//                }
//            }
//        });
    }

    public String getCookie() {
        return new StringBuilder(cookiepre).append("auth=").append(URLEncoder.encode(auth)).append(";").toString();
//        return new StringBuilder(cookiepre).append("auth=").append(auth).append(";")
//                .append(cookiepre).append("saltkey=").append(saltkey).append(";").append("path=/; domain=lm.rednet.cn").toString();

//        return new StringBuilder(cookiepre).append("auth=").append(auth).append(";").toString();

//        return new StringBuilder(cookiepre).append("auth=").append(auth).append(";").append("path=/; domain=lm.rednet.cn").toString();
//
    }

    public String getCookie2() {
        return new StringBuilder(cookiepre).append("saltkey=").append(URLEncoder.encode(saltkey)).append(";").toString();
    }

    public String getCookie3() {
        return new StringBuilder("").append("path=/").append(";").toString();
    }

    public String getCookie4() {
        return new StringBuilder("").append("domain=.rednet.cn").append(";").toString();
    }

    public String getCookiepre() {
        return cookiepre;
    }

    public void setCookiepre(String cookiepre) {
        this.cookiepre = cookiepre;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSaltkey() {
        return saltkey;
    }

    public void setSaltkey(String saltkey) {
        this.saltkey = saltkey;
    }

    public String getMember_uid() {
        return member_uid;
    }

    public void setMember_uid(String member_uid) {
        this.member_uid = member_uid;
    }

    public String getMember_username() {
        return member_username;
    }

    public void setMember_username(String member_username) {
        this.member_username = member_username;
    }

    public String getMember_loginstatus() {
        return member_loginstatus;
    }

    public void setMember_loginstatus(String member_loginstatus) {
        this.member_loginstatus = member_loginstatus;
    }

    public synchronized String getFormhash() {
        return formhash;
    }

    public synchronized void setFormhash(String formhash) {
        this.formhash = formhash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cookiepre);
        dest.writeString(auth);
        dest.writeString(saltkey);
        dest.writeString(member_uid);
        dest.writeString(member_username);
        dest.writeString(member_loginstatus);
        dest.writeString(formhash);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
