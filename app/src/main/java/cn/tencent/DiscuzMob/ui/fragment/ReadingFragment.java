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

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.ui.activity.SearchActivity;
import cn.tencent.DiscuzMob.ui.activity.ToolbarActivity;
import cn.tencent.DiscuzMob.ui.adapter.FragmentPersistedPagerAdapter;
import cn.tencent.DiscuzMob.ui.fragment.guide.ArticleAllianceFragment;
import cn.tencent.DiscuzMob.ui.fragment.guide.ArticleFragment;
import cn.tencent.DiscuzMob.ui.fragment.guide.ArticleMiniHNFragment;
import cn.tencent.DiscuzMob.ui.fragment.guide.PopularStarFragment;
import cn.tencent.DiscuzMob.widget.pagerindicator.PagerSlidingTabStrip;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/20.
 * 阅读
 */
public class ReadingFragment extends BaseFragment implements View.OnClickListener {

    private PagerSlidingTabStrip mIndicator;
    private ViewPager mPager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reading_guide, container, false);
        mIndicator = (PagerSlidingTabStrip) view.findViewById(R.id.indicator);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        view.findViewById(R.id.search).setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager.setAdapter(new SimplePagerAdapter(getChildFragmentManager()));
        mIndicator.setViewPager(mPager);
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
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    class SimplePagerAdapter extends FragmentPersistedPagerAdapter {

        String[] titles;
        Fragment[] fragments = new Fragment[]{
                instantiate(getActivity(), ArticleAllianceFragment.class.getName()),
                instantiate(getActivity(), PopularStarFragment.class.getName()),
                instantiate(getActivity(), ArticleMiniHNFragment.class.getName()),
                ArticleFragment.newInstance("hot"),
        };

        public SimplePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            titles = getResources().getStringArray(R.array.menu_reading_guide);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ReadingFragment(阅读)");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ReadingFragment(阅读)");
    }

}
