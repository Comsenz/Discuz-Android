package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/20.
 */

public class ConversationBean {

    /**
     * Version : 4
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","auth":"1019NVFszmQ81m3rYHMiUox/9SbNGyLtAxO+lTi90dthARCKG7aMOEv7NORNHQcJ3m+bbJyoNmj0CDlG5o8x","saltkey":"xaCXbaOd","member_uid":"7","member_username":"陈国","member_avatar":"http://u.rednet.cn/images/noavatar_middle.gif","member_conisbind":"0","member_weixinisbind":"0","member_loginstatus":"0","groupid":"10","formhash":"18a51744","ismoderator":"","readaccess":"10","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"list":[{"plid":"6","subject":"测试小自习","pmid":"49","message":"测试小自习","touid":"7","msgfromid":"10","msgfrom":"陈国1","vdateline":"昨天&nbsp;14:11","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"},{"plid":"6","subject":"测试小自习","pmid":"50","message":"回复测试","touid":"10","msgfromid":"7","msgfrom":"陈国","vdateline":"昨天&nbsp;15:04","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"},{"plid":"6","subject":"测试小自习","pmid":"52","message":"33333333333333333333333333333333333333333","touid":"10","msgfromid":"7","msgfrom":"陈国","vdateline":"昨天&nbsp;17:34","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"},{"plid":"6","subject":"测试小自习","pmid":"58","message":"你要做什么？","touid":"7","msgfromid":"10","msgfrom":"陈国1","vdateline":"14&nbsp;分钟前","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"}],"count":"4","perpage":"5","page":"1","pmid":"49"}
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
         * auth : 1019NVFszmQ81m3rYHMiUox/9SbNGyLtAxO+lTi90dthARCKG7aMOEv7NORNHQcJ3m+bbJyoNmj0CDlG5o8x
         * saltkey : xaCXbaOd
         * member_uid : 7
         * member_username : 陈国
         * member_avatar : http://u.rednet.cn/images/noavatar_middle.gif
         * member_conisbind : 0
         * member_weixinisbind : 0
         * member_loginstatus : 0
         * groupid : 10
         * formhash : 18a51744
         * ismoderator :
         * readaccess : 10
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * list : [{"plid":"6","subject":"测试小自习","pmid":"49","message":"测试小自习","touid":"7","msgfromid":"10","msgfrom":"陈国1","vdateline":"昨天&nbsp;14:11","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"},{"plid":"6","subject":"测试小自习","pmid":"50","message":"回复测试","touid":"10","msgfromid":"7","msgfrom":"陈国","vdateline":"昨天&nbsp;15:04","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"},{"plid":"6","subject":"测试小自习","pmid":"52","message":"33333333333333333333333333333333333333333","touid":"10","msgfromid":"7","msgfrom":"陈国","vdateline":"昨天&nbsp;17:34","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"},{"plid":"6","subject":"测试小自习","pmid":"58","message":"你要做什么？","touid":"7","msgfromid":"10","msgfrom":"陈国1","vdateline":"14&nbsp;分钟前","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"}]
         * count : 4
         * perpage : 5
         * page : 1
         * pmid : 49
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
        private String count;
        private String perpage;
        private String page;
        private String pmid;
        private List<ListBean> list;

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

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPerpage() {
            return perpage;
        }

        public void setPerpage(String perpage) {
            this.perpage = perpage;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPmid() {
            return pmid;
        }

        public void setPmid(String pmid) {
            this.pmid = pmid;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
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

        public static class ListBean {
            /**
             * plid : 6
             * subject : 测试小自习
             * pmid : 49
             * message : 测试小自习
             * touid : 7
             * msgfromid : 10
             * msgfrom : 陈国1
             * vdateline : 昨天&nbsp;14:11
             * fromavatar : http://u.rednet.cn/images/noavatar_middle.gif
             * toavatar : http://u.rednet.cn/images/noavatar_middle.gif
             */

            private String plid;
            private String subject;
            private String pmid;
            private String message;
            private String touid;
            private String msgfromid;
            private String msgfrom;
            private String vdateline;
            private String fromavatar;
            private String toavatar;

            public String getPlid() {
                return plid;
            }

            public void setPlid(String plid) {
                this.plid = plid;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getPmid() {
                return pmid;
            }

            public void setPmid(String pmid) {
                this.pmid = pmid;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTouid() {
                return touid;
            }

            public void setTouid(String touid) {
                this.touid = touid;
            }

            public String getMsgfromid() {
                return msgfromid;
            }

            public void setMsgfromid(String msgfromid) {
                this.msgfromid = msgfromid;
            }

            public String getMsgfrom() {
                return msgfrom;
            }

            public void setMsgfrom(String msgfrom) {
                this.msgfrom = msgfrom;
            }

            public String getVdateline() {
                return vdateline;
            }

            public void setVdateline(String vdateline) {
                this.vdateline = vdateline;
            }

            public String getFromavatar() {
                return fromavatar;
            }

            public void setFromavatar(String fromavatar) {
                this.fromavatar = fromavatar;
            }

            public String getToavatar() {
                return toavatar;
            }

            public void setToavatar(String toavatar) {
                this.toavatar = toavatar;
            }
        }
    }
}
