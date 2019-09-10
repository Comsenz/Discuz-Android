package cn.tencent.DiscuzMob.js;

import android.content.Context;
import android.webkit.JavascriptInterface;

import cn.tencent.DiscuzMob.base.Config;


/**
 * Created by kurt on 15-7-29.
 */
@Deprecated
public class ZhangShangJavaScriptInterface {

    private Context mContext;
    private OnActionListener onActionFinishListener;

    public interface OnActionListener {
        void onAction(Object object, int type);
    }

    public ZhangShangJavaScriptInterface(Context c, OnActionListener onActionFinishListener) {
        this.mContext = c;
        this.onActionFinishListener = onActionFinishListener;
    }

    @JavascriptInterface
    public void onDiscussUser(String pid) {
        onActionFinishListener.onAction(pid, Config.ACTIONTYPE_DISCUSSUSER);
    }

    @JavascriptInterface
    public void onPraise() {
        onActionFinishListener.onAction("", Config.ACTIONTYPE_PRAISE);
    }

    @JavascriptInterface
    public void onShare() {
        onActionFinishListener.onAction("", Config.ACTIONTYPE_SHARE);
    }

    @JavascriptInterface
    public void onSubmit() {
        onActionFinishListener.onAction("", Config.ACTIONTYPE_JOIN);
    }

    @JavascriptInterface
    public void onSendPoll(String[] value) {
        onActionFinishListener.onAction(value, Config.ACTIONTYPE_SENDPOLL);
    }

    @JavascriptInterface
    public void onVisitVoters() {
        onActionFinishListener.onAction("", Config.ACTIONTYPE_CHECKVOTER);
    }

    @JavascriptInterface
    public void onLoadMore() {
        onActionFinishListener.onAction("", Config.ACTIONTYPE_LOADMORE);
    }

    @JavascriptInterface
    public void onUserInfo(String authorid) {
        onActionFinishListener.onAction(authorid, Config.ACTIONTYPE_USERINFO);
    }

    @JavascriptInterface
    public void onThreadThumbsClicked(String url) {
        onActionFinishListener.onAction(url, Config.ACTIONTYOE_THUMBCLICK);
    }

    @JavascriptInterface
    public void onReportComment(String pid) {
        onActionFinishListener.onAction(pid, Config.ACTIONTYOE_REPORT_COMMENT);
    }

    @JavascriptInterface
    public void onReport() {
        onActionFinishListener.onAction("", Config.ACTIONTYOE_REPORT);
    }

}
