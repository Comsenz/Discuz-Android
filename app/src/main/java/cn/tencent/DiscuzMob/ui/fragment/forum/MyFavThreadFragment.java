package cn.tencent.DiscuzMob.ui.fragment.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.tencent.DiscuzMob.base.BaseRecyclerAdapter;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.MyFavThreadBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.adapter.MyFavThreadAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRecyclerRefreshFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.ToastUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by AiWei on 2016/5/30.
 * //帖子 -  收藏
 */

public class MyFavThreadFragment extends SimpleRecyclerRefreshFragment {
    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private String formhash;
    private MyFavThreadAdapter mAdapter;

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cookiepre_auth = CacheUtils.getString(getActivity(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(getActivity(), "cookiepre_saltkey");
        formhash = CacheUtils.getString(getActivity(), "formhash");
        onRefresh();
    }

    @Override
    protected BaseRecyclerAdapter initAdapter() {
        mAdapter = new MyFavThreadAdapter(getActivity());
        mAdapter.setLoadListener(this);
        return mAdapter;
    }

    @Override
    public void onLoad() {
        getData(false);
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    private void getData(final boolean isRefresh) {
        final int page = isRefresh ? 1 : getCurrentPage() + 1;
        if (RednetUtils.networkIsOk(getActivity())) {
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                    .url(AppNetConfig.MYFAVTHREAD + "&page=" + page)
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            getDataFail();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
//                            Log.e("TAG", "我收藏的帖子=" + response.body().string());
                            setCurrentPage(page);
                            try {
                                MyFavThreadBean myFavThreadBean = new Gson().fromJson(response.body().string(), MyFavThreadBean.class);
                                CacheUtils.putString(RedNetApp.getInstance(), "formhash1", myFavThreadBean.getVariables().getFormhash());
                                final MyFavThreadBean.VariablesBean myFavForumBean = myFavThreadBean.getVariables();
                                if (myFavForumBean != null) {
                                    setPreCount(myFavForumBean.getPerpage());
                                    setTotalCount(myFavForumBean.getCount());
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshView.setRefreshing(false);
                                        mAdapter.setData(myFavForumBean.getList(), isRefresh, hasMore());
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
                                    ToastUtils.showToast("我收藏的版块请求失败");
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
}
