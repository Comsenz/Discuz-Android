package cn.tencent.DiscuzMob.ui.fragment.myinteraction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.MythreadBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.InteractionActivity;
import cn.tencent.DiscuzMob.ui.adapter.MythreadsAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;

/**
 * Created by cg on 2017/7/17.
 * 打招呼
 */
@SuppressLint("ValidFragment")
public class MyCallFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListener {

    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private FooterForList mFooter;
    private int mPage;
    MythreadsAdapter adapter;
    private Activity activity;

    @SuppressLint("ValidFragment")
    public MyCallFragment(InteractionActivity interactionActivity) {
        this.activity = interactionActivity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cookiepre_auth = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey");
        mRefreshView.setOnRefreshListener(this);
        mFooter = new FooterForList(mListView);
        mFooter.setOnLoadListener(this);
        mTip.setDisplayedChild(1);
        adapter = new MythreadsAdapter(activity);
        mListView.setAdapter(adapter);
        mListView.setDividerHeight(0);
        getUrl();
        onRefresh();
    }

    private void getUrl() {

        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.BASEURL + "?module=iwechat&data=json&version=5")
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                        Log.e("TAG", "获取公参 =" + response.body().string());
                        JSONObject jsonObject = JSON.parseObject(response.body().string());
                        String ucenterurl = jsonObject.getString("ucenterurl");
                        adapter.getUrl(ucenterurl);
                    }

                });
    }


    @Override
    public void onRefresh() {
        if (mFooter != null && mFooter.isLoading()) {
            RednetUtils.toast(RedNetApp.getInstance(), "正在加载");
            mRefreshView.setRefreshing(false);
        } else if (RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            adapter.cleanData();
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    private void load(final boolean append) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.MYCALL + "&page=" + String.valueOf(append ? ++mPage : (mPage = 1)))
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
//                        Log.e("TAG", "我的帖子=" + response.body().string());
                        MythreadBean userThreadsBean = null;
                        try {
                            userThreadsBean = new Gson().fromJson(response.body().string(), MythreadBean.class);
                            if (mRefreshView != null) {
                                if (userThreadsBean != null) {
                                    final MythreadBean.VariablesBean variables = userThreadsBean.getVariables();
                                    if (variables != null) {
                                        final List<MythreadBean.VariablesBean.ListBean> list = variables.getList();
                                        final String count = variables.getCount();
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (append) {
                                                    if ((list == null) || (Integer.parseInt(count) < Integer.parseInt(variables.getPerpage()))) {
                                                        mFooter.finishAll();
                                                    }else {
                                                        adapter.appendData(list);
                                                    }

                                                } else {
                                                    imageView.setVisibility(View.GONE);
                                                    if (null != list && list.size() > 0) {
                                                        mListView.setVisibility(View.VISIBLE);
                                                        imageView.setVisibility(View.GONE);
                                                        adapter.setData(list);
                                                        mFooter.reset();
                                                    } else {
                                                        mListView.setVisibility(View.GONE);
                                                        imageView.setVisibility(View.VISIBLE);
                                                    }

                                                }

                                            }
                                        });

                                    } else {
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                imageView.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    }
                                }

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        mRefreshView.setRefreshing(false);
                                        mTip.setDisplayedChild(0);
                                    }
                                });

                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
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
}
