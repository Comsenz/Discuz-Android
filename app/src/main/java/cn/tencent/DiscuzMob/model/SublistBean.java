package cn.tencent.DiscuzMob.model;

/**
 * Created by cg on 2017/5/24.
 */

public class SublistBean {
    /**
     * fid : 43
     * icon :
     * name : 插件子版块
     * posts : 1
     * threads : 1
     * todayposts : 0
     */

    private String fid;
    private String icon;
    private String name;
    private String posts;
    private String threads;
    private String todayposts;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getTodayposts() {
        return todayposts;
    }

    public void setTodayposts(String todayposts) {
        this.todayposts = todayposts;
    }
}
