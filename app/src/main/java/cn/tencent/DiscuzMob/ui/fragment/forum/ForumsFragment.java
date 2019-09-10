package cn.tencent.DiscuzMob.ui.fragment.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.CatlistBean;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.adapter.ForumsAdapter;
import cn.tencent.DiscuzMob.ui.adapter.MyBaseExpandableListAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.model.AllForumBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;


/**
 * Created by cg on 2017/4/14.
 */

public class ForumsFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener {
    //    private XExpandableListView xExpandableListView;
    private ListView listview;
    //一级列表的集合
    private List<CatlistBean> listGroup;
    //二级列表的集合
    private List<List<String>> listChild;
    private MyBaseExpandableListAdapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshView.setOnRefreshListener(this);
        mTip.setDisplayedChild(1);
        onRefresh();
    }

    String name;
    String posts;
    String todayposts;
    ForumsAdapter forumsAdapter;

    private void getDataFromNe() {
        String cookiepre = CacheUtils.getString(RedNetApp.getInstance(), "cookiepre");
        Log.e("TAG", "cookiepre2=" + cookiepre);
        OkHttpUtils
                .get()
                .addHeader("cookiepre", cookiepre)
                .url(AppNetConfig.ALLFORUM)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        mRefreshView.setRefreshing(false);
                        mTip.setDisplayedChild(0);
                        Toast.makeText(RedNetApp.getInstance(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        AllForumBean allForumBean = null;
                        if (response != null && !TextUtils.isEmpty(response) && !response.contains("error")) {
                            try {
                                if (mRefreshView != null) {
                                    allForumBean = new Gson().fromJson(response, AllForumBean.class);
                                    listGroup = allForumBean.getVariables().getCatlist();
                                    listChild = new ArrayList<List<String>>();
                                    CacheUtils.putString(RedNetApp.getInstance(), "formhash2", allForumBean.getVariables().getFormhash());
                                    CacheUtils.putString(RedNetApp.getInstance(), "cookiepre2", allForumBean.getVariables().getCookiepre());
                                  if(listGroup != null && listGroup.size() >0) {
                                      imageView.setVisibility(View.GONE);
                                      for (int i = 0; i < listGroup.size(); i++) {
                                          List<String> forums = listGroup.get(i).getForums();
                                          listChild.add(forums);
                                      }
                                      final List<AllForumBean.VariablesBean.ForumlistBean> forumlist = allForumBean.getVariables().getForumlist();
                                      forumsAdapter = new ForumsAdapter(getActivity(), listGroup, listChild, forumlist);
                                      mListView.setAdapter(forumsAdapter);
                                  }else {
                                      imageView.setVisibility(View.VISIBLE);
                                  }


                                } else {
                                    onRefresh();
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(RedNetApp.getInstance(), "请求失败", Toast.LENGTH_SHORT).show();
                        }

                        mRefreshView.setRefreshing(false);
                        mTip.setDisplayedChild(0);
//
                    }
                });
    }

    @Override
    public void onRefresh() {
        if (forumsAdapter != null) {
            forumsAdapter.cleanData();
        }
        getDataFromNe();
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
