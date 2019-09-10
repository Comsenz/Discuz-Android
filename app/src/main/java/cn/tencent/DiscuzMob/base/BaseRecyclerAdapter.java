package cn.tencent.DiscuzMob.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.ui.viewholder.LoadMoreHolder;
import cn.tencent.DiscuzMob.widget.ILoadListener;

/**
 * Created by Feng on 2017/12/27.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {
    protected final List<Mode> mDataMode = new ArrayList<>();//数据
    protected LoadMoreHolder mLoadMoreHolder;//加载更多UI控制
    private boolean mHasMore; //更多数据
    protected ILoadListener mLoadListener;//获取更多数据
    private final Activity mActivity;

    public BaseRecyclerAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * @return 显示空数据状态
     */
    public abstract boolean isShowEmpty();

    /**
     * 更新数据
     *
     * @param datas
     * @param isRefresh
     * @param hasMore
     */
    public abstract void setData(List<T> datas, boolean isRefresh, boolean hasMore);

    /**
     * @return 是否有更多数据
     */
    public boolean hasMore() {
        return mHasMore;
    }

    /**
     * @param mHasMore 设置更多数据状态
     */
    public void setHasMore(boolean mHasMore) {
        this.mHasMore = mHasMore;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setLoadListener(ILoadListener mListener) {
        this.mLoadListener = mListener;
    }

    /**
     * 更多数据UI刷新，动作响应
     */
    protected void bindLoadMore() {
        if (mLoadMoreHolder != null) {
            if (hasMore()) {
                mLoadMoreHolder.reset();
                mLoadMoreHolder.onLoad();
            } else {
                mLoadMoreHolder.finishAll();
            }
        }
    }

    /**
     * 更多数据获取失败
     */
    public void onFaile() {
        if (mLoadMoreHolder != null)
            mLoadMoreHolder.onFaile();
    }

    /**
     * 更多数据获取结束
     */
    public void finish() {
        if (mLoadMoreHolder != null)
            mLoadMoreHolder.finish();
    }

    /**
     * item个数
     */
    @Override
    public int getItemCount() {
        return mDataMode.size();
    }

    /**
     * item类型
     */
    @Override
    public int getItemViewType(int position) {
        return getItem(position).viewType;
    }

    /**
     * item数据
     */
    public Mode getItem(int position) {
        return mDataMode.get(position);
    }


    public static class Mode {
        public final int viewType;
        public final Object data;
        public boolean hideDivider;
        public int dividerHeight;

        public Mode(int viewType, Object data) {
            this.viewType = viewType;
            this.data = data;
        }

        public Mode setHideDivider(boolean showDivider) {
            this.hideDivider = showDivider;
            return this;
        }

        public Mode setDividerHeight(int dividerHeight) {
            this.dividerHeight = dividerHeight;
            return this;
        }
    }

    /**
     * itemUI模板
     */
    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        private final Activity mActivity;

        public BaseViewHolder(Activity mActivity, int resId, ViewGroup parent) {
            super(LayoutInflater.from(mActivity.getApplicationContext()).inflate(resId, parent, false));
            this.mActivity = mActivity;
        }

        public BaseViewHolder(Activity mActivity, int resId) {
            this(mActivity, resId, null);
        }

        public Activity getActivity() {
            return mActivity;
        }
    }


}
