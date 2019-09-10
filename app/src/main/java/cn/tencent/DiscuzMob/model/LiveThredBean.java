package cn.tencent.DiscuzMob.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cg on 2017/5/16.
 */

public class LiveThredBean {

    /**
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","formhash":"93d38bde","groupiconid":{"2":"admin"},"groupid":"7","ismoderator":"","livethread_list":[{"attachment":"2","author":"大皮特","authorid":"2","avatar":"http://10.0.6.58/uc_server/avatar.php?uid=2&size=big","bgcolor":"","blueprint":"0","closed":"0","comments":"0","cover":"0","dateline":"1493092355","digest":"1","displayorder":"1","favtimes":"7","fid":"2","forumnames":{"fid":"2","name":"Discuz!-模板"},"heats":"4","hidden":"0","highlight":"0","icon":"-1","isgroup":"0","lastpost":"1494488038","lastposter":"admin","maxposition":"19","moderated":"1","posttableid":"0","price":"0","pushedaid":"0","rate":"0","readperm":"0","recommend_add":"2","recommend_sub":"0","recommends":"2","relatebytag":"0","replies":"18","replycredit":"0","sharetimes":"0","sortid":"0","special":"0","stamp":"-1","status":"3104","stickreply":"0","subject":"麻辣小龙虾","tid":"46","typeid":"0","views":"196"}],"member_avatar":"http://10.0.6.58/uc_server/data/avatar/000/00/00/00_avatar_small.jpg","member_conisbind":"0","member_loginstatus":"0","member_uid":"0","member_username":"","member_weixinisbind":"0","notice":{"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"},"readaccess":"1","saltkey":"I9l9D9dP"}
     * Version : 5
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
         * cookiepre : 68nN_2132_
         * formhash : 93d38bde
         * groupiconid : {"2":"admin"}
         * groupid : 7
         * ismoderator :
         * livethread_list : [{"attachment":"2","author":"大皮特","authorid":"2","avatar":"http://10.0.6.58/uc_server/avatar.php?uid=2&size=big","bgcolor":"","blueprint":"0","closed":"0","comments":"0","cover":"0","dateline":"1493092355","digest":"1","displayorder":"1","favtimes":"7","fid":"2","forumnames":{"fid":"2","name":"Discuz!-模板"},"heats":"4","hidden":"0","highlight":"0","icon":"-1","isgroup":"0","lastpost":"1494488038","lastposter":"admin","maxposition":"19","moderated":"1","posttableid":"0","price":"0","pushedaid":"0","rate":"0","readperm":"0","recommend_add":"2","recommend_sub":"0","recommends":"2","relatebytag":"0","replies":"18","replycredit":"0","sharetimes":"0","sortid":"0","special":"0","stamp":"-1","status":"3104","stickreply":"0","subject":"麻辣小龙虾","tid":"46","typeid":"0","views":"196"}]
         * member_avatar : http://10.0.6.58/uc_server/data/avatar/000/00/00/00_avatar_small.jpg
         * member_conisbind : 0
         * member_loginstatus : 0
         * member_uid : 0
         * member_username :
         * member_weixinisbind : 0
         * notice : {"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"}
         * readaccess : 1
         * saltkey : I9l9D9dP
         */

        private String cookiepre;
        private String formhash;
        private GroupiconidBean groupiconid;
        private String groupid;
        private String ismoderator;
        private String member_avatar;
        private String member_conisbind;
        private String member_loginstatus;
        private String member_uid;
        private String member_username;
        private String member_weixinisbind;
        private NoticeBean notice;
        private String readaccess;
        private String saltkey;
        private List<LivethreadListBean> livethread_list;

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

        public GroupiconidBean getGroupiconid() {
            return groupiconid;
        }

        public void setGroupiconid(GroupiconidBean groupiconid) {
            this.groupiconid = groupiconid;
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

        public String getMember_conisbind() {
            return member_conisbind;
        }

        public void setMember_conisbind(String member_conisbind) {
            this.member_conisbind = member_conisbind;
        }

        public String getMember_loginstatus() {
            return member_loginstatus;
        }

        public void setMember_loginstatus(String member_loginstatus) {
            this.member_loginstatus = member_loginstatus;
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

        public String getMember_weixinisbind() {
            return member_weixinisbind;
        }

        public void setMember_weixinisbind(String member_weixinisbind) {
            this.member_weixinisbind = member_weixinisbind;
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

        public List<LivethreadListBean> getLivethread_list() {
            return livethread_list;
        }

        public void setLivethread_list(List<LivethreadListBean> livethread_list) {
            this.livethread_list = livethread_list;
        }

        public static class GroupiconidBean {
            /**
             * 2 : admin
             */

            @SerializedName("2")
            private String _$2;

            public String get_$2() {
                return _$2;
            }

            public void set_$2(String _$2) {
                this._$2 = _$2;
            }
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

        public static class LivethreadListBean {
            /**
             * attachment : 2
             * author : 大皮特
             * authorid : 2
             * avatar : http://10.0.6.58/uc_server/avatar.php?uid=2&size=big
             * bgcolor :
             * blueprint : 0
             * closed : 0
             * comments : 0
             * cover : 0
             * dateline : 1493092355
             * digest : 1
             * displayorder : 1
             * favtimes : 7
             * fid : 2
             * forumnames : {"fid":"2","name":"Discuz!-模板"}
             * heats : 4
             * hidden : 0
             * highlight : 0
             * icon : -1
             * isgroup : 0
             * lastpost : 1494488038
             * lastposter : admin
             * maxposition : 19
             * moderated : 1
             * posttableid : 0
             * price : 0
             * pushedaid : 0
             * rate : 0
             * readperm : 0
             * recommend_add : 2
             * recommend_sub : 0
             * recommends : 2
             * relatebytag : 0
             * replies : 18
             * replycredit : 0
             * sharetimes : 0
             * sortid : 0
             * special : 0
             * stamp : -1
             * status : 3104
             * stickreply : 0
             * subject : 麻辣小龙虾
             * tid : 46
             * typeid : 0
             * views : 196
             */

            private String attachment;
            private String author;
            private String authorid;
            private String avatar;
            private String bgcolor;
            private String blueprint;
            private String closed;
            private String comments;
            private String cover;
            private String dateline;
            private String digest;
            private String displayorder;
            private String favtimes;
            private String fid;
            private ForumnamesBean forumnames;
            private String heats;
            private String hidden;
            private String highlight;
            private String icon;
            private String isgroup;
            private String lastpost;
            private String lastposter;
            private String maxposition;
            private String moderated;
            private String posttableid;
            private String price;
            private String pushedaid;
            private String rate;
            private String readperm;
            private String recommend_add;
            private String recommend_sub;
            private String recommends;
            private String relatebytag;
            private String replies;
            private String replycredit;
            private String sharetimes;
            private String sortid;
            private String special;
            private String stamp;
            private String status;
            private String stickreply;
            private String subject;
            private String tid;
            private String typeid;
            private String views;

            public String getAttachment() {
                return attachment;
            }

            public void setAttachment(String attachment) {
                this.attachment = attachment;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getAuthorid() {
                return authorid;
            }

            public void setAuthorid(String authorid) {
                this.authorid = authorid;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getBgcolor() {
                return bgcolor;
            }

            public void setBgcolor(String bgcolor) {
                this.bgcolor = bgcolor;
            }

            public String getBlueprint() {
                return blueprint;
            }

            public void setBlueprint(String blueprint) {
                this.blueprint = blueprint;
            }

            public String getClosed() {
                return closed;
            }

            public void setClosed(String closed) {
                this.closed = closed;
            }

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getDateline() {
                return dateline;
            }

            public void setDateline(String dateline) {
                this.dateline = dateline;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public String getFavtimes() {
                return favtimes;
            }

            public void setFavtimes(String favtimes) {
                this.favtimes = favtimes;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public ForumnamesBean getForumnames() {
                return forumnames;
            }

            public void setForumnames(ForumnamesBean forumnames) {
                this.forumnames = forumnames;
            }

            public String getHeats() {
                return heats;
            }

            public void setHeats(String heats) {
                this.heats = heats;
            }

            public String getHidden() {
                return hidden;
            }

            public void setHidden(String hidden) {
                this.hidden = hidden;
            }

            public String getHighlight() {
                return highlight;
            }

            public void setHighlight(String highlight) {
                this.highlight = highlight;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getIsgroup() {
                return isgroup;
            }

            public void setIsgroup(String isgroup) {
                this.isgroup = isgroup;
            }

            public String getLastpost() {
                return lastpost;
            }

            public void setLastpost(String lastpost) {
                this.lastpost = lastpost;
            }

            public String getLastposter() {
                return lastposter;
            }

            public void setLastposter(String lastposter) {
                this.lastposter = lastposter;
            }

            public String getMaxposition() {
                return maxposition;
            }

            public void setMaxposition(String maxposition) {
                this.maxposition = maxposition;
            }

            public String getModerated() {
                return moderated;
            }

            public void setModerated(String moderated) {
                this.moderated = moderated;
            }

            public String getPosttableid() {
                return posttableid;
            }

            public void setPosttableid(String posttableid) {
                this.posttableid = posttableid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPushedaid() {
                return pushedaid;
            }

            public void setPushedaid(String pushedaid) {
                this.pushedaid = pushedaid;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getReadperm() {
                return readperm;
            }

            public void setReadperm(String readperm) {
                this.readperm = readperm;
            }

            public String getRecommend_add() {
                return recommend_add;
            }

            public void setRecommend_add(String recommend_add) {
                this.recommend_add = recommend_add;
            }

            public String getRecommend_sub() {
                return recommend_sub;
            }

            public void setRecommend_sub(String recommend_sub) {
                this.recommend_sub = recommend_sub;
            }

            public String getRecommends() {
                return recommends;
            }

            public void setRecommends(String recommends) {
                this.recommends = recommends;
            }

            public String getRelatebytag() {
                return relatebytag;
            }

            public void setRelatebytag(String relatebytag) {
                this.relatebytag = relatebytag;
            }

            public String getReplies() {
                return replies;
            }

            public void setReplies(String replies) {
                this.replies = replies;
            }

            public String getReplycredit() {
                return replycredit;
            }

            public void setReplycredit(String replycredit) {
                this.replycredit = replycredit;
            }

            public String getSharetimes() {
                return sharetimes;
            }

            public void setSharetimes(String sharetimes) {
                this.sharetimes = sharetimes;
            }

            public String getSortid() {
                return sortid;
            }

            public void setSortid(String sortid) {
                this.sortid = sortid;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getStamp() {
                return stamp;
            }

            public void setStamp(String stamp) {
                this.stamp = stamp;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStickreply() {
                return stickreply;
            }

            public void setStickreply(String stickreply) {
                this.stickreply = stickreply;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public String getViews() {
                return views;
            }

            public void setViews(String views) {
                this.views = views;
            }

            public static class ForumnamesBean {
                /**
                 * fid : 2
                 * name : Discuz!-模板
                 */

                private String fid;
                private String name;

                public String getFid() {
                    return fid;
                }

                public void setFid(String fid) {
                    this.fid = fid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
