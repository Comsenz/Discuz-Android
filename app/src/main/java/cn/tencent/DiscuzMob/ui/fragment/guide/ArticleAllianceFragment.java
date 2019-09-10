package cn.tencent.DiscuzMob.ui.fragment.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.model.ArticleGuide;
import cn.tencent.DiscuzMob.model.ArticleGuideVariables;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.ui.adapter.GuideArticleAdapter;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.R;

/**
 * 阅读 - 联盟
 */
public class ArticleAllianceFragment extends BaseFragment implements AdapterView.OnItemClickListener, ILoadListener, SwipeRefreshLayout.OnRefreshListener{

    private ViewAnimator mVa;
    private NestedSwipeRefreshLayout mRefreshView;
    private ListView mListView;
    private GuideArticleAdapter mAdapter;
    private int page = 1;
    private FooterForList mFooter;
//    private String mView;//hot digest

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_simple_content, container, false);
        mVa = (ViewAnimator) view.findViewById(R.id.va);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mListView = (ListView) view.findViewById(R.id.list);
        mFooter = new FooterForList(mListView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setAdapter(mAdapter = new GuideArticleAdapter());
        mFooter.setOnLoadListener(this);
        mListView.setOnItemClickListener(this);
        mRefreshView.setOnRefreshListener(this);
//        mView = getArguments().getString("view");
        mVa.setDisplayedChild(1);
        //先读缓存 之后强制刷新
        ApiVersion5.INSTANCE.requestReadingAllianceGuide(true, 1, new ApiVersion5.Result<Object>(getActivity(), ArticleGuideVariables.class, true, null, false) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                mAdapter.setData(((BaseModel<ArticleGuideVariables>) value).getVariables().getData());
                            } else {
                                mRefreshView.setRefreshing(true);
                            }
                            mVa.setDisplayedChild(0);
                            onRefresh();
                        }
                    }
                });
    }

    void load(final boolean append) {
        ApiVersion5.INSTANCE.requestReadingAllianceGuide(false,append ? ++page : (page = 1),new ApiVersion5.Result<Object>(getActivity(), ArticleGuideVariables.class, false, append ? mFooter : null, true) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                ArticleGuideVariables variables = ((BaseModel<ArticleGuideVariables>) value).getVariables();
                                if (variables != null) {
                                    if (append) {
                                        mAdapter.append(variables.getData());
                                    } else {
                                        mAdapter.setData(variables.getData());
                                        mFooter.reset();
                                    }
                                    if (variables.getThreads() == 0 || mAdapter.getCount() >= variables.getThreads()) {
                                        mFooter.finishAll();
                                    }
                                }
                            }
                            if (code == 0) page = Math.max(1, --page);
                            mRefreshView.setRefreshing(false);
                            if (mVa.getDisplayedChild() != 0) {
                                mVa.setDisplayedChild(0);
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        if (obj instanceof ArticleGuide) {
            ArticleGuide articleGuide = (ArticleGuide) obj;
            BaseFragment.toThreadDetails(getActivity(), articleGuide.getFid(), articleGuide.getTid(), articleGuide.getSpecial());
        }
    }

    @Override
    public void onLoad() {
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(getActivity())) {
            load(true);
        } else {
            mFooter.finish();
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ArticleAllianceFragment(阅读(联盟))");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ArticleAllianceFragment(阅读(联盟))");
    }

}
