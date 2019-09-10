package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.MyFriendBean;
import cn.tencent.DiscuzMob.model.Star;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.adapter.FriendAdapter;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-6-19.
 */
public class MyFriendActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private SwipeRefreshLayout mRefreshView;
    private ProgressBar mProgressBar;
    private ListView mList;
    private FriendAdapter mAdapter;
    List<Star> list;
    private ImageView iv_nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sample_layout);
        EventBus.getDefault().register(this);
        ((TextView) findViewById(R.id.action_bar_title)).setText("我的好友");
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mList = (ListView) findViewById(R.id.my_thread_list);
        mList.setAdapter(mAdapter = new FriendAdapter(this));
        findViewById(R.id.back).setOnClickListener(this);
        iv_nothing = (ImageView) findViewById(R.id.iv_nothing);
        mList.setOnItemClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mProgressBar.setVisibility(View.VISIBLE);
        onRefresh();
    }

    String formhash;

    void load(boolean append) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", RedNetApp.getInstance().getCookie())
//                    .url(AppNetConfig.BASEURL + "?module=sendpm&pmsubmit=yes&pmid=0&touid=" + touid + "&checkavatar=1&Android=1&smiley=no&convimg=1")
                .url(AppNetConfig.MYFRIEND + "&uid=" + RedNetApp.getInstance().getUid())
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MyFriendActivity.this, "获取好友信息失败", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                            Log.e("TAG", "jsonObject000000000000000000000=" + response.body().string());
                        if (response != null) {
                            final MyFriendBean myFriendBean = new Gson().fromJson(response.body().string(), MyFriendBean.class);
                            formhash = myFriendBean.getVariables().getFormhash();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mRefreshView != null) {
                                        if (myFriendBean != null) {
                                            list = myFriendBean.getVariables().getList();
                                            if (null != list && list.size() > 0) {
                                                iv_nothing.setVisibility(View.GONE);
                                                mAdapter.set(list, formhash);
                                            } else {
                                                iv_nothing.setVisibility(View.VISIBLE);
                                            }
                                            mProgressBar.setVisibility(View.GONE);
                                        } else {
                                            mRefreshView.setRefreshing(true);
                                            onRefresh();
                                        }
                                    }
                                    mRefreshView.setRefreshing(false);
                                }
                            });

                        }


                    }
                });
//        OkHttpUtils.get()
//                .url(AppNetConfig.MYFRIEND + "&uid=" + RedNetApp.getInstance().getUid())
//                .build()
//                .execute(new StringCallback() {
//                            @Override
//                            public void onError(Request request, Exception e) {
//                                Toast.makeText(MyFriendActivity.this, "获取好友信息失败", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onResponse(String response) {
//                                if (response != null && !TextUtils.isEmpty(response)) {
//                                    MyFriendBean myFriendBean = new Gson().fromJson(response, MyFriendBean.class);
//                                    formhash = myFriendBean.getVariables().getFormhash();
//                                    if (mRefreshView != null) {
//                                        if (myFriendBean != null) {
//                                            list = myFriendBean.getVariables().getList();
//                                            if (null != list && list.size() > 0) {
//                                                iv_nothing.setVisibility(View.GONE);
//                                                mAdapter.set(list, formhash);
//                                            } else {
//                                                iv_nothing.setVisibility(View.VISIBLE);
//                                            }
//                                            mProgressBar.setVisibility(View.GONE);
//                                        } else {
//                                            mRefreshView.setRefreshing(true);
//                                            onRefresh();
//                                        }
//                                    }
//                                    mRefreshView.setRefreshing(false);
//                                }
//
//
//                            }
//                        });
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            mAdapter.cleanData();
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object friend = parent.getItemAtPosition(position);
        if (friend instanceof Star) {
            startActivity(new Intent(this, UserDetailActivity.class)
                    .putExtra("isMyFriend", true)
                    .putExtra("userId", list.get(position).getUid())
                    .putExtra("formhash", formhash));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("MyFriendActivity(我的好友)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("MyFriendActivity(我的好友)");
    }

    @Subscribe
    public void onEventMainThread(ReFreshImg img) {
        Log.e("TAG", "无图模式");
        onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
