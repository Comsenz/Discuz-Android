package cn.tencent.DiscuzMob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/4/26.
 */
public class SimpleRefreshFragment extends BaseFragment {

    protected ViewAnimator mTip;
    protected NestedSwipeRefreshLayout mRefreshView;
    protected ListView mListView;
    //    private ListView listview;
    protected ImageView imageView;
    protected SwipeMenuListView listView;
    protected RelativeLayout rl_threads;
    public ProgressBar progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.simple_refresh, container, false);
        mTip = (ViewAnimator) view.findViewById(R.id.tip);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mListView = (ListView) view.findViewById(R.id.list);
        imageView = (ImageView) view.findViewById(R.id.iv_nothing);
        listView = (SwipeMenuListView) view.findViewById(R.id.listView);
        rl_threads = (RelativeLayout) view.findViewById(R.id.rl_threads);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        return view;
    }

}
