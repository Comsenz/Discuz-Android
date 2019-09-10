package cn.tencent.DiscuzMob.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cg on 2017/4/19.
 */

public class MessageRepliesBean {

    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","auth":"992fi93VI7+oa4zEIYdqWD7tVY9MJU63hwAtv+VR6JFYbtZztRzjlWVKTghWHwol4AKmF+y7Uhv4i6UcdDVO","saltkey":"czG6FoYg","member_uid":"7","member_username":"陈国","member_avatar":"http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg","member_conisbind":"0","member_weixinisbind":"0","member_loginstatus":"0","groupid":"10","formhash":"26f3cbb0","ismoderator":"","readaccess":"10","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"list":[{"id":"30","uid":"7","type":"post","new":"0","authorid":"10","author":"陈国1","note":"陈国1 回复了您的帖子 测试回复 &nbsp; 查看","dateline":"1492589242","from_id":"30","from_idtype":"post","from_num":"0","category":"1","special":"0"},{"id":"36","uid":"7","type":"post","new":"0","authorid":"10","author":"陈国1","note":"陈国1 回复了您的帖子 我的主題 &nbsp; 查看","dateline":"1492675193","from_id":"96","from_idtype":"quote","from_num":"0","category":"1","special":null},{"id":"40","uid":"7","type":"post","new":"0","authorid":"10","author":"陈国1","note":"陈国1 回复了您的帖子 有啥的的少时诵诗书所 &nbsp; 查看","dateline":"1492680005","from_id":"103","from_idtype":"quote","from_num":"0","category":"1","special":null},{"id":"43","uid":"7","type":"post","new":"0","authorid":"2","author":"大皮特","note":"大皮特 回复了您的帖子 主题测试 &nbsp; 查看","dateline":"1493001818","from_id":"32","from_idtype":"post","from_num":"0","category":"1","special":"0"}],"count":"1"}
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
         * auth : 992fi93VI7+oa4zEIYdqWD7tVY9MJU63hwAtv+VR6JFYbtZztRzjlWVKTghWHwol4AKmF+y7Uhv4i6UcdDVO
         * saltkey : czG6FoYg
         * member_uid : 7
         * member_username : 陈国
         * member_avatar : http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg
         * member_conisbind : 0
         * member_weixinisbind : 0
         * member_loginstatus : 0
         * groupid : 10
         * formhash : 26f3cbb0
         * ismoderator :
         * readaccess : 10
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * list : [{"id":"30","uid":"7","type":"post","new":"0","authorid":"10","author":"陈国1","note":"陈国1 回复了您的帖子 测试回复 &nbsp; 查看","dateline":"1492589242","from_id":"30","from_idtype":"post","from_num":"0","category":"1","special":"0"},{"id":"36","uid":"7","type":"post","new":"0","authorid":"10","author":"陈国1","note":"陈国1 回复了您的帖子 我的主題 &nbsp; 查看","dateline":"1492675193","from_id":"96","from_idtype":"quote","from_num":"0","category":"1","special":null},{"id":"40","uid":"7","type":"post","new":"0","authorid":"10","author":"陈国1","note":"陈国1 回复了您的帖子 有啥的的少时诵诗书所 &nbsp; 查看","dateline":"1492680005","from_id":"103","from_idtype":"quote","from_num":"0","category":"1","special":null},{"id":"43","uid":"7","type":"post","new":"0","authorid":"2","author":"大皮特","note":"大皮特 回复了您的帖子 主题测试 &nbsp; 查看","dateline":"1493001818","from_id":"32","from_idtype":"post","from_num":"0","category":"1","special":"0"}]
         * count : 1
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
             * id : 30
             * uid : 7
             * type : post
             * new : 0
             * authorid : 10
             * author : 陈国1
             * note : 陈国1 回复了您的帖子 测试回复 &nbsp; 查看
             * dateline : 1492589242
             * from_id : 30
             * from_idtype : post
             * from_num : 0
             * category : 1
             * special : 0
             */

            private String id;
            private String uid;
            private String type;
            @SerializedName("new")
            private String newX;
            private String authorid;
            private String author;
            private String note;
            private String dateline;
            private String from_id;
            private String from_idtype;
            private String from_num;
            private String category;
            private String special;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNewX() {
                return newX;
            }

            public void setNewX(String newX) {
                this.newX = newX;
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

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getDateline() {
                return dateline;
            }

            public void setDateline(String dateline) {
                this.dateline = dateline;
            }

            public String getFrom_id() {
                return from_id;
            }

            public void setFrom_id(String from_id) {
                this.from_id = from_id;
            }

            public String getFrom_idtype() {
                return from_idtype;
            }

            public void setFrom_idtype(String from_idtype) {
                this.from_idtype = from_idtype;
            }

            public String getFrom_num() {
                return from_num;
            }

            public void setFrom_num(String from_num) {
                this.from_num = from_num;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }
        }
    }
}
