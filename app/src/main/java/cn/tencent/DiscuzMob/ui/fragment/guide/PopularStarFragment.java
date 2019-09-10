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

import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.Star;
import cn.tencent.DiscuzMob.ui.adapter.StarAdapter;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.model.StarVariables;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/5/3.
 */
public class PopularStarFragment extends BaseFragment implements AdapterView.OnItemClickListener, ILoadListener, SwipeRefreshLayout.OnRefreshListener {

    private ViewAnimator mTip;
    private NestedSwipeRefreshLayout mRefreshView;
    private ListView mListView;
    // private FooterForList mFooter;
    private StarAdapter mAdapter;
    private int mPage = 1;
    private StarVariables variables;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.simple_refresh, container, false);
        mTip = (ViewAnimator) view.findViewById(R.id.tip);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mListView = (ListView) view.findViewById(R.id.list);
        //mFooter = new FooterForList(mListView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setAdapter(mAdapter = new StarAdapter());
        //mFooter.setOnLoadListener(this);
        mListView.setOnItemClickListener(this);

        mRefreshView.setOnRefreshListener(this);
        mTip.setDisplayedChild(1);
        ApiVersion5.INSTANCE.requestPopularStars(true, 1, new ApiVersion5.Result<Object>(getActivity(), StarVariables.class, true, null, false) {
            @Override
            public void onResult(int code, Object value) {
                if (mRefreshView != null) {
                    if (value instanceof BaseModel) {
                        mAdapter.setData(((BaseModel<StarVariables>) value).getVariables().getDiyuser());
                    } else {
                        mRefreshView.setRefreshing(true);
                    }
                    mTip.setDisplayedChild(0);
                    onRefresh();
                }
            }
        });
    }

    void load(final boolean append) {
        ApiVersion5.INSTANCE.requestPopularStars(false, append ? ++mPage : (mPage = 1)
                , new ApiVersion5.Result<Object>(getActivity(), StarVariables.class, false, /*append ? mFooter :*/ null, true) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                variables = ((BaseModel<StarVariables>) value).getVariables();
                                if (variables != null) {
                                    if (append) {
                                        mAdapter.append(variables.getDiyuser());
                                    } else {
                                        mAdapter.setData(variables.getDiyuser());
                                    }
                                }//finishAll
                            }
                            if (code == 0) mPage = Math.max(1, --mPage);
                            mRefreshView.setRefreshing(false);
                            if (mTip.getDisplayedChild() != 0) {
                                mTip.setDisplayedChild(0);
                            }
                        }
                    }
                });

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (variables!=null){
//                    startActivity(new Intent().setClass(getActivity(), SimpleWebActivity.class).putExtra("url", variables.getDiyuser().get(position).getUrl()));
//                }else {
//                    RednetUtils.toast(getActivity(), "无法查看详情");
//                }
//            }
//        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        if (obj instanceof Star) {
            startActivity(RednetUtils.newWebIntent(getActivity(), ((Star) obj).getUrl()).putExtra("title", ((Star) obj).getUsername()));
        } else {
            RednetUtils.toast(getActivity(), "无效的用户id");
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
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(getActivity())) {
            //load(true);
        } else {
            //mFooter.finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PopularStarFragment(阅读(专栏))");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PopularStarFragment(阅读(专栏))");
    }

}
