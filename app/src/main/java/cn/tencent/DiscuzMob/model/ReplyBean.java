package cn.tencent.DiscuzMob.model;

/**
 * Created by cg on 2017/4/20.
 */

public class ReplyBean {
    private String pid;
    private String fid;
    private String tid;
    private String first;
    private String author;
    private String authorid;
    private String subject;
    private String dateline;
    private String message;
    private String invisible;
    private String anonymous;
    private String usesig;
    private String htmlon;
    private String bbcodeoff;
    private String smileyoff;
    private String parseurloff;
    private String attachment;
    private String rate;
    private String ratetimes;
    private String status;
    private String tags;
    private String comment;
    private String replycredit;
    private String position;
    private String support;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInvisible() {
        return invisible;
    }

    public void setInvisible(String invisible) {
        this.invisible = invisible;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getUsesig() {
        return usesig;
    }

    public void setUsesig(String usesig) {
        this.usesig = usesig;
    }

    public String getHtmlon() {
        return htmlon;
    }

    public void setHtmlon(String htmlon) {
        this.htmlon = htmlon;
    }

    public String getBbcodeoff() {
        return bbcodeoff;
    }

    public void setBbcodeoff(String bbcodeoff) {
        this.bbcodeoff = bbcodeoff;
    }

    public String getSmileyoff() {
        return smileyoff;
    }

    public void setSmileyoff(String smileyoff) {
        this.smileyoff = smileyoff;
    }

    public String getParseurloff() {
        return parseurloff;
    }

    public void setParseurloff(String parseurloff) {
        this.parseurloff = parseurloff;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRatetimes() {
        return ratetimes;
    }

    public void setRatetimes(String ratetimes) {
        this.ratetimes = ratetimes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReplycredit() {
        return replycredit;
    }

    public void setReplycredit(String replycredit) {
        this.replycredit = replycredit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    @Override
    public String toString() {
        return "ReplyBean{" +
                "pid='" + pid + '\'' +
                ", fid='" + fid + '\'' +
                ", tid='" + tid + '\'' +
                ", first='" + first + '\'' +
                ", author='" + author + '\'' +
                ", authorid='" + authorid + '\'' +
                ", subject='" + subject + '\'' +
                ", dateline='" + dateline + '\'' +
                ", message='" + message + '\'' +
                ", invisible='" + invisible + '\'' +
                ", anonymous='" + anonymous + '\'' +
                ", usesig='" + usesig + '\'' +
                ", htmlon='" + htmlon + '\'' +
                ", bbcodeoff='" + bbcodeoff + '\'' +
                ", smileyoff='" + smileyoff + '\'' +
                ", parseurloff='" + parseurloff + '\'' +
                ", attachment='" + attachment + '\'' +
                ", rate='" + rate + '\'' +
                ", ratetimes='" + ratetimes + '\'' +
                ", status='" + status + '\'' +
                ", tags='" + tags + '\'' +
                ", comment='" + comment + '\'' +
                ", replycredit='" + replycredit + '\'' +
                ", position='" + position + '\'' +
                ", support='" + support + '\'' +
                '}';
    }
}
