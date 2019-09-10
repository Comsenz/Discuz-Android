package cn.tencent.DiscuzMob.ui.Event;

/**
 * Created by cg on 2017/7/7.
 */

public class RefreshPicturePath {
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RefreshPicturePath(String path) {
        this.path = path;
    }
}
