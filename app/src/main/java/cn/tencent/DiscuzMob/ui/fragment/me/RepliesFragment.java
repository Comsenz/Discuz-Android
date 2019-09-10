package cn.tencent.DiscuzMob.ui.fragment.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.model.UserRepliesBean;
import cn.tencent.DiscuzMob.ui.adapter.ReplayAdapter;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.ReplyBean;
import cn.tencent.DiscuzMob.model.ThreadlistBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;

/**
 * Created by cg on 2017/7/11.
 */

public class RepliesFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListener {
    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private FooterForList mFooter;
    private int mPage;
    ReplayAdapter replayAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cookiepre_auth = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey");
        mRefreshView.setOnRefreshListener(this);
        mFooter = new FooterForList(mListView);
        mFooter.setOnLoadListener(this);
        replayAdapter = new ReplayAdapter();
        mListView.setAdapter(replayAdapter);
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


    List<UserRepliesBean.VariablesBean.DataBean> lists_data = new ArrayList<>();

    private void load(final boolean append) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.MYREPLISE + "&uid=" + RedNetApp.getInstance().getUid() + "&page=" + String.valueOf(append ? ++mPage : (mPage = 1)))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                                mRefreshView.setRefreshing(false);
                                mTip.setDisplayedChild(0);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        UserRepliesBean userRepliesBean = null;

                        try {
                            String jsonStr = response.body().string();
                            LogUtils.d("回复:" + jsonStr);
                            userRepliesBean = new Gson().fromJson(jsonStr, UserRepliesBean.class);
                            if (mRefreshView != null) {
                                if (userRepliesBean.getVariables() != null) {
                                    final List<UserRepliesBean.VariablesBean.DataBean> lists = userRepliesBean.getVariables().getData();
//                                    final List<ThreadlistBean> threadlist = userRepliesBean.getVariables().getData();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (append) {
                                                if (null == lists || lists.size() == 0) {
                                                    mFooter.finishAll();
                                                } else {
                                                    for (int i = 0; i < lists.size(); i++) {
                                                        lists_data.add(lists.get(i));
                                                    }
                                                    replayAdapter.appendData(lists_data);
                                                    replayAdapter.notifyDataSetChanged();
                                                    mFooter.reset();
                                                }
                                            } else {
                                                lists_data.clear();
                                                for (int i = 0; i < lists.size(); i++) {
                                                    lists_data.add(lists.get(i));
                                                }
                                                replayAdapter.appendData(lists_data);
                                                replayAdapter.notifyDataSetChanged();
                                                mFooter.reset();
                                            }
//                                            if (append) {
//                                                  if (null == threadlist || threadlist.size() == 0) {
//                                                    getActivity().runOnUiThread(new Runnable() {
//                                                        @Override
//                                                        public void run() {
//                                                            mFooter.finishAll();
//                                                        }
//                                                    });
//                                                } else {
//                                                    for (int i = 0; i < threadlist.size(); i++) {
//                                                        List<ReplyBean> reply = threadlist.get(i).getReply();
//                                                        ThreadlistBean threadlistBean = threadlist.get(i);
//                                                        String subject = threadlist.get(i).getSubject();
//                                                        for (int i1 = 0; i1 < reply.size(); i1++) {
//                                                            lists.add(reply.get(i1));
//                                                            subjects.add(subject);
//                                                            threadlistBeanList.add(threadlistBean);
//                                                        }
//                                                    }
//                                                    replayAdapter.appendData(lists, threadlistBeanList);
//                                                    replayAdapter.notifyDataSetChanged();
//                                                    mFooter.reset();
//                                                }
//
//                                            } else {
//                                                lists.clear();
//                                                subjects.clear();
//                                                threadlistBeanList.clear();
//                                                if (null != threadlist && threadlist.size() > 0) {
//                                                    imageView.setVisibility(View.GONE);
//                                                    mListView.setVisibility(View.VISIBLE);
//                                                    for (int i = 0; i < threadlist.size(); i++) {
//                                                        List<ReplyBean> reply = threadlist.get(i).getReply();
//                                                        String subject = threadlist.get(i).getSubject();
//                                                        ThreadlistBean threadlistBean = threadlist.get(i);
//                                                        for (int i1 = 0; i1 < reply.size(); i1++) {
//                                                            lists.add(reply.get(i1));
//                                                            subjects.add(subject);
//                                                            threadlistBeanList.add(threadlistBean);
//                                                        }
//                                                    }
//                                                    replayAdapter.appendData(lists, threadlistBeanList);
//                                                    replayAdapter.notifyDataSetChanged();
//                                                    mFooter.reset();
//                                                } else {
//                                                    imageView.setVisibility(View.VISIBLE);
//                                                    mListView.setVisibility(View.GONE);
//                                                }

//                                            }
                                        }
                                    });

                                }
//                                    if (code == 0) mPage = Math.max(1, --mPage);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshView.setRefreshing(false);
                                        mTip.setDisplayedChild(0);
                                    }
                                });

                            }
                        } catch (JsonSyntaxException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRefreshView.setRefreshing(false);
                                    mTip.setDisplayedChild(0);
                                    Toast.makeText(getActivity(), "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        } catch (IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRefreshView.setRefreshing(false);
                                    mTip.setDisplayedChild(0);
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
}
