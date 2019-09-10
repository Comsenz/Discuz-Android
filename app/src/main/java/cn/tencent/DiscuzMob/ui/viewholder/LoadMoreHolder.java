package cn.tencent.DiscuzMob.ui.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseRecyclerAdapter;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.ToastUtils;
import cn.tencent.DiscuzMob.widget.IFooter;
import cn.tencent.DiscuzMob.widget.ILoadListener;

/**
 * Created by Feng on 2017/12/27.
 */

public class LoadMoreHolder extends BaseRecyclerAdapter.BaseViewHolder implements IFooter, View.OnClickListener {
    private View mFooterView;
    private ViewAnimator mViewAnimator;
    private ProgressBar mProgressBar;
    private TextView mLabel;
    private View mError;

    private ILoadListener mListener;

    private LoadMode mLoadMode = LoadMode.Auto;
    private int mCurrentState = STATE_IDEL;


    private String mFinishAllStr = "没有更多了";

    public boolean isLoading() {
        return mCurrentState == STATE_LOADING;
    }

    public enum LoadMode {
        Normal {
            @Override
            void init(LoadMoreHolder footer) {
                footer.mLabel.setText("加载更多");
                footer.mProgressBar.setVisibility(View.GONE);
                footer.mViewAnimator.setDisplayedChild(0);
                footer.mViewAnimator.setVisibility(View.VISIBLE);
            }
        }, Auto {
            @Override
            void init(LoadMoreHolder footer) {
                footer.mProgressBar.setVisibility(View.GONE);
                footer.mViewAnimator.setVisibility(View.GONE);
            }
        };

        abstract void init(LoadMoreHolder footer);
    }

    public LoadMoreHolder(Activity activity) {
        super(activity, R.layout.z_footer_view);
        mFooterView = itemView;
        mViewAnimator = (ViewAnimator) mFooterView.findViewById(R.id.options);
        mProgressBar = (ProgressBar) mFooterView.findViewById(R.id.progress);
        mError = mViewAnimator.findViewById(R.id.error);
        mLabel = (TextView) mViewAnimator.findViewById(R.id.label);
        mLoadMode.init(this);
        mLabel.setOnClickListener(this);
        mError.setOnClickListener(this);
    }

    public void onLoad() {
        mLabel.performClick();
    }

    public LoadMoreHolder setListener(ILoadListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public boolean isReady() {
        return mCurrentState != STATE_LOADING && mCurrentState != STATE_ALL;
    }

    public void reset() {
        if (mCurrentState != STATE_IDEL) {
            mCurrentState = STATE_IDEL;
            mLoadMode.init(this);
        }
    }

    public void setFinishAllStr(String str) {
        this.mFinishAllStr = str;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mLabel)) {
            if (isReady()) {
                if (!RedNetApp.getInstance().networkIsOk()) {
                    ToastUtils.showToast("网络不可用");
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
            mLabel.setText(mFinishAllStr);
            mViewAnimator.setDisplayedChild(0);
            mViewAnimator.setVisibility(View.VISIBLE);
        }
    }
}