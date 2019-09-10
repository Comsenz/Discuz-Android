package cn.tencent.DiscuzMob.js;

import android.os.Handler;

/**
 * Created by AiWei on 2016/7/12.
 */
class DefaultRednetJavascriptImpl extends AbstractRednetJavascript {

    private Handler mHandler;

    private IRednetJavascript mCallback;

    DefaultRednetJavascriptImpl(Handler handler, IRednetJavascript callback) {
        this.mHandler = handler;
        this.mCallback = callback;
    }

    @Override
    public void discussUser(final String pid) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.discussUser(pid);
            }
        });
    }

    @Override
    public void loadMore() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.loadMore();
            }
        });
    }

    @Override
    public void threadThumbsClick(final String url) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.threadThumbsClick(url);
            }
        });
    }

    @Override
    public void userInfo(final String authorid) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.userInfo(authorid);
            }
        });
    }

    @Override
    public void reportComment(final String pid) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.reportComment(pid);
            }
        });
    }

    @Override
    public void praise() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.praise();
            }
        });
    }

    @Override
    public void share() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.share();
            }
        });
    }

    @Override
    public void report() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.report();
            }
        });
    }

}
