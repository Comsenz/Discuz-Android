package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.io.IOException;

import cn.tencent.DiscuzMob.model.HisThemeBean;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.HotTThread;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.adapter.HisThemeAdapter;
import cn.tencent.DiscuzMob.ui.adapter.SampleThreadAdapter;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.R;


/**
 * Created by kurt on 15-6-19.
 */
public class UserThreadsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, ILoadListener {

    private SwipeRefreshLayout mRefreshView;
    private ProgressBar mProgressBar;
    private ListView mList;
    private FooterForList mFooter;
    private SampleThreadAdapter mAdapter;
    private String uid;
    private String from;
    private int mPage = 1;
    //        private From mFrom;
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    HisThemeAdapter myFavForumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sample_layout);
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        formhash = CacheUtils.getString(this, "formhash");
        uid = getIntent().getStringExtra("uid");
        from = getIntent().getStringExtra("from");
//        mFrom = "me".equals(from) ? From.ME : From.USER;
        ((TextView) findViewById(R.id.action_bar_title)).setText("me".equals(from) ? "我的主题" : "他的主题");
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mList = (ListView) findViewById(R.id.my_thread_list);
//        mList.setAdapter(mAdapter = new SampleThreadAdapter());
        mFooter = new FooterForList(mList);
        findViewById(R.id.back).setOnClickListener(this);
        mList.setOnItemClickListener(this);
        mFooter.setOnLoadListener(this);
        mRefreshView.setOnRefreshListener(this);
        mProgressBar.setVisibility(View.VISIBLE);
        myFavForumAdapter = new HisThemeAdapter();
        mList.setAdapter(myFavForumAdapter);
        onRefresh();

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    void load(final boolean append) {
//        if (from.equals("me")) {
//            RedNet.mHttpClient.newCall(new Request.Builder()
//                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
//                    .url(AppNetConfig.THEME + "&page=" + String.valueOf(append ? ++mPage : (mPage = 1)))
//                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
//                    .enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(UserThreadsActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onResponse(Response response) throws IOException {
////                            Log.e("TAG", "我的主题=" + response.body().string());
//                            UserThreadsBean userThreadsBean = new Gson().fromJson(response.body().string(), UserThreadsBean.class);
//                            if (mRefreshView != null) {
//                                if (userThreadsBean != null) {
//                                    final UserThreadsBean.VariablesBean variables1 = userThreadsBean.getVariables();
//                                    if (variables1 != null) {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                if (append) {
//                                                    UserThreadsAdapter myFavForumAdapter = new UserThreadsAdapter(variables1.getData());
//                                                    mList.setAdapter(myFavForumAdapter);
//                                                } else {
//                                                    UserThreadsAdapter myFavForumAdapter = new UserThreadsAdapter(variables1.getData());
//                                                    mList.setAdapter(myFavForumAdapter);
//                                                    mFooter.reset();
//                                                }
//                                                if ((variables1.getData() == null) || (variables1.getData().size() < Integer.parseInt(variables1.getPerpage()))) {
//                                                    mFooter.finishAll();
//                                                }
//                                            }
//                                        });
//
//                                    }
//                                }
//
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        mRefreshView.setRefreshing(false);
//                                        mProgressBar.setVisibility(View.GONE);
//                                    }
//                                });
//
//                            }
//                        }
//
//                    });
//        } else {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.HISTHME + "&uid=" + uid + "&page=" + String.valueOf(append ? ++mPage : (mPage = 1)))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserThreadsActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        Log.e("TAG", "他的主题url=" + (AppNetConfig.HISTHME + "&uid=" + uid + "&page=" + String.valueOf(mPage)));
                        HisThemeBean userThreadsBean = new Gson().fromJson(response.body().string(), HisThemeBean.class);
                        if (mRefreshView != null) {
                            if (userThreadsBean != null) {
                                final HisThemeBean.VariablesBean variables = userThreadsBean.getVariables();
                                if (variables != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (append) {
                                                myFavForumAdapter.setAppendData(variables.getThreadlist());
                                                mFooter.reset();
                                            } else {
                                                myFavForumAdapter.setThreads(variables.getThreadlist());
                                                mFooter.reset();
                                            }
                                            if (null == variables.getThreadlist() || Integer.parseInt(variables.getListcount()) < Integer.parseInt(variables.getTpp())) {
                                                mFooter.finishAll();
                                            }
                                        }
                                    });

                                }
                            }

//                            if (code == 0) mPage = Math.max(1, --mPage);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRefreshView.setRefreshing(false);
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });

                        }
                    }

                });
//        }
    }

    @Override
    public void onRefresh() {
        if (mFooter != null && mFooter.isLoading()) {
            RednetUtils.toast(this, "正在加载");
            mRefreshView.setRefreshing(false);
        } else if (RednetUtils.networkIsOk(this)) {
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onLoad() {
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(this)) {
            load(true);
        } else {
            mFooter.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        if (obj instanceof HotTThread) {
            HotTThread thread = (HotTThread) obj;
            BaseFragment.toThreadDetails(this, thread.getFid(), thread.getTid(), String.valueOf(thread.getSpecial()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("UserThreadsActivity(我(他)的主题)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("UserThreadsActivity(我(他)的主题)");
    }

}
