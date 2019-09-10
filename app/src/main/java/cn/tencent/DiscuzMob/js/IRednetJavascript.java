package cn.tencent.DiscuzMob.js;

/**
 * Created by AiWei on 2016/8/1.
 */
public interface IRednetJavascript {

    void userInfo(String authorid);

    void report();

    void reportComment(String pid);

    void praise();

    void discussUser(String pid);

    void share();

    void threadThumbsClick(String url);

    void loadMore();

}
