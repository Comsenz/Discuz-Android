package cn.tencent.DiscuzMob.ui.fragment.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.MessageRepliesBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.adapter.MessageRepliesAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by cg on 2017/4/19.
 */

public class RepliesFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener {
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cookiepre_auth = CacheUtils.getString(getActivity(), "cookiepre_auth");

        cookiepre_saltkey = CacheUtils.getString(getActivity(), "cookiepre_saltkey");
        formhash = CacheUtils.getString(getActivity(), "formhash");
        mRefreshView.setOnRefreshListener(this);

        mTip.setDisplayedChild(1);
        getDataFromNet();
    }


    private void getDataFromNet() {
        Log.e("TAG", "ssss=" + AppNetConfig.MESSAGE_REPLIES + "&touid=" + CacheUtils.getString(RedNetApp.getInstance(), "userId") + "&page=1"+"&version=5");
        OkHttpUtils
                .get()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";" + formhash + ";")
                .url(AppNetConfig.MESSAGE_REPLIES + "&touid=" + CacheUtils.getString(RedNetApp.getInstance(), "userId") + "&page=1"+"&version=5")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("TAG", "回复请求失败");
                        Toast.makeText(getActivity(), "回复请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "回复  " + response);
                        MessageRepliesBean messageRepliesBean = new Gson().fromJson(response, MessageRepliesBean.class);
                        if (mRefreshView != null) {
                            if (messageRepliesBean != null) {
                                List<MessageRepliesBean.VariablesBean.ListBean> list = messageRepliesBean.getVariables().getList();
                                MessageRepliesAdapter messageRepliesAdapter = new MessageRepliesAdapter(list);
                                mListView.setAdapter(messageRepliesAdapter);

                            } else {
                                mRefreshView.setRefreshing(true);
                                onRefresh();
                            }
                            mTip.setDisplayedChild(0);

                        }
                    }
                });
    }


    @Override
    public void onRefresh() {
//        if (RednetUtils.networkIsOk(getActivity())) {
//            mTip.setDisplayedChild(1);
//            OkHttpUtils
//                    .get()
//                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";" + formhash + ";")
//                    .url(AppNetConfig.MYFAV)
//                    .build()
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onError(Request request, Exception e) {
//                            Log.e("TAG", "热帖请求失败");
//                        }
//
//                        @Override
//                        public void onResponse(String response) {
//                            Log.e("TAG", "热帖请求成功=" + response);
//                            myFavForumBean = new Gson().fromJson(response, MyFavForumBean.class);
//                            if (mRefreshView != null) {
//                                if (myFavForumBean != null) {
//                                    List<MyFavForumBean.VariablesBean.ListBean> list = myFavForumBean.getVariables().getList();
//                                    myFavForumAdapter = new MyFavForumAdapter(getActivity(), list);
//                                    mListView.setAdapter(myFavForumAdapter);
//                                }
//                                mRefreshView.setRefreshing(false);
//                                if (mTip.getDisplayedChild() != 0) {
//                                    mTip.setDisplayedChild(0);
//                                }
//                            }
//
//                        }
//                    });
//        } else {
//            mRefreshView.setRefreshing(false);
//        }
    }
}
