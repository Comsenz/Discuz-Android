package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/18.
 */

public class MyFavThreadBean {

    /**
     * Version : 1
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","auth":"fe8aq96UbhHOUBdlzHbKBsOh2VHhBm9z6iaYN6IghdQHwUE7tzbiSc9Ovlw79UriJEAum57DMDDJKzUIPBBv","saltkey":"CDD8r6eh","member_uid":"7","member_username":"陈国","member_avatar":"http://10.0.6.58/uc_server/avatar.php?uid=7&size=small","groupid":"10","formhash":"77433183","ismoderator":null,"readaccess":"10","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"list":[{"favid":"29","uid":"7","id":"22","idtype":"tid","spaceuid":"0","title":"如何如何","description":"","dateline":"1492500832","icon":"<img src=\"static/image/feed/thread.gif\" alt=\"thread\" class=\"t\" /> ","url":"forum.php?mod=viewthread&tid=22","replies":"1","author":"大皮特"}],"perpage":"20","count":"1"}
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
         * auth : fe8aq96UbhHOUBdlzHbKBsOh2VHhBm9z6iaYN6IghdQHwUE7tzbiSc9Ovlw79UriJEAum57DMDDJKzUIPBBv
         * saltkey : CDD8r6eh
         * member_uid : 7
         * member_username : 陈国
         * member_avatar : http://10.0.6.58/uc_server/avatar.php?uid=7&size=small
         * groupid : 10
         * formhash : 77433183
         * ismoderator : null
         * readaccess : 10
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * list : [{"favid":"29","uid":"7","id":"22","idtype":"tid","spaceuid":"0","title":"如何如何","description":"","dateline":"1492500832","icon":"<img src=\"static/image/feed/thread.gif\" alt=\"thread\" class=\"t\" /> ","url":"forum.php?mod=viewthread&tid=22","replies":"1","author":"大皮特"}]
         * perpage : 20
         * count : 1
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
        private String perpage;
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

        public String getPerpage() {
            return perpage;
        }

        public void setPerpage(String perpage) {
            this.perpage = perpage;
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
             * favid : 29
             * uid : 7
             * id : 22
             * idtype : tid
             * spaceuid : 0
             * title : 如何如何
             * description :
             * dateline : 1492500832
             * icon : <img src="static/image/feed/thread.gif" alt="thread" class="t" />
             * url : forum.php?mod=viewthread&tid=22
             * replies : 1
             * author : 大皮特
             */

            private String favid;
            private String uid;
            private String id;
            private String idtype;
            private String spaceuid;
            private String title;
            private String description;
            private String dateline;
            private String icon;
            private String url;
            private String replies;
            private String author;
            private String special;
            public String getFavid() {
                return favid;
            }

            public void setFavid(String favid) {
                this.favid = favid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIdtype() {
                return idtype;
            }

            public void setIdtype(String idtype) {
                this.idtype = idtype;
            }

            public String getSpaceuid() {
                return spaceuid;
            }

            public void setSpaceuid(String spaceuid) {
                this.spaceuid = spaceuid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDateline() {
                return dateline;
            }

            public void setDateline(String dateline) {
                this.dateline = dateline;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getReplies() {
                return replies;
            }

            public void setReplies(String replies) {
                this.replies = replies;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
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
