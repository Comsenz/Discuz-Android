package cn.tencent.DiscuzMob.ui.fragment.me;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import cn.tencent.DiscuzMob.base.BaseRecyclerAdapter;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.AllForumBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.adapter.FootprintAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRecyclerRefreshFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.ToastUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by cg on 2017/6/16.
 */
@SuppressLint("ValidFragment")
public class FootprintFragment extends SimpleRecyclerRefreshFragment {
    FootprintAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRefresh();
    }

    @Override
    protected BaseRecyclerAdapter initAdapter() {
        mAdapter = new FootprintAdapter(getActivity(), 3);
        mAdapter.setLoadListener(this);
        return mAdapter;
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    @Override
    public void onLoad() {
        getData(false);
    }

    private void getData(final boolean isRefresh) {
        if (!RednetUtils.networkIsOk(getActivity())) {
            mRefreshView.setRefreshing(false);
            return;
        }
        final int page = isRefresh ? 1 : getCurrentPage() + 1;
        if (RednetUtils.networkIsOk(getActivity())) {
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", CacheUtils.getString(getActivity(), "cookiepre_auth") + ";" + CacheUtils.getString(getActivity(), "cookiepre_saltkey" + ";"))
                    .url(AppNetConfig.ALLFORUM + "&page=" + page)
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            getDataFail();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
//                            Log.e("TAG", "我的足迹版块=" + response.body().string());
                            setCurrentPage(page);
                            try {
                                AllForumBean allForumBean = new Gson().fromJson(response.body().string(), AllForumBean.class);
                                final AllForumBean.VariablesBean variablesBean = allForumBean.getVariables();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshView.setRefreshing(false);
                                        mAdapter.setData(variablesBean.getVisitedforums(), isRefresh, hasMore());
                                        imageViewEmpty.setVisibility(mAdapter.isShowEmpty() ? View.VISIBLE : View.GONE);
                                        if (mTip.getDisplayedChild() != 0) {
                                            mTip.setDisplayedChild(0);
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                getDataFail();
                            }
                        }

                        private void getDataFail() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast("请求失败");
                                    mAdapter.onFaile();
                                    if (mTip.getDisplayedChild() != 0) {
                                        mTip.setDisplayedChild(0);
                                    }
                                }
                            });
                        }
                    });
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Subscribe
    public void onEventMainThread(ReFreshImg img) {
        Log.e("TAG", "无图模式");
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
