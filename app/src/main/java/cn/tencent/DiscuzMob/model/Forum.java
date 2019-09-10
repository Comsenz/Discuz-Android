package cn.tencent.DiscuzMob.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AiWei on 2015/5/6.
 */
public class Forum implements Serializable {

    private Long id;
    private String fid;
    private String name;
    private long threads;
    private long posts;
    private int todayposts;
    private String lastpost;
    private String lastposter;
    private String lastpost_subject;
    private String lastpost_tid;
    private String description;
    private String rules;
    private String fup;
    private String autoclose;
    private String password;
    private String cat_fid;
    private List<String> allowpostspecial;
    private String allowspecialonly;
    private String allowpostspecialString;

    public Forum() {
    }

    public Forum(Long id, String fid, String name, int todayposts) {
        this.id = id;
        this.fid = fid;
        this.name = name;
        this.todayposts = todayposts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public long getThreads() {
        return threads;
    }

    public void setThreads(long threads) {
        this.threads = threads;
    }

    public long getPosts() {
        return posts;
    }

    public void setPosts(long posts) {
        this.posts = posts;
    }

    public int getTodayposts() {
        return todayposts;
    }

    public void setTodayposts(int todayposts) {
        this.todayposts = todayposts;
    }

    public String getLastpost() {
        return lastpost;
    }

    public void setLastpost(String lastpost) {
        this.lastpost = lastpost;
    }

    public String getLastposter() {
        return lastposter;
    }

    public void setLastposter(String lastposter) {
        this.lastposter = lastposter;
    }

    public String getLastpost_subject() {
        return lastpost_subject;
    }

    public void setLastpost_subject(String lastpost_subject) {
        this.lastpost_subject = lastpost_subject;
    }

    public String getLastpost_tid() {
        return lastpost_tid;
    }

    public void setLastpost_tid(String lastpost_tid) {
        this.lastpost_tid = lastpost_tid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAutoclose() {
        return autoclose;
    }

    public void setAutoclose(String autoclose) {
        this.autoclose = autoclose;
    }

    public String getFup() {
        return fup;
    }

    public void setFup(String fup) {
        this.fup = fup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getCat_fid() {
        return cat_fid;
    }

    public void setCat_fid(String cat_fid) {
        this.cat_fid = cat_fid;
    }

    public List<String> getAllowpostspecial() {
        return allowpostspecial;
    }

    public void setAllowpostspecial(List<String> allowpostspecial) {
        this.allowpostspecial = allowpostspecial;
    }

    public String getAllowspecialonly() {
        return allowspecialonly;
    }

    public void setAllowspecialonly(String allowspecialonly) {
        this.allowspecialonly = allowspecialonly;
    }

    public String getAllowpostspecialString() {
        return allowpostspecialString;
    }

    public void setAllowpostspecialString(String allowpostspecialString) {
        this.allowpostspecialString = allowpostspecialString;
    }

}
