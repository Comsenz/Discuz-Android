package cn.tencent.DiscuzMob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/5/9.
 */

public class GlideViewRefreshFragment  extends BaseFragment {

    protected ViewAnimator mTip;
    protected NestedSwipeRefreshLayout mRefreshView;
    protected GridView grid;
    protected ImageView iv_nothing;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.glide_refresh, container, false);
        mTip = (ViewAnimator) view.findViewById(R.id.tip);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        grid = (GridView) view.findViewById(R.id.grid);
        iv_nothing = (ImageView) view.findViewById(R.id.iv_nothing);
        return view;
    }
}
