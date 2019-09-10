package cn.tencent.DiscuzMob.ui.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.login.LoginApi;
import cn.sharesdk.login.OnLoginListener;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.User;
import cn.tencent.DiscuzMob.model.HotTThread;
import cn.tencent.DiscuzMob.model.Image;
import cn.tencent.DiscuzMob.model.UserInfoBean;
import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.net.RequestParams;
import cn.tencent.DiscuzMob.ui.activity.LoginActivity;
import cn.tencent.DiscuzMob.ui.activity.RelevanceLoginActivity;
import cn.tencent.DiscuzMob.ui.bean.BindBean;
import cn.tencent.DiscuzMob.utils.GsonUtil;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.ToastUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.AsyncImageView;

/**
 * Created by AiWei on 2015/5/11.
 * 板块列表
 */
public class ActivityBindAdapter extends BaseAdapter {

    private List<BindBean.VariablesBean.UsersBean> mThreads;
    Context mContext;
    String mformhash;

    public ActivityBindAdapter(Context context, List<BindBean.VariablesBean.UsersBean> list, String formhash) {
        mThreads = list;
        mContext = context;
        mformhash = formhash;
    }

    public void setThreads(ArrayList<BindBean.VariablesBean.UsersBean> mThreads) {
        if (mThreads != null) {
            this.mThreads = mThreads;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mThreads == null ? 0 : mThreads.size();
    }

    @Override
    public Object getItem(int position) {
        return mThreads.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bind_item, parent, false);
            holder.item_name = (TextView) convertView.findViewById(R.id.item_name);
            holder.item_isbind = (TextView) convertView.findViewById(R.id.item_isbind);
            holder.item_image = (ImageView) convertView.findViewById(R.id.item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BindBean.VariablesBean.UsersBean thread = mThreads.get(position);
        switch (thread.getType()) {
            case "qq":
                holder.item_name.setText("QQ");
                holder.item_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_qq));
                bindtext(position, holder, "qq");
                break;
            case "weixin":
                holder.item_name.setText("微信");
                holder.item_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wechat));
                bindtext(position, holder, "weixin");
                break;
            case "minapp":
                holder.item_name.setText("小程序");
                holder.item_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wechat));
                bindtext(position, holder, "minapp");
                break;
        }

        return convertView;
    }

    private void bindtext(int position, ViewHolder holder, final String type) {
        if (mThreads.get(position).getStatus().equals("1")) {
            holder.item_isbind.setText("解绑");
            holder.item_isbind.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.item_isbind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cookie1 = CacheUtils.getString(RedNetApp.getInstance(), "cookie1");
                    RedNet.mHttpClient.newCall(new Request.Builder()
                            .addHeader("Cookie", cookie1)
                            .url(Api.getInstance().URL + "?module=oauths&op=unbind&version=5")
                            .post(new MultipartBuilder().type(MultipartBuilder.FORM)
                                    .addFormDataPart("unbind", "yes")
                                    .addFormDataPart("type", type)
                                    .addFormDataPart("formhash", mformhash).build())
                            .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
                                    LogUtils.i("111111111");
                                }

                                @Override
                                public void onResponse(Response response) throws IOException {
                                    String json = response.body().string();
                                    LogUtils.i(json);
                                }
                            });
                }
            });
        } else {
            holder.item_isbind.setText("绑定");
            holder.item_isbind.setTextColor(mContext.getResources().getColor(R.color.blue));
            holder.item_isbind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login(type.equals("qq") ? QQ.NAME : Wechat.NAME);//isQQ ? "get_simple_userinfo" : "snsapi_userinfo"
                }
            });
        }
    }

    private String platform;
    private static volatile boolean isWecharCallTimeout;
    private String icon;
    private String nickname;
    private String openId;
    private String platformName;

    private void login(String platform) {
        LoginApi api = new LoginApi();
        this.platform = platform;
        api.setPlatform(platform);//设置登陆的平台后执行登陆的方法
        api.setOnLoginListener(mOnLoginListner);
        api.login(mContext);
    }

    private OnLoginListener mOnLoginListner = new OnLoginListener() {
        @Override
        public void onResult(boolean success, final String platform, final Bundle res) {
            Log.e("TAG", "onResult: success=" + success + " platform=" + platform);
            if (Wechat.NAME.equalsIgnoreCase(platform)) {
                if (isWecharCallTimeout)
                    return;
                else
                    isWecharCallTimeout = true;
            }
            if (success && res != null && res.size() > 0) {
                openId = res.getString("openId");
                platformName = res.getString("platform");
                nickname = res.getString("nickname");
                icon = res.getString("icon");
                if (QQ.NAME.equals(platformName)) {
                    login(true, openId, "", "qq", nickname, icon);
                } else if (Wechat.NAME.equals(platformName)) {
//                        Log.e("TAG", "微信");
                    Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
                    final String unionid = weChat.getDb().get("unionid");
                    login(true, openId, unionid, "weixin", nickname, icon);
                } else {
                }
            } else {
            }
        }
    };
    String seccodeverify;
    String sechash;

    public void login(boolean isthird, final String openId, final String unionid, final String type, final String nickname, final String icon) {
        RequestBody body = null;
        String questionId = "";
        String answer = "";
        String formhash = "";
        String seccodeCookie = "";
        String url = "";
        String username,password;
        seccodeverify = CacheUtils.getString(mContext,"seccodeverify");
        sechash = CacheUtils.getString(mContext,"sechash");
        formhash = CacheUtils.getString(mContext, "formhash");
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (!TextUtils.isEmpty(questionId)&&!TextUtils.isEmpty(answer)){
            builder.add("questionid", questionId)
                    .add("answer", answer);
        }
        if (!TextUtils.isEmpty(openId)){
            builder.add("openid", openId);
        }
        if (!TextUtils.isEmpty(unionid)){
            builder.add("openid", openId);
            builder.add("unionid", unionid);
        }
        seccodeCookie = RedNetPreferences.getSeccodeCookie();
        body = builder
                .add("sechash", sechash)
                .add("seccodeverify", seccodeverify)
                .add("loginsubmit","yes")
                .build();
        url = AppNetConfig.LOGIN + "&type=" + type;
        LogUtils.i(url + "  body: " + body.toString());
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetPreferences.getVcodeCookie() + ";" + RedNetPreferences.getSaltkey() + ";" + seccodeCookie + ";")
                .url(url)
                .post(body)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        ToastUtils.showToast("登录失败");
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String string = response.body().string();
                        LogUtils.i(string);
                        if (null != string) {
                            try {
                                Headers headers = response.request().headers();
                                String cookie1 = headers.get("Cookie");
                                CacheUtils.putString(mContext, "cookie1", cookie1);
                                JSONObject jsonObject = new JSONObject(string);
                                Log.e("TAG", "登陆成功jsonObject =" + jsonObject.toString());
                                if (null != jsonObject) {
                                    JSONObject variables = jsonObject.getJSONObject("Variables");
                                    JSONObject message = jsonObject.getJSONObject("Message");
                                    if (null != message) {
                                        String messagestr = message.getString("messagestr");
                                        String messageval = message.getString("messageval");
                                        if (messageval != null && (messageval.contains("login_succeed") || messageval.contains("succeed"))) {
                                            UserInfoBean userInfoBean = new Gson().fromJson(jsonObject.toString(), UserInfoBean.class);
                                            ContentValues values = User.getContentValues(userInfoBean.getVariables());
                                            return;
                                        } else if (messageval != null && messageval.contains("no_bind")) {
//                                            RelevanceLoginActivity.ComeIn(LoginActivity.this,openId,nickname,unionid,type);
                                        } else {
                                            ToastUtils.showToast(messagestr);
                                        }
                                    } else {
                                        ToastUtils.showToast("登录失败");
                                    }
                                } else {
                                    ToastUtils.showToast("登录失败");
                                }
                            } catch (JSONException e) {
                                ToastUtils.showToast("请求异常");
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showToast("登录失败");
                        }
                    }
                });
    }

    static class ViewHolder {
        TextView item_name, item_isbind;
        ImageView item_image;
    }
}
