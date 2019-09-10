package cn.tencent.DiscuzMob.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.NavigationVariables;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.ui.activity.SearchActivity;
import cn.tencent.DiscuzMob.ui.activity.ToolbarActivity;
import cn.tencent.DiscuzMob.ui.adapter.FragmentPersistedPagerAdapter;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.pagerindicator.PagerSlidingTabStrip;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/20.
 * 精选
 */
public class IndexFragment extends BaseFragment implements View.OnClickListener {

    private View mSearch;
    private ViewAnimator mVa;
    private PagerSlidingTabStrip mPagerIndicator;
    private ViewPager mPager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        mVa = (ViewAnimator) view.findViewById(R.id.va);
        mSearch = view.findViewById(R.id.search);
        mPagerIndicator = (PagerSlidingTabStrip) view.findViewById(R.id.indicator);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearch.setOnClickListener(this);
        mPager.setAdapter(new IndexPagerAdapter(getChildFragmentManager()));
        mPagerIndicator.setViewPager(mPager);
        mVa.getChildAt(2).setOnClickListener(this);
        mVa.setDisplayedChild(1);
        ApiVersion5.INSTANCE.requestNavigation(true, "index"
                , new ApiVersion5.Result<Object>(getActivity(), NavigationVariables.class, true, null, false) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mVa != null) {
                            if (value instanceof BaseModel) {
                                ((IndexPagerAdapter) mPager.getAdapter())
                                        .setNavigation(((BaseModel<NavigationVariables>) value)
                                                .getVariables(), mPagerIndicator);
                                mVa.setDisplayedChild(0);
                            }
                            refresh();//刷新导航数据
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mSearch)) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        } else {
            if (v.getId() == R.id.label) {
                if (RednetUtils.networkIsOk(getActivity())) {
                    refresh();
                }
            }
        }
    }

    void refresh() {
        mVa.setDisplayedChild(1);
        ApiVersion5.INSTANCE.requestNavigation(false, "index",
                new ApiVersion5.Result<Object>(getActivity(), NavigationVariables.class, false, null, true) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mVa != null) {
                            if (value instanceof BaseModel) {
                                ((IndexPagerAdapter) mPager.getAdapter())
                                        .setNavigation(((BaseModel<NavigationVariables>) value)
                                                .getVariables(), mPagerIndicator);
                                if (mVa.getDisplayedChild() != 0) {
                                    mVa.setDisplayedChild(0);
                                }
                            } else {
                                mVa.setDisplayedChild(2);
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

    static final class IndexPagerAdapter extends FragmentPersistedPagerAdapter {

        NavigationVariables navigationVariables;

        public IndexPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void setNavigation(NavigationVariables navigationVariables, PagerSlidingTabStrip indicator) {
            if (navigationVariables != null && !navigationVariables.isEmpty()) {
                this.navigationVariables = navigationVariables;
                notifyDataSetChanged();
                indicator.notifyDataSetChanged();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return navigationVariables.getBanere()[position].getName();
        }

        @Override
        public Fragment getItem(int position) {
            return IndexContentFragment.newInstance(navigationVariables.getBanere()[position].getFid(), navigationVariables.getBanere()[position].getName());
        }

        @Override
        public int getCount() {
            return navigationVariables == null ? 0 : navigationVariables.getCount();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("IndexFragment(精选)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("IndexFragment(精选)");
    }

}
