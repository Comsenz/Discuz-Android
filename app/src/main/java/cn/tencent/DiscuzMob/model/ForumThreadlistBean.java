package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/5/22.
 */

public class ForumThreadlistBean {
    /**
     * attachment : 2
     * author : admin
     * authorid : 1
     * avatar : http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=small
     * cover : []
     * dateline : 2017-5-26
     * dbdateline : 1495781858
     * dblastpost : 1496908753
     * digest : 0
     * displayorder : 3
     * imglist : []
     * lastpost : 4&nbsp;天前
     * lastposter : admin
     * price : 0
     * readperm : 0
     * recommend : 0
     * recommend_add : 2
     * replies : 4
     * reply : [{"author":"admin","authorid":"1","message":"水电费水电费水电费水电费","pid":"56"},{"author":"大皮特","authorid":"75","message":"好的哈","pid":"169"},{"author":"admin","authorid":"1","message":"回复帖子","pid":"197"}]
     * replycredit : 0
     * rushreply : 0
     * special : 1
     * subject : 按时发顺丰递四方速递
     * tid : 20
     * typeid : 0
     * views : 75
     */

    private String attachment;
    private String author;
    private String authorid;
    private String avatar;
    private String dateline;
    private String dbdateline;
    private String dblastpost;
    private String digest;
    private String displayorder;
    private String lastpost;
    private String lastposter;
    private String price;
    private String readperm;
    private String recommend;
    private String recommend_add;
    private String replies;
    private String replycredit;
    private String rushreply;
    private String special;
    private String subject;
    private String tid;
    private String typeid;
    private String views;
    private List<?> cover;
    private List<EssenceBean.VariablesBean.DataBean.AttachlistBean> imglist;
    private String level;
//    private List<ReplyBean> reply;
    private ForumnamesBean forumnames;
    private String message;
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
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

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
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

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReadperm() {
        return readperm;
    }

    public void setReadperm(String readperm) {
        this.readperm = readperm;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommend_add() {
        return recommend_add;
    }

    public void setRecommend_add(String recommend_add) {
        this.recommend_add = recommend_add;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getReplycredit() {
        return replycredit;
    }

    public void setReplycredit(String replycredit) {
        this.replycredit = replycredit;
    }

    public String getRushreply() {
        return rushreply;
    }

    public void setRushreply(String rushreply) {
        this.rushreply = rushreply;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public List<?> getCover() {
        return cover;
    }

    public void setCover(List<?> cover) {
        this.cover = cover;
    }

    public List<EssenceBean.VariablesBean.DataBean.AttachlistBean> getImglist() {
        return imglist;
    }

    public void setImglist(List<EssenceBean.VariablesBean.DataBean.AttachlistBean> imglist) {
        this.imglist = imglist;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    public ForumnamesBean getForumnames() {
        return forumnames;
    }

    public void setForumnames(ForumnamesBean forumnames) {
        this.forumnames = forumnames;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ForumnamesBean {
        String fid;
        String name;

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

        @Override
        public String toString() {
            return "ForumnamesBean{" +
                    "fid='" + fid + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ForumThreadlistBean{" +
                "attachment='" + attachment + '\'' +
                ", author='" + author + '\'' +
                ", authorid='" + authorid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", dateline='" + dateline + '\'' +
                ", dbdateline='" + dbdateline + '\'' +
                ", dblastpost='" + dblastpost + '\'' +
                ", digest='" + digest + '\'' +
                ", displayorder='" + displayorder + '\'' +
                ", lastpost='" + lastpost + '\'' +
                ", lastposter='" + lastposter + '\'' +
                ", price='" + price + '\'' +
                ", readperm='" + readperm + '\'' +
                ", recommend='" + recommend + '\'' +
                ", recommend_add='" + recommend_add + '\'' +
                ", replies='" + replies + '\'' +
                ", replycredit='" + replycredit + '\'' +
                ", rushreply='" + rushreply + '\'' +
                ", special='" + special + '\'' +
                ", subject='" + subject + '\'' +
                ", tid='" + tid + '\'' +
                ", typeid='" + typeid + '\'' +
                ", views='" + views + '\'' +
                ", cover=" + cover +
                ", imglist=" + imglist +
                ", level='" + level + '\'' +
                ", forumnames=" + forumnames +
                ", message='" + message + '\'' +
                '}';
    }
}
