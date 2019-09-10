package cn.tencent.DiscuzMob.widget;

/**
 * Created by AiWei on 2015/11/17.
 */
public interface IFooter {

    int STATE_IDEL = 0, STATE_LOADING = 1, STATE_FINISHED = 2, STATE_ALL = 3, STATE_FAILED = 4;

    void onFaile();//加载失败

    void finish();//结束加载更多

    void finishAll();//已加载全部

}
