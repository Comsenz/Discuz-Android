package cn.tencent.DiscuzMob.ui.Event;

/**
 * Created by cg on 2017/4/17.
 */

public class RefreshUserInfo {
    private String id;

    public RefreshUserInfo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
