package cn.tencent.DiscuzMob.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.model.ArticleSnap;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.CommunityVariables;
import cn.tencent.DiscuzMob.ui.activity.SearchActivity;
import cn.tencent.DiscuzMob.ui.activity.ToolbarActivity;
import cn.tencent.DiscuzMob.ui.adapter.ArticleSnapAdapter;
import cn.tencent.DiscuzMob.ui.adapter.BanereAdapter;
import cn.tencent.DiscuzMob.ui.adapter.BanerePagerAdapter;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.widget.pagerindicator.CirclePageIndicator;
import cn.tencent.DiscuzMob.model.Banere;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/20.
 * 社区  分为四块(按照分割线划分)
 */
public class CommunityFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private View mSearch;
    private ViewAnimator mVa;
    private NestedSwipeRefreshLayout mRefreshView;
    private ViewPager mPager;
    private CirclePageIndicator mIndicator;
    private ListView mThread, mForum;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_community, container, false);
        mSearch = view.findViewById(R.id.search);//1
        mVa = (ViewAnimator) view.findViewById(R.id.va);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mPager = (ViewPager) view.findViewById(R.id.pager);//2
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);//2
        mThread = (ListView) view.findViewById(R.id.thread);//3
        mForum = (ListView) view.findViewById(R.id.forum);//4
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearch.setOnClickListener(this);
        mVa.getChildAt(1).setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mForum.setOnItemClickListener(this);
        mThread.setOnItemClickListener(this);
        mPager.setAdapter(new BanerePagerAdapter());
        mThread.setAdapter(new ArticleSnapAdapter());
        mForum.setAdapter(new BanereAdapter());
        mIndicator.setViewPager(mPager);
        mVa.setDisplayedChild(2);
        ApiVersion5.INSTANCE.requestCommunity(true, new ApiVersion5.Result<Object>(getActivity(), CommunityVariables.class, true, null, false) {
            @Override
            public void onResult(int code, Object value) {
                if (mRefreshView != null) {
                    if (value instanceof BaseModel) {
                        CommunityVariables variables = ((BaseModel<CommunityVariables>) value).getVariables();
                        if (variables != null) {
                            mPager.setAdapter(new BanerePagerAdapter(variables.getBanerehead()));
                            ((ArticleSnapAdapter) mThread.getAdapter()).setData(variables.getArtlist());
                            ((BanereAdapter) mForum.getAdapter()).setData(variables.getBanerefoot());
                        }
                        mVa.setDisplayedChild(0);
                    } else {
                        if (!RednetUtils.networkIsOk(null)) {
                            mVa.setDisplayedChild(1);
                        } else {
                            mRefreshView.setRefreshing(true);
                        }
                    }
                    if (mVa.getDisplayedChild() != 1) {
                        onRefresh();
                    }
                }
            }
        });
    }

    void initToolbar() {
        if (getSupportToolbar() != null) {
            getSupportToolbar().show(ToolbarActivity.Toolbar.DEFAULT);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initToolbar();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        if (obj instanceof Banere) {
//            ForumDetailsActivity.start(getActivity(), (String) obj);
            startActivity(RednetUtils.newWebIntent(getActivity(), ((Banere) obj).getUrl())
                    .putExtra("title", ((Banere) obj).getName()));
        } else if (obj instanceof ArticleSnap) {
            startActivity(RednetUtils.newWebIntent(getActivity(), ((ArticleSnap) obj).getTourl())
                    .putExtra("title", ((ArticleSnap) obj).getTitle()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        } else {
            if (RednetUtils.networkIsOk(getActivity())) {
                mVa.setDisplayedChild(2);
                onRefresh();
            }
        }
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(getActivity())) {
            ApiVersion5.INSTANCE.requestCommunity(false, new ApiVersion5.Result<Object>(getActivity(), CommunityVariables.class, false, null, true) {
                @Override
                public void onResult(int code, Object value) {
                    if (mRefreshView != null) {
                        if (value instanceof BaseModel) {
                            CommunityVariables variables = ((BaseModel<CommunityVariables>) value).getVariables();
                            if (variables != null) {
                                mPager.setAdapter(new BanerePagerAdapter(variables.getBanerehead()));
                                ((ArticleSnapAdapter) mThread.getAdapter()).setData(variables.getArtlist());
                                ((BanereAdapter) mForum.getAdapter()).setData(variables.getBanerefoot());
                            }
                        }
                        if (mVa.getDisplayedChild() != 0) {
                            mVa.setDisplayedChild(0);
                        }
                        mRefreshView.setRefreshing(false);
                    }
                }
            });
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("CommunityFragment(社区)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("CommunityFragment(社区)");
    }

}
