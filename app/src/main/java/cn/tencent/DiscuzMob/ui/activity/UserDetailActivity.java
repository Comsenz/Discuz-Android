package cn.tencent.DiscuzMob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.MakeFriendVariables;
import cn.tencent.DiscuzMob.model.MyFriendBean;
import cn.tencent.DiscuzMob.model.ProfileCredit;
import cn.tencent.DiscuzMob.model.ProfileInfo;
import cn.tencent.DiscuzMob.model.Star;
import cn.tencent.DiscuzMob.model.UserInfoBean;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.ui.dialog.ProgressDialog;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.widget.ProfileItemView;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.R;

import static android.R.attr.value;

/**
 * Created by AiWei on 2015/5/12.
 */
public class UserDetailActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ViewAnimator mVa;
    private ProgressDialog mProgressDialog;
    private SwipeRefreshLayout mRefresh;
    private AsyncRoundedImageView mAvatar;
    private TextView mName, mLevel, mRegistTime, mThreadCount, mReplyCount, mFriend;
    private LinearLayout mUserReply, mUserTopic;
    private LinearLayout mGroupLayout, mValueLayout;
    private String userId;
    private TextView tv_black;
    private TextView mMessage;
    private String formhash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        userId = getIntent().getStringExtra("userId");
        formhash = getIntent().getStringExtra("formhash");
        mVa = (ViewAnimator) findViewById(R.id.va);
        tv_black = (TextView) findViewById(R.id.tv_black);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mAvatar = (AsyncRoundedImageView) findViewById(R.id.user_detail_avatar_preview);
        mName = (TextView) findViewById(R.id.user_detail_name);
        mLevel = (TextView) findViewById(R.id.user_detail_level);
        mRegistTime = (TextView) findViewById(R.id.user_register_time);
        mThreadCount = (TextView) findViewById(R.id.user_theme_count);
        mFriend = (TextView) findViewById(R.id.friend);
        mFriend.setVisibility(getIntent().getBooleanExtra("isMyFriend", false) ? View.GONE : View.VISIBLE);
        mReplyCount = (TextView) findViewById(R.id.user_reply_count);
        mUserTopic = (LinearLayout) findViewById(R.id.user_detail_topic);
        mUserTopic.setVisibility(View.GONE);
        mUserReply = (LinearLayout) findViewById(R.id.user_detail_reply);
        mUserReply.setVisibility(View.GONE);
        mGroupLayout = (LinearLayout) findViewById(R.id.group_Layout);
        mValueLayout = (LinearLayout) findViewById(R.id.value_Layout);
        mProgressDialog = new ProgressDialog(this);
//        findViewById(R.id.options).setVisibility(userId.equals(RedNetApp.getInstance().getUid()) ? View.GONE : View.VISIBLE);
        findViewById(R.id.options).setVisibility(View.GONE);
        findViewById(R.id.back).setOnClickListener(this);
        mMessage = (TextView) findViewById(R.id.message);
        mMessage.setOnClickListener(this);
        mRefresh.setOnRefreshListener(this);
        mFriend.setOnClickListener(this);
        mUserTopic.setOnClickListener(this);
        tv_black.setOnClickListener(this);
        mUserReply.setOnClickListener(this);
        mVa.setDisplayedChild(1);
        onRefresh();
        if ((CacheUtils.getBoolean(this, "login") == true || CacheUtils.getBoolean(this, "otherLogin") == true) && getIntent().getBooleanExtra("isMyFriend", false) == false && !userId.equals(RedNetApp.getInstance().getUid())) {
            getFriendsFromNet();
        }

    }

    private void getFriendsFromNet() {
        OkHttpUtils.get()
                .url(AppNetConfig.MYFRIEND + "&uid=" + RedNetApp.getInstance().getUid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        mFriend.setVisibility(View.VISIBLE);
                        mMessage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response) {
//                        Log.e("TAG", "好友列表=" + response);
                        if (response != null && !TextUtils.isEmpty(response)) {
                            try {
                                MyFriendBean myFriendBean = new Gson().fromJson(response, MyFriendBean.class);
                                MyFriendBean.VariablesBean variables = myFriendBean.getVariables();
                                if (variables != null) {
                                    List<Star> list = variables.getList();
                                    for (int i = 0; i < list.size(); i++) {
                                        if (userId.equals(list.get(i).getUid())) {
                                            mFriend.setVisibility(View.GONE);
                                            mMessage.setVisibility(View.VISIBLE);
                                        }
                                        mFriend.setVisibility(View.VISIBLE);
                                        mMessage.setVisibility(View.GONE);
                                    }
                                }
                                mFriend.setVisibility(View.VISIBLE);
                                mMessage.setVisibility(View.GONE);
                            } catch (JsonSyntaxException e) {
                                mFriend.setVisibility(View.VISIBLE);
                                mMessage.setVisibility(View.GONE);
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    JSONObject jsonObject;

    @Override
    public void onRefresh() {
        OkHttpUtils.get()
                .url(AppNetConfig.USERINFO + "&uid=" + userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(UserDetailActivity.this, "获取信息失败", Toast.LENGTH_SHORT).show();
                        mRefresh.setRefreshing(false);
                        if (mVa.getDisplayedChild() != 0) {
                            mVa.setDisplayedChild(0);
                        }

                    }

                    @Override
                    public void onResponse(String response) {
                        jsonObject = JSON.parseObject(response);
                        UserInfoBean userInfoBean = null;
                        LogUtils.d("userinfo=" + response);
                        try {
                            userInfoBean = new Gson().fromJson(response, UserInfoBean.class);
                            if (mRefresh != null) {
                                if (userInfoBean != null) {
                                    Log.e("TAG", "value =" + value);
                                    UserInfoBean.VariablesBean variables = userInfoBean.getVariables();
                                    createViewByData(variables);
                                    userId = variables.getSpace().getUid();
                                }
                                mRefresh.setRefreshing(false);
                                if (mVa.getDisplayedChild() != 0) {
                                    mVa.setDisplayedChild(0);
                                }

                            }
                        } catch (JsonSyntaxException e) {
                            Toast.makeText(UserDetailActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void createViewByData(UserInfoBean.VariablesBean variables) {
        if (variables == null) {
            return;
        }
        int size1 = mGroupLayout.getChildCount();
        int size2 = mValueLayout.getChildCount();

        if (size1 > 1) {
            for (int i = 1; i < size1; i++) {
                mGroupLayout.removeViewAt(i);
            }
        }
        if (size2 > 2) {
            for (int i = 2; i < size2; i++) {
                mValueLayout.removeViewAt(2);
            }
        }

        ProfileInfo profile = variables.getSpace();
        Map<String, ProfileCredit> numericals = null;
        if (profile != null) {
//            mAvatar.load(profile.getAvatar());
            mAvatar.load(variables.getMember_avatar(), R.drawable.ic_header_def);
            mName.setText(profile.getUsername());
            String threads = profile.getThreads();
            String posts = profile.getPosts();
            mThreadCount.setText(threads);
            if (null != posts && null != threads) {
                int i = Integer.parseInt(posts) - Integer.parseInt(threads);
                mReplyCount.setText(i + "");
            }

//            mThreadCount.setText(profile.getThreads());
//            mReplyCount.setText(profile.getPosts());
            mRegistTime.setText(profile.getRegdate());
            ProfileInfo.GroupBean info = profile.getGroup();
//            UserInfoBean.VariablesBean.SpaceBean.GroupBean info = profile.getGroup();
//            mGroupLayout.addView(new ProfileItemView(this, "用户组", info.getGrouptitle()));
//            mGroupLayout.addView(new ProfileItemView(this, "管理组", info.getGrouptitle()));
            if (info != null) {
                mLevel.setText(info.getGrouptitle());
                if (info.getType().equals("member")) {
                    mGroupLayout.addView(new ProfileItemView(this, "用户组", info.getGrouptitle()), 0);
                } else if (info.getType().equals("system")) {
                    mGroupLayout.addView(new ProfileItemView(this, "管理组", info.getGrouptitle()), 0);
                }
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
            mValueLayout.addView(new ProfileItemView(UserDetailActivity.this, "积分", profile.getCredits()));
        }
        int i = 0;
        for (Map.Entry<String, Object> entry : (this.jsonObject.getJSONObject("Variables").getJSONObject("extcredits")).entrySet()) {
            i++;
            String string = this.jsonObject.getJSONObject("Variables").getJSONObject("space").getString("extcredits" + i + "");
            Log.e("TAG", "string=" + string);
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
                mValueLayout.addView(new ProfileItemView(UserDetailActivity.this, val.getTitle(), string));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.user_detail_topic:
                if (RednetUtils.hasLogin(this)) {
                    startActivity(new Intent(this, UserThreadsActivity.class)
                            .putExtra("uid", userId)
                            .putExtra("from", "user"));
                }

                break;
            case R.id.user_detail_reply:
                if (RednetUtils.hasLogin(this)) {
                    startActivity(new Intent(this, UserRepliesActivity.class)
                            .putExtra("uid", userId)
                            .putExtra("from", "user"));
                }

                break;
            case R.id.message:
                MyPmActivity.startActivityToMyPm(this, userId, mName.getText().toString(), formhash);
                break;
            case R.id.friend:
                if (RednetUtils.hasLogin(this)) {
                    makeFriend(this, mProgressDialog, userId, "1");
                }
                break;
            case R.id.tv_black:
                if (RednetUtils.hasLogin(this)) {
                    Toast.makeText(UserDetailActivity.this, "拉黑", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    public static void makeFriend(final Context context, final ProgressDialog dialog, String type, String uid) {
        if (TextUtils.isEmpty(uid)) {
            RednetUtils.toast(context, "无效的uid");
        } else {
            dialog.show();
            ApiVersion5.INSTANCE.requestMakeFriend(uid, type, new ApiVersion5.Result<Object>(context, MakeFriendVariables.class, false) {
                @Override
                public void onResult(int code, Object value) {
                    if (dialog != null) {
                        if (value instanceof BaseModel) {
                            MakeFriendVariables variables = ((BaseModel<MakeFriendVariables>) value).getVariables();
                            RednetUtils.toast(context, variables.getMessage());
                        } else {
                            if (code == 0) {
                                RednetUtils.toast(context, "请求失败");
                            }
                        }
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("UserDetailActivity(用户信息)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("UserDetailActivity(用户信息)");
    }

}
