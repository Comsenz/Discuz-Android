package cn.tencent.DiscuzMob.ui.Event;

import java.util.List;

import cn.tencent.DiscuzMob.model.SublistBean;

/**
 * Created by cg on 2017/5/24.
 */

public class RefreshHeadView {
    private List<SublistBean> sublist;

    public RefreshHeadView(List<SublistBean> sublist) {
        this.sublist = sublist;
    }

    public List<SublistBean> getSublist() {
        return sublist;
    }

    public void setSublist(List<SublistBean> sublist) {
        this.sublist = sublist;
    }
}
