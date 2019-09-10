package cn.tencent.DiscuzMob.ui.fragment.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.tencent.DiscuzMob.model.HotForumBean;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.adapter.ForumAdapter;
import cn.tencent.DiscuzMob.ui.adapter.HotForumAdapter;
import cn.tencent.DiscuzMob.ui.fragment.GlideViewRefreshFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by AiWei on 2016/4/25.
 */
public class HotForumFragment extends GlideViewRefreshFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private ForumAdapter mAdapter;
    private HotForumAdapter hotForumAdapter;
    HotForumBean hotForumBean;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshView.setOnRefreshListener(this);
        grid.setOnItemClickListener(this);
        hotForumAdapter = new HotForumAdapter();
        grid.setAdapter(hotForumAdapter);
        mTip.setDisplayedChild(1);
        onRefresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Object obj = parent.getItemAtPosition(position);
//        String fid = hotForumBean.getVariables().getData().get(position).getFid();
//        if (fid != null && !fid.equals("")) {
//            startActivity(new Intent(getActivity(), ForumDetailsActivity.class).putExtra("fid", fid));
//        }
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(getActivity())) {
            mTip.setDisplayedChild(1);
            hotForumAdapter.cleanData();
            String cookiepre = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre");
            OkHttpUtils
                    .get()
                    .addHeader("cookie", cookiepre)
                    .url(AppNetConfig.HOTFORUM)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Toast.makeText(RedNetApp.getInstance(), "请求失败", Toast.LENGTH_SHORT).show();
                            mRefreshView.setRefreshing(false);
                            if (mTip.getDisplayedChild() != 0) {
                                mTip.setDisplayedChild(0);
                            }
                        }

                        @Override
                        public void onResponse(String response) {
                            if (response != null && !TextUtils.isEmpty(response) && !response.contains("error")) {
                                try {
                                    hotForumBean = new Gson().fromJson(response, HotForumBean.class);
                                    CacheUtils.putString(RedNetApp.getInstance(), "formhash2", hotForumBean.getVariables().getFormhash());
                                    if (mRefreshView != null) {
                                        if (hotForumBean != null) {
                                            List<HotForumBean.VariablesBean.DataBean> data = hotForumBean.getVariables().getData();
                                            if (null != data && data.size() > 0) {
                                                hotForumAdapter.addData(data);
                                            } else {
                                                iv_nothing.setVisibility(View.VISIBLE);
                                            }

                                        }
                                    }
                                } catch (JsonSyntaxException e) {
                                    Toast.makeText(RedNetApp.getInstance(), "请求异常", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            } else {

                            }

                            mRefreshView.setRefreshing(false);
                            if (mTip.getDisplayedChild() != 0) {
                                mTip.setDisplayedChild(0);
                            }
                        }
                    });
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Subscribe
    public void onEventMainThread(ReFreshImg img) {
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
