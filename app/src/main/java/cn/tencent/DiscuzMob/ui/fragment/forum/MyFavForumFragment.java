package cn.tencent.DiscuzMob.ui.fragment.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.tencent.DiscuzMob.base.BaseRecyclerAdapter;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.listeners.FavEventListener;
import cn.tencent.DiscuzMob.model.ColecteBean;
import cn.tencent.DiscuzMob.model.MyFavForumBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.adapter.MyFavForumAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRecyclerRefreshFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.ToastUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by AiWei on 2016/4/25.
 * //板块 -  收藏
 */

public class MyFavForumFragment extends SimpleRecyclerRefreshFragment implements FavEventListener<MyFavForumBean.VariablesBean.ListBean> {
    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private String formhash;
    private MyFavForumAdapter mAdapter;

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
        mAdapter = new MyFavForumAdapter(getActivity());
        mAdapter.setLoadListener(this);
        mAdapter.setFavEventListener(this);
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

    @Override
    public void onFavChange(MyFavForumBean.VariablesBean.ListBean listBean, boolean addOrDelete) {
        if (!addOrDelete) {
            deledteFav(listBean);
        }
    }

    public void deledteFav(final MyFavForumBean.VariablesBean.ListBean bean) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                .url(AppNetConfig.UNCOLLECTION + bean.getId())
                .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("formhash", formhash)
                        .addFormDataPart("deletesubmit", "true")
                        .build())
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("TAG", "收藏失败");
                        ToastUtils.showToast("取消收藏请求失败");
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        ColecteBean colecteBean = null;
//                        Log.e("TAG", "取消收藏="+response.body().string());
                        try {
                            colecteBean = new Gson().fromJson(response.body().string(), ColecteBean.class);
                            ColecteBean.MessageBean message = colecteBean.getMessage();

                            if (null != message) {
                                final String messagestr = message.getMessagestr();
                                final String messageval = message.getMessageval();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (messageval.equals("do_success")) {
                                            mAdapter.remove(bean);
                                        }
                                        ToastUtils.showToast(messagestr);
                                    }
                                });

                            }
                        } catch (JsonSyntaxException e) {
                            ToastUtils.showToast("请求异常");
                        }
                    }
                });
    }

    private void getData(final boolean isRefresh) {
        final int page = isRefresh ? 1 : getCurrentPage() + 1;
        if (RednetUtils.networkIsOk(getActivity())) {
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                    .url(AppNetConfig.MYFAV + "&page=" + page)
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            getDataFail();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
//                            Log.e("TAG", "我收藏的版块=" + response.body().string());
                            setCurrentPage(page);
                            try {
                                MyFavForumBean myFavForumBean = new Gson().fromJson(response.body().string(), MyFavForumBean.class);
                                final MyFavForumBean.VariablesBean variablesBean = myFavForumBean.getVariables();
                                if (myFavForumBean != null) {
                                    setPreCount(variablesBean.getPerpage());
                                    setTotalCount(variablesBean.getCount());
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshView.setRefreshing(false);
                                        mAdapter.setData(variablesBean.getList(), isRefresh, hasMore());
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
