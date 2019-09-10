package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/20.
 */

public class HisThemeBean {

    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","auth":"77fatoWU1reQSk1P+FbB2m+gbTnfW6RvK9oE/6ZAGuJ9Yuf3cFYO933qCvLH6sAeOF2m+CQutCAHH2KLLQYy","saltkey":"XRN7K27a","member_uid":"7","member_username":"陈国","member_avatar":"http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg","member_conisbind":"0","member_weixinisbind":"0","member_loginstatus":"0","groupid":"10","formhash":"6e48ffb2","ismoderator":"","readaccess":"10","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"threadlist":[{"tid":"34","fid":"36","author":"陈国1","authorid":"10","subject":"我的主題","dateline":"2017-4-20","lastpost":"半小时前","lastposter":"陈国1","views":"3","replies":"0","special":"0","attachment":"0","closed":"0","forumname":"Discuz!-插件"}],"tpp":"20","page":"1","listcount":"1"}
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
         * cookiepre : 68nN_2132_
         * auth : 77fatoWU1reQSk1P+FbB2m+gbTnfW6RvK9oE/6ZAGuJ9Yuf3cFYO933qCvLH6sAeOF2m+CQutCAHH2KLLQYy
         * saltkey : XRN7K27a
         * member_uid : 7
         * member_username : 陈国
         * member_avatar : http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg
         * member_conisbind : 0
         * member_weixinisbind : 0
         * member_loginstatus : 0
         * groupid : 10
         * formhash : 6e48ffb2
         * ismoderator :
         * readaccess : 10
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * threadlist : [{"tid":"34","fid":"36","author":"陈国1","authorid":"10","subject":"我的主題","dateline":"2017-4-20","lastpost":"半小时前","lastposter":"陈国1","views":"3","replies":"0","special":"0","attachment":"0","closed":"0","forumname":"Discuz!-插件"}]
         * tpp : 20
         * page : 1
         * listcount : 1
         */

        private String cookiepre;
        private String auth;
        private String saltkey;
        private String member_uid;
        private String member_username;
        private String member_avatar;
        private String member_conisbind;
        private String member_weixinisbind;
        private String member_loginstatus;
        private String groupid;
        private String formhash;
        private String ismoderator;
        private String readaccess;
        private NoticeBean notice;
        private String tpp;
        private String page;
        private String listcount;
        private List<ThreadlistBean> threadlist;

        public String getCookiepre() {
            return cookiepre;
        }

        public void setCookiepre(String cookiepre) {
            this.cookiepre = cookiepre;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
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

        public String getMember_conisbind() {
            return member_conisbind;
        }

        public void setMember_conisbind(String member_conisbind) {
            this.member_conisbind = member_conisbind;
        }

        public String getMember_weixinisbind() {
            return member_weixinisbind;
        }

        public void setMember_weixinisbind(String member_weixinisbind) {
            this.member_weixinisbind = member_weixinisbind;
        }

        public String getMember_loginstatus() {
            return member_loginstatus;
        }

        public void setMember_loginstatus(String member_loginstatus) {
            this.member_loginstatus = member_loginstatus;
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

        public String getIsmoderator() {
            return ismoderator;
        }

        public void setIsmoderator(String ismoderator) {
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

        public String getTpp() {
            return tpp;
        }

        public void setTpp(String tpp) {
            this.tpp = tpp;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getListcount() {
            return listcount;
        }

        public void setListcount(String listcount) {
            this.listcount = listcount;
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
             * tid : 34
             * fid : 36
             * author : 陈国1
             * authorid : 10
             * subject : 我的主題
             * dateline : 2017-4-20
             * lastpost : 半小时前
             * lastposter : 陈国1
             * views : 3
             * replies : 0
             * special : 0
             * attachment : 0
             * closed : 0
             * forumname : Discuz!-插件
             */

            private String tid;
            private String fid;
            private String author;
            private String authorid;
            private String subject;
            private String dateline;
            private String lastpost;
            private String lastposter;
            private String views;
            private String replies;
            private String special;
            private String attachment;
            private String closed;
            private String forumname;

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

            public String getClosed() {
                return closed;
            }

            public void setClosed(String closed) {
                this.closed = closed;
            }

            public String getForumname() {
                return forumname;
            }

            public void setForumname(String forumname) {
                this.forumname = forumname;
            }
        }
    }
}
