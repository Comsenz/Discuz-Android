package cn.tencent.DiscuzMob.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.squareup.picasso.Picasso;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2015/6/15.
 */
public class FooterForList implements IFooter, View.OnClickListener, AbsListView.OnScrollListener {

    private RedNetApp mApplication;
    private ListView mListView;
    private View mFooterView;
    private ViewAnimator mViewAnimator;
    private ProgressBar mProgressBar;
    private TextView mLabel;
    private View mError;
    private ILoadListener mListener;
    private LoadMode mLoadMode = LoadMode.Auto;
    private int mCurrentState = STATE_IDEL;
    private int mVisibleItemCount, mTotalItemCount;

    private String mFinishAllStr = "没有更多了";

    public enum LoadMode {
        Normal {
            @Override
            void init(FooterForList footer) {
                footer.mLabel.setText("加载更多");
                footer.mProgressBar.setVisibility(View.GONE);
                footer.mViewAnimator.setDisplayedChild(0);
                footer.mViewAnimator.setVisibility(View.VISIBLE);
            }
        }, Auto {
            @Override
            void init(FooterForList footer) {
                footer.mProgressBar.setVisibility(View.GONE);
                footer.mViewAnimator.setVisibility(View.GONE);
            }
        };

        abstract void init(FooterForList footer);
    }

    public FooterForList(ListView listView) {
        this.mListView = listView;
        mApplication = RedNetApp.getInstance();
        mFooterView = LayoutInflater.from(mListView.getContext()).inflate(R.layout.z_footer_view, mListView, false);
        mViewAnimator = (ViewAnimator) mFooterView.findViewById(R.id.options);
        mProgressBar = (ProgressBar) mFooterView.findViewById(R.id.progress);
        mError = mViewAnimator.findViewById(R.id.error);
        mLabel = (TextView) mViewAnimator.findViewById(R.id.label);
        mLoadMode.init(this);
        mListView.addFooterView(mFooterView);
        mListView.setOnScrollListener(this);
        mLabel.setOnClickListener(this);
        mError.setOnClickListener(this);
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

    public boolean isLoading() {
        return mCurrentState == STATE_LOADING;
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final Picasso picasso = Picasso.with(mListView.getContext());
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            picasso.resumeTag(RedNetApp.TAG_PICASSO);
        } else {
            picasso.pauseTag(RedNetApp.TAG_PICASSO);
        }
        loadable(scrollState);
    }

    void loadable(int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE
                && mListener != null
                && mLoadMode == LoadMode.Auto
                && mCurrentState != STATE_FAILED
                && isReady()
                //&& (mVisibleItemCount != mTotalItemCount)
                && (mListView.getFirstVisiblePosition() + mVisibleItemCount == mTotalItemCount)) {
            mVisibleItemCount = 0;
            mTotalItemCount = 0;
            if (mApplication.networkIsOk()) {
                mViewAnimator.setVisibility(View.GONE);
                mCurrentState = STATE_LOADING;
                mProgressBar.setVisibility(View.VISIBLE);
                mListener.onLoad();
            } else {
                onFaile();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mVisibleItemCount = Integer.valueOf(visibleItemCount);
        mTotalItemCount = Integer.valueOf(totalItemCount);
    }

}
