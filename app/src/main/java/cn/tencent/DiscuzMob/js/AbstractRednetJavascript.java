package cn.tencent.DiscuzMob.js;

import android.webkit.JavascriptInterface;

/**
 * Created by AiWei on 2016/7/12.
 */
abstract class AbstractRednetJavascript {

    @JavascriptInterface
    abstract void userInfo(String authorid);

    @JavascriptInterface
    abstract void report();

    @JavascriptInterface
    abstract void reportComment(String pid);

    @JavascriptInterface
    abstract void praise();

    @JavascriptInterface
    abstract void discussUser(String pid);

    @JavascriptInterface
    abstract void share();

    @JavascriptInterface
    abstract void threadThumbsClick(String url);

    @JavascriptInterface
    abstract void loadMore();

}
