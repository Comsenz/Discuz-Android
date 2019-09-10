package cn.tencent.DiscuzMob.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.ui.adapter.JoinManagerAdapter;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.JoinManagerBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.dialog.JoinManagerDialog;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.R;


public class JoinManagerActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private ImageView back;
    protected ViewAnimator mTip;
    protected NestedSwipeRefreshLayout mRefreshView;
    protected ListView mListView;
    //    private ListView listview;
    protected ImageView imageView;
    protected SwipeMenuListView listView;
    protected RelativeLayout rl_threads;
    private FooterForList mFooter;
    public JoinManagerAdapter adapter;
    private int mPage;
    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private String tid;
    private String pid;
    private RelativeLayout action_bar;
    JoinManagerDialog joinManagerDialog;
    List<JoinManagerBean.VariablesBean.ActivityapplylistBean.ApplylistBean> list;
    String formhash;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_manager);
        cookiepre_auth = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey");
        tid = getIntent().getStringExtra("tid");
        pid = getIntent().getStringExtra("pid");
        initView();
        onRefresh();
    }

    private void initView() {
        action_bar = (RelativeLayout) findViewById(R.id.action_bar);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        mTip = (ViewAnimator) findViewById(R.id.tip);
        mRefreshView = (NestedSwipeRefreshLayout) findViewById(R.id.refresh);
        mListView = (ListView) findViewById(R.id.list);
        imageView = (ImageView) findViewById(R.id.iv_nothing);
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        rl_threads = (RelativeLayout) findViewById(R.id.rl_threads);
        rl_threads.setBackgroundColor(Color.WHITE);
        mRefreshView.setOnRefreshListener(this);
//        mFooter = new FooterForList(mListView);
//        mFooter.setOnLoadListener(this);
        mTip.setDisplayedChild(1);
        mListView.setDivider(new ColorDrawable(Color.WHITE));
        adapter = new JoinManagerAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JoinManagerBean.VariablesBean.ActivityapplylistBean.ApplylistBean applylistBean = list.get(position);
                JSONObject applylistItem = (JSONObject) jsonObject.getJSONObject("Variables").getJSONObject("activityapplylist").getJSONArray("applylist").get(position);
                joinManagerDialog = new JoinManagerDialog(JoinManagerActivity.this, applylistItem, formhash);
                joinManagerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                joinManagerDialog.show();
            }
        });
        if (joinManagerDialog != null) {
            joinManagerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    //处理监听事件
                    onRefresh();
                }
            });
        }

    }

    //    public voi
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
//        if (mFooter != null && mFooter.isLoading()) {
//            RednetUtils.toast(RedNetApp.getInstance(), "正在加载");
//            mRefreshView.setRefreshing(false);
//        } else
        if (RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            if (adapter != null) {
                adapter.cleanData();
            }

            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    public void load(final boolean b) {
        //&tid=29&pid=162
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.JOINMANAGER + "&pid=" + pid + "&tid=" + tid + "&page=" + String.valueOf(b ? ++mPage : (mPage = 1)))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(JoinManagerActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        JoinManagerBean joinManagerBean = null;
                        String string = response.body().string();
                        LogUtils.i(string);
                        try {
                            jsonObject = JSON.parseObject(string);
                            joinManagerBean = new Gson().fromJson(jsonObject.toString(), JoinManagerBean.class);
                            if (mRefreshView != null) {
                                if (joinManagerBean != null) {
                                    final JoinManagerBean.VariablesBean variables = joinManagerBean.getVariables();
                                    formhash = variables.getFormhash();
                                    if (variables != null) {
                                        list = variables.getActivityapplylist().getApplylist();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
//                                                if (b) {
                                                if ((list == null) || list.size() == 0) {
//                                                        mFooter.finishAll();
                                                    mListView.setVisibility(View.GONE);
                                                    imageView.setVisibility(View.VISIBLE);
                                                } else

                                                {
                                                    imageView.setVisibility(View.GONE);
                                                    mListView.setVisibility(View.VISIBLE);
                                                    adapter.setData(list);
                                                }

//                                                } else {
//                                                    if (null != list && list.size() > 0) {
//                                                        mListView.setVisibility(View.VISIBLE);
//                                                        imageView.setVisibility(View.GONE);
//                                                        adapter.setData(list);
//                                                        mFooter.reset();
//                                                    } else {
//                                                        mListView.setVisibility(View.GONE);
//                                                        imageView.setVisibility(View.VISIBLE);
//                                                    }
//
//                                                }

                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageView.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    }
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        mRefreshView.setRefreshing(false);
                                        mTip.setDisplayedChild(0);
                                    }
                                });

                            }
                        } catch (
                                JsonSyntaxException e)

                        {
                            e.printStackTrace();
                        }
                    }

                });
    }

//    @Override
//    public void onLoad() {
//        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(RedNetApp.getInstance())) {
//            load(true);
//        } else {
//            mFooter.finish();
//        }
//    }
}
