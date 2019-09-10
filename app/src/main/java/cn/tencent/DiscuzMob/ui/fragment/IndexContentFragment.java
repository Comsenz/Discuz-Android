package cn.tencent.DiscuzMob.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.util.List;

import cn.tencent.DiscuzMob.model.Article;
import cn.tencent.DiscuzMob.model.ArticleVariables;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.Image;
import cn.tencent.DiscuzMob.ui.adapter.ArticleAdapter;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.widget.pagerindicator.CirclePageIndicator;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.AsyncImageView;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/21.
 * 精选下的子页面
 */
public class IndexContentFragment extends BaseFragment implements AdapterView.OnItemClickListener, ILoadListener, SwipeRefreshLayout.OnRefreshListener {

    private View mCoverView, mIndicatorLayout;
    private ViewAnimator mVa;
    private NestedSwipeRefreshLayout mRefreshView;
    private ListView mListView;
    private ViewPager mCoverPager;
    private TextView mPagerTitle;
    private ArticleAdapter mAdapter;
    private FooterForList mFooter;
    private int mPage = 1;

    public static IndexContentFragment newInstance(String fid, String name) {
        IndexContentFragment f = new IndexContentFragment();
        Bundle args = new Bundle();
        args.putString("fid", fid);
        args.putString("name", name);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_content, container, false);
        mVa = (ViewAnimator) view.findViewById(R.id.va);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mListView = (ListView) view.findViewById(R.id.list);
        mFooter = new FooterForList(mListView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setAdapter(mAdapter = new ArticleAdapter());
        mListView.setOnItemClickListener(this);
        mFooter.setOnLoadListener(this);
        mRefreshView.setOnRefreshListener(this);
        mVa.setDisplayedChild(1);
        //首页的name 值为 首页
        if (getArguments().getString("name").equals("首页")) {
            ApiVersion5.INSTANCE.requestNavigationHomePageList(true, 1,
                    new ApiVersion5.Result<Object>(getActivity(), ArticleVariables.class, true, null, false) {
                        @Override
                        public void onResult(int code, Object value) {
                            if (mRefreshView != null) {
                                if (value instanceof BaseModel) {
                                    ArticleVariables variables = ((BaseModel<ArticleVariables>) value).getVariables();
                                    if (variables != null) {
                                        setCover(variables.getImageinfo());
                                        mAdapter.set(variables.getForum_threadlist());
                                    }
                                } else {
                                    mRefreshView.setRefreshing(true);
                                }
                                mVa.setDisplayedChild(0);
                                onRefresh();
                            }
                        }
                    });
        } else {
            ApiVersion5.INSTANCE.requestNavigationCotentList(true, getArguments().getString("fid"), 1
                    , new ApiVersion5.Result<Object>(getActivity(), ArticleVariables.class, true, null, false) {
                        @Override
                        public void onResult(int code, Object value) {
                            if (mRefreshView != null) {
                                if (value instanceof BaseModel) {
                                    ArticleVariables variables = ((BaseModel<ArticleVariables>) value).getVariables();
                                    if (variables != null) {
                                        setCover(variables.getImageinfo());
                                        mAdapter.set(variables.getForum_threadlist());
                                    }
                                } else {
                                    mRefreshView.setRefreshing(true);
                                }
                                mVa.setDisplayedChild(0);
                                onRefresh();
                            }
                        }
                    });
        }
    }

    void load(final boolean append) {
        //首页的name 值为 首页  单独接口
        if (getArguments().getString("name").equals("首页")) {
            ApiVersion5.INSTANCE.requestNavigationHomePageList(false, !append ? mPage = 1 : ++mPage,
                    new ApiVersion5.Result<Object>(getActivity(), ArticleVariables.class, false, append ? mFooter : null, true) {
                        @Override
                        public void onResult(int code, Object value) {
                            if (mRefreshView != null) {
                                if (value instanceof BaseModel) {
                                    ArticleVariables variables = ((BaseModel<ArticleVariables>) value).getVariables();
                                    if (variables != null) {
                                        if (append) {
                                            mAdapter.append(variables.getForum_threadlist());
                                        } else {
                                            setCover(variables.getImageinfo());
                                            mAdapter.set(variables.getForum_threadlist());
                                            mFooter.reset();
                                        }
//                                        if (variables.getForum() == null || mAdapter.getCount() >= variables.getForum().getThreads()) {
//                                            mFooter.finishAll();
//                                        }
                                        if (variables.getForum() == null) {
                                            mFooter.finishAll();
                                        }
                                    }
                                }
                                if (code == 0) mPage = Math.max(--mPage, 1);
                                mRefreshView.setRefreshing(false);
                                if (mVa.getDisplayedChild() != 0) {
                                    mVa.setDisplayedChild(0);
                                }
                            }
                        }
                    });
        } else {
            ApiVersion5.INSTANCE.requestNavigationCotentList(false, getArguments().getString("fid"), !append ? mPage = 1 : ++mPage,
                    new ApiVersion5.Result<Object>(getActivity(), ArticleVariables.class, false, append ? mFooter : null, true) {
                        @Override
                        public void onResult(int code, Object value) {
                            if (mRefreshView != null) {
                                if (value instanceof BaseModel) {
                                    ArticleVariables variables = ((BaseModel<ArticleVariables>) value).getVariables();
                                    if (variables != null) {
                                        if (append) {
                                            mAdapter.append(variables.getForum_threadlist());
                                        } else {
                                            setCover(variables.getImageinfo());
                                            mAdapter.set(variables.getForum_threadlist());
                                            mFooter.reset();
                                        }
//                                        if (variables.getForum() == null || mAdapter.getCount() >= variables.getForum().getThreads()) {
//                                            mFooter.finishAll();
//                                        }
                                        if (variables.getForum() == null) {
                                            mFooter.finishAll();
                                        }
                                    }
                                }
                                if (code == 0) mPage = Math.max(--mPage, 1);
                                mRefreshView.setRefreshing(false);
                                if (mVa.getDisplayedChild() != 0) {
                                    mVa.setDisplayedChild(0);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        if (obj instanceof Article) {
            Article article = (Article) obj;
            toThreadDetails(getActivity(), getArguments().getString("fid"), article.getTid(), article.getSpecial());
        }
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(getActivity())) {
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onLoad() {
        if (RednetUtils.networkIsOk(getActivity()) && !mRefreshView.isRefreshing()) {
            load(true);
        } else {
            mFooter.finish();
        }
    }

    void setCover(List<Image> images) {
        if (!RednetUtils.isEmpty(images)) {
            if (mCoverPager != null) {
                mCoverPager.setAdapter(new CoverPagerAdapter(images));
            } else {
                mCoverView = LayoutInflater.from(getActivity()).inflate(R.layout.simple_pager_auto, mListView, false);
                mIndicatorLayout = mCoverView.findViewById(R.id.layout);
                mIndicatorLayout.setVisibility(images.size() > 1 ? View.VISIBLE : View.GONE);
                mPagerTitle = (TextView) mCoverView.findViewById(R.id.title);
                int widthPixels = getResources().getDisplayMetrics().widthPixels;
                int heightPixels = widthPixels * 9 / 16;
                mCoverPager = (ViewPager) mCoverView.findViewById(R.id.pager);
                mCoverPager.setLayoutParams(new FrameLayout.LayoutParams(widthPixels, heightPixels));
                mCoverPager.setAdapter(new CoverPagerAdapter(images));
                mCoverPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        mPagerTitle.setText(mCoverPager.getAdapter().getPageTitle(position));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
                CirclePageIndicator indicator = (CirclePageIndicator) mCoverView.findViewById(R.id.indicator);
                indicator.setViewPager(mCoverPager);
                indicator.setCentered(false);
                mPagerTitle.setText(mCoverPager.getAdapter().getPageTitle(0));
                mListView.setAdapter(null);
                mListView.addHeaderView(mCoverView);
                mListView.setAdapter(mAdapter = new ArticleAdapter());
            }
        } else {
            if (mCoverPager != null) {
                mListView.removeHeaderView(mCoverView);
            }
        }
    }

    static class CoverPagerAdapter extends PagerAdapter {

        List<Image> images;

        public CoverPagerAdapter(List<Image> images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images != null ? images.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return images.get(position).getTitle();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            AsyncImageView page = new AsyncImageView(container.getContext());
            page.setScaleType(ImageView.ScaleType.FIT_XY);
            page.load(images.get(position).getImgsrc(), android.R.drawable.screen_background_light_transparent);
            page.setTag(images.get(position));
            page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() instanceof Image) {
                        Context context = v.getContext();
                        context.startActivity(RednetUtils.newWebIntent(context, ((Image) v.getTag()).getImgurl()));
                    }
                }
            });
            container.addView(page, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
