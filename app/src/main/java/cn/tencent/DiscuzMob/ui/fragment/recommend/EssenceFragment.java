package cn.tencent.DiscuzMob.ui.fragment.recommend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.model.EssenceBean;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.Event.RefreshUserInfo;
import cn.tencent.DiscuzMob.ui.adapter.EssenceAdapter;
import cn.tencent.DiscuzMob.ui.adapter.RecommendAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.StringUtil;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;

import static cn.tencent.DiscuzMob.R.id.code;


/**
 * Created by cg on 2017/5/10.
 * 精华页面
 */

public class EssenceFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListener {
    RecommendAdapter adapter;
    private List<EssenceBean.VariablesBean.DataBean> list = new ArrayList<>();
    private FooterForList mFooter;
    private int offset;
    private int page;
    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private String threads;
    private String formhash;
    private Map<String, Object> strings = new HashMap<>();
    EssenceAdapter myFavForumAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cookiepre_auth = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey");
        mRefreshView.setOnRefreshListener(this);
        adapter = new RecommendAdapter(getActivity());
//        adapter.setTag("noadd");
        mListView.setAdapter(adapter);
        mRefreshView.setOnRefreshListener(this);
        mFooter = new FooterForList(mListView);
        mFooter.setOnLoadListener(this);
        myFavForumAdapter = new EssenceAdapter(getActivity());
        mListView.setAdapter(myFavForumAdapter);
        mTip.setDisplayedChild(1);
        strings.clear();
        onRefresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onRefresh() {
        list.clear();
        if (mFooter != null && mFooter.isLoading()) {
            RednetUtils.toast(RedNetApp.getInstance(), "正在加载");
            mRefreshView.setRefreshing(false);
        } else if (RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            if (myFavForumAdapter != null) {
                myFavForumAdapter.cleanData();
            }
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    private void load(final boolean append) {
        if (RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            mTip.setDisplayedChild(1);
            progress.setVisibility(View.GONE);
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                    .url(AppNetConfig.ESSENCE + String.valueOf(append ? ++page : (page = 1)))
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                    .enqueue(new com.squareup.okhttp.Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
//                            Log.e("TAG", "精华url=" + AppNetConfig.ESSENCE + String.valueOf(page));

                            try {
                                String json = response.body().string();
                                LogUtils.d("精华内容="+json);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    });
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                    .url(AppNetConfig.ESSENCE + String.valueOf(append ? ++page : (page = 1)))
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                    .enqueue(new com.squareup.okhttp.Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRefreshView.setRefreshing(false);
                                    mTip.setDisplayedChild(0);
                                    Toast.makeText(RedNetApp.getInstance(), "网络请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String string = response.body().string();
                            JSONObject jso = null;
                            try {
                                jso = JSON.parseObject(string);
                                if (null != jso && !jso.toString().contains("error")) {
                                    JSONObject Variables = (JSONObject) jso.get("Variables");
                                    Object groupiconid = Variables.get("groupiconid");
                                    if (null != groupiconid) {
                                        for (Map.Entry<String, Object> entry : ((JSONObject) groupiconid).entrySet()) {
                                            strings.put(entry.getKey(), entry.getValue().toString());
                                        }
                                    }
                                }
                                EssenceBean essenceBean = new Gson().fromJson(string, EssenceBean.class);
                                if (mRefreshView != null) {
                                    if (essenceBean != null) {
                                        final EssenceBean.VariablesBean variables = essenceBean.getVariables();
                                        if (variables != null) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (page == 1 && StringUtil.isEmpty(variables.getData().toString())) {
                                                        mListView.setVisibility(View.GONE);
                                                        imageView.setVisibility(View.VISIBLE);
                                                        mFooter.finishAll();
                                                    } else {
                                                        mListView.setVisibility(View.VISIBLE);
                                                        imageView.setVisibility(View.GONE);
                                                        if (append) {
                                                            list.addAll(variables.getData());
//                                                        myFavForumAdapter = new EssenceAdapter(getActivity(), variables.getData(), strings);
//                                                        mListView.setAdapter(myFavForumAdapter);
                                                            myFavForumAdapter.addData(variables.getData());
                                                            if (page != 1 && variables.getData().size() == 0) {
                                                                mFooter.finishAll();
                                                            } else {
                                                                mFooter.reset();
                                                            }

                                                        } else {
                                                            list.clear();
                                                            list.addAll(variables.getData());
                                                            myFavForumAdapter.setData(variables.getData(), strings);
                                                            mFooter.reset();
                                                        }
                                                    }
                                                }
                                            });

                                        }
                                    }

                                    if (code == 0) page = Math.max(1, --page);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mRefreshView.setRefreshing(false);
                                            mTip.setDisplayedChild(0);
                                        }
                                    });

                                } else {
                                    onRefresh();
                                }

                            } catch (JsonSyntaxException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshView.setRefreshing(false);
                                        mTip.setDisplayedChild(0);
                                        Toast.makeText(RedNetApp.getInstance(), "请求异常", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                e.printStackTrace();
                            } catch (Exception e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshView.setRefreshing(false);
                                        mTip.setDisplayedChild(0);
                                        Toast.makeText(RedNetApp.getInstance(), "请求异常", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            mRefreshView.setRefreshing(false);
        }
    }


    @Override
    public void onLoad() {
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            load(true);
        } else {
            mFooter.finish();
        }
    }

    @Subscribe
    public void onEventMainThread(ReFreshImg img) {
        Log.e("TAG", "无图模式");
        onRefresh();
    }

    @Subscribe
    public void onEventMainThread(RefreshUserInfo finish) {
        String id = finish.getId();
        Log.e("TAG", "id=" + id);
        if (id != null && !TextUtils.isEmpty(id)) {
//            onRefresh();
            onRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
