package cn.tencent.DiscuzMob.ui.fragment.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.adapter.ThreadAdapter;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.ui.adapter.RecommendAdapter;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/6/16.
 */

public class ThreadFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListener {
    RecommendAdapter recommendAdapter;
    private List<ForumThreadlistBean> list;
    private FooterForList mFooter;
    private int offset;
    private ListView mListView;
    private NestedSwipeRefreshLayout mRefreshView;
    private ViewAnimator mTip;
    private ImageView iv_nothing;

    private int Size = 10;
    ThreadAdapter threadAdapter;
    View inflate;
    private Map<String, Object> strings = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = View.inflate(RedNetApp.getInstance(), R.layout.thread_footprint, null);
        return inflate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTip = (ViewAnimator) inflate.findViewById(R.id.tip);
        iv_nothing = (ImageView) inflate.findViewById(R.id.iv_nothing);
        mRefreshView = (NestedSwipeRefreshLayout) inflate.findViewById(R.id.refresh);
        mListView = (ListView) inflate.findViewById(R.id.list);
        mRefreshView.setOnRefreshListener(this);
        recommendAdapter = new RecommendAdapter(getActivity());
        mRefreshView.setOnRefreshListener(this);
        mTip.setDisplayedChild(1);
        mFooter = new FooterForList(mListView);
        mFooter.setOnLoadListener(this);
        threadAdapter = new ThreadAdapter(getActivity());
        mListView.setAdapter(threadAdapter);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mFooter.reset();
        list = new ArrayList<>();
        offset = 0;
//        list.clear();
//        recommendAdapter.cleanData();
//        recommendAdapter.notifyDataSetChanged();
        List<ForumThreadlistBean> data = Modal.getInstance().getUserAccountDao().getScrollData(offset, 10);
        Log.e("TAG", "data=" + data.size());
        if (data.size() == 0) {
            mRefreshView.setRefreshing(false);
            mTip.setDisplayedChild(0);
            iv_nothing.setVisibility(View.VISIBLE);
        } else {
            iv_nothing.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            list.addAll(data);
            threadAdapter.setData(list);
//            threadAdapter.setData(list);
//            recommendAdapter.addData(list, "", "");
//            recommendAdapter.notifyDataSetChanged();
//            if (list.size() % 10 != 0) {
//                mFooter.finishAll();
//            }
            mRefreshView.setRefreshing(false);
            if (mTip.getDisplayedChild() != 0) {
                mTip.setDisplayedChild(0);
            }
        }
    }


    @Override
    public void onLoad() {
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            offset += Size;
            getMoreData();
        } else {
            mFooter.finishAll();
        }
    }

    private void getMoreData() {
        List<ForumThreadlistBean> data = Modal.getInstance().getUserAccountDao().getScrollData(offset, Size);
        list.addAll(data);
        threadAdapter.setData(list);
        threadAdapter.notifyDataSetChanged();
        if (list.size() < offset + Size) {
            mFooter.finishAll();
        } else {
            mFooter.finish();
        }
        if (mTip.getDisplayedChild() != 0) {
            mTip.setDisplayedChild(0);
        }
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
