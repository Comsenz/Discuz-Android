package cn.tencent.DiscuzMob.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kurt on 2015/6/8.
 */
public class HotTThread implements Serializable {

    private String tid;
    private String fid;
    private String author;
    private String authorid;
    private String subject;
    private String dateline;
    private String lastpost;
    private String lastposter;
    private long views;
    private long replies;
    private int attachment;
    private int closed;
    private String status;
    private String comments;
    private String avatar;
    private String recommend_add;
    private String displayorder;
    private String forumName;
    private int special;
    private boolean isMine;
    private boolean isHot;
    private List<Reply> reply;
    private List<String> thumburl;
    private String replyString;
    private String thumburlString;
    private long datelineLong;
    private long lastpostLong;


    public HotTThread() {
    }

    public String optThumburl(int position) {
        return thumburl != null && position < thumburl.size() ? thumburl.get(position) : null;
    }

    public int getAttachment() {
        return attachment;
    }

    public void setAttachment(int attachment) {
        this.attachment = attachment;
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

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public long getDatelineLong() {
        return datelineLong;
    }

    public void setDatelineLong(long datelineLong) {
        this.datelineLong = datelineLong;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
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

    public long getLastpostLong() {
        return lastpostLong;
    }

    public void setLastpostLong(long lastpostLong) {
        this.lastpostLong = lastpostLong;
    }

    public String getRecommend_add() {
        return recommend_add;
    }

    public void setRecommend_add(String recommend_add) {
        this.recommend_add = recommend_add;
    }

    public long getReplies() {
        return replies;
    }

    public void setReplies(long replies) {
        this.replies = replies;
    }

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    public String getReplyString() {
        return replyString;
    }

    public void setReplyString(String replyString) {
        this.replyString = replyString;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getThumburl() {
        return thumburl;
    }

    public void setThumburl(List<String> thumburl) {
        this.thumburl = thumburl;
    }

    public String getThumburlString() {
        return thumburlString;
    }

    public void setThumburlString(String thumburlString) {
        this.thumburlString = thumburlString;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
