package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/20.
 */

public class HisRepliesBean {

    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","auth":"f2c2AZaTcV5V9EFubMG1hpmdOYEC2Ui6FALkGoo3zpqT42FxXlPg6/kZn+COelNJXRZU7MABKsQWPFjwC7iW","saltkey":"oW8Bdx81","member_uid":"7","member_username":"陈国","member_avatar":"http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg","member_conisbind":"0","member_weixinisbind":"0","member_loginstatus":"0","groupid":"10","formhash":"bc5d8447","ismoderator":"","readaccess":"10","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"1"},"threadlist":[{"tid":"30","fid":"2","author":"陈国","authorid":"7","subject":"测试回复","dateline":"2017-4-19","lastpost":"昨天&nbsp;17:36","lastposter":"陈国","views":"45","replies":"3","displayorder":"0","highlight":"","digest":"0","special":"0","attachment":"0","closed":"0","forumname":"Discuz!-模板","reply":[{"pid":"67","fid":"2","tid":"30","first":"0","author":"陈国1","authorid":"10","subject":"","dateline":"2017-4-19 16:07","message":"什么情况","invisible":"0","anonymous":"0","usesig":"1","htmlon":"0","bbcodeoff":"-1","smileyoff":"-1","parseurloff":"0","attachment":"0","rate":"0","ratetimes":"0","status":"1024","tags":"0","comment":"0","replycredit":"0","position":"3","support":"0"},{"pid":"65","fid":"2","tid":"30","first":"0","author":"陈国1","authorid":"10","subject":"","dateline":"2017-4-19 15:09","message":"测试回复","invisible":"0","anonymous":"0","usesig":"1","htmlon":"0","bbcodeoff":"-1","smileyoff":"-1","parseurloff":"0","attachment":"0","rate":"0","ratetimes":"0","status":"1024","tags":"0","comment":"0","replycredit":"0","position":"2","support":"0"}]},{"tid":"34","fid":"36","author":"陈国1","authorid":"10","subject":"我的主題","dateline":"2017-4-20","lastpost":"11&nbsp;秒前","lastposter":"陈国1","views":"6","replies":"2","displayorder":"0","highlight":"","digest":"0","special":"0","attachment":"0","closed":"0","forumname":"Discuz!-插件","reply":[{"pid":"96","fid":"36","tid":"34","first":"0","author":"陈国1","authorid":"10","subject":"","dateline":"2017-4-20 15:59","message":"不知道啊","invisible":"0","anonymous":"0","usesig":"1","htmlon":"0","bbcodeoff":"0","smileyoff":"-1","parseurloff":"0","attachment":"0","rate":"0","ratetimes":"0","status":"0","tags":"0","comment":"0","replycredit":"0","position":"3","support":"0"}]}],"tpp":"20","page":"1","listcount":"3"}
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
         * auth : f2c2AZaTcV5V9EFubMG1hpmdOYEC2Ui6FALkGoo3zpqT42FxXlPg6/kZn+COelNJXRZU7MABKsQWPFjwC7iW
         * saltkey : oW8Bdx81
         * member_uid : 7
         * member_username : 陈国
         * member_avatar : http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg
         * member_conisbind : 0
         * member_weixinisbind : 0
         * member_loginstatus : 0
         * groupid : 10
         * formhash : bc5d8447
         * ismoderator :
         * readaccess : 10
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"1"}
         * threadlist : [{"tid":"30","fid":"2","author":"陈国","authorid":"7","subject":"测试回复","dateline":"2017-4-19","lastpost":"昨天&nbsp;17:36","lastposter":"陈国","views":"45","replies":"3","displayorder":"0","highlight":"","digest":"0","special":"0","attachment":"0","closed":"0","forumname":"Discuz!-模板","reply":[{"pid":"67","fid":"2","tid":"30","first":"0","author":"陈国1","authorid":"10","subject":"","dateline":"2017-4-19 16:07","message":"什么情况","invisible":"0","anonymous":"0","usesig":"1","htmlon":"0","bbcodeoff":"-1","smileyoff":"-1","parseurloff":"0","attachment":"0","rate":"0","ratetimes":"0","status":"1024","tags":"0","comment":"0","replycredit":"0","position":"3","support":"0"},{"pid":"65","fid":"2","tid":"30","first":"0","author":"陈国1","authorid":"10","subject":"","dateline":"2017-4-19 15:09","message":"测试回复","invisible":"0","anonymous":"0","usesig":"1","htmlon":"0","bbcodeoff":"-1","smileyoff":"-1","parseurloff":"0","attachment":"0","rate":"0","ratetimes":"0","status":"1024","tags":"0","comment":"0","replycredit":"0","position":"2","support":"0"}]},{"tid":"34","fid":"36","author":"陈国1","authorid":"10","subject":"我的主題","dateline":"2017-4-20","lastpost":"11&nbsp;秒前","lastposter":"陈国1","views":"6","replies":"2","displayorder":"0","highlight":"","digest":"0","special":"0","attachment":"0","closed":"0","forumname":"Discuz!-插件","reply":[{"pid":"96","fid":"36","tid":"34","first":"0","author":"陈国1","authorid":"10","subject":"","dateline":"2017-4-20 15:59","message":"不知道啊","invisible":"0","anonymous":"0","usesig":"1","htmlon":"0","bbcodeoff":"0","smileyoff":"-1","parseurloff":"0","attachment":"0","rate":"0","ratetimes":"0","status":"0","tags":"0","comment":"0","replycredit":"0","position":"3","support":"0"}]}]
         * tpp : 20
         * page : 1
         * listcount : 3
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
             * newmypost : 1
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
    }
}
