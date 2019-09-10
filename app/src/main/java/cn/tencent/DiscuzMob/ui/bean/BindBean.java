package cn.tencent.DiscuzMob.ui.bean;

import java.util.List;

public class BindBean {

    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"a9ja_2132_","auth":"55673BT+44CsFR2DCJsMbC8QsIFORrb3HNGR7zRMp+2Sgbdc8NKr/8KgfzCyVNlBHim24EdIhm891kuRyenoyQ","saltkey":"Zp1ZAUDP","member_uid":"49","member_username":"a943456249","member_avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=49&size=small","groupid":"10","formhash":"1e1cc71d","ismoderator":null,"readaccess":"15","notice":{"newpush":"0","newpm":"0","newprompt":"1","newmypost":"0"},"users":[{"uid":"49","openid":"2702BB6AACDF0C4024C854C9E0961FB7","status":"1","session_key":"","type":"qq"},{"uid":"49","openid":"oav0n0bOUXuUlusDQOrBDdhekK4w","status":"1","session_key":"","type":"weixin"},{"uid":"49","openid":"","status":"0","session_key":"","type":"minapp"}]}
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
         * auth : 55673BT+44CsFR2DCJsMbC8QsIFORrb3HNGR7zRMp+2Sgbdc8NKr/8KgfzCyVNlBHim24EdIhm891kuRyenoyQ
         * saltkey : Zp1ZAUDP
         * member_uid : 49
         * member_username : a943456249
         * member_avatar : https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=49&size=small
         * groupid : 10
         * formhash : 1e1cc71d
         * ismoderator : null
         * readaccess : 15
         * notice : {"newpush":"0","newpm":"0","newprompt":"1","newmypost":"0"}
         * users : [{"uid":"49","openid":"2702BB6AACDF0C4024C854C9E0961FB7","status":"1","session_key":"","type":"qq"},{"uid":"49","openid":"oav0n0bOUXuUlusDQOrBDdhekK4w","status":"1","session_key":"","type":"weixin"},{"uid":"49","openid":"","status":"0","session_key":"","type":"minapp"}]
         */

        private String cookiepre;
        private String auth;
        private String saltkey;
        private String member_uid;
        private String member_username;
        private String member_avatar;
        private String groupid;
        private String formhash;
        private Object ismoderator;
        private String readaccess;
        private NoticeBean notice;
        private List<UsersBean> users;

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

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class NoticeBean {
            /**
             * newpush : 0
             * newpm : 0
             * newprompt : 1
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

        public static class UsersBean {
            /**
             * uid : 49
             * openid : 2702BB6AACDF0C4024C854C9E0961FB7
             * status : 1
             * session_key :
             * type : qq
             */

            private String uid;
            private String openid;
            private String status;
            private String session_key;
            private String type;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSession_key() {
                return session_key;
            }

            public void setSession_key(String session_key) {
                this.session_key = session_key;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
