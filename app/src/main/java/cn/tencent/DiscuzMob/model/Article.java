package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by AiWei on 2016/4/22.
 */
public class Article implements Serializable {

    private String tid;
    private long readperm;
    private String author;
    private String authorid;
    private String subject;
    private String dateline;
    private String lastpost;
    private String lastposter;
    private String special;
    private String desc;
    private long views;
    private long replies;
    private long digest;
    private long attachment;
    private long recommend_add;
    private String dbdateline;
    private String dblastpost;
    private String avatar;
    private String[] imglist;

    public Article() {
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public long getReadperm() {
        return readperm;
    }

    public void setReadperm(long readperm) {
        this.readperm = readperm;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
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

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getReplies() {
        return replies;
    }

    public void setReplies(long replies) {
        this.replies = replies;
    }

    public long getDigest() {
        return digest;
    }

    public void setDigest(long digest) {
        this.digest = digest;
    }

    public long getAttachment() {
        return attachment;
    }

    public void setAttachment(long attachment) {
        this.attachment = attachment;
    }

    public long getRecommend_add() {
        return recommend_add;
    }

    public void setRecommend_add(long recommend_add) {
        this.recommend_add = recommend_add;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    public String getDblastpost() {
        return dblastpost;
    }

    public void setDblastpost(String dblastpost) {
        this.dblastpost = dblastpost;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String[] getImglist() {
        return imglist;
    }

    public void setImglist(String[] imglist) {
        this.imglist = imglist;
    }

}
