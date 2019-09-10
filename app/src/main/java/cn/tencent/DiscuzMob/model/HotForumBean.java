package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/6.
 */

public class HotForumBean {


    /**
     * Charset : UTF-8
     * Variables : {"cookiepre":"IiUw_2132_","data":[{"fid":"2","lastpost":"2017-6-11 13:14","lastpost_subject":"他苏杰东北大米","lastpost_tid":"107","lastposter":"国文_ZLUnJ","name":"天下实事","posts":"97","threads":"30","todayposts":"0"},{"fid":"41","lastpost":"2017-6-8 19:38","lastpost_subject":"一个个的","lastpost_tid":"92","lastposter":"mac11","name":"名人历史","posts":"2","threads":"2","todayposts":"0"},{"fid":"38","lastpost":"2017-5-26 09:57","lastpost_subject":"语音发帖迭代中","lastpost_tid":"16","lastposter":"admin","name":"北京新闻","posts":"4","threads":"2","todayposts":"0"},{"fid":"37","lastpost":"2017-6-8 18:34","lastpost_subject":"图片图片图片","lastpost_tid":"89","lastposter":"lgw520","name":"奇闻趣事","posts":"8","threads":"4","todayposts":"0"},{"fid":"42","lastpost":"2017-6-11 13:13","lastpost_subject":"山东人","lastpost_tid":"111","lastposter":"广饶信息网","name":"北京交通","posts":"2","threads":"2","todayposts":"0"},{"fid":"39","lastpost":"2017-6-9 16:25","lastpost_subject":"投票贴投票","lastpost_tid":"95","lastposter":"lgw520","name":"每日微微一笑","posts":"19","threads":"13","todayposts":"0"},{"fid":"50","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"通知公告","posts":"0","threads":"0","todayposts":"0"},{"fid":"43","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"北京城事","posts":"0","threads":"0","todayposts":"0"},{"fid":"40","lastpost":"2017-6-9 17:23","lastpost_subject":"杜均杜均减肥减肥","lastpost_tid":"110","lastposter":"国文_ZLUnJ","name":"文学哲学","posts":"85","threads":"45","todayposts":"0"},{"fid":"49","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"问题反馈","posts":"0","threads":"0","todayposts":"0"},{"fid":"44","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"科技公司","posts":"0","threads":"0","todayposts":"0"},{"fid":"45","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"国家行政管理","posts":"0","threads":"0","todayposts":"0"}],"formhash":"dd401814","groupid":"7","member_avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=0&size=small","member_uid":"0","member_username":"","notice":{"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"},"readaccess":"1","saltkey":"sc6PcXQK"}
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
         * cookiepre : IiUw_2132_
         * data : [{"fid":"2","lastpost":"2017-6-11 13:14","lastpost_subject":"他苏杰东北大米","lastpost_tid":"107","lastposter":"国文_ZLUnJ","name":"天下实事","posts":"97","threads":"30","todayposts":"0"},{"fid":"41","lastpost":"2017-6-8 19:38","lastpost_subject":"一个个的","lastpost_tid":"92","lastposter":"mac11","name":"名人历史","posts":"2","threads":"2","todayposts":"0"},{"fid":"38","lastpost":"2017-5-26 09:57","lastpost_subject":"语音发帖迭代中","lastpost_tid":"16","lastposter":"admin","name":"北京新闻","posts":"4","threads":"2","todayposts":"0"},{"fid":"37","lastpost":"2017-6-8 18:34","lastpost_subject":"图片图片图片","lastpost_tid":"89","lastposter":"lgw520","name":"奇闻趣事","posts":"8","threads":"4","todayposts":"0"},{"fid":"42","lastpost":"2017-6-11 13:13","lastpost_subject":"山东人","lastpost_tid":"111","lastposter":"广饶信息网","name":"北京交通","posts":"2","threads":"2","todayposts":"0"},{"fid":"39","lastpost":"2017-6-9 16:25","lastpost_subject":"投票贴投票","lastpost_tid":"95","lastposter":"lgw520","name":"每日微微一笑","posts":"19","threads":"13","todayposts":"0"},{"fid":"50","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"通知公告","posts":"0","threads":"0","todayposts":"0"},{"fid":"43","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"北京城事","posts":"0","threads":"0","todayposts":"0"},{"fid":"40","lastpost":"2017-6-9 17:23","lastpost_subject":"杜均杜均减肥减肥","lastpost_tid":"110","lastposter":"国文_ZLUnJ","name":"文学哲学","posts":"85","threads":"45","todayposts":"0"},{"fid":"49","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"问题反馈","posts":"0","threads":"0","todayposts":"0"},{"fid":"44","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"科技公司","posts":"0","threads":"0","todayposts":"0"},{"fid":"45","lastpost":"1970-1-1 08:00","lastpost_subject":"","lastpost_tid":"","lastposter":"","name":"国家行政管理","posts":"0","threads":"0","todayposts":"0"}]
         * formhash : dd401814
         * groupid : 7
         * member_avatar : http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=0&size=small
         * member_uid : 0
         * member_username :
         * notice : {"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"}
         * readaccess : 1
         * saltkey : sc6PcXQK
         */

        private String cookiepre;
        private String formhash;
        private String groupid;
        private String member_avatar;
        private String member_uid;
        private String member_username;
        private NoticeBean notice;
        private String readaccess;
        private String saltkey;
        private List<DataBean> data;

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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
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

        public static class DataBean {
            /**
             * fid : 2
             * lastpost : 2017-6-11 13:14
             * lastpost_subject : 他苏杰东北大米
             * lastpost_tid : 107
             * lastposter : 国文_ZLUnJ
             * name : 天下实事
             * posts : 97
             * threads : 30
             * todayposts : 0
             */

            private String fid;
            private String lastpost;
            private String lastpost_subject;
            private String lastpost_tid;
            private String lastposter;
            private String name;
            private String posts;
            private String threads;
            private String todayposts;
            private String icon;
            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getLastpost() {
                return lastpost;
            }

            public void setLastpost(String lastpost) {
                this.lastpost = lastpost;
            }

            public String getLastpost_subject() {
                return lastpost_subject;
            }

            public void setLastpost_subject(String lastpost_subject) {
                this.lastpost_subject = lastpost_subject;
            }

            public String getLastpost_tid() {
                return lastpost_tid;
            }

            public void setLastpost_tid(String lastpost_tid) {
                this.lastpost_tid = lastpost_tid;
            }

            public String getLastposter() {
                return lastposter;
            }

            public void setLastposter(String lastposter) {
                this.lastposter = lastposter;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosts() {
                return posts;
            }

            public void setPosts(String posts) {
                this.posts = posts;
            }

            public String getThreads() {
                return threads;
            }

            public void setThreads(String threads) {
                this.threads = threads;
            }

            public String getTodayposts() {
                return todayposts;
            }

            public void setTodayposts(String todayposts) {
                this.todayposts = todayposts;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}
