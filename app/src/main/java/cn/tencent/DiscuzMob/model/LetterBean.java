package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/19.
 */

public class LetterBean {

    /**
     * Version : 4
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","auth":"9e33wf1+7x2aGAqSQe4Nd0YpLhaZ/aKrVCEB+xsi5X9mATIsy6mpjXxNDUlKRnVGUEszg1kZ/OtT0GZE6v6d","saltkey":"Z8p875OL","member_uid":"7","member_username":"陈国","member_avatar":"http://u.rednet.cn/images/noavatar_middle.gif","member_conisbind":"0","member_weixinisbind":"0","member_loginstatus":"0","groupid":"10","formhash":"dd51264e","ismoderator":"","readaccess":"10","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"list":[{"plid":"6","isnew":"1","subject":"测试小自习","touid":"10","pmid":"6","msgfromid":"10","msgfrom":"陈国1","message":"测试小自习","tousername":"陈国1","vdateline":"20&nbsp;分钟前","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"}],"count":"1","perpage":"15","page":"1"}
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
         * auth : 9e33wf1+7x2aGAqSQe4Nd0YpLhaZ/aKrVCEB+xsi5X9mATIsy6mpjXxNDUlKRnVGUEszg1kZ/OtT0GZE6v6d
         * saltkey : Z8p875OL
         * member_uid : 7
         * member_username : 陈国
         * member_avatar : http://u.rednet.cn/images/noavatar_middle.gif
         * member_conisbind : 0
         * member_weixinisbind : 0
         * member_loginstatus : 0
         * groupid : 10
         * formhash : dd51264e
         * ismoderator :
         * readaccess : 10
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * list : [{"plid":"6","isnew":"1","subject":"测试小自习","touid":"10","pmid":"6","msgfromid":"10","msgfrom":"陈国1","message":"测试小自习","tousername":"陈国1","vdateline":"20&nbsp;分钟前","fromavatar":"http://u.rednet.cn/images/noavatar_middle.gif","toavatar":"http://u.rednet.cn/images/noavatar_middle.gif"}]
         * count : 1
         * perpage : 15
         * page : 1
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
             * isnew : 1
             * subject : 测试小自习
             * touid : 10
             * pmid : 6
             * msgfromid : 10
             * msgfrom : 陈国1
             * message : 测试小自习
             * tousername : 陈国1
             * vdateline : 20&nbsp;分钟前
             * fromavatar : http://u.rednet.cn/images/noavatar_middle.gif
             * toavatar : http://u.rednet.cn/images/noavatar_middle.gif
             */

            private String plid;
            private String isnew;
            private String subject;
            private String touid;
            private String pmid;
            private String msgfromid;
            private String msgfrom;
            private String message;
            private String tousername;
            private String vdateline;
            private String fromavatar;
            private String toavatar;

            public String getPlid() {
                return plid;
            }

            public void setPlid(String plid) {
                this.plid = plid;
            }

            public String getIsnew() {
                return isnew;
            }

            public void setIsnew(String isnew) {
                this.isnew = isnew;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getTouid() {
                return touid;
            }

            public void setTouid(String touid) {
                this.touid = touid;
            }

            public String getPmid() {
                return pmid;
            }

            public void setPmid(String pmid) {
                this.pmid = pmid;
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

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTousername() {
                return tousername;
            }

            public void setTousername(String tousername) {
                this.tousername = tousername;
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
