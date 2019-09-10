package cn.tencent.DiscuzMob.ui.fragment.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.RemindBean;
import cn.tencent.DiscuzMob.ui.adapter.RemindAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;

/**
 * Created by cg on 2017/4/19.
 */

public class RemindFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListener, AdapterView.OnItemClickListener {
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    int mPage;
    private FooterForList mFooter;
    private RemindAdapter remindAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mListView.setAdapter(mAdapter = new MyForumAdapter(getActivity(), null, true));
        cookiepre_auth = CacheUtils.getString(getActivity(), "cookiepre_auth");

        cookiepre_saltkey = CacheUtils.getString(getActivity(), "cookiepre_saltkey");
        formhash = CacheUtils.getString(getActivity(), "formhash");
        mRefreshView.setOnRefreshListener(this);
        mFooter = new FooterForList(mListView);
        mFooter.setOnLoadListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(remindAdapter = new RemindAdapter());
        mTip.setDisplayedChild(1);
        mPage = 1;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (mFooter != null && mFooter.isLoading()) {
            RednetUtils.toast(RedNetApp.getInstance(), "正在加载");
            mRefreshView.setRefreshing(false);
        } else if (RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    private void load(final boolean append) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
//                .url(AppNetConfig.SYSTEM_MESSAGE + String.valueOf(append ? ++mPage : (mPage = 1)) + "&touid=" + RedNetApp.getInstance().getUid())
                .url(AppNetConfig.SYSTEM_MESSAGE + String.valueOf(append ? ++mPage : (mPage = 1)))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                        Log.e("TAG", "公共消息=" + response.body().string());
                        RemindBean remindBean;
                        try {
                            remindBean = new Gson().fromJson(response.body().string(), RemindBean.class);
                            if (mRefreshView != null) {
                                if (remindBean != null) {
                                    final RemindBean.VariablesBean variables = remindBean.getVariables();
                                    if (variables != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (append) {
                                                    if ((null == variables.getList()) || variables.getList().size() == 0) {
                                                        mFooter.finishAll();
                                                    } else {
                                                        remindAdapter.setAppendData(variables.getList());
                                                        mFooter.reset();
                                                    }

                                                } else {
                                                    if ((null != variables.getList()) && variables.getList().size() > 0) {
                                                        imageView.setVisibility(View.GONE);
                                                        mListView.setVisibility(View.VISIBLE);
                                                        remindAdapter.setThreads(variables.getList());
                                                        mFooter.reset();
                                                    } else {
                                                        imageView.setVisibility(View.VISIBLE);
                                                        mListView.setVisibility(View.GONE);
                                                    }

                                                }
                                            }
                                        });

                                    }
                                } else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshView.setRefreshing(false);
                                        if (mTip.getDisplayedChild() != 0) {
                                            mTip.setDisplayedChild(0);
                                        }
                                    }
                                });

                            }
                        } catch (JsonSyntaxException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        } catch (IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }

                });
    }

    @Override
    public void onLoad() {
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            load(true);
        } else {
            mFooter.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
