package cn.tencent.DiscuzMob.ui.bean;

public class RegistBean {

    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"a9ja_2132_","auth":null,"saltkey":"gv3V35DP","member_uid":"0","member_username":"","member_avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=0&size=small","groupid":"7","formhash":"f7bca3cd","ismoderator":null,"readaccess":"1","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"reginput":{"username":"mkP314","password":"PYW7SA","password2":"H2ZP92","email":"GVT6p5"}}
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
         * saltkey : gv3V35DP
         * member_uid : 0
         * member_username :
         * member_avatar : https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=0&size=small
         * groupid : 7
         * formhash : f7bca3cd
         * ismoderator : null
         * readaccess : 1
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * reginput : {"username":"mkP314","password":"PYW7SA","password2":"H2ZP92","email":"GVT6p5"}
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
        private ReginputBean reginput;

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

        public ReginputBean getReginput() {
            return reginput;
        }

        public void setReginput(ReginputBean reginput) {
            this.reginput = reginput;
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

        public static class ReginputBean {
            /**
             * username : mkP314
             * password : PYW7SA
             * password2 : H2ZP92
             * email : GVT6p5
             */

            private String username;
            private String password;
            private String password2;
            private String email;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPassword2() {
                return password2;
            }

            public void setPassword2(String password2) {
                this.password2 = password2;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }
    }
}
