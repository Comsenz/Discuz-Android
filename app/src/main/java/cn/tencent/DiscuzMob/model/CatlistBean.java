package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/14.
 */

public class CatlistBean {
    private String fid;
    private String name;
    private List<String> forums;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getForums() {
        return forums;
    }

    public void setForums(List<String> forums) {
        this.forums = forums;
    }
}
