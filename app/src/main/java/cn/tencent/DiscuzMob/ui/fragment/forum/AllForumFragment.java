package cn.tencent.DiscuzMob.ui.fragment.forum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.User;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.Cat;
import cn.tencent.DiscuzMob.model.Forum;
import cn.tencent.DiscuzMob.model.ForumVariables;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.ui.activity.ForumDetailsActivity;
import cn.tencent.DiscuzMob.ui.adapter.ForumIndexExpandableListAdapter;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/25.
 * 全部版面
 */
public class AllForumFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, ExpandableListView.OnChildClickListener {

    private ViewAnimator mVa;
    private SwipeRefreshLayout mRefreshView;
    private FloatingGroupExpandableListView mAllForumListView;
    private ForumIndexExpandableListAdapter mAdapter;
    private MyFavHandler myFavHandler;

    //    private AllForunmAdapter allForunmAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_refresh_ext, container, false);
        mVa = (ViewAnimator) view.findViewById(R.id.va);
        mRefreshView = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mAllForumListView = (FloatingGroupExpandableListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myFavHandler = new MyFavHandler();
        myFavHandler.onAttach((FragmentActivity) activity);
    }

    @Override
    public void onDetach() {
        myFavHandler.onDetach(getActivity());
        super.onDetach();
    }

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshView.setOnRefreshListener(this);
        mAdapter = new ForumIndexExpandableListAdapter(mAllForumListView);
//        allForunmAdapter = new AllForunmAdapter(mAllForumListView);
        mAllForumListView.setAdapter(new WrapperExpandableListAdapter(mAdapter));
//        mAllForumListView.setAdapter(new WrapperExpandableListAdapter(allForunmAdapter));
        mAllForumListView.setOnChildClickListener(this);
        mVa.setDisplayedChild(1);
        myFavHandler.setAdapter(mAdapter);
        myFavHandler.onViewCreated(view, savedInstanceState);
//        mRefreshView.setRefreshing(true);
//        toRefresh();
//        mAdapter.notifyDataSetChanged();
//        OkHttpUtils
//                .get()
//                .url("http://10.0.6.58/api/mobile/?module=forumindex&version=4&debug=1&mobiletype=IOS")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        Log.e("TAG", "请求失败");
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("TAG", "请求成功=" + response);
//                        AllForumBean allForumBean = new Gson().fromJson(response, AllForumBean.class);
//                        if (mRefreshView != null) {
//                            if (allForumBean != null) {
//                                AllForumBean.VariablesBean variables1 = allForumBean.getVariables();
//                                set(variables1);
//                            } else {
//                                mRefreshView.setRefreshing(true);
//                            }
//                            mVa.setDisplayedChild(0);
//                            onRefresh();
//                        }
//
//                    }
//                });

        ApiVersion5.INSTANCE.requestForum(true,
                new ApiVersion5.Result<Object>(getActivity(), ForumVariables.class, true, null, false) {
                    @Override
                    public void onResult(int code, Object value) {
//                        Log.e("TAG", "value =" + value.toString());
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                set(((BaseModel<ForumVariables>) value).getVariables());
                            } else {
                                mRefreshView.setRefreshing(true);
                            }
                            mVa.setDisplayedChild(0);
                            onRefresh();
                        }
                    }
                });
    }

    private void toRefresh() {
        if (hasLogin() && RednetUtils.networkIsOk(getActivity())) {
            User.prepareForum(getActivity(), new Callback() {
                @Override
                public void onCallback(Object obj) {
                    if (mRefreshView != null) {
                        mRefreshView.setRefreshing(false);
                    }
                }
            });
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        if (obj instanceof String) {
            startActivity(new Intent(getActivity(), ForumDetailsActivity.class).putExtra("fid", (String) obj));
        }
    }
    ForumVariables variables;
    @Override
    public void onRefresh() {
//        OkHttpUtils
//                .get()
//                .url("http://10.0.6.58/api/mobile/?module=forumindex&version=4&debug=1&mobiletype=IOS")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        Log.e("TAG", "请求失败");
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("TAG", "请求成功=" + response);
//                        AllForumBean allForumBean = new Gson().fromJson(response, AllForumBean.class);
//                        if (mRefreshView != null) {
//                            if (allForumBean != null) {
//                                AllForumBean.VariablesBean variables = allForumBean.getVariables();
//                                set(variables);
//                            }
//                            mRefreshView.setRefreshing(false);
//                            if (mVa.getDisplayedChild() != 0) {
//                                mVa.setDisplayedChild(0);
//                            }
//                        }else {
//                            mRefreshView.setRefreshing(false);
//                        }
//
//                    }
//                });
        ApiVersion5.INSTANCE.requestForum(false,
                new ApiVersion5.Result<Object>(getActivity(), ForumVariables.class, false, null, true) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                variables = ((BaseModel<ForumVariables>) value).getVariables();
                                set(variables);
                            }
                            mRefreshView.setRefreshing(false);
                            if (mVa.getDisplayedChild() != 0) {
                                mVa.setDisplayedChild(0);
                            }
                        } else {
                            mRefreshView.setRefreshing(false);
                        }

                    }
                });

    }


    void set(ForumVariables variables) {
        if (variables != null) {
            List<Cat> groupList = variables.getCatlist();
//            Log.e("TAG", "groupList ="+groupList.size());
//            List<AllForumBean.VariablesBean.CatlistBean> catlist = variables.getCatlist();
            List<Forum> forumlist = variables.getForumlist();
            ArrayList<ArrayList<Forum>> childList = new ArrayList<>();
//            Log.e("TAG", "childList="+childList.size());
//            List<AllForumBean.VariablesBean.ForumlistBean> forumlist = variables.getForumlist();
//            ArrayList<AllForumBean.VariablesBean.ForumlistBean> childIndexList = new ArrayList<>();
//            childIndexList.addAll(forumlist);
//            childList.add(childIndexList);
//            if (null != groupList && groupList.size() > 0 && null != childList && childList.size() > 0) {
//                allForunmAdapter = new AllForunmAdapter(RedNetApp.getAppContext(),groupList,childList);
//                mAllForumListView.setAdapter(allForunmAdapter);
//                for (int i = 0; i < allForunmAdapter.getGroupCount(); i++) {
//                    mAllForumListView.expandGroup(i);
//                }
//            }
            for (int i = 0; i < groupList.size(); i++) {
                ArrayList<Forum> childIndexList = new ArrayList<>();
                for (int j = 0; j < variables.getForumlist().size(); j++) {
                    Forum forum = variables.getForumlist().get(j);
                    if (contains(groupList.get(i).getForums(), forum.getFid())) {
                        forum.setFid(groupList.get(i).getFid());
                        ArrayList<String> allowpostspecial = (ArrayList<String>) forum.getAllowpostspecial();
                        if (allowpostspecial != null && allowpostspecial.size() > 0) {
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            forum.setAllowpostspecialString(gson.toJson(allowpostspecial));
                        }
                        childIndexList.add(forum);
                    }
                }
                childList.add(childIndexList);
            }

            HashMap<String, Object> hashData = new HashMap<>();
//            hashData.put("group", catlist);
//            hashData.put("child", forumlist);
            hashData.put("group", groupList);
            hashData.put("child", childList);
            mAdapter.setData(hashData);
        }
    }

    /**
     * 判断某个字符串是否存在于数组中
     *
     * @param stringArray 原数组
     * @param source      查找的字符串
     * @return 是否找到
     */
    public static boolean contains(String[] stringArray, String source) {
        // 转换为list
        List<String> tempList = Arrays.asList(stringArray);

        // 利用list的包含方法,进行判断
        if (tempList.contains(source)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasLogin() {
        return RedNetApp.getInstance().isLogin();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Forum forum = (Forum) mAdapter.getChild(groupPosition, childPosition);
//        Forum forum = (Forum) allForunmAdapter.getChild(groupPosition, childPosition);
        if(forum.getTodayposts()>0){//帖子数量小于0时不跳转
        startActivity(new Intent(getActivity(), ForumDetailsActivity.class)
                .putExtra("fid", forum.getFid())
                .putExtra("todaypost", forum.getTodayposts() + "")
                .putExtra("threads", forum.getThreads() + "")
                .putExtra("name", forum.getName()));
        ForumDetailsActivity.start(getActivity(), forum.getFid());
        }else {
            Toast.makeText(getActivity(),"此版块没有帖子",Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
