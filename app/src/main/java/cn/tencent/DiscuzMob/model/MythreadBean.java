package cn.tencent.DiscuzMob.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cg on 2017/7/17.
 */

public class MythreadBean {

    /**
     * Version : 3
     * Charset : UTF-8
     * Variables : {"cookiepre":"IiUw_2132_","auth":"eb97F8bIlgEwsVhRbPrblGT6pY4R3qCVw0i7auYchROvIRGiSqpsmC7DFF1Ss1sCzrlp7c1NMKq6Slc3mXf5Ng","saltkey":"FHSR6VQH","member_uid":"84","member_username":"测试00","member_avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=84&size=small","groupid":"12","formhash":"4ae107a2","ismoderator":null,"readaccess":"30","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"list":[{"id":"188","uid":"84","type":"post","new":"0","authorid":"82","author":"mac11","note":"<a href=\"home.php?mod=space&uid=82\">mac11<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=593&pid=1700\" target=\"_blank\">哇咔咔是谁<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1700&ptid=593\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1500003322","from_id":"593","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"593","pid":"1700","subject":"哇咔咔是谁","actoruid":"82","actorusername":"mac11"},"vdateline":"3&nbsp;天前"},{"id":"83","uid":"84","type":"post","new":"0","authorid":"197","author":"chenpeiping","note":"<a href=\"home.php?mod=space&uid=197\">chenpeiping<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=288&pid=1545\" target=\"_blank\">测试语音<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1545&ptid=288\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1499683553","from_id":"288","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"288","pid":"1545","subject":"测试语音","actoruid":"197","actorusername":"chenpeiping"},"vdateline":"7&nbsp;天前"},{"id":"104","uid":"84","type":"post","new":"0","authorid":"80","author":"敏敏","note":"<a href=\"home.php?mod=space&uid=80\">敏敏<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=313&pid=1261\" target=\"_blank\">测试多条语音贴<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1261&ptid=313\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1498641300","from_id":"313","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"313","pid":"1261","subject":"测试多条语音贴","actoruid":"80","actorusername":"敏敏"},"vdateline":"2017-6-28 17:15"},{"id":"103","uid":"84","type":"post","new":"0","authorid":"1","author":"admin","note":"<a href=\"home.php?mod=space&uid=1\">admin<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=265&pid=1253\" target=\"_blank\">测试传图<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1253&ptid=265\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1498621213","from_id":"265","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"265","pid":"1253","subject":"测试传图","actoruid":"1","actorusername":"admin"},"vdateline":"2017-6-28 11:40"},{"id":"98","uid":"84","type":"post","new":"0","authorid":"123","author":"国文_ZLUnJ","note":"<a href=\"home.php?mod=space&uid=123\">国文_ZLUnJ<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=315&pid=1182\" target=\"_blank\">语音长度<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1182&ptid=315\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1498470271","from_id":"315","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"315","pid":"1182","subject":"语音长度","actoruid":"123","actorusername":"国文_ZLUnJ"},"vdateline":"2017-6-26 17:44"}],"count":"5","perpage":"30","page":"1"}
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
         * auth : eb97F8bIlgEwsVhRbPrblGT6pY4R3qCVw0i7auYchROvIRGiSqpsmC7DFF1Ss1sCzrlp7c1NMKq6Slc3mXf5Ng
         * saltkey : FHSR6VQH
         * member_uid : 84
         * member_username : 测试00
         * member_avatar : http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=84&size=small
         * groupid : 12
         * formhash : 4ae107a2
         * ismoderator : null
         * readaccess : 30
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * list : [{"id":"188","uid":"84","type":"post","new":"0","authorid":"82","author":"mac11","note":"<a href=\"home.php?mod=space&uid=82\">mac11<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=593&pid=1700\" target=\"_blank\">哇咔咔是谁<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1700&ptid=593\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1500003322","from_id":"593","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"593","pid":"1700","subject":"哇咔咔是谁","actoruid":"82","actorusername":"mac11"},"vdateline":"3&nbsp;天前"},{"id":"83","uid":"84","type":"post","new":"0","authorid":"197","author":"chenpeiping","note":"<a href=\"home.php?mod=space&uid=197\">chenpeiping<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=288&pid=1545\" target=\"_blank\">测试语音<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1545&ptid=288\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1499683553","from_id":"288","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"288","pid":"1545","subject":"测试语音","actoruid":"197","actorusername":"chenpeiping"},"vdateline":"7&nbsp;天前"},{"id":"104","uid":"84","type":"post","new":"0","authorid":"80","author":"敏敏","note":"<a href=\"home.php?mod=space&uid=80\">敏敏<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=313&pid=1261\" target=\"_blank\">测试多条语音贴<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1261&ptid=313\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1498641300","from_id":"313","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"313","pid":"1261","subject":"测试多条语音贴","actoruid":"80","actorusername":"敏敏"},"vdateline":"2017-6-28 17:15"},{"id":"103","uid":"84","type":"post","new":"0","authorid":"1","author":"admin","note":"<a href=\"home.php?mod=space&uid=1\">admin<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=265&pid=1253\" target=\"_blank\">测试传图<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1253&ptid=265\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1498621213","from_id":"265","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"265","pid":"1253","subject":"测试传图","actoruid":"1","actorusername":"admin"},"vdateline":"2017-6-28 11:40"},{"id":"98","uid":"84","type":"post","new":"0","authorid":"123","author":"国文_ZLUnJ","note":"<a href=\"home.php?mod=space&uid=123\">国文_ZLUnJ<\/a> 回复了您的帖子 <a href=\"forum.php?mod=redirect&goto=findpost&ptid=315&pid=1182\" target=\"_blank\">语音长度<\/a> &nbsp; <a href=\"forum.php?mod=redirect&goto=findpost&pid=1182&ptid=315\" target=\"_blank\" class=\"lit\">查看<\/a>","dateline":"1498470271","from_id":"315","from_idtype":"post","from_num":"0","style":"","rowid":"","notevar":{"tid":"315","pid":"1182","subject":"语音长度","actoruid":"123","actorusername":"国文_ZLUnJ"},"vdateline":"2017-6-26 17:44"}]
         * count : 5
         * perpage : 30
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
             * id : 188
             * uid : 84
             * type : post
             * new : 0
             * authorid : 82
             * author : mac11
             * note : <a href="home.php?mod=space&uid=82">mac11</a> 回复了您的帖子 <a href="forum.php?mod=redirect&goto=findpost&ptid=593&pid=1700" target="_blank">哇咔咔是谁</a> &nbsp; <a href="forum.php?mod=redirect&goto=findpost&pid=1700&ptid=593" target="_blank" class="lit">查看</a>
             * dateline : 1500003322
             * from_id : 593
             * from_idtype : post
             * from_num : 0
             * style :
             * rowid :
             * notevar : {"tid":"593","pid":"1700","subject":"哇咔咔是谁","actoruid":"82","actorusername":"mac11"}
             * vdateline : 3&nbsp;天前
             */

            private String id;
            private String uid;
            private String type;
            @SerializedName("new")
            private String newX;
            private String authorid;
            private String author;
            private String note;
            private long dateline;
            private String from_id;
            private String from_idtype;
            private String from_num;
            private String style;
            private String rowid;
            private NotevarBean notevar;
            private String vdateline;

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

            public long getDateline() {
                return dateline;
            }

            public void setDateline(long dateline) {
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

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getRowid() {
                return rowid;
            }

            public void setRowid(String rowid) {
                this.rowid = rowid;
            }

            public NotevarBean getNotevar() {
                return notevar;
            }

            public void setNotevar(NotevarBean notevar) {
                this.notevar = notevar;
            }

            public String getVdateline() {
                return vdateline;
            }

            public void setVdateline(String vdateline) {
                this.vdateline = vdateline;
            }

            public static class NotevarBean {
                /**
                 * tid : 593
                 * pid : 1700
                 * subject : 哇咔咔是谁
                 * actoruid : 82
                 * actorusername : mac11
                 */

                private String tid;
                private String pid;
                private String subject;
                private String actoruid;
                private String actorusername;

                public String getTid() {
                    return tid;
                }

                public void setTid(String tid) {
                    this.tid = tid;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public String getSubject() {
                    return subject;
                }

                public void setSubject(String subject) {
                    this.subject = subject;
                }

                public String getActoruid() {
                    return actoruid;
                }

                public void setActoruid(String actoruid) {
                    this.actoruid = actoruid;
                }

                public String getActorusername() {
                    return actorusername;
                }

                public void setActorusername(String actorusername) {
                    this.actorusername = actorusername;
                }
            }
        }
    }
}
