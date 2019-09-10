package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/5.
 */

public class AllForumBean {

    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","auth":null,"saltkey":"jFnkrekt","member_uid":"0","member_username":"","member_avatar":"http://10.0.6.58/uc_server/data/avatar/000/00/00/00_avatar_small.jpg","member_conisbind":"0","member_weixinisbind":"0","member_loginstatus":"0","groupid":"7","formhash":"a399367d","ismoderator":"","readaccess":"1","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"member_email":null,"member_credits":"0","setting_bbclosed":"","group":{"groupid":"7","grouptitle":"游客","allowvisit":"1","allowsendpm":"1","allowinvite":"0","allowmailinvite":"0","allowpost":"0","allowreply":"0","allowpostpoll":"0","allowpostreward":"0","allowposttrade":"0","allowpostactivity":"0","allowdirectpost":"0","allowgetattach":"0","allowgetimage":"0","allowpostattach":"0","allowpostimage":"0","allowvote":"0","allowsearch":"19","allowcstatus":"0","allowinvisible":"0","allowtransfer":"0","allowsetreadperm":"0","allowsetattachperm":"0","allowposttag":"0","allowhidecode":"0","allowhtml":"0","allowanonymous":"0","allowsigbbcode":"0","allowsigimgcode":"0","allowmagics":"0","allowpostdebate":"0","allowposturl":"3","allowrecommend":"0","allowpostrushreply":"0","allowcomment":"0","allowcommentarticle":"0","allowblog":"0","allowdoing":"0","allowupload":"0","allowshare":"0","allowblogmod":"0","allowdoingmod":"0","allowuploadmod":"0","allowsharemod":"0","allowcss":"0","allowpoke":"0","allowfriend":"0","allowclick":"0","allowmagic":"0","allowstat":"0","allowstatdata":"0","allowviewvideophoto":"0","allowmyop":"1","allowbuildgroup":"0","allowgroupdirectpost":"3","allowgroupposturl":"0","allowpostarticle":"0","allowdownlocalimg":"0","allowdownremoteimg":"0","allowpostarticlemod":"0","allowspacediyhtml":"0","allowspacediybbcode":"0","allowspacediyimgcode":"0","allowcommentpost":"0","allowcommentitem":"0","allowcommentreply":"0","allowreplycredit":"0","allowsendallpm":"0","allowsendpmmaxnum":"0","allowmediacode":"0","allowbegincode":"0","allowat":"0","allowsetpublishdate":"0","allowfollowcollection":"0","allowcommentcollection":"0","allowcreatecollection":"0","allowimgcontent":"0","allowthreadplugin":[]},"catlist":[{"fid":"1","name":"Discuz!","forums":["2","36"]},{"fid":"40","name":"Comsenz 公司动态","forums":["41"]},{"fid":"37","name":"Discuz! 交流与讨论","forums":["38","39","42"]}],"forumlist":[{"fid":"2","name":"Discuz!-模板","threads":"51","posts":"161","todayposts":"2","description":"","icon":"http://10.0.6.58/data/attachment/common/c8/common_2_icon.png","redirect":""},{"fid":"36","name":"Discuz!-插件","threads":"23","posts":"32","todayposts":"0","description":"","icon":"","redirect":"","sublist":[{"fid":"43","name":"插件子版块","threads":"1","posts":"1","todayposts":"0"},{"fid":"44","name":"插件子","threads":"1","posts":"1","todayposts":"0"}]},{"fid":"38","name":"站长帮","threads":"4","posts":"11","todayposts":"0","description":"","icon":"","redirect":""},{"fid":"39","name":"Discuz!-BUG反馈","threads":"16","posts":"16","todayposts":"0","description":"","icon":"","redirect":""},{"fid":"41","name":"Discuz! 程序发布","threads":"17","posts":"34","todayposts":"0","description":"","icon":"","redirect":""},{"fid":"42","name":"随时可以发","threads":"4","posts":"5","todayposts":"0","description":"","icon":"","redirect":""}],"visitedforums":[{"fid":"36","name":"Discuz!-插件","threads":"23","todayposts":"0","icon":""},{"fid":"38","name":"站长帮","threads":"4","todayposts":"0","icon":""},{"fid":"2","name":"Discuz!-模板","threads":"51","todayposts":"2","icon":"http://10.0.6.58/data/attachment/common/c8/common_2_icon.png"}]}
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
         * auth : null
         * saltkey : jFnkrekt
         * member_uid : 0
         * member_username :
         * member_avatar : http://10.0.6.58/uc_server/data/avatar/000/00/00/00_avatar_small.jpg
         * member_conisbind : 0
         * member_weixinisbind : 0
         * member_loginstatus : 0
         * groupid : 7
         * formhash : a399367d
         * ismoderator :
         * readaccess : 1
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * member_email : null
         * member_credits : 0
         * setting_bbclosed :
         * group : {"groupid":"7","grouptitle":"游客","allowvisit":"1","allowsendpm":"1","allowinvite":"0","allowmailinvite":"0","allowpost":"0","allowreply":"0","allowpostpoll":"0","allowpostreward":"0","allowposttrade":"0","allowpostactivity":"0","allowdirectpost":"0","allowgetattach":"0","allowgetimage":"0","allowpostattach":"0","allowpostimage":"0","allowvote":"0","allowsearch":"19","allowcstatus":"0","allowinvisible":"0","allowtransfer":"0","allowsetreadperm":"0","allowsetattachperm":"0","allowposttag":"0","allowhidecode":"0","allowhtml":"0","allowanonymous":"0","allowsigbbcode":"0","allowsigimgcode":"0","allowmagics":"0","allowpostdebate":"0","allowposturl":"3","allowrecommend":"0","allowpostrushreply":"0","allowcomment":"0","allowcommentarticle":"0","allowblog":"0","allowdoing":"0","allowupload":"0","allowshare":"0","allowblogmod":"0","allowdoingmod":"0","allowuploadmod":"0","allowsharemod":"0","allowcss":"0","allowpoke":"0","allowfriend":"0","allowclick":"0","allowmagic":"0","allowstat":"0","allowstatdata":"0","allowviewvideophoto":"0","allowmyop":"1","allowbuildgroup":"0","allowgroupdirectpost":"3","allowgroupposturl":"0","allowpostarticle":"0","allowdownlocalimg":"0","allowdownremoteimg":"0","allowpostarticlemod":"0","allowspacediyhtml":"0","allowspacediybbcode":"0","allowspacediyimgcode":"0","allowcommentpost":"0","allowcommentitem":"0","allowcommentreply":"0","allowreplycredit":"0","allowsendallpm":"0","allowsendpmmaxnum":"0","allowmediacode":"0","allowbegincode":"0","allowat":"0","allowsetpublishdate":"0","allowfollowcollection":"0","allowcommentcollection":"0","allowcreatecollection":"0","allowimgcontent":"0","allowthreadplugin":[]}
         * catlist : [{"fid":"1","name":"Discuz!","forums":["2","36"]},{"fid":"40","name":"Comsenz 公司动态","forums":["41"]},{"fid":"37","name":"Discuz! 交流与讨论","forums":["38","39","42"]}]
         * forumlist : [{"fid":"2","name":"Discuz!-模板","threads":"51","posts":"161","todayposts":"2","description":"","icon":"http://10.0.6.58/data/attachment/common/c8/common_2_icon.png","redirect":""},{"fid":"36","name":"Discuz!-插件","threads":"23","posts":"32","todayposts":"0","description":"","icon":"","redirect":"","sublist":[{"fid":"43","name":"插件子版块","threads":"1","posts":"1","todayposts":"0"},{"fid":"44","name":"插件子","threads":"1","posts":"1","todayposts":"0"}]},{"fid":"38","name":"站长帮","threads":"4","posts":"11","todayposts":"0","description":"","icon":"","redirect":""},{"fid":"39","name":"Discuz!-BUG反馈","threads":"16","posts":"16","todayposts":"0","description":"","icon":"","redirect":""},{"fid":"41","name":"Discuz! 程序发布","threads":"17","posts":"34","todayposts":"0","description":"","icon":"","redirect":""},{"fid":"42","name":"随时可以发","threads":"4","posts":"5","todayposts":"0","description":"","icon":"","redirect":""}]
         * visitedforums : [{"fid":"36","name":"Discuz!-插件","threads":"23","todayposts":"0","icon":""},{"fid":"38","name":"站长帮","threads":"4","todayposts":"0","icon":""},{"fid":"2","name":"Discuz!-模板","threads":"51","todayposts":"2","icon":"http://10.0.6.58/data/attachment/common/c8/common_2_icon.png"}]
         */

        private String cookiepre;
        private Object auth;
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
        private Object member_email;
        private String member_credits;
        private String setting_bbclosed;
        private GroupBean group;
        private List<CatlistBean> catlist;
        private List<ForumlistBean> forumlist;
        private List<ForumlistBean> visitedforums;

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

        public Object getMember_email() {
            return member_email;
        }

        public void setMember_email(Object member_email) {
            this.member_email = member_email;
        }

        public String getMember_credits() {
            return member_credits;
        }

        public void setMember_credits(String member_credits) {
            this.member_credits = member_credits;
        }

        public String getSetting_bbclosed() {
            return setting_bbclosed;
        }

        public void setSetting_bbclosed(String setting_bbclosed) {
            this.setting_bbclosed = setting_bbclosed;
        }

        public GroupBean getGroup() {
            return group;
        }

        public void setGroup(GroupBean group) {
            this.group = group;
        }

        public List<CatlistBean> getCatlist() {
            return catlist;
        }

        public void setCatlist(List<CatlistBean> catlist) {
            this.catlist = catlist;
        }

        public List<ForumlistBean> getForumlist() {
            return forumlist;
        }

        public void setForumlist(List<ForumlistBean> forumlist) {
            this.forumlist = forumlist;
        }

        public List<ForumlistBean> getVisitedforums() {
            return visitedforums;
        }

        public void setVisitedforums(List<ForumlistBean> visitedforums) {
            this.visitedforums = visitedforums;
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

        public static class GroupBean {
            /**
             * groupid : 7
             * grouptitle : 游客
             * allowvisit : 1
             * allowsendpm : 1
             * allowinvite : 0
             * allowmailinvite : 0
             * allowpost : 0
             * allowreply : 0
             * allowpostpoll : 0
             * allowpostreward : 0
             * allowposttrade : 0
             * allowpostactivity : 0
             * allowdirectpost : 0
             * allowgetattach : 0
             * allowgetimage : 0
             * allowpostattach : 0
             * allowpostimage : 0
             * allowvote : 0
             * allowsearch : 19
             * allowcstatus : 0
             * allowinvisible : 0
             * allowtransfer : 0
             * allowsetreadperm : 0
             * allowsetattachperm : 0
             * allowposttag : 0
             * allowhidecode : 0
             * allowhtml : 0
             * allowanonymous : 0
             * allowsigbbcode : 0
             * allowsigimgcode : 0
             * allowmagics : 0
             * allowpostdebate : 0
             * allowposturl : 3
             * allowrecommend : 0
             * allowpostrushreply : 0
             * allowcomment : 0
             * allowcommentarticle : 0
             * allowblog : 0
             * allowdoing : 0
             * allowupload : 0
             * allowshare : 0
             * allowblogmod : 0
             * allowdoingmod : 0
             * allowuploadmod : 0
             * allowsharemod : 0
             * allowcss : 0
             * allowpoke : 0
             * allowfriend : 0
             * allowclick : 0
             * allowmagic : 0
             * allowstat : 0
             * allowstatdata : 0
             * allowviewvideophoto : 0
             * allowmyop : 1
             * allowbuildgroup : 0
             * allowgroupdirectpost : 3
             * allowgroupposturl : 0
             * allowpostarticle : 0
             * allowdownlocalimg : 0
             * allowdownremoteimg : 0
             * allowpostarticlemod : 0
             * allowspacediyhtml : 0
             * allowspacediybbcode : 0
             * allowspacediyimgcode : 0
             * allowcommentpost : 0
             * allowcommentitem : 0
             * allowcommentreply : 0
             * allowreplycredit : 0
             * allowsendallpm : 0
             * allowsendpmmaxnum : 0
             * allowmediacode : 0
             * allowbegincode : 0
             * allowat : 0
             * allowsetpublishdate : 0
             * allowfollowcollection : 0
             * allowcommentcollection : 0
             * allowcreatecollection : 0
             * allowimgcontent : 0
             * allowthreadplugin : []
             */

            private String groupid;
            private String grouptitle;
            private String allowvisit;
            private String allowsendpm;
            private String allowinvite;
            private String allowmailinvite;
            private String allowpost;
            private String allowreply;
            private String allowpostpoll;
            private String allowpostreward;
            private String allowposttrade;
            private String allowpostactivity;
            private String allowdirectpost;
            private String allowgetattach;
            private String allowgetimage;
            private String allowpostattach;
            private String allowpostimage;
            private String allowvote;
            private String allowsearch;
            private String allowcstatus;
            private String allowinvisible;
            private String allowtransfer;
            private String allowsetreadperm;
            private String allowsetattachperm;
            private String allowposttag;
            private String allowhidecode;
            private String allowhtml;
            private String allowanonymous;
            private String allowsigbbcode;
            private String allowsigimgcode;
            private String allowmagics;
            private String allowpostdebate;
            private String allowposturl;
            private String allowrecommend;
            private String allowpostrushreply;
            private String allowcomment;
            private String allowcommentarticle;
            private String allowblog;
            private String allowdoing;
            private String allowupload;
            private String allowshare;
            private String allowblogmod;
            private String allowdoingmod;
            private String allowuploadmod;
            private String allowsharemod;
            private String allowcss;
            private String allowpoke;
            private String allowfriend;
            private String allowclick;
            private String allowmagic;
            private String allowstat;
            private String allowstatdata;
            private String allowviewvideophoto;
            private String allowmyop;
            private String allowbuildgroup;
            private String allowgroupdirectpost;
            private String allowgroupposturl;
            private String allowpostarticle;
            private String allowdownlocalimg;
            private String allowdownremoteimg;
            private String allowpostarticlemod;
            private String allowspacediyhtml;
            private String allowspacediybbcode;
            private String allowspacediyimgcode;
            private String allowcommentpost;
            private String allowcommentitem;
            private String allowcommentreply;
            private String allowreplycredit;
            private String allowsendallpm;
            private String allowsendpmmaxnum;
            private String allowmediacode;
            private String allowbegincode;
            private String allowat;
            private String allowsetpublishdate;
            private String allowfollowcollection;
            private String allowcommentcollection;
            private String allowcreatecollection;
            private String allowimgcontent;
            private List<?> allowthreadplugin;

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public String getGrouptitle() {
                return grouptitle;
            }

            public void setGrouptitle(String grouptitle) {
                this.grouptitle = grouptitle;
            }

            public String getAllowvisit() {
                return allowvisit;
            }

            public void setAllowvisit(String allowvisit) {
                this.allowvisit = allowvisit;
            }

            public String getAllowsendpm() {
                return allowsendpm;
            }

            public void setAllowsendpm(String allowsendpm) {
                this.allowsendpm = allowsendpm;
            }

            public String getAllowinvite() {
                return allowinvite;
            }

            public void setAllowinvite(String allowinvite) {
                this.allowinvite = allowinvite;
            }

            public String getAllowmailinvite() {
                return allowmailinvite;
            }

            public void setAllowmailinvite(String allowmailinvite) {
                this.allowmailinvite = allowmailinvite;
            }

            public String getAllowpost() {
                return allowpost;
            }

            public void setAllowpost(String allowpost) {
                this.allowpost = allowpost;
            }

            public String getAllowreply() {
                return allowreply;
            }

            public void setAllowreply(String allowreply) {
                this.allowreply = allowreply;
            }

            public String getAllowpostpoll() {
                return allowpostpoll;
            }

            public void setAllowpostpoll(String allowpostpoll) {
                this.allowpostpoll = allowpostpoll;
            }

            public String getAllowpostreward() {
                return allowpostreward;
            }

            public void setAllowpostreward(String allowpostreward) {
                this.allowpostreward = allowpostreward;
            }

            public String getAllowposttrade() {
                return allowposttrade;
            }

            public void setAllowposttrade(String allowposttrade) {
                this.allowposttrade = allowposttrade;
            }

            public String getAllowpostactivity() {
                return allowpostactivity;
            }

            public void setAllowpostactivity(String allowpostactivity) {
                this.allowpostactivity = allowpostactivity;
            }

            public String getAllowdirectpost() {
                return allowdirectpost;
            }

            public void setAllowdirectpost(String allowdirectpost) {
                this.allowdirectpost = allowdirectpost;
            }

            public String getAllowgetattach() {
                return allowgetattach;
            }

            public void setAllowgetattach(String allowgetattach) {
                this.allowgetattach = allowgetattach;
            }

            public String getAllowgetimage() {
                return allowgetimage;
            }

            public void setAllowgetimage(String allowgetimage) {
                this.allowgetimage = allowgetimage;
            }

            public String getAllowpostattach() {
                return allowpostattach;
            }

            public void setAllowpostattach(String allowpostattach) {
                this.allowpostattach = allowpostattach;
            }

            public String getAllowpostimage() {
                return allowpostimage;
            }

            public void setAllowpostimage(String allowpostimage) {
                this.allowpostimage = allowpostimage;
            }

            public String getAllowvote() {
                return allowvote;
            }

            public void setAllowvote(String allowvote) {
                this.allowvote = allowvote;
            }

            public String getAllowsearch() {
                return allowsearch;
            }

            public void setAllowsearch(String allowsearch) {
                this.allowsearch = allowsearch;
            }

            public String getAllowcstatus() {
                return allowcstatus;
            }

            public void setAllowcstatus(String allowcstatus) {
                this.allowcstatus = allowcstatus;
            }

            public String getAllowinvisible() {
                return allowinvisible;
            }

            public void setAllowinvisible(String allowinvisible) {
                this.allowinvisible = allowinvisible;
            }

            public String getAllowtransfer() {
                return allowtransfer;
            }

            public void setAllowtransfer(String allowtransfer) {
                this.allowtransfer = allowtransfer;
            }

            public String getAllowsetreadperm() {
                return allowsetreadperm;
            }

            public void setAllowsetreadperm(String allowsetreadperm) {
                this.allowsetreadperm = allowsetreadperm;
            }

            public String getAllowsetattachperm() {
                return allowsetattachperm;
            }

            public void setAllowsetattachperm(String allowsetattachperm) {
                this.allowsetattachperm = allowsetattachperm;
            }

            public String getAllowposttag() {
                return allowposttag;
            }

            public void setAllowposttag(String allowposttag) {
                this.allowposttag = allowposttag;
            }

            public String getAllowhidecode() {
                return allowhidecode;
            }

            public void setAllowhidecode(String allowhidecode) {
                this.allowhidecode = allowhidecode;
            }

            public String getAllowhtml() {
                return allowhtml;
            }

            public void setAllowhtml(String allowhtml) {
                this.allowhtml = allowhtml;
            }

            public String getAllowanonymous() {
                return allowanonymous;
            }

            public void setAllowanonymous(String allowanonymous) {
                this.allowanonymous = allowanonymous;
            }

            public String getAllowsigbbcode() {
                return allowsigbbcode;
            }

            public void setAllowsigbbcode(String allowsigbbcode) {
                this.allowsigbbcode = allowsigbbcode;
            }

            public String getAllowsigimgcode() {
                return allowsigimgcode;
            }

            public void setAllowsigimgcode(String allowsigimgcode) {
                this.allowsigimgcode = allowsigimgcode;
            }

            public String getAllowmagics() {
                return allowmagics;
            }

            public void setAllowmagics(String allowmagics) {
                this.allowmagics = allowmagics;
            }

            public String getAllowpostdebate() {
                return allowpostdebate;
            }

            public void setAllowpostdebate(String allowpostdebate) {
                this.allowpostdebate = allowpostdebate;
            }

            public String getAllowposturl() {
                return allowposturl;
            }

            public void setAllowposturl(String allowposturl) {
                this.allowposturl = allowposturl;
            }

            public String getAllowrecommend() {
                return allowrecommend;
            }

            public void setAllowrecommend(String allowrecommend) {
                this.allowrecommend = allowrecommend;
            }

            public String getAllowpostrushreply() {
                return allowpostrushreply;
            }

            public void setAllowpostrushreply(String allowpostrushreply) {
                this.allowpostrushreply = allowpostrushreply;
            }

            public String getAllowcomment() {
                return allowcomment;
            }

            public void setAllowcomment(String allowcomment) {
                this.allowcomment = allowcomment;
            }

            public String getAllowcommentarticle() {
                return allowcommentarticle;
            }

            public void setAllowcommentarticle(String allowcommentarticle) {
                this.allowcommentarticle = allowcommentarticle;
            }

            public String getAllowblog() {
                return allowblog;
            }

            public void setAllowblog(String allowblog) {
                this.allowblog = allowblog;
            }

            public String getAllowdoing() {
                return allowdoing;
            }

            public void setAllowdoing(String allowdoing) {
                this.allowdoing = allowdoing;
            }

            public String getAllowupload() {
                return allowupload;
            }

            public void setAllowupload(String allowupload) {
                this.allowupload = allowupload;
            }

            public String getAllowshare() {
                return allowshare;
            }

            public void setAllowshare(String allowshare) {
                this.allowshare = allowshare;
            }

            public String getAllowblogmod() {
                return allowblogmod;
            }

            public void setAllowblogmod(String allowblogmod) {
                this.allowblogmod = allowblogmod;
            }

            public String getAllowdoingmod() {
                return allowdoingmod;
            }

            public void setAllowdoingmod(String allowdoingmod) {
                this.allowdoingmod = allowdoingmod;
            }

            public String getAllowuploadmod() {
                return allowuploadmod;
            }

            public void setAllowuploadmod(String allowuploadmod) {
                this.allowuploadmod = allowuploadmod;
            }

            public String getAllowsharemod() {
                return allowsharemod;
            }

            public void setAllowsharemod(String allowsharemod) {
                this.allowsharemod = allowsharemod;
            }

            public String getAllowcss() {
                return allowcss;
            }

            public void setAllowcss(String allowcss) {
                this.allowcss = allowcss;
            }

            public String getAllowpoke() {
                return allowpoke;
            }

            public void setAllowpoke(String allowpoke) {
                this.allowpoke = allowpoke;
            }

            public String getAllowfriend() {
                return allowfriend;
            }

            public void setAllowfriend(String allowfriend) {
                this.allowfriend = allowfriend;
            }

            public String getAllowclick() {
                return allowclick;
            }

            public void setAllowclick(String allowclick) {
                this.allowclick = allowclick;
            }

            public String getAllowmagic() {
                return allowmagic;
            }

            public void setAllowmagic(String allowmagic) {
                this.allowmagic = allowmagic;
            }

            public String getAllowstat() {
                return allowstat;
            }

            public void setAllowstat(String allowstat) {
                this.allowstat = allowstat;
            }

            public String getAllowstatdata() {
                return allowstatdata;
            }

            public void setAllowstatdata(String allowstatdata) {
                this.allowstatdata = allowstatdata;
            }

            public String getAllowviewvideophoto() {
                return allowviewvideophoto;
            }

            public void setAllowviewvideophoto(String allowviewvideophoto) {
                this.allowviewvideophoto = allowviewvideophoto;
            }

            public String getAllowmyop() {
                return allowmyop;
            }

            public void setAllowmyop(String allowmyop) {
                this.allowmyop = allowmyop;
            }

            public String getAllowbuildgroup() {
                return allowbuildgroup;
            }

            public void setAllowbuildgroup(String allowbuildgroup) {
                this.allowbuildgroup = allowbuildgroup;
            }

            public String getAllowgroupdirectpost() {
                return allowgroupdirectpost;
            }

            public void setAllowgroupdirectpost(String allowgroupdirectpost) {
                this.allowgroupdirectpost = allowgroupdirectpost;
            }

            public String getAllowgroupposturl() {
                return allowgroupposturl;
            }

            public void setAllowgroupposturl(String allowgroupposturl) {
                this.allowgroupposturl = allowgroupposturl;
            }

            public String getAllowpostarticle() {
                return allowpostarticle;
            }

            public void setAllowpostarticle(String allowpostarticle) {
                this.allowpostarticle = allowpostarticle;
            }

            public String getAllowdownlocalimg() {
                return allowdownlocalimg;
            }

            public void setAllowdownlocalimg(String allowdownlocalimg) {
                this.allowdownlocalimg = allowdownlocalimg;
            }

            public String getAllowdownremoteimg() {
                return allowdownremoteimg;
            }

            public void setAllowdownremoteimg(String allowdownremoteimg) {
                this.allowdownremoteimg = allowdownremoteimg;
            }

            public String getAllowpostarticlemod() {
                return allowpostarticlemod;
            }

            public void setAllowpostarticlemod(String allowpostarticlemod) {
                this.allowpostarticlemod = allowpostarticlemod;
            }

            public String getAllowspacediyhtml() {
                return allowspacediyhtml;
            }

            public void setAllowspacediyhtml(String allowspacediyhtml) {
                this.allowspacediyhtml = allowspacediyhtml;
            }

            public String getAllowspacediybbcode() {
                return allowspacediybbcode;
            }

            public void setAllowspacediybbcode(String allowspacediybbcode) {
                this.allowspacediybbcode = allowspacediybbcode;
            }

            public String getAllowspacediyimgcode() {
                return allowspacediyimgcode;
            }

            public void setAllowspacediyimgcode(String allowspacediyimgcode) {
                this.allowspacediyimgcode = allowspacediyimgcode;
            }

            public String getAllowcommentpost() {
                return allowcommentpost;
            }

            public void setAllowcommentpost(String allowcommentpost) {
                this.allowcommentpost = allowcommentpost;
            }

            public String getAllowcommentitem() {
                return allowcommentitem;
            }

            public void setAllowcommentitem(String allowcommentitem) {
                this.allowcommentitem = allowcommentitem;
            }

            public String getAllowcommentreply() {
                return allowcommentreply;
            }

            public void setAllowcommentreply(String allowcommentreply) {
                this.allowcommentreply = allowcommentreply;
            }

            public String getAllowreplycredit() {
                return allowreplycredit;
            }

            public void setAllowreplycredit(String allowreplycredit) {
                this.allowreplycredit = allowreplycredit;
            }

            public String getAllowsendallpm() {
                return allowsendallpm;
            }

            public void setAllowsendallpm(String allowsendallpm) {
                this.allowsendallpm = allowsendallpm;
            }

            public String getAllowsendpmmaxnum() {
                return allowsendpmmaxnum;
            }

            public void setAllowsendpmmaxnum(String allowsendpmmaxnum) {
                this.allowsendpmmaxnum = allowsendpmmaxnum;
            }

            public String getAllowmediacode() {
                return allowmediacode;
            }

            public void setAllowmediacode(String allowmediacode) {
                this.allowmediacode = allowmediacode;
            }

            public String getAllowbegincode() {
                return allowbegincode;
            }

            public void setAllowbegincode(String allowbegincode) {
                this.allowbegincode = allowbegincode;
            }

            public String getAllowat() {
                return allowat;
            }

            public void setAllowat(String allowat) {
                this.allowat = allowat;
            }

            public String getAllowsetpublishdate() {
                return allowsetpublishdate;
            }

            public void setAllowsetpublishdate(String allowsetpublishdate) {
                this.allowsetpublishdate = allowsetpublishdate;
            }

            public String getAllowfollowcollection() {
                return allowfollowcollection;
            }

            public void setAllowfollowcollection(String allowfollowcollection) {
                this.allowfollowcollection = allowfollowcollection;
            }

            public String getAllowcommentcollection() {
                return allowcommentcollection;
            }

            public void setAllowcommentcollection(String allowcommentcollection) {
                this.allowcommentcollection = allowcommentcollection;
            }

            public String getAllowcreatecollection() {
                return allowcreatecollection;
            }

            public void setAllowcreatecollection(String allowcreatecollection) {
                this.allowcreatecollection = allowcreatecollection;
            }

            public String getAllowimgcontent() {
                return allowimgcontent;
            }

            public void setAllowimgcontent(String allowimgcontent) {
                this.allowimgcontent = allowimgcontent;
            }

            public List<?> getAllowthreadplugin() {
                return allowthreadplugin;
            }

            public void setAllowthreadplugin(List<?> allowthreadplugin) {
                this.allowthreadplugin = allowthreadplugin;
            }
        }

//        public static class CatlistBean {
//            /**
//             * fid : 1
//             * name : Discuz!
//             * forums : ["2","36"]
//             */
//
//            private String fid;
//            private String name;
//            private List<String> forums;
//
//            public String getFid() {
//                return fid;
//            }
//
//            public void setFid(String fid) {
//                this.fid = fid;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public List<String> getForums() {
//                return forums;
//            }
//
//            public void setForums(List<String> forums) {
//                this.forums = forums;
//            }
//        }

        public static class ForumlistBean {
            /**
             * fid : 2
             * name : Discuz!-模板
             * threads : 51
             * posts : 161
             * todayposts : 2
             * description :
             * icon : http://10.0.6.58/data/attachment/common/c8/common_2_icon.png
             * redirect :
             * sublist : [{"fid":"43","name":"插件子版块","threads":"1","posts":"1","todayposts":"0"},{"fid":"44","name":"插件子","threads":"1","posts":"1","todayposts":"0"}]
             */

            private String fid;
            private String name;
            private String threads;
            private String posts;
            private String todayposts;
            private String description;
            private String icon;
            private String redirect;
            private List<SublistBean> sublist;

            @Override
            public String toString() {
                return "ForumlistBean{" +
                        "fid='" + fid + '\'' +
                        ", name='" + name + '\'' +
                        ", threads='" + threads + '\'' +
                        ", posts='" + posts + '\'' +
                        ", todayposts='" + todayposts + '\'' +
                        ", description='" + description + '\'' +
                        ", icon='" + icon + '\'' +
                        ", redirect='" + redirect + '\'' +
                        ", sublist=" + sublist +
                        '}';
            }

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

            public String getThreads() {
                return threads;
            }

            public void setThreads(String threads) {
                this.threads = threads;
            }

            public String getPosts() {
                return posts;
            }

            public void setPosts(String posts) {
                this.posts = posts;
            }

            public String getTodayposts() {
                return todayposts;
            }

            public void setTodayposts(String todayposts) {
                this.todayposts = todayposts;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getRedirect() {
                return redirect;
            }

            public void setRedirect(String redirect) {
                this.redirect = redirect;
            }

            public List<SublistBean> getSublist() {
                return sublist;
            }

            public void setSublist(List<SublistBean> sublist) {
                this.sublist = sublist;
            }

            public static class SublistBean {
                /**
                 * fid : 43
                 * name : 插件子版块
                 * threads : 1
                 * posts : 1
                 * todayposts : 0
                 */

                private String fid;
                private String name;
                private String threads;
                private String posts;
                private String todayposts;

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

                public String getThreads() {
                    return threads;
                }

                public void setThreads(String threads) {
                    this.threads = threads;
                }

                public String getPosts() {
                    return posts;
                }

                public void setPosts(String posts) {
                    this.posts = posts;
                }

                public String getTodayposts() {
                    return todayposts;
                }

                public void setTodayposts(String todayposts) {
                    this.todayposts = todayposts;
                }
            }
        }
    }
//    /**
//     * Charset : UTF-8
//     * Variables : {"catlist":[{"fid":"1","forums":["2","36"],"name":"Discuz!"},{"fid":"40","forums":["41"],"name":"Comsenz 公司动态"},{"fid":"37","forums":["38","39","42"],"name":"Discuz! 交流与讨论"}],"cookiepre":"68nN_2132_","formhash":"860f1509","forumlist":[{"allowpostspecial":"9","allowspecialonly":"0","description":"","fid":"2","icon":"","name":"Discuz!-模板","posts":"12","redirect":"","threads":"5","todayposts":"0"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"36","icon":"","name":"Discuz!-插件","posts":"4","redirect":"","threads":"4","todayposts":"0"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"38","icon":"","name":"站长帮","posts":"4","redirect":"","threads":"1","todayposts":"0"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"39","icon":"","name":"Discuz!-BUG反馈","posts":"1","redirect":"","threads":"1","todayposts":"0"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"41","icon":"","name":"Discuz! 程序发布","posts":"16","redirect":"","threads":"6","todayposts":"5"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"42","icon":"","name":"随时可以发","posts":"1","redirect":"","threads":"1","todayposts":"0"}],"group":{"allowanonymous":"0","allowat":"0","allowbegincode":"0","allowblog":"0","allowblogmod":"0","allowbuildgroup":"0","allowclick":"0","allowcomment":"0","allowcommentarticle":"0","allowcommentcollection":"0","allowcommentitem":"0","allowcommentpost":"0","allowcommentreply":"0","allowcreatecollection":"0","allowcss":"0","allowcstatus":"0","allowdirectpost":"0","allowdoing":"0","allowdoingmod":"0","allowdownlocalimg":"0","allowdownremoteimg":"0","allowfollowcollection":"0","allowfriend":"0","allowgetattach":"0","allowgetimage":"0","allowgroupdirectpost":"3","allowgroupposturl":"0","allowhidecode":"0","allowhtml":"0","allowimgcontent":"0","allowinvisible":"0","allowinvite":"0","allowmagic":"0","allowmagics":"0","allowmailinvite":"0","allowmediacode":"0","allowmyop":"1","allowpoke":"0","allowpost":"0","allowpostactivity":"0","allowpostarticle":"0","allowpostarticlemod":"0","allowpostattach":"0","allowpostdebate":"0","allowpostimage":"0","allowpostpoll":"0","allowpostreward":"0","allowpostrushreply":"0","allowposttag":"0","allowposttrade":"0","allowposturl":"3","allowrecommend":"0","allowreply":"0","allowreplycredit":"0","allowsearch":"19","allowsendallpm":"0","allowsendpm":"1","allowsendpmmaxnum":"0","allowsetattachperm":"0","allowsetpublishdate":"0","allowsetreadperm":"0","allowshare":"0","allowsharemod":"0","allowsigbbcode":"0","allowsigimgcode":"0","allowspacediybbcode":"0","allowspacediyhtml":"0","allowspacediyimgcode":"0","allowstat":"0","allowstatdata":"0","allowthreadplugin":[],"allowtransfer":"0","allowupload":"0","allowuploadmod":"0","allowviewvideophoto":"0","allowvisit":"1","allowvote":"0","groupid":"7","grouptitle":"游客"},"groupid":"7","member_avatar":"http://10.0.6.58/uc_server/avatar.php?uid=0&size=small","member_credits":"0","member_uid":"0","member_username":"","notice":{"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"},"readaccess":"1","saltkey":"Gm58XpN5","setting_bbclosed":""}
//     * Version : 4
//     */
//
//    private String Charset;
//    private VariablesBean Variables;
//    private String Version;
//
//    public String getCharset() {
//        return Charset;
//    }
//
//    public void setCharset(String Charset) {
//        this.Charset = Charset;
//    }
//
//    public VariablesBean getVariables() {
//        return Variables;
//    }
//
//    public void setVariables(VariablesBean Variables) {
//        this.Variables = Variables;
//    }
//
//    public String getVersion() {
//        return Version;
//    }
//
//    public void setVersion(String Version) {
//        this.Version = Version;
//    }
//
//    public static class VariablesBean {
//        /**
//         * catlist : [{"fid":"1","forums":["2","36"],"name":"Discuz!"},{"fid":"40","forums":["41"],"name":"Comsenz 公司动态"},{"fid":"37","forums":["38","39","42"],"name":"Discuz! 交流与讨论"}]
//         * cookiepre : 68nN_2132_
//         * formhash : 860f1509
//         * forumlist : [{"allowpostspecial":"9","allowspecialonly":"0","description":"","fid":"2","icon":"","name":"Discuz!-模板","posts":"12","redirect":"","threads":"5","todayposts":"0"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"36","icon":"","name":"Discuz!-插件","posts":"4","redirect":"","threads":"4","todayposts":"0"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"38","icon":"","name":"站长帮","posts":"4","redirect":"","threads":"1","todayposts":"0"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"39","icon":"","name":"Discuz!-BUG反馈","posts":"1","redirect":"","threads":"1","todayposts":"0"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"41","icon":"","name":"Discuz! 程序发布","posts":"16","redirect":"","threads":"6","todayposts":"5"},{"allowpostspecial":"1","allowspecialonly":"0","description":"","fid":"42","icon":"","name":"随时可以发","posts":"1","redirect":"","threads":"1","todayposts":"0"}]
//         * group : {"allowanonymous":"0","allowat":"0","allowbegincode":"0","allowblog":"0","allowblogmod":"0","allowbuildgroup":"0","allowclick":"0","allowcomment":"0","allowcommentarticle":"0","allowcommentcollection":"0","allowcommentitem":"0","allowcommentpost":"0","allowcommentreply":"0","allowcreatecollection":"0","allowcss":"0","allowcstatus":"0","allowdirectpost":"0","allowdoing":"0","allowdoingmod":"0","allowdownlocalimg":"0","allowdownremoteimg":"0","allowfollowcollection":"0","allowfriend":"0","allowgetattach":"0","allowgetimage":"0","allowgroupdirectpost":"3","allowgroupposturl":"0","allowhidecode":"0","allowhtml":"0","allowimgcontent":"0","allowinvisible":"0","allowinvite":"0","allowmagic":"0","allowmagics":"0","allowmailinvite":"0","allowmediacode":"0","allowmyop":"1","allowpoke":"0","allowpost":"0","allowpostactivity":"0","allowpostarticle":"0","allowpostarticlemod":"0","allowpostattach":"0","allowpostdebate":"0","allowpostimage":"0","allowpostpoll":"0","allowpostreward":"0","allowpostrushreply":"0","allowposttag":"0","allowposttrade":"0","allowposturl":"3","allowrecommend":"0","allowreply":"0","allowreplycredit":"0","allowsearch":"19","allowsendallpm":"0","allowsendpm":"1","allowsendpmmaxnum":"0","allowsetattachperm":"0","allowsetpublishdate":"0","allowsetreadperm":"0","allowshare":"0","allowsharemod":"0","allowsigbbcode":"0","allowsigimgcode":"0","allowspacediybbcode":"0","allowspacediyhtml":"0","allowspacediyimgcode":"0","allowstat":"0","allowstatdata":"0","allowthreadplugin":[],"allowtransfer":"0","allowupload":"0","allowuploadmod":"0","allowviewvideophoto":"0","allowvisit":"1","allowvote":"0","groupid":"7","grouptitle":"游客"}
//         * groupid : 7
//         * member_avatar : http://10.0.6.58/uc_server/avatar.php?uid=0&size=small
//         * member_credits : 0
//         * member_uid : 0
//         * member_username :
//         * notice : {"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"}
//         * readaccess : 1
//         * saltkey : Gm58XpN5
//         * setting_bbclosed :
//         */
//
//        private String cookiepre;
//        private String formhash;
//        private GroupBean group;
//        private String groupid;
//        private String member_avatar;
//        private String member_credits;
//        private String member_uid;
//        private String member_username;
//        private NoticeBean notice;
//        private String readaccess;
//        private String saltkey;
//        private String setting_bbclosed;
//        private List<CatlistBean> catlist;
//        private List<ForumlistBean> forumlist;
//
//        public String getCookiepre() {
//            return cookiepre;
//        }
//
//        public void setCookiepre(String cookiepre) {
//            this.cookiepre = cookiepre;
//        }
//
//        public String getFormhash() {
//            return formhash;
//        }
//
//        public void setFormhash(String formhash) {
//            this.formhash = formhash;
//        }
//
//        public GroupBean getGroup() {
//            return group;
//        }
//
//        public void setGroup(GroupBean group) {
//            this.group = group;
//        }
//
//        public String getGroupid() {
//            return groupid;
//        }
//
//        public void setGroupid(String groupid) {
//            this.groupid = groupid;
//        }
//
//        public String getMember_avatar() {
//            return member_avatar;
//        }
//
//        public void setMember_avatar(String member_avatar) {
//            this.member_avatar = member_avatar;
//        }
//
//        public String getMember_credits() {
//            return member_credits;
//        }
//
//        public void setMember_credits(String member_credits) {
//            this.member_credits = member_credits;
//        }
//
//        public String getMember_uid() {
//            return member_uid;
//        }
//
//        public void setMember_uid(String member_uid) {
//            this.member_uid = member_uid;
//        }
//
//        public String getMember_username() {
//            return member_username;
//        }
//
//        public void setMember_username(String member_username) {
//            this.member_username = member_username;
//        }
//
//        public NoticeBean getNotice() {
//            return notice;
//        }
//
//        public void setNotice(NoticeBean notice) {
//            this.notice = notice;
//        }
//
//        public String getReadaccess() {
//            return readaccess;
//        }
//
//        public void setReadaccess(String readaccess) {
//            this.readaccess = readaccess;
//        }
//
//        public String getSaltkey() {
//            return saltkey;
//        }
//
//        public void setSaltkey(String saltkey) {
//            this.saltkey = saltkey;
//        }
//
//        public String getSetting_bbclosed() {
//            return setting_bbclosed;
//        }
//
//        public void setSetting_bbclosed(String setting_bbclosed) {
//            this.setting_bbclosed = setting_bbclosed;
//        }
//
//        public List<CatlistBean> getCatlist() {
//            return catlist;
//        }
//
//        public void setCatlist(List<CatlistBean> catlist) {
//            this.catlist = catlist;
//        }
//
//        public List<ForumlistBean> getForumlist() {
//            return forumlist;
//        }
//
//        public void setForumlist(List<ForumlistBean> forumlist) {
//            this.forumlist = forumlist;
//        }
//
//        public static class GroupBean {
//            /**
//             * allowanonymous : 0
//             * allowat : 0
//             * allowbegincode : 0
//             * allowblog : 0
//             * allowblogmod : 0
//             * allowbuildgroup : 0
//             * allowclick : 0
//             * allowcomment : 0
//             * allowcommentarticle : 0
//             * allowcommentcollection : 0
//             * allowcommentitem : 0
//             * allowcommentpost : 0
//             * allowcommentreply : 0
//             * allowcreatecollection : 0
//             * allowcss : 0
//             * allowcstatus : 0
//             * allowdirectpost : 0
//             * allowdoing : 0
//             * allowdoingmod : 0
//             * allowdownlocalimg : 0
//             * allowdownremoteimg : 0
//             * allowfollowcollection : 0
//             * allowfriend : 0
//             * allowgetattach : 0
//             * allowgetimage : 0
//             * allowgroupdirectpost : 3
//             * allowgroupposturl : 0
//             * allowhidecode : 0
//             * allowhtml : 0
//             * allowimgcontent : 0
//             * allowinvisible : 0
//             * allowinvite : 0
//             * allowmagic : 0
//             * allowmagics : 0
//             * allowmailinvite : 0
//             * allowmediacode : 0
//             * allowmyop : 1
//             * allowpoke : 0
//             * allowpost : 0
//             * allowpostactivity : 0
//             * allowpostarticle : 0
//             * allowpostarticlemod : 0
//             * allowpostattach : 0
//             * allowpostdebate : 0
//             * allowpostimage : 0
//             * allowpostpoll : 0
//             * allowpostreward : 0
//             * allowpostrushreply : 0
//             * allowposttag : 0
//             * allowposttrade : 0
//             * allowposturl : 3
//             * allowrecommend : 0
//             * allowreply : 0
//             * allowreplycredit : 0
//             * allowsearch : 19
//             * allowsendallpm : 0
//             * allowsendpm : 1
//             * allowsendpmmaxnum : 0
//             * allowsetattachperm : 0
//             * allowsetpublishdate : 0
//             * allowsetreadperm : 0
//             * allowshare : 0
//             * allowsharemod : 0
//             * allowsigbbcode : 0
//             * allowsigimgcode : 0
//             * allowspacediybbcode : 0
//             * allowspacediyhtml : 0
//             * allowspacediyimgcode : 0
//             * allowstat : 0
//             * allowstatdata : 0
//             * allowthreadplugin : []
//             * allowtransfer : 0
//             * allowupload : 0
//             * allowuploadmod : 0
//             * allowviewvideophoto : 0
//             * allowvisit : 1
//             * allowvote : 0
//             * groupid : 7
//             * grouptitle : 游客
//             */
//
//            private String allowanonymous;
//            private String allowat;
//            private String allowbegincode;
//            private String allowblog;
//            private String allowblogmod;
//            private String allowbuildgroup;
//            private String allowclick;
//            private String allowcomment;
//            private String allowcommentarticle;
//            private String allowcommentcollection;
//            private String allowcommentitem;
//            private String allowcommentpost;
//            private String allowcommentreply;
//            private String allowcreatecollection;
//            private String allowcss;
//            private String allowcstatus;
//            private String allowdirectpost;
//            private String allowdoing;
//            private String allowdoingmod;
//            private String allowdownlocalimg;
//            private String allowdownremoteimg;
//            private String allowfollowcollection;
//            private String allowfriend;
//            private String allowgetattach;
//            private String allowgetimage;
//            private String allowgroupdirectpost;
//            private String allowgroupposturl;
//            private String allowhidecode;
//            private String allowhtml;
//            private String allowimgcontent;
//            private String allowinvisible;
//            private String allowinvite;
//            private String allowmagic;
//            private String allowmagics;
//            private String allowmailinvite;
//            private String allowmediacode;
//            private String allowmyop;
//            private String allowpoke;
//            private String allowpost;
//            private String allowpostactivity;
//            private String allowpostarticle;
//            private String allowpostarticlemod;
//            private String allowpostattach;
//            private String allowpostdebate;
//            private String allowpostimage;
//            private String allowpostpoll;
//            private String allowpostreward;
//            private String allowpostrushreply;
//            private String allowposttag;
//            private String allowposttrade;
//            private String allowposturl;
//            private String allowrecommend;
//            private String allowreply;
//            private String allowreplycredit;
//            private String allowsearch;
//            private String allowsendallpm;
//            private String allowsendpm;
//            private String allowsendpmmaxnum;
//            private String allowsetattachperm;
//            private String allowsetpublishdate;
//            private String allowsetreadperm;
//            private String allowshare;
//            private String allowsharemod;
//            private String allowsigbbcode;
//            private String allowsigimgcode;
//            private String allowspacediybbcode;
//            private String allowspacediyhtml;
//            private String allowspacediyimgcode;
//            private String allowstat;
//            private String allowstatdata;
//            private String allowtransfer;
//            private String allowupload;
//            private String allowuploadmod;
//            private String allowviewvideophoto;
//            private String allowvisit;
//            private String allowvote;
//            private String groupid;
//            private String grouptitle;
//            private List<?> allowthreadplugin;
//
//            public String getAllowanonymous() {
//                return allowanonymous;
//            }
//
//            public void setAllowanonymous(String allowanonymous) {
//                this.allowanonymous = allowanonymous;
//            }
//
//            public String getAllowat() {
//                return allowat;
//            }
//
//            public void setAllowat(String allowat) {
//                this.allowat = allowat;
//            }
//
//            public String getAllowbegincode() {
//                return allowbegincode;
//            }
//
//            public void setAllowbegincode(String allowbegincode) {
//                this.allowbegincode = allowbegincode;
//            }
//
//            public String getAllowblog() {
//                return allowblog;
//            }
//
//            public void setAllowblog(String allowblog) {
//                this.allowblog = allowblog;
//            }
//
//            public String getAllowblogmod() {
//                return allowblogmod;
//            }
//
//            public void setAllowblogmod(String allowblogmod) {
//                this.allowblogmod = allowblogmod;
//            }
//
//            public String getAllowbuildgroup() {
//                return allowbuildgroup;
//            }
//
//            public void setAllowbuildgroup(String allowbuildgroup) {
//                this.allowbuildgroup = allowbuildgroup;
//            }
//
//            public String getAllowclick() {
//                return allowclick;
//            }
//
//            public void setAllowclick(String allowclick) {
//                this.allowclick = allowclick;
//            }
//
//            public String getAllowcomment() {
//                return allowcomment;
//            }
//
//            public void setAllowcomment(String allowcomment) {
//                this.allowcomment = allowcomment;
//            }
//
//            public String getAllowcommentarticle() {
//                return allowcommentarticle;
//            }
//
//            public void setAllowcommentarticle(String allowcommentarticle) {
//                this.allowcommentarticle = allowcommentarticle;
//            }
//
//            public String getAllowcommentcollection() {
//                return allowcommentcollection;
//            }
//
//            public void setAllowcommentcollection(String allowcommentcollection) {
//                this.allowcommentcollection = allowcommentcollection;
//            }
//
//            public String getAllowcommentitem() {
//                return allowcommentitem;
//            }
//
//            public void setAllowcommentitem(String allowcommentitem) {
//                this.allowcommentitem = allowcommentitem;
//            }
//
//            public String getAllowcommentpost() {
//                return allowcommentpost;
//            }
//
//            public void setAllowcommentpost(String allowcommentpost) {
//                this.allowcommentpost = allowcommentpost;
//            }
//
//            public String getAllowcommentreply() {
//                return allowcommentreply;
//            }
//
//            public void setAllowcommentreply(String allowcommentreply) {
//                this.allowcommentreply = allowcommentreply;
//            }
//
//            public String getAllowcreatecollection() {
//                return allowcreatecollection;
//            }
//
//            public void setAllowcreatecollection(String allowcreatecollection) {
//                this.allowcreatecollection = allowcreatecollection;
//            }
//
//            public String getAllowcss() {
//                return allowcss;
//            }
//
//            public void setAllowcss(String allowcss) {
//                this.allowcss = allowcss;
//            }
//
//            public String getAllowcstatus() {
//                return allowcstatus;
//            }
//
//            public void setAllowcstatus(String allowcstatus) {
//                this.allowcstatus = allowcstatus;
//            }
//
//            public String getAllowdirectpost() {
//                return allowdirectpost;
//            }
//
//            public void setAllowdirectpost(String allowdirectpost) {
//                this.allowdirectpost = allowdirectpost;
//            }
//
//            public String getAllowdoing() {
//                return allowdoing;
//            }
//
//            public void setAllowdoing(String allowdoing) {
//                this.allowdoing = allowdoing;
//            }
//
//            public String getAllowdoingmod() {
//                return allowdoingmod;
//            }
//
//            public void setAllowdoingmod(String allowdoingmod) {
//                this.allowdoingmod = allowdoingmod;
//            }
//
//            public String getAllowdownlocalimg() {
//                return allowdownlocalimg;
//            }
//
//            public void setAllowdownlocalimg(String allowdownlocalimg) {
//                this.allowdownlocalimg = allowdownlocalimg;
//            }
//
//            public String getAllowdownremoteimg() {
//                return allowdownremoteimg;
//            }
//
//            public void setAllowdownremoteimg(String allowdownremoteimg) {
//                this.allowdownremoteimg = allowdownremoteimg;
//            }
//
//            public String getAllowfollowcollection() {
//                return allowfollowcollection;
//            }
//
//            public void setAllowfollowcollection(String allowfollowcollection) {
//                this.allowfollowcollection = allowfollowcollection;
//            }
//
//            public String getAllowfriend() {
//                return allowfriend;
//            }
//
//            public void setAllowfriend(String allowfriend) {
//                this.allowfriend = allowfriend;
//            }
//
//            public String getAllowgetattach() {
//                return allowgetattach;
//            }
//
//            public void setAllowgetattach(String allowgetattach) {
//                this.allowgetattach = allowgetattach;
//            }
//
//            public String getAllowgetimage() {
//                return allowgetimage;
//            }
//
//            public void setAllowgetimage(String allowgetimage) {
//                this.allowgetimage = allowgetimage;
//            }
//
//            public String getAllowgroupdirectpost() {
//                return allowgroupdirectpost;
//            }
//
//            public void setAllowgroupdirectpost(String allowgroupdirectpost) {
//                this.allowgroupdirectpost = allowgroupdirectpost;
//            }
//
//            public String getAllowgroupposturl() {
//                return allowgroupposturl;
//            }
//
//            public void setAllowgroupposturl(String allowgroupposturl) {
//                this.allowgroupposturl = allowgroupposturl;
//            }
//
//            public String getAllowhidecode() {
//                return allowhidecode;
//            }
//
//            public void setAllowhidecode(String allowhidecode) {
//                this.allowhidecode = allowhidecode;
//            }
//
//            public String getAllowhtml() {
//                return allowhtml;
//            }
//
//            public void setAllowhtml(String allowhtml) {
//                this.allowhtml = allowhtml;
//            }
//
//            public String getAllowimgcontent() {
//                return allowimgcontent;
//            }
//
//            public void setAllowimgcontent(String allowimgcontent) {
//                this.allowimgcontent = allowimgcontent;
//            }
//
//            public String getAllowinvisible() {
//                return allowinvisible;
//            }
//
//            public void setAllowinvisible(String allowinvisible) {
//                this.allowinvisible = allowinvisible;
//            }
//
//            public String getAllowinvite() {
//                return allowinvite;
//            }
//
//            public void setAllowinvite(String allowinvite) {
//                this.allowinvite = allowinvite;
//            }
//
//            public String getAllowmagic() {
//                return allowmagic;
//            }
//
//            public void setAllowmagic(String allowmagic) {
//                this.allowmagic = allowmagic;
//            }
//
//            public String getAllowmagics() {
//                return allowmagics;
//            }
//
//            public void setAllowmagics(String allowmagics) {
//                this.allowmagics = allowmagics;
//            }
//
//            public String getAllowmailinvite() {
//                return allowmailinvite;
//            }
//
//            public void setAllowmailinvite(String allowmailinvite) {
//                this.allowmailinvite = allowmailinvite;
//            }
//
//            public String getAllowmediacode() {
//                return allowmediacode;
//            }
//
//            public void setAllowmediacode(String allowmediacode) {
//                this.allowmediacode = allowmediacode;
//            }
//
//            public String getAllowmyop() {
//                return allowmyop;
//            }
//
//            public void setAllowmyop(String allowmyop) {
//                this.allowmyop = allowmyop;
//            }
//
//            public String getAllowpoke() {
//                return allowpoke;
//            }
//
//            public void setAllowpoke(String allowpoke) {
//                this.allowpoke = allowpoke;
//            }
//
//            public String getAllowpost() {
//                return allowpost;
//            }
//
//            public void setAllowpost(String allowpost) {
//                this.allowpost = allowpost;
//            }
//
//            public String getAllowpostactivity() {
//                return allowpostactivity;
//            }
//
//            public void setAllowpostactivity(String allowpostactivity) {
//                this.allowpostactivity = allowpostactivity;
//            }
//
//            public String getAllowpostarticle() {
//                return allowpostarticle;
//            }
//
//            public void setAllowpostarticle(String allowpostarticle) {
//                this.allowpostarticle = allowpostarticle;
//            }
//
//            public String getAllowpostarticlemod() {
//                return allowpostarticlemod;
//            }
//
//            public void setAllowpostarticlemod(String allowpostarticlemod) {
//                this.allowpostarticlemod = allowpostarticlemod;
//            }
//
//            public String getAllowpostattach() {
//                return allowpostattach;
//            }
//
//            public void setAllowpostattach(String allowpostattach) {
//                this.allowpostattach = allowpostattach;
//            }
//
//            public String getAllowpostdebate() {
//                return allowpostdebate;
//            }
//
//            public void setAllowpostdebate(String allowpostdebate) {
//                this.allowpostdebate = allowpostdebate;
//            }
//
//            public String getAllowpostimage() {
//                return allowpostimage;
//            }
//
//            public void setAllowpostimage(String allowpostimage) {
//                this.allowpostimage = allowpostimage;
//            }
//
//            public String getAllowpostpoll() {
//                return allowpostpoll;
//            }
//
//            public void setAllowpostpoll(String allowpostpoll) {
//                this.allowpostpoll = allowpostpoll;
//            }
//
//            public String getAllowpostreward() {
//                return allowpostreward;
//            }
//
//            public void setAllowpostreward(String allowpostreward) {
//                this.allowpostreward = allowpostreward;
//            }
//
//            public String getAllowpostrushreply() {
//                return allowpostrushreply;
//            }
//
//            public void setAllowpostrushreply(String allowpostrushreply) {
//                this.allowpostrushreply = allowpostrushreply;
//            }
//
//            public String getAllowposttag() {
//                return allowposttag;
//            }
//
//            public void setAllowposttag(String allowposttag) {
//                this.allowposttag = allowposttag;
//            }
//
//            public String getAllowposttrade() {
//                return allowposttrade;
//            }
//
//            public void setAllowposttrade(String allowposttrade) {
//                this.allowposttrade = allowposttrade;
//            }
//
//            public String getAllowposturl() {
//                return allowposturl;
//            }
//
//            public void setAllowposturl(String allowposturl) {
//                this.allowposturl = allowposturl;
//            }
//
//            public String getAllowrecommend() {
//                return allowrecommend;
//            }
//
//            public void setAllowrecommend(String allowrecommend) {
//                this.allowrecommend = allowrecommend;
//            }
//
//            public String getAllowreply() {
//                return allowreply;
//            }
//
//            public void setAllowreply(String allowreply) {
//                this.allowreply = allowreply;
//            }
//
//            public String getAllowreplycredit() {
//                return allowreplycredit;
//            }
//
//            public void setAllowreplycredit(String allowreplycredit) {
//                this.allowreplycredit = allowreplycredit;
//            }
//
//            public String getAllowsearch() {
//                return allowsearch;
//            }
//
//            public void setAllowsearch(String allowsearch) {
//                this.allowsearch = allowsearch;
//            }
//
//            public String getAllowsendallpm() {
//                return allowsendallpm;
//            }
//
//            public void setAllowsendallpm(String allowsendallpm) {
//                this.allowsendallpm = allowsendallpm;
//            }
//
//            public String getAllowsendpm() {
//                return allowsendpm;
//            }
//
//            public void setAllowsendpm(String allowsendpm) {
//                this.allowsendpm = allowsendpm;
//            }
//
//            public String getAllowsendpmmaxnum() {
//                return allowsendpmmaxnum;
//            }
//
//            public void setAllowsendpmmaxnum(String allowsendpmmaxnum) {
//                this.allowsendpmmaxnum = allowsendpmmaxnum;
//            }
//
//            public String getAllowsetattachperm() {
//                return allowsetattachperm;
//            }
//
//            public void setAllowsetattachperm(String allowsetattachperm) {
//                this.allowsetattachperm = allowsetattachperm;
//            }
//
//            public String getAllowsetpublishdate() {
//                return allowsetpublishdate;
//            }
//
//            public void setAllowsetpublishdate(String allowsetpublishdate) {
//                this.allowsetpublishdate = allowsetpublishdate;
//            }
//
//            public String getAllowsetreadperm() {
//                return allowsetreadperm;
//            }
//
//            public void setAllowsetreadperm(String allowsetreadperm) {
//                this.allowsetreadperm = allowsetreadperm;
//            }
//
//            public String getAllowshare() {
//                return allowshare;
//            }
//
//            public void setAllowshare(String allowshare) {
//                this.allowshare = allowshare;
//            }
//
//            public String getAllowsharemod() {
//                return allowsharemod;
//            }
//
//            public void setAllowsharemod(String allowsharemod) {
//                this.allowsharemod = allowsharemod;
//            }
//
//            public String getAllowsigbbcode() {
//                return allowsigbbcode;
//            }
//
//            public void setAllowsigbbcode(String allowsigbbcode) {
//                this.allowsigbbcode = allowsigbbcode;
//            }
//
//            public String getAllowsigimgcode() {
//                return allowsigimgcode;
//            }
//
//            public void setAllowsigimgcode(String allowsigimgcode) {
//                this.allowsigimgcode = allowsigimgcode;
//            }
//
//            public String getAllowspacediybbcode() {
//                return allowspacediybbcode;
//            }
//
//            public void setAllowspacediybbcode(String allowspacediybbcode) {
//                this.allowspacediybbcode = allowspacediybbcode;
//            }
//
//            public String getAllowspacediyhtml() {
//                return allowspacediyhtml;
//            }
//
//            public void setAllowspacediyhtml(String allowspacediyhtml) {
//                this.allowspacediyhtml = allowspacediyhtml;
//            }
//
//            public String getAllowspacediyimgcode() {
//                return allowspacediyimgcode;
//            }
//
//            public void setAllowspacediyimgcode(String allowspacediyimgcode) {
//                this.allowspacediyimgcode = allowspacediyimgcode;
//            }
//
//            public String getAllowstat() {
//                return allowstat;
//            }
//
//            public void setAllowstat(String allowstat) {
//                this.allowstat = allowstat;
//            }
//
//            public String getAllowstatdata() {
//                return allowstatdata;
//            }
//
//            public void setAllowstatdata(String allowstatdata) {
//                this.allowstatdata = allowstatdata;
//            }
//
//            public String getAllowtransfer() {
//                return allowtransfer;
//            }
//
//            public void setAllowtransfer(String allowtransfer) {
//                this.allowtransfer = allowtransfer;
//            }
//
//            public String getAllowupload() {
//                return allowupload;
//            }
//
//            public void setAllowupload(String allowupload) {
//                this.allowupload = allowupload;
//            }
//
//            public String getAllowuploadmod() {
//                return allowuploadmod;
//            }
//
//            public void setAllowuploadmod(String allowuploadmod) {
//                this.allowuploadmod = allowuploadmod;
//            }
//
//            public String getAllowviewvideophoto() {
//                return allowviewvideophoto;
//            }
//
//            public void setAllowviewvideophoto(String allowviewvideophoto) {
//                this.allowviewvideophoto = allowviewvideophoto;
//            }
//
//            public String getAllowvisit() {
//                return allowvisit;
//            }
//
//            public void setAllowvisit(String allowvisit) {
//                this.allowvisit = allowvisit;
//            }
//
//            public String getAllowvote() {
//                return allowvote;
//            }
//
//            public void setAllowvote(String allowvote) {
//                this.allowvote = allowvote;
//            }
//
//            public String getGroupid() {
//                return groupid;
//            }
//
//            public void setGroupid(String groupid) {
//                this.groupid = groupid;
//            }
//
//            public String getGrouptitle() {
//                return grouptitle;
//            }
//
//            public void setGrouptitle(String grouptitle) {
//                this.grouptitle = grouptitle;
//            }
//
//            public List<?> getAllowthreadplugin() {
//                return allowthreadplugin;
//            }
//
//            public void setAllowthreadplugin(List<?> allowthreadplugin) {
//                this.allowthreadplugin = allowthreadplugin;
//            }
//        }
//
//        public static class NoticeBean {
//            /**
//             * newmypost : 0
//             * newpm : 0
//             * newprompt : 0
//             * newpush : 0
//             */
//
//            private String newmypost;
//            private String newpm;
//            private String newprompt;
//            private String newpush;
//
//            public String getNewmypost() {
//                return newmypost;
//            }
//
//            public void setNewmypost(String newmypost) {
//                this.newmypost = newmypost;
//            }
//
//            public String getNewpm() {
//                return newpm;
//            }
//
//            public void setNewpm(String newpm) {
//                this.newpm = newpm;
//            }
//
//            public String getNewprompt() {
//                return newprompt;
//            }
//
//            public void setNewprompt(String newprompt) {
//                this.newprompt = newprompt;
//            }
//
//            public String getNewpush() {
//                return newpush;
//            }
//
//            public void setNewpush(String newpush) {
//                this.newpush = newpush;
//            }
//        }
//
////        public static class CatlistBean {
////            /**
////             * fid : 1
////             * forums : ["2","36"]
////             * name : Discuz!
////             */
////
////            private String fid;
////            private String name;
////            private List<String> forums;
////
////            public String getFid() {
////                return fid;
////            }
////
////            public void setFid(String fid) {
////                this.fid = fid;
////            }
////
////            public String getName() {
////                return name;
////            }
////
////            public void setName(String name) {
////                this.name = name;
////            }
////
////            public List<String> getForums() {
////                return forums;
////            }
////
////            public void setForums(List<String> forums) {
////                this.forums = forums;
////            }
////        }
//
//        public static class ForumlistBean {
//            /**
//             * allowpostspecial : 9
//             * allowspecialonly : 0
//             * description :
//             * fid : 2
//             * icon :
//             * name : Discuz!-模板
//             * posts : 12
//             * redirect :
//             * threads : 5
//             * todayposts : 0
//             */
//
//            private String allowpostspecial;
//            private String allowspecialonly;
//            private String description;
//            private String fid;
//            private String icon;
//            private String name;
//            private String posts;
//            private String redirect;
//            private String threads;
//            private String todayposts;
//
//            public String getAllowpostspecial() {
//                return allowpostspecial;
//            }
//
//            public void setAllowpostspecial(String allowpostspecial) {
//                this.allowpostspecial = allowpostspecial;
//            }
//
//            public String getAllowspecialonly() {
//                return allowspecialonly;
//            }
//
//            public void setAllowspecialonly(String allowspecialonly) {
//                this.allowspecialonly = allowspecialonly;
//            }
//
//            public String getDescription() {
//                return description;
//            }
//
//            public void setDescription(String description) {
//                this.description = description;
//            }
//
//            public String getFid() {
//                return fid;
//            }
//
//            public void setFid(String fid) {
//                this.fid = fid;
//            }
//
//            public String getIcon() {
//                return icon;
//            }
//
//            public void setIcon(String icon) {
//                this.icon = icon;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getPosts() {
//                return posts;
//            }
//
//            public void setPosts(String posts) {
//                this.posts = posts;
//            }
//
//            public String getRedirect() {
//                return redirect;
//            }
//
//            public void setRedirect(String redirect) {
//                this.redirect = redirect;
//            }
//
//            public String getThreads() {
//                return threads;
//            }
//
//            public void setThreads(String threads) {
//                this.threads = threads;
//            }
//
//            public String getTodayposts() {
//                return todayposts;
//            }
//
//            public void setTodayposts(String todayposts) {
//                this.todayposts = todayposts;
//            }
//        }
//    }
}
