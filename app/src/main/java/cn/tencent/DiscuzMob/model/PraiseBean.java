package cn.tencent.DiscuzMob.model;

/**
 * Created by cg on 2017/4/27.
 */

public class PraiseBean {


    /**
     * Charset : UTF-8
     * Message : {"messagestr":"评价指数 1您已评价过本主题","messageval":"recommend_duplicate"}
     * Variables : {"auth":"c313MAuyJt+5p1sNl146P3mv62A6a1qkeMEkkhm2CuruMJQAaNIqJliwphvks8qwo1lseL6xYQ3ir4mKObKKtw","cookiepre":"IiUw_2132_","formhash":"bff58d96","groupid":"10","ismoderator":"0","member_avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=84&size=small","member_uid":"84","member_username":"测试00","notice":{"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"},"readaccess":"10","saltkey":"kKXH3s3G"}
     * Version : 4
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
         * messagestr : 评价指数 1您已评价过本主题
         * messageval : recommend_duplicate
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
         * auth : c313MAuyJt+5p1sNl146P3mv62A6a1qkeMEkkhm2CuruMJQAaNIqJliwphvks8qwo1lseL6xYQ3ir4mKObKKtw
         * cookiepre : IiUw_2132_
         * formhash : bff58d96
         * groupid : 10
         * ismoderator : 0
         * member_avatar : http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=84&size=small
         * member_uid : 84
         * member_username : 测试00
         * notice : {"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"}
         * readaccess : 10
         * saltkey : kKXH3s3G
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
        private String readaccess;
        private String saltkey;

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

        public static class NoticeBean {
            /**
             * newmypost : 0
             * newpm : 0
             * newprompt : 0
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
