package cn.tencent.DiscuzMob.ui.fragment.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.ui.adapter.MyNoticeAdapter;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.model.BaseMessageVariables;
import cn.tencent.DiscuzMob.model.MyNotice;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/6/22.
 */
public class ThreadNoticeFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, ILoadListener {

    private ViewAnimator mTip;
    private NestedSwipeRefreshLayout mRefreshView;
    private ListView mList;
    private FooterForList mFooter;
    private MyNoticeAdapter mAdapter;
    private int mPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_refresh, container, false);
        mTip = (ViewAnimator) view.findViewById(R.id.tip);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mList = (ListView) view.findViewById(R.id.list);
        mFooter = new FooterForList(mList);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList.setAdapter(mAdapter = new MyNoticeAdapter(getActivity()));
        mList.setOnItemClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mFooter.setOnLoadListener(this);
        mRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshView.setRefreshing(true);
                onRefresh();
            }
        }, 500);
    }

    void load(final boolean append) {
        ApiVersion5.INSTANCE.requestNotice(append ? ++mPage : (mPage = 1),
                new ApiVersion5.Result<Object>(getActivity(), MyNotice.class, false, append ? mFooter : null, !append) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                BaseMessageVariables<MyNotice> variables = ((BaseModel<BaseMessageVariables<MyNotice>>) value).getVariables();
                                if (append) {
                                    mAdapter.append(variables.getList());
                                } else {
                                    mAdapter.set(variables.getList());
                                    mFooter.reset();
                                }
                                if (variables.isFinishAll()) mFooter.finishAll();
                            }
                            if (code == 0) mPage = Math.max(1, --mPage);
                            mRefreshView.setRefreshing(false);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /* Object object = parent.getItemAtPosition(position);
        if (object instanceof MyNotice) {
            if ("post".equals(((MyNotice) object).getType())) {
                Map<String, String> notevar = ((MyNotice) object).getNotevar();
                BaseFragment.toThreadDetails(getActivity(), "", notevar.get("tid"), notevar.get("special"));
            }
        }*/
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
            load(true);
        } else {
            mFooter.finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ThreadNoticeFragment(我的提醒(帖子))");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MessageMineActivity(我的提醒(帖子))");
    }

}
