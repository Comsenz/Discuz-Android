package cn.tencent.DiscuzMob.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.ProfileInfo;
import cn.tencent.DiscuzMob.ui.Event.RefreshUserInfo;
import cn.tencent.DiscuzMob.ui.activity.BindManagerActivity;
import cn.tencent.DiscuzMob.ui.activity.FootprintActivity;
import cn.tencent.DiscuzMob.ui.activity.MyFavritesActivity;
import cn.tencent.DiscuzMob.ui.activity.MyNewsActivity;
import cn.tencent.DiscuzMob.ui.activity.ToolbarActivity;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.widget.ProfileItemView;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.ProfileCredit;
import cn.tencent.DiscuzMob.model.UserInfoBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.MyFriendActivity;
import cn.tencent.DiscuzMob.ui.activity.PostThreadActivity;
import cn.tencent.DiscuzMob.ui.activity.RednetMainActivity;
import cn.tencent.DiscuzMob.ui.dialog.ProgressDialog;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.R;

import static android.app.Activity.RESULT_OK;
import static cn.tencent.DiscuzMob.utils.RednetUtils.REQUEST_CODE_LOGIN;

/**
 * Created by AiWei on 2015/5/12.
 * 个人中心
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ViewAnimator mViewAnimator;
    private NestedSwipeRefreshLayout mRefreshView;
    private AsyncRoundedImageView mAvatar;
    private TextView mName, mLevel, mRegisterTime, mThreadCount, mReplyCount;
    private View mMyFriendView, mMyFavView, mMyNotice, mMyThread, mMyReply, mReleaseBinding, mLogoutView;
    private LinearLayout mGroupLayout, mValueLayout;
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    String cookie1;
    String member_conisbind;
    String member_weixinisbind;
    private String type = null;
//    private RelativeLayout rl_footprint;
    String unionid;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (variables != null) {
                        createViewByData(variables);
                    }

                    break;
                case 2:
                    mRefreshView.setRefreshing(true);
                    onRefresh();
                    break;
                case 3:
                    mViewAnimator.setDisplayedChild(0);
                    break;
                case 4:
                    Toast.makeText(RedNetApp.getInstance(), "请求异常", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    handler.sendEmptyMessage(5);
                    break;
                case 6:
                    Toast.makeText(RedNetApp.getInstance(), "网络请求失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
        onRefresh();
    }

    RelativeLayout bind_relative;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        mViewAnimator = (ViewAnimator) view.findViewById(R.id.va);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRefreshView.setOnRefreshListener(this);
        mAvatar = (AsyncRoundedImageView) view.findViewById(R.id.avatar_preview);
        mName = (TextView) view.findViewById(R.id.my_name);
        mLevel = (TextView) view.findViewById(R.id.my_level);
        bind_relative = (RelativeLayout) view.findViewById(R.id.bind_relative);

        mRegisterTime = (TextView) view.findViewById(R.id.register_time_text);
        mThreadCount = (TextView) view.findViewById(R.id.thread_count_text);
        mReplyCount = (TextView) view.findViewById(R.id.reply_count_text);
        mGroupLayout = (LinearLayout) view.findViewById(R.id.group_Layout);
        mValueLayout = (LinearLayout) view.findViewById(R.id.value_Layout);
        mMyFriendView = view.findViewById(R.id.my_friend);
        mMyFavView = view.findViewById(R.id.my_collection);
        mMyNotice = view.findViewById(R.id.my_remind);
        mMyThread = view.findViewById(R.id.my_topic);
        mMyReply = view.findViewById(R.id.my_reply);
        view.findViewById(R.id.my_thread).setOnClickListener(this);
        mReleaseBinding = view.findViewById(R.id.release_binding);
        mLogoutView = view.findViewById(R.id.logout_view);
//        rl_footprint = (RelativeLayout) view.findViewById(R.id.rl_footprint);
//        rl_footprint.setOnClickListener(this);
        onRefresh();
        mViewAnimator.setDisplayedChild(1);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mRefreshView.setOnRefreshListener(this);
        mMyFriendView.setOnClickListener(this);
        mMyFavView.setOnClickListener(this);
        mMyNotice.setOnClickListener(this);
        mMyThread.setOnClickListener(this);
        mMyReply.setOnClickListener(this);
        mReleaseBinding.setOnClickListener(this);
        mLogoutView.setOnClickListener(this);
        mViewAnimator.getChildAt(1).setOnClickListener(this);
        cookiepre_auth = CacheUtils.getString(getActivity(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(getActivity(), "cookiepre_saltkey");
        formhash = CacheUtils.getString(getActivity(), "formhash");
        cookie1 = CacheUtils.getString(getActivity(), "cookie1");
        onRefresh();
    }

    com.alibaba.fastjson.JSONObject jsonObject;
    UserInfoBean.VariablesBean variables;

    private void getUserInfoData() {
        String cookie1 = CacheUtils.getString(RedNetApp.getInstance(), "cookie1");
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookie1)
                .url(AppNetConfig.USERINFO)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        handler.sendEmptyMessage(6);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        jsonObject = JSON.parseObject(response.body().string());
                        com.alibaba.fastjson.JSONObject iwechat_user = jsonObject.getJSONObject("iwechat_user");
                        if (null != iwechat_user && !TextUtils.isEmpty(iwechat_user.toString())) {
                            unionid = iwechat_user.getString("unionid");
                            Log.e("TAG", "unionid=" + unionid);
                        }
                        UserInfoBean userInfoBean = null;
                        try {
                            String json = jsonObject.toString();
                            LogUtils.d("profile=" + json);
                            userInfoBean = new Gson().fromJson(json, UserInfoBean.class);
                            LogUtils.d("MeFragment=" + jsonObject.getJSONObject("Variables").toString());

                            if (mRefreshView != null) {
                                if (userInfoBean != null) {
                                    variables = userInfoBean.getVariables();
                                    handler.sendEmptyMessage(1);

                                } else {
                                    handler.sendEmptyMessage(2);
                                }
                                handler.sendEmptyMessage(3);

                            }
                        } catch (JsonSyntaxException e) {
                            handler.sendEmptyMessage(4);
                            e.printStackTrace();
                        }
                    }
                });
    }

    void initToolbar() {
        if (getSupportToolbar() != null) {
            getSupportToolbar().show(ToolbarActivity.Toolbar.DEFAULTS);
            getSupportToolbar().title.setText(getString(R.string.my));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initToolbar();
        }
        hasLogin();
    }

    @Override
    public boolean hasLogin() {
        boolean hasLogin = super.hasLogin();
        if (mViewAnimator != null) {
            if (!hasLogin && mViewAnimator.getDisplayedChild() != 1) {
                mViewAnimator.setDisplayedChild(1);
            } else if (hasLogin && mViewAnimator.getDisplayedChild() != 0) {//onActivityResult
                mViewAnimator.setDisplayedChild(0);
            }
        }
        return hasLogin;
    }

    void logout() {
        CacheUtils.putBoolean(getActivity(), "login", false);
        CacheUtils.putBoolean(RedNetApp.getInstance(), "otherLogin", false);
        CacheUtils.clean(RedNetApp.getInstance());
        RedNetApp.getInstance().logout();
        RednetUtils.login(getActivity());
        ((RednetMainActivity) getActivity()).removeMePage();
        EventBus.getDefault().post(new RefreshUserInfo("1"));
    }

    ProgressDialog progressDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_friend:
                startActivity(new Intent(getActivity(), MyFriendActivity.class));
                break;
            case R.id.my_collection:
                startActivity(new Intent(getActivity(), MyFavritesActivity.class));
                break;
            case R.id.my_remind:
                startActivity(new Intent(getActivity(), MyNewsActivity.class));
                break;
            case R.id.release_binding:
                showLogout("是否解除已绑定的" + getBindingName(unionid) + "账号", new Callback<Integer>() {
                    @Override
                    public void onCallback(Integer obj) {
                        if (obj == R.id.yes) {
                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.show();
                            RedNet.mHttpClient.newCall(new Request.Builder()
                                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                                    .url(new StringBuilder(AppNetConfig.BASEURL + "?version=5&android=1&debug=1&module=unbind&type=").append(type).toString())
                                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                                    .enqueue(new com.squareup.okhttp.Callback() {
                                        @Override
                                        public void onFailure(Request request, IOException e) {
                                            handler.sendEmptyMessage(5);
                                        }

                                        @Override
                                        public void onResponse(Response response) throws IOException {
                                            progressDialog.dismiss();
                                            String s = response.body().string();
                                            if (s != null) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(s);
                                                    JSONObject message = jsonObject.getJSONObject("Message");
                                                    String messagestatus = message.getString("messagestatus");
//                                                    CacheUtils.getString(LoginActivity.this, "formhash")
                                                    if (messagestatus.equals("1"))
                                                        CacheUtils.putString(RedNetApp.getInstance(), "platform", null);
                                                    CacheUtils.putString(RedNetApp.getInstance(), "member_loginstatus", "");
                                                    onRefresh();
                                                } catch (JSONException e) {
                                                    handler.sendEmptyMessage(4);
                                                    e.printStackTrace();
                                                }

                                            } else {
                                                handler.sendEmptyMessage(5);
//                                                Toast.makeText(RedNetApp.getInstance(), "解绑失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
                break;
            case R.id.logout_view:
                showLogout("确认退出?", new Callback<Integer>() {
                    @Override
                    public void onCallback(Integer obj) {
                        if (obj == R.id.yes) {
                            logout();
//
                        }
                    }
                });
                break;
            case R.id.tip:
                RednetUtils.login(getActivity());
                break;
//            case R.id.rl_footprint:
//                startActivity(new Intent(getActivity(), FootprintActivity.class));
//                break;
            case R.id.my_thread:
                Intent intent = new Intent(getActivity(), PostThreadActivity.class);
                startActivity(intent);
                break;
        }

    }

    String getBindingName(String unionid) {
        String status = CacheUtils.getString(RedNetApp.getInstance(), "member_loginstatus");
        Log.e("TAG", "status=" + status + ";unionid=" + unionid);
        if (status.equals("qq")) {
            type = "qq";
            return "QQ";
        } else if (status.equals("weixin") && unionid != null && !TextUtils.isEmpty(unionid)) {
            type = "weixin";
            return "微信";
        } else
            return "0";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN && requestCode == RESULT_OK && hasLogin()) {
            mRefreshView.setRefreshing(true);
            onRefresh();
        }
    }

    private void createViewByData(final UserInfoBean.VariablesBean variables) {
        if (null == variables) {
            return;
        }
        CacheUtils.putString(RedNetApp.getInstance(), "cookiepre", variables.getCookiepre());
        int size1 = mGroupLayout.getChildCount();
        int size2 = mValueLayout.getChildCount();
        final String formhash = variables.getFormhash();
//        CacheUtils.putString(getActivity(), "formhash3", formhash);
//        Iwechat_userBean iwechat_user = variables.getIwechat_user();
//        unionid = iwechat_user.getUnionid();
        if (CacheUtils.getBoolean(RedNetApp.getInstance(), "login") == true) {
            String bindingName = getBindingName(unionid);
            Log.e("TAG", "bindingName=" + bindingName);
            mReleaseBinding.setVisibility("0".equalsIgnoreCase(getBindingName(unionid)) ? View.GONE : View.VISIBLE);
            mViewAnimator.setDisplayedChild(2);
        }
        if (size2 > 2) {
            for (int i = 2; i < size2; i++) {
                mValueLayout.removeViewAt(2);
            }
        }

        ProfileInfo profile = variables.getSpace();
        Map<String, ProfileCredit> numericals = null;
        if (profile != null) {
            mAvatar.load(variables.getMember_avatar(), R.drawable.ic_header_def);
            mName.setText(profile.getUsername());
            String threads = profile.getThreads();
            String posts = profile.getPosts();
            mThreadCount.setText(threads);
            if (null != posts && null != threads) {
                int i = Integer.parseInt(posts) - Integer.parseInt(threads);
                mReplyCount.setText(i + "");
            }

            mRegisterTime.setText(profile.getRegdate());
            ProfileInfo.GroupBean info = profile.getGroup();
            if (info != null) {
                mLevel.setText(info.getGrouptitle());
            }
            if (numericals == null) {
                numericals = new TreeMap<String, ProfileCredit>(
                        new Comparator<String>() {
                            public int compare(String obj1, String obj2) {
                                // 降序排序
                                return obj1.compareTo(obj2);
                            }
                        });
            }
//            numericals.put("积分", new ProfileCredit("积分", profile.getCredits()));
            mValueLayout.addView(new ProfileItemView(getActivity(), "积分", profile.getCredits()));
        }
        int i = 0;
        LogUtils.d(jsonObject.getJSONObject("Variables").getJSONObject("extcredits").toString());
        for (Map.Entry<String, Object> entry : (this.jsonObject.getJSONObject("Variables").getJSONObject("extcredits")).entrySet()) {
            i++;
            String string = this.jsonObject.getJSONObject("Variables").getJSONObject("space").getString("extcredits" + i + "");
            numericals.put(entry.getKey(), new ProfileCredit(((com.alibaba.fastjson.JSONObject) entry.getValue()).getString("title"), string));
        }
        if (numericals != null && numericals.size() > 0) {
            int index = 0;
            Iterator iter = numericals.entrySet().iterator();
            while (iter.hasNext()) {
                index++;
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                int i1 = Integer.parseInt(key.toString());
                ProfileCredit val = (ProfileCredit) entry.getValue();
                String string = jsonObject.getJSONObject("Variables").getJSONObject("space").getString("extcredits" + index + "");
                mValueLayout.addView(new ProfileItemView(getActivity(), val.getTitle(), string));
            }
        }
        bind_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BindManagerActivity.ComeIn(getActivity(),variables.getMember_avatar(),formhash);
            }
        });
    }


    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            getUserInfoData();
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    public void showLogout(final String content, final Callback<Integer> callback) {
        final Dialog d = new Dialog(getActivity(), R.style.Theme_Rednet_Dialog_UnDim);
        if (Build.VERSION.SDK_INT >= 21) {
            d.getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
        d.setContentView(R.layout.dialog_custom);
        d.setCanceledOnTouchOutside(true);
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ((TextView) d.findViewById(R.id.content)).setText(content);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onCallback(v.getId());
                        d.dismiss();
                    }
                };
                d.findViewById(R.id.yes).setOnClickListener(listener);
                d.findViewById(R.id.no).setOnClickListener(listener);
            }
        });
        d.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        hasLogin();
        MobclickAgent.onPageStart("MeFragment(个人中心)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MeFragment(个人中心)");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(RefreshUserInfo finish) {
        String id = finish.getId();
        if (id != null && !TextUtils.isEmpty(id) && id.equals("0")) {
//            onRefresh();
            getUserInfoData();
        }
    }
}
