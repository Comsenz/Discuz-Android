package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/19.
 */

public class RemindBean {


    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"IiUw_2132_","auth":"da4f4wQCkBl4aVP6ZgTchyc128uYTFAgCGYUZhHchFYNhWJgXAD9rGykCOOQbElbxbncB7AU4gAZxPZCV5eX","saltkey":"Qa3BaJKi","member_uid":"1","member_username":"admin","member_avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=small","groupid":"1","formhash":"d749edc6","ismoderator":null,"readaccess":"200","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"list":[{"id":"1","authorid":"1","author":"admin","dateline":"1495769586","message":"你好的额的的的标题这是 &nbsp; 内容这是按时老骥伏枥谁看见都是大力看估计都是空间圣诞快乐估计是都看过山","numbers":"1","vdateline":"2017-5-26 11:33"}],"count":null,"perpage":"15","page":"1"}
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
         * cookiepre : IiUw_2132_
         * auth : da4f4wQCkBl4aVP6ZgTchyc128uYTFAgCGYUZhHchFYNhWJgXAD9rGykCOOQbElbxbncB7AU4gAZxPZCV5eX
         * saltkey : Qa3BaJKi
         * member_uid : 1
         * member_username : admin
         * member_avatar : http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=1&size=small
         * groupid : 1
         * formhash : d749edc6
         * ismoderator : null
         * readaccess : 200
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * list : [{"id":"1","authorid":"1","author":"admin","dateline":"1495769586","message":"你好的额的的的标题这是 &nbsp; 内容这是按时老骥伏枥谁看见都是大力看估计都是空间圣诞快乐估计是都看过山","numbers":"1","vdateline":"2017-5-26 11:33"}]
         * count : null
         * perpage : 15
         * page : 1
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
        private Object count;
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

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
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
             * id : 1
             * authorid : 1
             * author : admin
             * dateline : 1495769586
             * message : 你好的额的的的标题这是 &nbsp; 内容这是按时老骥伏枥谁看见都是大力看估计都是空间圣诞快乐估计是都看过山
             * numbers : 1
             * vdateline : 2017-5-26 11:33
             */

            private String id;
            private String authorid;
            private String author;
            private String dateline;
            private String message;
            private String numbers;
            private String vdateline;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAuthorid() {
                return authorid;
            }

            public void setAuthorid(String authorid) {
                this.authorid = authorid;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getDateline() {
                return dateline;
            }

            public void setDateline(String dateline) {
                this.dateline = dateline;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getNumbers() {
                return numbers;
            }

            public void setNumbers(String numbers) {
                this.numbers = numbers;
            }

            public String getVdateline() {
                return vdateline;
            }

            public void setVdateline(String vdateline) {
                this.vdateline = vdateline;
            }
        }
    }
}
