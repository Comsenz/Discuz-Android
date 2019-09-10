package cn.tencent.DiscuzMob.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cg on 2017/5/31.
 */

public class HomePageBean {


    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"IiUw_2132_","auth":null,"saltkey":"yYXrbyxP","member_uid":"0","member_username":"","member_avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=0&size=small","groupid":"7","formhash":"c656d5d0","ismoderator":null,"readaccess":"1","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"recommonthread_list":[{"tid":"288","fid":"2","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"测试00","authorid":"84","subject":"测试语音","dateline":"3&nbsp;天前","lastpost":"1498039312","lastposter":"测试00","views":"40","replies":"2","displayorder":"0","highlight":"0","digest":"0","rate":"0","special":"0","attachment":"2","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"2","status":"1056","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"-1","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"3","bgcolor":"","comments":"0","hidden":"0","avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=84&size=big","forumnames":{"fid":"2","name":"天下实事"},"imagelist":[]},{"tid":"289","fid":"57","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"admin","authorid":"1","subject":"[发布] 关于 X3.3 的补充说明","dateline":"3&nbsp;天前","lastpost":"1498202338","lastposter":"admin","views":"19","replies":"0","displayorder":"0","highlight":"0","digest":"0","rate":"0","special":"0","attachment":"0","moderated":"0","closed":"0","stickreply":"0","recommends":"2","recommend_add":"2","recommend_sub":"0","heats":"2","status":"32","isgroup":"0","favtimes":"3","sharetimes":"0","stamp":"-1","icon":"-1","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=big","forumnames":{"fid":"57","name":"微社区"},"imagelist":[]},{"tid":"300","fid":"56","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"admin","authorid":"1","subject":"金媒人相亲旅游7月23日周日相约从化星溪线深谷竹林溪涧[...","dateline":"3&nbsp;天前","lastpost":"1497933697","lastposter":"admin","views":"103","replies":"0","displayorder":"0","highlight":"0","digest":"0","rate":"0","special":"4","attachment":"2","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"0","status":"32","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"-1","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"1498190717\t","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=big","forumnames":{"fid":"56","name":"微活动"},"imagelist":{"aid":"428","tid":"300","pid":"1089","uid":"1","dateline":"1497933664","filename":"1496653305880085.jpg","filesize":"147114","attachment":"201706/20/124104v6m16x6xkokdmodi.jpg","remote":"0","description":"","readperm":"0","price":"0","isimage":"1","width":"800","thumb":"0","picid":"0","url":"data/attachment/forum/","src":"http://bbs.wsq.comsenz-service.com/newwsq/forum.php?mod=image&aid=428&size=200x170&key=5700eec368bfa447&type=fixnone"}}],"groupiconid":{"84":"2","1":"admin"}}
     */

    private String Version;
    private String Charset;
    private VariablesBean Variables;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getCharset() {
        return Charset;
    }

    public void setCharset(String Charset) {
        this.Charset = Charset;
    }

    public VariablesBean getVariables() {
        return Variables;
    }

    public void setVariables(VariablesBean Variables) {
        this.Variables = Variables;
    }

    public static class VariablesBean {
        /**
         * cookiepre : IiUw_2132_
         * auth : null
         * saltkey : yYXrbyxP
         * member_uid : 0
         * member_username :
         * member_avatar : http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=0&size=small
         * groupid : 7
         * formhash : c656d5d0
         * ismoderator : null
         * readaccess : 1
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * recommonthread_list : [{"tid":"288","fid":"2","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"测试00","authorid":"84","subject":"测试语音","dateline":"3&nbsp;天前","lastpost":"1498039312","lastposter":"测试00","views":"40","replies":"2","displayorder":"0","highlight":"0","digest":"0","rate":"0","special":"0","attachment":"2","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"2","status":"1056","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"-1","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"3","bgcolor":"","comments":"0","hidden":"0","avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=84&size=big","forumnames":{"fid":"2","name":"天下实事"},"imagelist":[]},{"tid":"289","fid":"57","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"admin","authorid":"1","subject":"[发布] 关于 X3.3 的补充说明","dateline":"3&nbsp;天前","lastpost":"1498202338","lastposter":"admin","views":"19","replies":"0","displayorder":"0","highlight":"0","digest":"0","rate":"0","special":"0","attachment":"0","moderated":"0","closed":"0","stickreply":"0","recommends":"2","recommend_add":"2","recommend_sub":"0","heats":"2","status":"32","isgroup":"0","favtimes":"3","sharetimes":"0","stamp":"-1","icon":"-1","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=big","forumnames":{"fid":"57","name":"微社区"},"imagelist":[]},{"tid":"300","fid":"56","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"admin","authorid":"1","subject":"金媒人相亲旅游7月23日周日相约从化星溪线深谷竹林溪涧[...","dateline":"3&nbsp;天前","lastpost":"1497933697","lastposter":"admin","views":"103","replies":"0","displayorder":"0","highlight":"0","digest":"0","rate":"0","special":"4","attachment":"2","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"0","status":"32","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"-1","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"1498190717\t","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=big","forumnames":{"fid":"56","name":"微活动"},"imagelist":{"aid":"428","tid":"300","pid":"1089","uid":"1","dateline":"1497933664","filename":"1496653305880085.jpg","filesize":"147114","attachment":"201706/20/124104v6m16x6xkokdmodi.jpg","remote":"0","description":"","readperm":"0","price":"0","isimage":"1","width":"800","thumb":"0","picid":"0","url":"data/attachment/forum/","src":"http://bbs.wsq.comsenz-service.com/newwsq/forum.php?mod=image&aid=428&size=200x170&key=5700eec368bfa447&type=fixnone"}}]
         * groupiconid : {"84":"2","1":"admin"}
         */

        private String cookiepre;
        private Object auth;
        private String saltkey;
        private String member_uid;
        private String member_username;
        private String member_avatar;
        private String groupid;
        private String formhash;
        private Object ismoderator;
        private String readaccess;
        private NoticeBean notice;
        private GroupiconidBean groupiconid;
        private List<RecommonthreadListBean> recommonthread_list;

        public String getCookiepre() {
            return cookiepre;
        }

        public void setCookiepre(String cookiepre) {
            this.cookiepre = cookiepre;
        }

        public Object getAuth() {
            return auth;
        }

        public void setAuth(Object auth) {
            this.auth = auth;
        }

        public String getSaltkey() {
            return saltkey;
        }

        public void setSaltkey(String saltkey) {
            this.saltkey = saltkey;
        }

        public String getMember_uid() {
            return member_uid;
        }

        public void setMember_uid(String member_uid) {
            this.member_uid = member_uid;
        }

        public String getMember_username() {
            return member_username;
        }

        public void setMember_username(String member_username) {
            this.member_username = member_username;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getFormhash() {
            return formhash;
        }

        public void setFormhash(String formhash) {
            this.formhash = formhash;
        }

        public Object getIsmoderator() {
            return ismoderator;
        }

        public void setIsmoderator(Object ismoderator) {
            this.ismoderator = ismoderator;
        }

        public String getReadaccess() {
            return readaccess;
        }

        public void setReadaccess(String readaccess) {
            this.readaccess = readaccess;
        }

        public NoticeBean getNotice() {
            return notice;
        }

        public void setNotice(NoticeBean notice) {
            this.notice = notice;
        }

        public GroupiconidBean getGroupiconid() {
            return groupiconid;
        }

        public void setGroupiconid(GroupiconidBean groupiconid) {
            this.groupiconid = groupiconid;
        }

        public List<RecommonthreadListBean> getRecommonthread_list() {
            return recommonthread_list;
        }

        public void setRecommonthread_list(List<RecommonthreadListBean> recommonthread_list) {
            this.recommonthread_list = recommonthread_list;
        }

        public static class NoticeBean {
            /**
             * newpush : 0
             * newpm : 0
             * newprompt : 0
             * newmypost : 0
             */

            private String newpush;
            private String newpm;
            private String newprompt;
            private String newmypost;

            public String getNewpush() {
                return newpush;
            }

            public void setNewpush(String newpush) {
                this.newpush = newpush;
            }

            public String getNewpm() {
                return newpm;
            }

            public void setNewpm(String newpm) {
                this.newpm = newpm;
            }

            public String getNewprompt() {
                return newprompt;
            }

            public void setNewprompt(String newprompt) {
                this.newprompt = newprompt;
            }

            public String getNewmypost() {
                return newmypost;
            }

            public void setNewmypost(String newmypost) {
                this.newmypost = newmypost;
            }
        }

        public static class GroupiconidBean {
            /**
             * 84 : 2
             * 1 : admin
             */

            @SerializedName("84")
            private String _$84;
            @SerializedName("1")
            private String _$1;

            public String get_$84() {
                return _$84;
            }

            public void set_$84(String _$84) {
                this._$84 = _$84;
            }

            public String get_$1() {
                return _$1;
            }

            public void set_$1(String _$1) {
                this._$1 = _$1;
            }
        }

        public static class RecommonthreadListBean {
            /**
             * tid : 288
             * fid : 2
             * posttableid : 0
             * typeid : 0
             * sortid : 0
             * readperm : 0
             * price : 0
             * author : 测试00
             * authorid : 84
             * subject : 测试语音
             * dateline : 3&nbsp;天前
             * lastpost : 1498039312
             * lastposter : 测试00
             * views : 40
             * replies : 2
             * displayorder : 0
             * highlight : 0
             * digest : 0
             * rate : 0
             * special : 0
             * attachment : 2
             * moderated : 0
             * closed : 0
             * stickreply : 0
             * recommends : 0
             * recommend_add : 0
             * recommend_sub : 0
             * heats : 2
             * status : 1056
             * isgroup : 0
             * favtimes : 0
             * sharetimes : 0
             * stamp : -1
             * icon : -1
             * pushedaid : 0
             * cover : 0
             * replycredit : 0
             * relatebytag : 0
             * maxposition : 3
             * bgcolor :
             * comments : 0
             * hidden : 0
             * avatar : http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=84&size=big
             * forumnames : {"fid":"2","name":"天下实事"}
             * imagelist : []
             */

            private String tid;
            private String fid;
            private String posttableid;
            private String typeid;
            private String sortid;
            private String readperm;
            private String price;
            private String author;
            private String authorid;
            private String subject;
            private String dateline;
            private String lastpost;
            private String lastposter;
            private String views;
            private String replies;
            private String displayorder;
            private String highlight;
            private String digest;
            private String rate;
            private String special;
            private String attachment;
            private String moderated;
            private String closed;
            private String stickreply;
            private String recommends;
            private String recommend_add;
            private String recommend_sub;
            private String heats;
            private String status;
            private String isgroup;
            private String favtimes;
            private String sharetimes;
            private String stamp;
            private String icon;
            private String pushedaid;
            private String cover;
            private String replycredit;
            private String relatebytag;
            private String maxposition;
            private String bgcolor;
            private String comments;
            private String hidden;
            private String avatar;
            private ForumnamesBean forumnames;
            private List<?> imagelist;

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getPosttableid() {
                return posttableid;
            }

            public void setPosttableid(String posttableid) {
                this.posttableid = posttableid;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public String getSortid() {
                return sortid;
            }

            public void setSortid(String sortid) {
                this.sortid = sortid;
            }

            public String getReadperm() {
                return readperm;
            }

            public void setReadperm(String readperm) {
                this.readperm = readperm;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getViews() {
                return views;
            }

            public void setViews(String views) {
                this.views = views;
            }

            public String getReplies() {
                return replies;
            }

            public void setReplies(String replies) {
                this.replies = replies;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public String getHighlight() {
                return highlight;
            }

            public void setHighlight(String highlight) {
                this.highlight = highlight;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getAttachment() {
                return attachment;
            }

            public void setAttachment(String attachment) {
                this.attachment = attachment;
            }

            public String getModerated() {
                return moderated;
            }

            public void setModerated(String moderated) {
                this.moderated = moderated;
            }

            public String getClosed() {
                return closed;
            }

            public void setClosed(String closed) {
                this.closed = closed;
            }

            public String getStickreply() {
                return stickreply;
            }

            public void setStickreply(String stickreply) {
                this.stickreply = stickreply;
            }

            public String getRecommends() {
                return recommends;
            }

            public void setRecommends(String recommends) {
                this.recommends = recommends;
            }

            public String getRecommend_add() {
                return recommend_add;
            }

            public void setRecommend_add(String recommend_add) {
                this.recommend_add = recommend_add;
            }

            public String getRecommend_sub() {
                return recommend_sub;
            }

            public void setRecommend_sub(String recommend_sub) {
                this.recommend_sub = recommend_sub;
            }

            public String getHeats() {
                return heats;
            }

            public void setHeats(String heats) {
                this.heats = heats;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIsgroup() {
                return isgroup;
            }

            public void setIsgroup(String isgroup) {
                this.isgroup = isgroup;
            }

            public String getFavtimes() {
                return favtimes;
            }

            public void setFavtimes(String favtimes) {
                this.favtimes = favtimes;
            }

            public String getSharetimes() {
                return sharetimes;
            }

            public void setSharetimes(String sharetimes) {
                this.sharetimes = sharetimes;
            }

            public String getStamp() {
                return stamp;
            }

            public void setStamp(String stamp) {
                this.stamp = stamp;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getPushedaid() {
                return pushedaid;
            }

            public void setPushedaid(String pushedaid) {
                this.pushedaid = pushedaid;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getReplycredit() {
                return replycredit;
            }

            public void setReplycredit(String replycredit) {
                this.replycredit = replycredit;
            }

            public String getRelatebytag() {
                return relatebytag;
            }

            public void setRelatebytag(String relatebytag) {
                this.relatebytag = relatebytag;
            }

            public String getMaxposition() {
                return maxposition;
            }

            public void setMaxposition(String maxposition) {
                this.maxposition = maxposition;
            }

            public String getBgcolor() {
                return bgcolor;
            }

            public void setBgcolor(String bgcolor) {
                this.bgcolor = bgcolor;
            }

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public String getHidden() {
                return hidden;
            }

            public void setHidden(String hidden) {
                this.hidden = hidden;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public ForumnamesBean getForumnames() {
                return forumnames;
            }

            public void setForumnames(ForumnamesBean forumnames) {
                this.forumnames = forumnames;
            }

            public List<?> getImagelist() {
                return imagelist;
            }

            public void setImagelist(List<?> imagelist) {
                this.imagelist = imagelist;
            }

            public static class ForumnamesBean {
                /**
                 * fid : 2
                 * name : 天下实事
                 */

                private String fid;
                private String name;

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
            }
        }
    }
}
