package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by AiWei on 2016/5/4.
 */
public class ArticleGuide implements Serializable {

    private String tid;
    private String fid;
    private String posttableid;
    private String typeid;
    private String sortid;
    private String readperm;
    //private String price;
    private String author;
    private String authorid;
    private String subject;
    private String desc;
    private String dateline;
    private String lastpost;
    private String lastposter;
    private long views;
    private long replies;
    //private String displayorder;
    //private String highlight;
    private String digest;
    private String rate;
    private String special;
    private int attachment;
    // private String moderated;
    // private String closed;
    // private String stickreply;
    private long recommends;
    private int recommend_add;
    private int recommend_sub;
    private long heats;
    private int status;
    private int isgroup;
    //private String favtimes;
    //private String sharetimes;
    //private String stamp;
    //private String icon;
    //private String pushedaid;
    //private String cover;
    //private String replycredit;
    //private String relatebytag;
    //private String maxposition;
    //private String bgcolor;
    private long comments;
    private int hidden;
    private String lastposterenc;
    // private String multipage;
    //private String recommendicon;
    // private String new;
    //private String heatlevel;
    //private String moved;
    //private String icontid;
    //private String folder;
    //private String weeknew;
    //private int istoday;
    //private String dbdateline;
    // private String dblastpost;
    private String id;
    //private String rushreply;
    private String avatar;
    private String attachment_url;

    public ArticleGuide() {
    }

    public String getAttachment_url() {
        return attachment_url;
    }

    public void setAttachment_url(String attachment_url) {
        this.attachment_url = attachment_url;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public long getHeats() {
        return heats;
    }

    public void setHeats(long heats) {
        this.heats = heats;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsgroup() {
        return isgroup;
    }

    public void setIsgroup(int isgroup) {
        this.isgroup = isgroup;
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

    public String getLastposterenc() {
        return lastposterenc;
    }

    public void setLastposterenc(String lastposterenc) {
        this.lastposterenc = lastposterenc;
    }

    public String getPosttableid() {
        return posttableid;
    }

    public void setPosttableid(String posttableid) {
        this.posttableid = posttableid;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReadperm() {
        return readperm;
    }

    public void setReadperm(String readperm) {
        this.readperm = readperm;
    }

    public int getRecommend_add() {
        return recommend_add;
    }

    public void setRecommend_add(int recommend_add) {
        this.recommend_add = recommend_add;
    }

    public int getRecommend_sub() {
        return recommend_sub;
    }

    public void setRecommend_sub(int recommend_sub) {
        this.recommend_sub = recommend_sub;
    }

    public long getRecommends() {
        return recommends;
    }

    public void setRecommends(long recommends) {
        this.recommends = recommends;
    }

    public long getReplies() {
        return replies;
    }

    public void setReplies(long replies) {
        this.replies = replies;
    }

    public String getSortid() {
        return sortid;
    }

    public void setSortid(String sortid) {
        this.sortid = sortid;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public int getAttachment() {
        return attachment;
    }

    public void setAttachment(int attachment) {
        this.attachment = attachment;
    }

}
