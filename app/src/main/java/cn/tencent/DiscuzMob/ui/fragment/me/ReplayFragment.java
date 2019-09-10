package cn.tencent.DiscuzMob.ui.fragment.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.UserThreadsBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.adapter.UserThreadsAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;

/**
 * Created by cg on 2017/7/11.
 * 主题
 */

public class ReplayFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListener {

    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private FooterForList mFooter;
    private int mPage;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cookiepre_auth = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey");
        mRefreshView.setOnRefreshListener(this);
        mFooter = new FooterForList(mListView);
        mFooter.setOnLoadListener(this);
        mTip.setDisplayedChild(1);
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
                .url(AppNetConfig.MYREPLISE + "&page=" + String.valueOf(append ? ++mPage : (mPage = 1)))
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
//                            Log.e("TAG", "我的主题=" + response.body().string());
                        UserThreadsBean userThreadsBean = new Gson().fromJson(response.body().string(), UserThreadsBean.class);
                        if (mRefreshView != null) {
                            if (userThreadsBean != null) {
                                final UserThreadsBean.VariablesBean variables1 = userThreadsBean.getVariables();
                                if (variables1 != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<UserThreadsBean.VariablesBean.DataBean> data = variables1.getData();
                                            if (append) {
                                                UserThreadsAdapter myFavForumAdapter = new UserThreadsAdapter(data);
                                                mListView.setAdapter(myFavForumAdapter);
                                            } else {
                                                if (null != data && data.size() > 0) {
                                                    imageView.setVisibility(View.GONE);
                                                    mListView.setVisibility(View.VISIBLE);
                                                    UserThreadsAdapter myFavForumAdapter = new UserThreadsAdapter(data);
                                                    mListView.setAdapter(myFavForumAdapter);
                                                    mFooter.reset();
                                                } else {
                                                    imageView.setVisibility(View.VISIBLE);
                                                    mListView.setVisibility(View.GONE);
                                                }

                                            }
                                            if ((data == null) || (data.size() < Integer.parseInt(variables1.getPerpage()))) {
                                                mFooter.finishAll();
                                            }
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
