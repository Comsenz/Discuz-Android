package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/18.
 */

public class MyFriendBean {

    /**
     * Charset : UTF-8
     * Variables : {"auth":"970cJ9VJRDP0logcDvPoRSLvjk5mjs6kJkHFfTogAr8uapWW2fVzbO0XjCfa9mLgEbt5RxQDD5VNvQvDoWD8","cookiepre":"68nN_2132_","count":"1","formhash":"b04e6aa8","groupid":"10","list":[{"uid":"10","username":"陈国1"}],"member_avatar":"http://10.0.6.58/uc_server/avatar.php?uid=7&size=small","member_uid":"7","member_username":"陈国","notice":{"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"},"readaccess":"10","saltkey":"TES5nbAF"}
     * Version : 1
     */

    private String Charset;
    private VariablesBean Variables;
    private String Version;

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

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public static class VariablesBean {
        /**
         * auth : 970cJ9VJRDP0logcDvPoRSLvjk5mjs6kJkHFfTogAr8uapWW2fVzbO0XjCfa9mLgEbt5RxQDD5VNvQvDoWD8
         * cookiepre : 68nN_2132_
         * count : 1
         * formhash : b04e6aa8
         * groupid : 10
         * list : [{"uid":"10","username":"陈国1"}]
         * member_avatar : http://10.0.6.58/uc_server/avatar.php?uid=7&size=small
         * member_uid : 7
         * member_username : 陈国
         * notice : {"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"}
         * readaccess : 10
         * saltkey : TES5nbAF
         */

        private String auth;
        private String cookiepre;
        private String count;
        private String formhash;
        private String groupid;
        private String member_avatar;
        private String member_uid;
        private String member_username;
        private NoticeBean notice;
        private String readaccess;
        private String saltkey;
        private List<Star> list;

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

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
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

        public List<Star> getList() {
            return list;
        }

        public void setList(List<Star> list) {
            this.list = list;
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

        public static class ListBean {
            /**
             * uid : 10
             * username : 陈国1
             */

            private String uid;
            private String username;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
