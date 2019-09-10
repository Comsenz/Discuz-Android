package cn.tencent.DiscuzMob.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.squareup.picasso.Picasso;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2015/10/20.
 */
public class FooterForRecycler extends RecyclerView.OnScrollListener implements IFooter, View.OnClickListener {

    private RedNetApp mApplication;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ViewAnimator mViewAnimator;
    private TextView mLabel;
    private View mError;
    private ILoadListener mListener;
    private LoadMode mLoadMode = LoadMode.Auto;
    private int mCurrentState = STATE_IDEL;

    public enum LoadMode {
        Normal {
            @Override
            void init(FooterForRecycler footer) {
                footer.mLabel.setText("加载更多");
                footer.mProgressBar.setVisibility(View.GONE);
                footer.mViewAnimator.setDisplayedChild(0);
                footer.mViewAnimator.setVisibility(View.VISIBLE);
            }
        }, Auto {
            @Override
            void init(FooterForRecycler footer) {
                footer.mProgressBar.setVisibility(View.GONE);
                footer.mViewAnimator.setVisibility(View.GONE);
            }
        };

        abstract void init(FooterForRecycler footer);
    }

    public FooterForRecycler(RecyclerView recyclerView, View root) {
        this(recyclerView, root, LoadMode.Auto);
    }

    public FooterForRecycler(RecyclerView recyclerView, View root, LoadMode mode) {
        if (recyclerView == null) {
            //throw new IllegalArgumentException("RecyclerView can't be null");
        }
        mApplication = RedNetApp.getInstance();
        this.mRecyclerView = recyclerView;
        this.mLoadMode = mode;
        this.mProgressBar = (ProgressBar) root.findViewById(R.id.progress);
        this.mViewAnimator = (ViewAnimator) root.findViewById(R.id.options);
        this.mLabel = (TextView) mViewAnimator.findViewById(R.id.label);
        this.mError = mViewAnimator.findViewById(R.id.error);
        this.mRecyclerView.addOnScrollListener(this);
        this.mLabel.setOnClickListener(this);
        this.mError.setOnClickListener(this);
        this.mLoadMode.init(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mLabel)) {
            if (isReady()) {
                if (!mApplication.networkIsOk()) {
                    RednetUtils.toast(v.getContext(), "网络不可用");
                } else {
                    reset();
                    mProgressBar.setVisibility(View.VISIBLE);
                    mCurrentState = STATE_LOADING;
                    mLabel.setText("正在加载...");
                    if (mListener != null) {
                        mListener.onLoad();
                    }
                }
            }
        } else {
            mLabel.performClick();
        }
    }

    public void setOnLoadListener(ILoadListener listener) {
        this.mListener = listener;
    }

    public boolean isReady() {
        return mCurrentState != STATE_LOADING && mCurrentState != STATE_ALL;
    }

    public void reset() {
        if (mCurrentState != STATE_LOADING) {
            mCurrentState = STATE_IDEL;
            mLoadMode.init(this);
        }
    }

    @Override
    public void onFaile() {
        if (mCurrentState != STATE_ALL) {
            mCurrentState = STATE_FAILED;
            mProgressBar.setVisibility(View.GONE);
            mViewAnimator.setDisplayedChild(1);
            mViewAnimator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finish() {
        if (mCurrentState == STATE_LOADING) {
            mCurrentState = STATE_FINISHED;
            mLoadMode.init(this);
        }
    }

    @Override
    public void finishAll() {
        if (mCurrentState != STATE_ALL) {
            mCurrentState = STATE_ALL;
            mProgressBar.setVisibility(View.GONE);
            mLabel.setText("没有更多了");
            mViewAnimator.setDisplayedChild(0);
            mViewAnimator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        final Picasso picasso = Picasso.with(recyclerView.getContext());
        if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            picasso.resumeTag(RedNetApp.TAG_PICASSO);
        } else {
            picasso.pauseTag(RedNetApp.TAG_PICASSO);
        }
        loadable(newState);
    }

    void loadable(int state) {
        if (state == RecyclerView.SCROLL_STATE_IDLE
                && mListener != null
                && mLoadMode == LoadMode.Auto
                && mCurrentState != STATE_FAILED
                && isReady()) {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount - 1 == lastVisible) {
                    if (mApplication.networkIsOk()) {
                        mCurrentState = STATE_LOADING;
                        mViewAnimator.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        mListener.onLoad();
                    } else {
                        onFaile();
                    }
                }
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

}
