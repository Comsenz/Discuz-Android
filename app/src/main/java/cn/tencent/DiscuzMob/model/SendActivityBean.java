package cn.tencent.DiscuzMob.model;

/**
 * Created by cg on 2017/4/25.
 */

public class SendActivityBean {


    /**
     * Charset : UTF-8
     * Message : {"messagestr":"非常感谢，您的主题已发布，现在将转入主题页，请稍候\u2026\u2026[ 点击这里转入主题列表 ]","messageval":"post_newthread_succeed"}
     * Variables : {"auth":"af0dK1yzh7UsIGTdslFuFSYEW+1wO6VVQeWNY+03hLNfmt+pV7XwYDm/VUjUQiBU0G9nlb5/lHkj2Aa8N5VZ","cookiepre":"IiUw_2132_","formhash":"00778f33","groupid":"1","ismoderator":"1","member_avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=small","member_uid":"1","member_username":"admin","notice":{"newmypost":"0","newpm":"0","newprompt":"1","newpush":"0"},"pid":"188","readaccess":"200","saltkey":"HrkQ8rr9","tid":"83"}
     * Version : 5
     */

    private String Charset;
    private MessageBean Message;
    private VariablesBean Variables;
    private String Version;

    public String getCharset() {
        return Charset;
    }

    public void setCharset(String Charset) {
        this.Charset = Charset;
    }

    public MessageBean getMessage() {
        return Message;
    }

    public void setMessage(MessageBean Message) {
        this.Message = Message;
    }

    public VariablesBean getVariables() {
        return Variables;
    }

    public void setVariables(VariablesBean Variables) {
        this.Variables = Variables;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public static class MessageBean {
        /**
         * messagestr : 非常感谢，您的主题已发布，现在将转入主题页，请稍候……[ 点击这里转入主题列表 ]
         * messageval : post_newthread_succeed
         */

        private String messagestr;
        private String messageval;

        public String getMessagestr() {
            return messagestr;
        }

        public void setMessagestr(String messagestr) {
            this.messagestr = messagestr;
        }

        public String getMessageval() {
            return messageval;
        }

        public void setMessageval(String messageval) {
            this.messageval = messageval;
        }
    }

    public static class VariablesBean {
        /**
         * auth : af0dK1yzh7UsIGTdslFuFSYEW+1wO6VVQeWNY+03hLNfmt+pV7XwYDm/VUjUQiBU0G9nlb5/lHkj2Aa8N5VZ
         * cookiepre : IiUw_2132_
         * formhash : 00778f33
         * groupid : 1
         * ismoderator : 1
         * member_avatar : http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=small
         * member_uid : 1
         * member_username : admin
         * notice : {"newmypost":"0","newpm":"0","newprompt":"1","newpush":"0"}
         * pid : 188
         * readaccess : 200
         * saltkey : HrkQ8rr9
         * tid : 83
         */

        private String auth;
        private String cookiepre;
        private String formhash;
        private String groupid;
        private String ismoderator;
        private String member_avatar;
        private String member_uid;
        private String member_username;
        private NoticeBean notice;
        private String pid;
        private String readaccess;
        private String saltkey;
        private String tid;

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getCookiepre() {
            return cookiepre;
        }

        public void setCookiepre(String cookiepre) {
            this.cookiepre = cookiepre;
        }

        public String getFormhash() {
            return formhash;
        }

        public void setFormhash(String formhash) {
            this.formhash = formhash;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getIsmoderator() {
            return ismoderator;
        }

        public void setIsmoderator(String ismoderator) {
            this.ismoderator = ismoderator;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
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

        public NoticeBean getNotice() {
            return notice;
        }

        public void setNotice(NoticeBean notice) {
            this.notice = notice;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getReadaccess() {
            return readaccess;
        }

        public void setReadaccess(String readaccess) {
            this.readaccess = readaccess;
        }

        public String getSaltkey() {
            return saltkey;
        }

        public void setSaltkey(String saltkey) {
            this.saltkey = saltkey;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public static class NoticeBean {
            /**
             * newmypost : 0
             * newpm : 0
             * newprompt : 1
             * newpush : 0
             */

            private String newmypost;
            private String newpm;
            private String newprompt;
            private String newpush;

            public String getNewmypost() {
                return newmypost;
            }

            public void setNewmypost(String newmypost) {
                this.newmypost = newmypost;
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

            public String getNewpush() {
                return newpush;
            }

            public void setNewpush(String newpush) {
                this.newpush = newpush;
            }
        }
    }
}
