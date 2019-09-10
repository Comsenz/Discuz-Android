package cn.tencent.DiscuzMob.ui.fragment.thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import cn.tencent.DiscuzMob.base.Config;
import cn.tencent.DiscuzMob.model.LinkReplyVariables;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.ui.activity.ShowImageActivity;
import cn.tencent.DiscuzMob.ui.dialog.ShareDialog;
import cn.tencent.DiscuzMob.js.RednetJavascriptManager;
import cn.tencent.DiscuzMob.ui.activity.UserDetailActivity;
import cn.tencent.DiscuzMob.utils.RednetUtils;

/**
 * Created by AiWei on 2016/7/1.
 */
public class ThreadDetailsFragment extends SimpleWebFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ShareDialog mShareDialog;

    private LinkReplyVariables mLinkReply;//引用回复
    private String mReplyAnchorId;
    private String mFid, mTid;
    private Bundle mMetaData;

    private RednetWebViewClient mWebClient;
    private int mPage = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFid = getArguments().getString("fid");
        mTid = getArguments().getString("tid");
        mRefreshView.setOnRefreshListener(this);
        RednetJavascriptManager.addJavascriptInterface(mWebView, this);
        mWebView.setWebViewClient(mWebClient = new RednetWebViewClient(this));
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(getActivity())) {
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    void load(boolean append) {
        ApiVersion5.INSTANCE.requestThreadDetails(append ? ++mPage : (mPage = 1), mTid, null,
                new ApiVersion5.Result<Object>(getActivity(), null, false) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (code == 0) mPage = Math.max(1, --mPage);
                            if (value instanceof String) {
                                mMetaData = mPage == 1 ? parse(ThreadDetailsFragment.this, (String) value) : mMetaData;
                                if (mMetaData != null) {
                                    mFid = mMetaData.getString("fid");
                                }
                                loadUrl((String) value);
                            }
                            mRefreshView.setRefreshing(false);
                        }
                    }
                });
    }

    void loadUrl(final String json) {
        if (!TextUtils.isEmpty(mReplyAnchorId)) {
            //mWebClient.add("javascript:setImgLoadable(" + (RedNetPreferences.getImageSetting() != 1) + ");");
            //mWebClient.add("javascript:onDiscussSuccess(" + json + "," + String.valueOf(mReplyAnchorId) + ");");
            mWebView.clearHistory();
            mWebView.loadUrl("file:///android_asset/templates/thread_temp_common.html");
            mReplyAnchorId = null;
        } else if (mPage == 1) {
            //mWebClient.add("javascript:setImgLoadable(" + (RedNetPreferences.getImageSetting() != 1) + ");");
            //mWebClient.add("javascript:onRefresh(" + json + ");");
            mWebView.clearHistory();
            mWebView.loadUrl("file:///android_asset/templates/thread_temp_common.html");
        } else {
            //mWebClient.add("javascript:onLoadReply(" + json + ",true);");
        }
    }

    @Override
    public void onInitData(String json) {
    }

    @Override
    public void onOptionsItemClick(int id) {
    }

    @Override
    public void discussUser(String pid) {
        if (RednetUtils.hasLogin(getActivity())) {
            if (pid instanceof String) {//回复评论用户

            } else {//回复帖子
                mLinkReply = null;
                mReplyAnchorId = null;
            }
        }
    }

    @Override
    public void loadMore() {
        if (RednetUtils.networkIsOk(getActivity())) {
            load(true);
        }
    }

    @Override
    public void threadThumbsClick(String url) {
        startActivity(new Intent(getActivity(), ShowImageActivity.class).putExtra("url", url));//scale
    }

    @Override
    public void userInfo(String authorid) {
        startActivity(new Intent(getActivity(), UserDetailActivity.class).putExtra("userId", authorid));
    }

    @Override
    public void report() {
    }

    @Override
    public void reportComment(String pid) {
    }

    @Override
    public void praise() {
    }

    @Override
    public void share() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(getActivity()) {
                @Override
                public ShareDialog.Builder onShare() {
                    return ShareDialog.Builder.create(getActivity())
                            .setText(mMetaData.getString("subject"), mMetaData.getString("shareText"))
                            .setImageUrl("http://bbs.rednet.cn/" + mMetaData.getString("thumburl"))
                            .setUrl(Config.SHARE_URL + mTid);
                }
            };
        }
        if (mMetaData == null)
            RednetUtils.toast(getActivity(), "分享失败,请刷新重试");
        else
            mShareDialog.show();
    }

}
