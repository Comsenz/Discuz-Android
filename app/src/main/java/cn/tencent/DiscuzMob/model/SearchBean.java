package cn.tencent.DiscuzMob.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cg on 2017/4/25.
 */

public class SearchBean {
    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"a9ja_2132_","auth":null,"saltkey":"siQiNivb","member_uid":"0","member_username":"","member_avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=0&size=small","groupid":"7","formhash":"f1a1e933","ismoderator":null,"readaccess":"1","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"threadlist":[{"tid":"228","fid":"37","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"admin","authorid":"1","subject":"avi视频测试","dateline":"2019-7-11 16:53","lastpost":"4&nbsp;天前","lastposter":"admin","views":"9","replies":"0","displayorder":"0","highlight":"","digest":"0","rate":"0","special":"0","attachment":"1","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"0","status":"32","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","realtid":"228","folder":"common","new":"0","id":"017480","forumname":"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ","lastposterenc":"admin","multipage":"","message":"上创视频上的"},{"tid":"39","fid":"37","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"刘代","authorid":"9","subject":"Mozilla Firefox，火狐浏览器，由 Mozilla 全球基金会开发、","dateline":"2019-6-18 09:26","lastpost":"2019-6-18 09:26","lastposter":"刘代","views":"24","replies":"0","displayorder":"0","highlight":"","digest":"0","rate":"0","special":"0","attachment":"0","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"1","status":"1024","isgroup":"0","favtimes":"1","sharetimes":"0","stamp":"-1","icon":"","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","realtid":"39","folder":"common","new":"0","id":"226875","forumname":"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ","lastposterenc":"%E5%88%98%E4%BB%A3","multipage":"","message":"Mozilla Firefox，火狐浏览器，由 Mozilla 全球基金会开发、管理和运营，旨在为全世界互联网用户带来更开放、自由的互联网浏览体验。\n\n中国版火狐浏览器由北京谋智火狐信息技术有限公司运营，自公司成立以来，谋智火 ..."},{"tid":"30","fid":"37","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"刘代","authorid":"9","subject":"wwssddsa","dateline":"2019-6-17 15:26","lastpost":"2019-6-17 15:37","lastposter":"刘代","views":"25","replies":"1","displayorder":"0","highlight":"","digest":"0","rate":"0","special":"0","attachment":"2","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"1","status":"1024","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"2","bgcolor":"","comments":"1","hidden":"0","realtid":"30","folder":"common","new":"0","id":"558542","forumname":"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ","lastposterenc":"%E5%88%98%E4%BB%A3","multipage":"","message":"wwssddsa"}],"total":"3","tpp":"10"}
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
         * cookiepre : a9ja_2132_
         * auth : null
         * saltkey : siQiNivb
         * member_uid : 0
         * member_username :
         * member_avatar : https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=0&size=small
         * groupid : 7
         * formhash : f1a1e933
         * ismoderator : null
         * readaccess : 1
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * threadlist : [{"tid":"228","fid":"37","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"admin","authorid":"1","subject":"avi视频测试","dateline":"2019-7-11 16:53","lastpost":"4&nbsp;天前","lastposter":"admin","views":"9","replies":"0","displayorder":"0","highlight":"","digest":"0","rate":"0","special":"0","attachment":"1","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"0","status":"32","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","realtid":"228","folder":"common","new":"0","id":"017480","forumname":"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ","lastposterenc":"admin","multipage":"","message":"上创视频上的"},{"tid":"39","fid":"37","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"刘代","authorid":"9","subject":"Mozilla Firefox，火狐浏览器，由 Mozilla 全球基金会开发、","dateline":"2019-6-18 09:26","lastpost":"2019-6-18 09:26","lastposter":"刘代","views":"24","replies":"0","displayorder":"0","highlight":"","digest":"0","rate":"0","special":"0","attachment":"0","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"1","status":"1024","isgroup":"0","favtimes":"1","sharetimes":"0","stamp":"-1","icon":"","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","realtid":"39","folder":"common","new":"0","id":"226875","forumname":"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ","lastposterenc":"%E5%88%98%E4%BB%A3","multipage":"","message":"Mozilla Firefox，火狐浏览器，由 Mozilla 全球基金会开发、管理和运营，旨在为全世界互联网用户带来更开放、自由的互联网浏览体验。\n\n中国版火狐浏览器由北京谋智火狐信息技术有限公司运营，自公司成立以来，谋智火 ..."},{"tid":"30","fid":"37","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"刘代","authorid":"9","subject":"wwssddsa","dateline":"2019-6-17 15:26","lastpost":"2019-6-17 15:37","lastposter":"刘代","views":"25","replies":"1","displayorder":"0","highlight":"","digest":"0","rate":"0","special":"0","attachment":"2","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"1","status":"1024","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"2","bgcolor":"","comments":"1","hidden":"0","realtid":"30","folder":"common","new":"0","id":"558542","forumname":"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ","lastposterenc":"%E5%88%98%E4%BB%A3","multipage":"","message":"wwssddsa"}]
         * total : 3
         * tpp : 10
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
        private String total;
        private String tpp;
        private List<ThreadlistBean> threadlist;

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

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getTpp() {
            return tpp;
        }

        public void setTpp(String tpp) {
            this.tpp = tpp;
        }

        public List<ThreadlistBean> getThreadlist() {
            return threadlist;
        }

        public void setThreadlist(List<ThreadlistBean> threadlist) {
            this.threadlist = threadlist;
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

        public static class ThreadlistBean {
            /**
             * tid : 228
             * fid : 37
             * posttableid : 0
             * typeid : 0
             * sortid : 0
             * readperm : 0
             * price : 0
             * author : admin
             * authorid : 1
             * subject : avi视频测试
             * dateline : 2019-7-11 16:53
             * lastpost : 4&nbsp;天前
             * lastposter : admin
             * views : 9
             * replies : 0
             * displayorder : 0
             * highlight :
             * digest : 0
             * rate : 0
             * special : 0
             * attachment : 1
             * moderated : 0
             * closed : 0
             * stickreply : 0
             * recommends : 0
             * recommend_add : 0
             * recommend_sub : 0
             * heats : 0
             * status : 32
             * isgroup : 0
             * favtimes : 0
             * sharetimes : 0
             * stamp : -1
             * icon :
             * pushedaid : 0
             * cover : 0
             * replycredit : 0
             * relatebytag : 0
             * maxposition : 1
             * bgcolor :
             * comments : 0
             * hidden : 0
             * realtid : 228
             * folder : common
             * new : 0
             * id : 017480
             * forumname : JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ
             * lastposterenc : admin
             * multipage :
             * message : 上创视频上的
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
            private String realtid;
            private String folder;
            @SerializedName("new")
            private String newX;
            private String id;
            private String forumname;
            private String lastposterenc;
            private String multipage;
            private String message;

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

            public String getRealtid() {
                return realtid;
            }

            public void setRealtid(String realtid) {
                this.realtid = realtid;
            }

            public String getFolder() {
                return folder;
            }

            public void setFolder(String folder) {
                this.folder = folder;
            }

            public String getNewX() {
                return newX;
            }

            public void setNewX(String newX) {
                this.newX = newX;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getForumname() {
                return forumname;
            }

            public void setForumname(String forumname) {
                this.forumname = forumname;
            }

            public String getLastposterenc() {
                return lastposterenc;
            }

            public void setLastposterenc(String lastposterenc) {
                this.lastposterenc = lastposterenc;
            }

            public String getMultipage() {
                return multipage;
            }

            public void setMultipage(String multipage) {
                this.multipage = multipage;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }

//    /**
//     * Version : 5
//     * Charset : UTF-8
//     * Variables : {"cookiepre":"68nN_2132_","auth":"3bf4zv9G6ZpQntDdZyonSTXpmFdWiKBxdOiRx60KGOXG365vBBFnTPCIk3M4pUKCz1RveqIu9Jk3LocAadKk","saltkey":"fmiQpqEA","member_uid":"7","member_username":"陈国","member_avatar":"http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg","member_conisbind":"0","member_weixinisbind":"0","member_loginstatus":"0","groupid":"10","formhash":"f1b3b3cd","ismoderator":"","readaccess":"10","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"thread":[{"dateline":"2017-04-19 15:09","tid":"30","fid":"2","authorid":"7","subject":"测试回复","author":"陈国","replies":"3","views":"152","recommend_add":"1","special":"0","heats":"4"}]}
//     */
//
//    private String Version;
//    private String Charset;
//    private VariablesBean Variables;
//
//    public String getVersion() {
//        return Version;
//    }
//
//    public void setVersion(String Version) {
//        this.Version = Version;
//    }
//
//    public String getCharset() {
//        return Charset;
//    }
//
//    public void setCharset(String Charset) {
//        this.Charset = Charset;
//    }
//
//    public VariablesBean getVariables() {
//        return Variables;
//    }
//
//    public void setVariables(VariablesBean Variables) {
//        this.Variables = Variables;
//    }
//
//    public static class VariablesBean {
//        /**
//         * cookiepre : 68nN_2132_
//         * auth : 3bf4zv9G6ZpQntDdZyonSTXpmFdWiKBxdOiRx60KGOXG365vBBFnTPCIk3M4pUKCz1RveqIu9Jk3LocAadKk
//         * saltkey : fmiQpqEA
//         * member_uid : 7
//         * member_username : 陈国
//         * member_avatar : http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg
//         * member_conisbind : 0
//         * member_weixinisbind : 0
//         * member_loginstatus : 0
//         * groupid : 10
//         * formhash : f1b3b3cd
//         * ismoderator :
//         * readaccess : 10
//         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
//         * thread : [{"dateline":"2017-04-19 15:09","tid":"30","fid":"2","authorid":"7","subject":"测试回复","author":"陈国","replies":"3","views":"152","recommend_add":"1","special":"0","heats":"4"}]
//         */
//
//        private String cookiepre;
//        private String auth;
//        private String saltkey;
//        private String member_uid;
//        private String member_username;
//        private String member_avatar;
//        private String member_conisbind;
//        private String member_weixinisbind;
//        private String member_loginstatus;
//        private String groupid;
//        private String formhash;
//        private String ismoderator;
//        private String readaccess;
//        private NoticeBean notice;
//        private List<ThreadBean> thread;
//
//        public String getCookiepre() {
//            return cookiepre;
//        }
//
//        public void setCookiepre(String cookiepre) {
//            this.cookiepre = cookiepre;
//        }
//
//        public String getAuth() {
//            return auth;
//        }
//
//        public void setAuth(String auth) {
//            this.auth = auth;
//        }
//
//        public String getSaltkey() {
//            return saltkey;
//        }
//
//        public void setSaltkey(String saltkey) {
//            this.saltkey = saltkey;
//        }
//
//        public String getMember_uid() {
//            return member_uid;
//        }
//
//        public void setMember_uid(String member_uid) {
//            this.member_uid = member_uid;
//        }
//
//        public String getMember_username() {
//            return member_username;
//        }
//
//        public void setMember_username(String member_username) {
//            this.member_username = member_username;
//        }
//
//        public String getMember_avatar() {
//            return member_avatar;
//        }
//
//        public void setMember_avatar(String member_avatar) {
//            this.member_avatar = member_avatar;
//        }
//
//        public String getMember_conisbind() {
//            return member_conisbind;
//        }
//
//        public void setMember_conisbind(String member_conisbind) {
//            this.member_conisbind = member_conisbind;
//        }
//
//        public String getMember_weixinisbind() {
//            return member_weixinisbind;
//        }
//
//        public void setMember_weixinisbind(String member_weixinisbind) {
//            this.member_weixinisbind = member_weixinisbind;
//        }
//
//        public String getMember_loginstatus() {
//            return member_loginstatus;
//        }
//
//        public void setMember_loginstatus(String member_loginstatus) {
//            this.member_loginstatus = member_loginstatus;
//        }
//
//        public String getGroupid() {
//            return groupid;
//        }
//
//        public void setGroupid(String groupid) {
//            this.groupid = groupid;
//        }
//
//        public String getFormhash() {
//            return formhash;
//        }
//
//        public void setFormhash(String formhash) {
//            this.formhash = formhash;
//        }
//
//        public String getIsmoderator() {
//            return ismoderator;
//        }
//
//        public void setIsmoderator(String ismoderator) {
//            this.ismoderator = ismoderator;
//        }
//
//        public String getReadaccess() {
//            return readaccess;
//        }
//
//        public void setReadaccess(String readaccess) {
//            this.readaccess = readaccess;
//        }
//
//        public NoticeBean getNotice() {
//            return notice;
//        }
//
//        public void setNotice(NoticeBean notice) {
//            this.notice = notice;
//        }
//
//        public List<ThreadBean> getThread() {
//            return thread;
//        }
//
//        public void setThread(List<ThreadBean> thread) {
//            this.thread = thread;
//        }
//
//        public static class NoticeBean {
//            /**
//             * newpush : 0
//             * newpm : 0
//             * newprompt : 0
//             * newmypost : 0
//             */
//
//            private String newpush;
//            private String newpm;
//            private String newprompt;
//            private String newmypost;
//
//            public String getNewpush() {
//                return newpush;
//            }
//
//            public void setNewpush(String newpush) {
//                this.newpush = newpush;
//            }
//
//            public String getNewpm() {
//                return newpm;
//            }
//
//            public void setNewpm(String newpm) {
//                this.newpm = newpm;
//            }
//
//            public String getNewprompt() {
//                return newprompt;
//            }
//
//            public void setNewprompt(String newprompt) {
//                this.newprompt = newprompt;
//            }
//
//            public String getNewmypost() {
//                return newmypost;
//            }
//
//            public void setNewmypost(String newmypost) {
//                this.newmypost = newmypost;
//            }
//        }
//
//        public static class ThreadBean {
//            /**
//             * dateline : 2017-04-19 15:09
//             * tid : 30
//             * fid : 2
//             * authorid : 7
//             * subject : 测试回复
//             * author : 陈国
//             * replies : 3
//             * views : 152
//             * recommend_add : 1
//             * special : 0
//             * heats : 4
//             */
//
//            private String dateline;
//            private String tid;
//            private String fid;
//            private String authorid;
//            private String subject;
//            private String author;
//            private String replies;
//            private String views;
//            private String recommend_add;
//            private String special;
//            private String heats;
//
//            public String getDateline() {
//                return dateline;
//            }
//
//            public void setDateline(String dateline) {
//                this.dateline = dateline;
//            }
//
//            public String getTid() {
//                return tid;
//            }
//
//            public void setTid(String tid) {
//                this.tid = tid;
//            }
//
//            public String getFid() {
//                return fid;
//            }
//
//            public void setFid(String fid) {
//                this.fid = fid;
//            }
//
//            public String getAuthorid() {
//                return authorid;
//            }
//
//            public void setAuthorid(String authorid) {
//                this.authorid = authorid;
//            }
//
//            public String getSubject() {
//                return subject;
//            }
//
//            public void setSubject(String subject) {
//                this.subject = subject;
//            }
//
//            public String getAuthor() {
//                return author;
//            }
//
//            public void setAuthor(String author) {
//                this.author = author;
//            }
//
//            public String getReplies() {
//                return replies;
//            }
//
//            public void setReplies(String replies) {
//                this.replies = replies;
//            }
//
//            public String getViews() {
//                return views;
//            }
//
//            public void setViews(String views) {
//                this.views = views;
//            }
//
//            public String getRecommend_add() {
//                return recommend_add;
//            }
//
//            public void setRecommend_add(String recommend_add) {
//                this.recommend_add = recommend_add;
//            }
//
//            public String getSpecial() {
//                return special;
//            }
//
//            public void setSpecial(String special) {
//                this.special = special;
//            }
//
//            public String getHeats() {
//                return heats;
//            }
//
//            public void setHeats(String heats) {
//                this.heats = heats;
//            }
//        }
//    }
}
