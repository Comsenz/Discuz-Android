package cn.tencent.DiscuzMob.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseRecyclerAdapter;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;

public abstract class SimpleRecyclerRefreshFragment extends BaseFragment implements ILoadListener, SwipeRefreshLayout.OnRefreshListener {

    protected ViewAnimator mTip;
    protected NestedSwipeRefreshLayout mRefreshView;
    protected RelativeLayout rlThreads;
    protected RecyclerView mRecyclerView;
    protected ImageView imageViewEmpty;

    private int mCurrentPage = 1;
    private int mTotalCount = 0;
    private int mPreCount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.simple_refresh_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTip = (ViewAnimator) view.findViewById(R.id.tip);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        rlThreads = (RelativeLayout) view.findViewById(R.id.rl_recycler);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        imageViewEmpty = (ImageView) view.findViewById(R.id.iv_nothing);

        mTip.setDisplayedChild(1);
        mRefreshView.setRefreshing(true);
        mRefreshView.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
            }

            @Override
            public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
                // super.measureChildWithMargins(child, widthUsed, heightUsed);
                final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int widthSpec = getChildMeasureSpec(mRecyclerView.getMeasuredWidth(), View.MeasureSpec.EXACTLY,
                        getPaddingLeft() + getPaddingRight() +
                                lp.leftMargin + lp.rightMargin + widthUsed, ViewGroup.LayoutParams.MATCH_PARENT,
                        canScrollHorizontally());
                final int heightSpec = getChildMeasureSpec(getHeight(), getHeightMode(),
                        getPaddingTop() + getPaddingBottom() +
                                lp.topMargin + lp.bottomMargin + heightUsed, lp.height,
                        canScrollVertically());
                child.measure(widthSpec, heightSpec);
            }
        });
        mRecyclerView.setAdapter(initAdapter());
    }

    public void setTotalCount(String count) {
        if (TextUtils.isEmpty(count)) {
            mTotalCount = 0;
        } else {
            mTotalCount = Integer.valueOf(count);
        }
    }

    public void setPreCount(String preCount) {
        if (TextUtils.isEmpty(preCount)) {
            mPreCount = 0;
        } else {
            mPreCount = Integer.valueOf(preCount);
        }
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
    }

    protected boolean hasMore() {
        if (mTotalCount > mPreCount * mCurrentPage) {
            return true;
        } else {
            return false;
        }
    }

    protected abstract BaseRecyclerAdapter initAdapter();

}
