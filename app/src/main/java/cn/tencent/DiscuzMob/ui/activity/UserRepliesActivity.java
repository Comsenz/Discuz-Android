package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.model.UserRepliesBean;
import cn.tencent.DiscuzMob.ui.adapter.ReplayAdapter;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.ReplyBean;
import cn.tencent.DiscuzMob.model.ThreadlistBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.widget.XExpandableListView;
import cn.tencent.DiscuzMob.R;


/**
 * Created by kurt on 15-6-19.
 */
public class UserRepliesActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener
        , ILoadListener {

    private SwipeRefreshLayout mRefreshView;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private FooterForList mFooter;
    private int mPage;
    private String uid;
    private String from;
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    private XExpandableListView expandableListView;
    private List<ThreadlistBean> listGroup;
    private List<List<ReplyBean>> listChild;
    private List<ReplyBean> lists = new ArrayList<>();
    private List<String> subjects = new ArrayList<>();
    private ReplayAdapter replayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sample_layout);
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        formhash = CacheUtils.getString(this, "formhash");
        uid = getIntent().getStringExtra("uid");
        from = getIntent().getStringExtra("from");
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.my_thread_list);
        mFooter = new FooterForList(mListView);
        expandableListView = (XExpandableListView) findViewById(R.id.expandableListView);
        ((TextView) findViewById(R.id.action_bar_title)).setText("me".equals(from) ? "我的回复" : "他的回复");
        findViewById(R.id.back).setOnClickListener(this);
//        mListView.setOnItemClickListener(this);
        mFooter.setOnLoadListener(this);
        mRefreshView.setOnRefreshListener(this);
        mProgressBar.setVisibility(View.VISIBLE);
        replayAdapter = new ReplayAdapter();
        mListView.setAdapter(replayAdapter);
        onRefresh();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private List<ThreadlistBean> threadlistBeanList = new ArrayList<>();

    void load(final boolean append) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.MYREPLISE + "&uid=" + uid + "&page=" + String.valueOf(mPage))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserRepliesActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        UserRepliesBean userRepliesBean = null;
//                        Log.e("TAG", "他的回复response=" + response.body().string());
                        try {
                            userRepliesBean = new Gson().fromJson(response.body().string(), UserRepliesBean.class);
                            if (mRefreshView != null) {
                                if (userRepliesBean != null) {
//                                    final UserRepliesBean.VariablesBean variables = userRepliesBean.getVariables();
//                                    final List<ThreadlistBean> threadlist = userRepliesBean.getVariables().getThreadlist();
//                                    if (null == threadlist || threadlist.size() == 0) {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                mFooter.finishAll();
//                                            }
//                                        });

                                    } else {
//                                        runOnUiThread(new Runnable() {
////                                            @Override
////                                            public void run() {
////
////                                                if (append) {
////                                                    for (int i = 0; i < threadlist.size(); i++) {
////                                                        List<ReplyBean> reply = threadlist.get(i).getReply();
////                                                        String subject = threadlist.get(i).getSubject();
////                                                        ThreadlistBean threadlistBean = threadlist.get(i);
////                                                        for (int i1 = 0; i1 < reply.size(); i1++) {
////                                                            lists.add(reply.get(i1));
////                                                            subjects.add(subject);
////                                                            threadlistBeanList.add(threadlistBean);
////                                                        }
////                                                    }
////                                                    replayAdapter.appendData(lists, threadlistBeanList);
////                                                    replayAdapter.notifyDataSetChanged();
////                                                    mFooter.reset();
////                                                } else {
////                                                    lists.clear();
////                                                    subjects.clear();
////                                                    threadlistBeanList.clear();
////                                                    for (int i = 0; i < threadlist.size(); i++) {
////                                                        List<ReplyBean> reply = threadlist.get(i).getReply();
////                                                        String subject = threadlist.get(i).getSubject();
////                                                        ThreadlistBean threadlistBean = threadlist.get(i);
////                                                        for (int i1 = 0; i1 < reply.size(); i1++) {
////                                                            lists.add(reply.get(i1));
////                                                            subjects.add(subject);
////                                                            threadlistBeanList.add(threadlistBean);
////                                                        }
////                                                    }
//////
////                                                    replayAdapter.appendData(lists, threadlistBeanList);
////                                                    replayAdapter.notifyDataSetChanged();
////                                                    mFooter.reset();
////                                                }
////                                            }
////                                        });
//                                    }

                                }
//                                    if (code == 0) mPage = Math.max(1, --mPage);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshView.setRefreshing(false);
                                        mProgressBar.setVisibility(View.GONE);
                                    }
                                });

                            }
                        } catch (JsonSyntaxException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRefreshView.setRefreshing(false);
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(UserRepliesActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        } catch (IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRefreshView.setRefreshing(false);
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(UserRepliesActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }


                    }

                });


    }

    @Override
    public void onRefresh() {
        if (mFooter != null && mFooter.isLoading()) {
            RednetUtils.toast(this, "正在加载");
            mRefreshView.setRefreshing(false);
        } else if (RednetUtils.networkIsOk(this)) {
            mPage = 1;
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onLoad() {
        if (RednetUtils.networkIsOk(this)) {

            ++mPage;
            Log.e("TAG", "加载=" + mPage);
            load(true);
        } else {
            mFooter.finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("UserRepliesActivity(我(他)的回复)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("UserRepliesActivity(我(他)的回复)");
    }

}
