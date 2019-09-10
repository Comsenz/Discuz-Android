package cn.tencent.DiscuzMob.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityThreadBean {

    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"a9ja_2132_","auth":"657fnisTMx3xKZwUPj4iAGTPx3K2Mg3B3dlkPHxN2/lHJ/POvTI4nMKHtiHbUG2hccsVcHA4fKuwKt633QgP","saltkey":"uuuY279f","member_uid":"1","member_username":"admin","member_avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=1&size=small","groupid":"1","formhash":"43112009","ismoderator":"1","readaccess":"200","notice":{"newpush":"0","newpm":"0","newprompt":"5","newmypost":"0"},"allowperm":{"allowpost":"1","allowreply":"1","allowupload":{"jpg":"-1","jpeg":"-1","gif":"-1","png":"-1","mp3":"-1","txt":"-1","zip":"-1","rar":"-1","pdf":"-1"},"attachremain":{"size":"-1","count":"-1"},"uploadhash":"d2372c4e17eeff785ea8431604bc54f9"},"thread":{"tid":"309","fid":"94","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"admin","authorid":"1","subject":"哈哈哈","dateline":"1563271225","lastpost":"2019-7-16 18:00","lastposter":"admin","views":"2","replies":"0","displayorder":"0","highlight":"0","digest":"0","rate":"0","special":"4","attachment":"0","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"0","status":"1056","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"-1","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","threadtable":"forum_thread","threadtableid":"0","posttable":"forum_post","allreplies":"0","is_archived":"","archiveid":"0","subjectenc":"%E5%93%88%E5%93%88%E5%93%88","short_subject":"哈哈哈","replycredit_rule":{"extcreditstype":"2"},"starttime":"1563271225","remaintime":"","recommendlevel":"0","heatlevel":"0","relay":"0","forumnames":"测试测试车车车车车车车车二车场额","ordertype":"0","favorited":"0","recommend":"0"},"fid":"94","postlist":[{"pid":"532","tid":"309","first":"1","author":"admin","authorid":"1","dateline":"3&nbsp;分钟前","message":"哈哈哈","anonymous":"0","attachment":"0","status":"264","replycredit":"0","position":"1","username":"admin","adminid":"1","groupid":"1","memberstatus":"0","number":"1","dbdateline":"1563271225","groupiconid":"admin"}],"allowpostcomment":["1"],"comments":[],"commentcount":[],"ppp":"5","setting_rewriterule":null,"setting_rewritestatus":"","forum_threadpay":"","cache_custominfo_postno":["<sup>#<\/sup>","楼主","沙发","板凳","地板"],"special_activity":{"tid":"309","uid":"1","aid":"0","cost":"20","starttimefrom":"1&nbsp;小时前","starttimeto":"2019-7-18 18:15","place":"北京","class":"朋友聚会","gender":"0","number":"10","applynumber":"0","expiration":"2019-7-18 18:15","ufield":{"userfield":"","extfield":[]},"credit":"100","thumb":"","attachurl":"","allapplynum":"0","creditcost":"100 威望","joinfield":[],"userfield":null,"extfield":null,"basefield":[],"closed":"0","status":"join","button":"join"},"forum":{"password":"0","threadtypes":{"required":"","listable":"1","prefix":"0","types":{"13":"aaaaaaa   aaaaaaaaaa","14":"[color]哈哈哈[color]","15":"ccccccccccccccccccc","16":"dddddddddddddddd"},"icons":{"13":"","14":"","15":"","16":""},"moderators":{"13":null,"14":null,"15":null,"16":null},"dbtypes":{"13":"aaaaaaa   aaaaaaaaaa","14":"[color]哈哈哈[color]","15":"ccccccccccccccccccc","16":"dddddddddddddddd"}}}}
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
         * auth : 657fnisTMx3xKZwUPj4iAGTPx3K2Mg3B3dlkPHxN2/lHJ/POvTI4nMKHtiHbUG2hccsVcHA4fKuwKt633QgP
         * saltkey : uuuY279f
         * member_uid : 1
         * member_username : admin
         * member_avatar : https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=1&size=small
         * groupid : 1
         * formhash : 43112009
         * ismoderator : 1
         * readaccess : 200
         * notice : {"newpush":"0","newpm":"0","newprompt":"5","newmypost":"0"}
         * allowperm : {"allowpost":"1","allowreply":"1","allowupload":{"jpg":"-1","jpeg":"-1","gif":"-1","png":"-1","mp3":"-1","txt":"-1","zip":"-1","rar":"-1","pdf":"-1"},"attachremain":{"size":"-1","count":"-1"},"uploadhash":"d2372c4e17eeff785ea8431604bc54f9"}
         * thread : {"tid":"309","fid":"94","posttableid":"0","typeid":"0","sortid":"0","readperm":"0","price":"0","author":"admin","authorid":"1","subject":"哈哈哈","dateline":"1563271225","lastpost":"2019-7-16 18:00","lastposter":"admin","views":"2","replies":"0","displayorder":"0","highlight":"0","digest":"0","rate":"0","special":"4","attachment":"0","moderated":"0","closed":"0","stickreply":"0","recommends":"0","recommend_add":"0","recommend_sub":"0","heats":"0","status":"1056","isgroup":"0","favtimes":"0","sharetimes":"0","stamp":"-1","icon":"-1","pushedaid":"0","cover":"0","replycredit":"0","relatebytag":"0","maxposition":"1","bgcolor":"","comments":"0","hidden":"0","threadtable":"forum_thread","threadtableid":"0","posttable":"forum_post","allreplies":"0","is_archived":"","archiveid":"0","subjectenc":"%E5%93%88%E5%93%88%E5%93%88","short_subject":"哈哈哈","replycredit_rule":{"extcreditstype":"2"},"starttime":"1563271225","remaintime":"","recommendlevel":"0","heatlevel":"0","relay":"0","forumnames":"测试测试车车车车车车车车二车场额","ordertype":"0","favorited":"0","recommend":"0"}
         * fid : 94
         * postlist : [{"pid":"532","tid":"309","first":"1","author":"admin","authorid":"1","dateline":"3&nbsp;分钟前","message":"哈哈哈","anonymous":"0","attachment":"0","status":"264","replycredit":"0","position":"1","username":"admin","adminid":"1","groupid":"1","memberstatus":"0","number":"1","dbdateline":"1563271225","groupiconid":"admin"}]
         * allowpostcomment : ["1"]
         * comments : []
         * commentcount : []
         * ppp : 5
         * setting_rewriterule : null
         * setting_rewritestatus :
         * forum_threadpay :
         * cache_custominfo_postno : ["<sup>#<\/sup>","楼主","沙发","板凳","地板"]
         * special_activity : {"tid":"309","uid":"1","aid":"0","cost":"20","starttimefrom":"1&nbsp;小时前","starttimeto":"2019-7-18 18:15","place":"北京","class":"朋友聚会","gender":"0","number":"10","applynumber":"0","expiration":"2019-7-18 18:15","ufield":{"userfield":"","extfield":[]},"credit":"100","thumb":"","attachurl":"","allapplynum":"0","creditcost":"100 威望","joinfield":[],"userfield":null,"extfield":null,"basefield":[],"closed":"0","status":"join","button":"join"}
         * forum : {"password":"0","threadtypes":{"required":"","listable":"1","prefix":"0","types":{"13":"aaaaaaa   aaaaaaaaaa","14":"[color]哈哈哈[color]","15":"ccccccccccccccccccc","16":"dddddddddddddddd"},"icons":{"13":"","14":"","15":"","16":""},"moderators":{"13":null,"14":null,"15":null,"16":null},"dbtypes":{"13":"aaaaaaa   aaaaaaaaaa","14":"[color]哈哈哈[color]","15":"ccccccccccccccccccc","16":"dddddddddddddddd"}}}
         */

        private String cookiepre;
        private String auth;
        private String saltkey;
        private String member_uid;
        private String member_username;
        private String member_avatar;
        private String groupid;
        private String formhash;
        private String ismoderator;
        private String readaccess;
        private NoticeBean notice;
        private AllowpermBean allowperm;
        private ThreadBean thread;
        private String fid;
        private String ppp;
        private Object setting_rewriterule;
        private String setting_rewritestatus;
        private String forum_threadpay;
        private SpecialActivityBean special_activity;
        private ForumBean forum;
        private List<PostlistBean> postlist;
        private List<String> allowpostcomment;
        private List<?> comments;
        private List<?> commentcount;
        private List<String> cache_custominfo_postno;

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

        public AllowpermBean getAllowperm() {
            return allowperm;
        }

        public void setAllowperm(AllowpermBean allowperm) {
            this.allowperm = allowperm;
        }

        public ThreadBean getThread() {
            return thread;
        }

        public void setThread(ThreadBean thread) {
            this.thread = thread;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getPpp() {
            return ppp;
        }

        public void setPpp(String ppp) {
            this.ppp = ppp;
        }

        public Object getSetting_rewriterule() {
            return setting_rewriterule;
        }

        public void setSetting_rewriterule(Object setting_rewriterule) {
            this.setting_rewriterule = setting_rewriterule;
        }

        public String getSetting_rewritestatus() {
            return setting_rewritestatus;
        }

        public void setSetting_rewritestatus(String setting_rewritestatus) {
            this.setting_rewritestatus = setting_rewritestatus;
        }

        public String getForum_threadpay() {
            return forum_threadpay;
        }

        public void setForum_threadpay(String forum_threadpay) {
            this.forum_threadpay = forum_threadpay;
        }

        public SpecialActivityBean getSpecial_activity() {
            return special_activity;
        }

        public void setSpecial_activity(SpecialActivityBean special_activity) {
            this.special_activity = special_activity;
        }

        public ForumBean getForum() {
            return forum;
        }

        public void setForum(ForumBean forum) {
            this.forum = forum;
        }

        public List<PostlistBean> getPostlist() {
            return postlist;
        }

        public void setPostlist(List<PostlistBean> postlist) {
            this.postlist = postlist;
        }

        public List<String> getAllowpostcomment() {
            return allowpostcomment;
        }

        public void setAllowpostcomment(List<String> allowpostcomment) {
            this.allowpostcomment = allowpostcomment;
        }

        public List<?> getComments() {
            return comments;
        }

        public void setComments(List<?> comments) {
            this.comments = comments;
        }

        public List<?> getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(List<?> commentcount) {
            this.commentcount = commentcount;
        }

        public List<String> getCache_custominfo_postno() {
            return cache_custominfo_postno;
        }

        public void setCache_custominfo_postno(List<String> cache_custominfo_postno) {
            this.cache_custominfo_postno = cache_custominfo_postno;
        }

        public static class NoticeBean {
            /**
             * newpush : 0
             * newpm : 0
             * newprompt : 5
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

        public static class AllowpermBean {
            /**
             * allowpost : 1
             * allowreply : 1
             * allowupload : {"jpg":"-1","jpeg":"-1","gif":"-1","png":"-1","mp3":"-1","txt":"-1","zip":"-1","rar":"-1","pdf":"-1"}
             * attachremain : {"size":"-1","count":"-1"}
             * uploadhash : d2372c4e17eeff785ea8431604bc54f9
             */

            private String allowpost;
            private String allowreply;
            private AllowuploadBean allowupload;
            private AttachremainBean attachremain;
            private String uploadhash;

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

            public AllowuploadBean getAllowupload() {
                return allowupload;
            }

            public void setAllowupload(AllowuploadBean allowupload) {
                this.allowupload = allowupload;
            }

            public AttachremainBean getAttachremain() {
                return attachremain;
            }

            public void setAttachremain(AttachremainBean attachremain) {
                this.attachremain = attachremain;
            }

            public String getUploadhash() {
                return uploadhash;
            }

            public void setUploadhash(String uploadhash) {
                this.uploadhash = uploadhash;
            }

            public static class AllowuploadBean {
                /**
                 * jpg : -1
                 * jpeg : -1
                 * gif : -1
                 * png : -1
                 * mp3 : -1
                 * txt : -1
                 * zip : -1
                 * rar : -1
                 * pdf : -1
                 */

                private String jpg;
                private String jpeg;
                private String gif;
                private String png;
                private String mp3;
                private String txt;
                private String zip;
                private String rar;
                private String pdf;

                public String getJpg() {
                    return jpg;
                }

                public void setJpg(String jpg) {
                    this.jpg = jpg;
                }

                public String getJpeg() {
                    return jpeg;
                }

                public void setJpeg(String jpeg) {
                    this.jpeg = jpeg;
                }

                public String getGif() {
                    return gif;
                }

                public void setGif(String gif) {
                    this.gif = gif;
                }

                public String getPng() {
                    return png;
                }

                public void setPng(String png) {
                    this.png = png;
                }

                public String getMp3() {
                    return mp3;
                }

                public void setMp3(String mp3) {
                    this.mp3 = mp3;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                public String getZip() {
                    return zip;
                }

                public void setZip(String zip) {
                    this.zip = zip;
                }

                public String getRar() {
                    return rar;
                }

                public void setRar(String rar) {
                    this.rar = rar;
                }

                public String getPdf() {
                    return pdf;
                }

                public void setPdf(String pdf) {
                    this.pdf = pdf;
                }
            }

            public static class AttachremainBean {
                /**
                 * size : -1
                 * count : -1
                 */

                private String size;
                private String count;

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }
            }
        }

        public static class ThreadBean {
            /**
             * tid : 309
             * fid : 94
             * posttableid : 0
             * typeid : 0
             * sortid : 0
             * readperm : 0
             * price : 0
             * author : admin
             * authorid : 1
             * subject : 哈哈哈
             * dateline : 1563271225
             * lastpost : 2019-7-16 18:00
             * lastposter : admin
             * views : 2
             * replies : 0
             * displayorder : 0
             * highlight : 0
             * digest : 0
             * rate : 0
             * special : 4
             * attachment : 0
             * moderated : 0
             * closed : 0
             * stickreply : 0
             * recommends : 0
             * recommend_add : 0
             * recommend_sub : 0
             * heats : 0
             * status : 1056
             * isgroup : 0
             * favtimes : 0
             * sharetimes : 0
             * stamp : -1
             * icon : -1
             * pushedaid : 0
             * cover : 0
             * replycredit : 0
             * relatebytag : 0
             * maxposition : 1
             * bgcolor :
             * comments : 0
             * hidden : 0
             * threadtable : forum_thread
             * threadtableid : 0
             * posttable : forum_post
             * allreplies : 0
             * is_archived :
             * archiveid : 0
             * subjectenc : %E5%93%88%E5%93%88%E5%93%88
             * short_subject : 哈哈哈
             * replycredit_rule : {"extcreditstype":"2"}
             * starttime : 1563271225
             * remaintime :
             * recommendlevel : 0
             * heatlevel : 0
             * relay : 0
             * forumnames : 测试测试车车车车车车车车二车场额
             * ordertype : 0
             * favorited : 0
             * recommend : 0
             */

            private String tid;
            private String fid;
            private String posttableid;
            private String typeid;
            private String sortid;
            private String readperm;
            private String price;
            private String author;
            private String authorid;
            private String subject;
            private String dateline;
            private String lastpost;
            private String lastposter;
            private String views;
            private String replies;
            private String displayorder;
            private String highlight;
            private String digest;
            private String rate;
            private String special;
            private String attachment;
            private String moderated;
            private String closed;
            private String stickreply;
            private String recommends;
            private String recommend_add;
            private String recommend_sub;
            private String heats;
            private String status;
            private String isgroup;
            private String favtimes;
            private String sharetimes;
            private String stamp;
            private String icon;
            private String pushedaid;
            private String cover;
            private String replycredit;
            private String relatebytag;
            private String maxposition;
            private String bgcolor;
            private String comments;
            private String hidden;
            private String threadtable;
            private String threadtableid;
            private String posttable;
            private String allreplies;
            private String is_archived;
            private String archiveid;
            private String subjectenc;
            private String short_subject;
            private ReplycreditRuleBean replycredit_rule;
            private String starttime;
            private String remaintime;
            private String recommendlevel;
            private String heatlevel;
            private String relay;
            private String forumnames;
            private String ordertype;
            private String favorited;
            private String recommend;

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getPosttableid() {
                return posttableid;
            }

            public void setPosttableid(String posttableid) {
                this.posttableid = posttableid;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public String getSortid() {
                return sortid;
            }

            public void setSortid(String sortid) {
                this.sortid = sortid;
            }

            public String getReadperm() {
                return readperm;
            }

            public void setReadperm(String readperm) {
                this.readperm = readperm;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getDateline() {
                return dateline;
            }

            public void setDateline(String dateline) {
                this.dateline = dateline;
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

            public String getViews() {
                return views;
            }

            public void setViews(String views) {
                this.views = views;
            }

            public String getReplies() {
                return replies;
            }

            public void setReplies(String replies) {
                this.replies = replies;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public String getHighlight() {
                return highlight;
            }

            public void setHighlight(String highlight) {
                this.highlight = highlight;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getAttachment() {
                return attachment;
            }

            public void setAttachment(String attachment) {
                this.attachment = attachment;
            }

            public String getModerated() {
                return moderated;
            }

            public void setModerated(String moderated) {
                this.moderated = moderated;
            }

            public String getClosed() {
                return closed;
            }

            public void setClosed(String closed) {
                this.closed = closed;
            }

            public String getStickreply() {
                return stickreply;
            }

            public void setStickreply(String stickreply) {
                this.stickreply = stickreply;
            }

            public String getRecommends() {
                return recommends;
            }

            public void setRecommends(String recommends) {
                this.recommends = recommends;
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

            public String getHeats() {
                return heats;
            }

            public void setHeats(String heats) {
                this.heats = heats;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIsgroup() {
                return isgroup;
            }

            public void setIsgroup(String isgroup) {
                this.isgroup = isgroup;
            }

            public String getFavtimes() {
                return favtimes;
            }

            public void setFavtimes(String favtimes) {
                this.favtimes = favtimes;
            }

            public String getSharetimes() {
                return sharetimes;
            }

            public void setSharetimes(String sharetimes) {
                this.sharetimes = sharetimes;
            }

            public String getStamp() {
                return stamp;
            }

            public void setStamp(String stamp) {
                this.stamp = stamp;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getPushedaid() {
                return pushedaid;
            }

            public void setPushedaid(String pushedaid) {
                this.pushedaid = pushedaid;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getReplycredit() {
                return replycredit;
            }

            public void setReplycredit(String replycredit) {
                this.replycredit = replycredit;
            }

            public String getRelatebytag() {
                return relatebytag;
            }

            public void setRelatebytag(String relatebytag) {
                this.relatebytag = relatebytag;
            }

            public String getMaxposition() {
                return maxposition;
            }

            public void setMaxposition(String maxposition) {
                this.maxposition = maxposition;
            }

            public String getBgcolor() {
                return bgcolor;
            }

            public void setBgcolor(String bgcolor) {
                this.bgcolor = bgcolor;
            }

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public String getHidden() {
                return hidden;
            }

            public void setHidden(String hidden) {
                this.hidden = hidden;
            }

            public String getThreadtable() {
                return threadtable;
            }

            public void setThreadtable(String threadtable) {
                this.threadtable = threadtable;
            }

            public String getThreadtableid() {
                return threadtableid;
            }

            public void setThreadtableid(String threadtableid) {
                this.threadtableid = threadtableid;
            }

            public String getPosttable() {
                return posttable;
            }

            public void setPosttable(String posttable) {
                this.posttable = posttable;
            }

            public String getAllreplies() {
                return allreplies;
            }

            public void setAllreplies(String allreplies) {
                this.allreplies = allreplies;
            }

            public String getIs_archived() {
                return is_archived;
            }

            public void setIs_archived(String is_archived) {
                this.is_archived = is_archived;
            }

            public String getArchiveid() {
                return archiveid;
            }

            public void setArchiveid(String archiveid) {
                this.archiveid = archiveid;
            }

            public String getSubjectenc() {
                return subjectenc;
            }

            public void setSubjectenc(String subjectenc) {
                this.subjectenc = subjectenc;
            }

            public String getShort_subject() {
                return short_subject;
            }

            public void setShort_subject(String short_subject) {
                this.short_subject = short_subject;
            }

            public ReplycreditRuleBean getReplycredit_rule() {
                return replycredit_rule;
            }

            public void setReplycredit_rule(ReplycreditRuleBean replycredit_rule) {
                this.replycredit_rule = replycredit_rule;
            }

            public String getStarttime() {
                return starttime;
            }

            public void setStarttime(String starttime) {
                this.starttime = starttime;
            }

            public String getRemaintime() {
                return remaintime;
            }

            public void setRemaintime(String remaintime) {
                this.remaintime = remaintime;
            }

            public String getRecommendlevel() {
                return recommendlevel;
            }

            public void setRecommendlevel(String recommendlevel) {
                this.recommendlevel = recommendlevel;
            }

            public String getHeatlevel() {
                return heatlevel;
            }

            public void setHeatlevel(String heatlevel) {
                this.heatlevel = heatlevel;
            }

            public String getRelay() {
                return relay;
            }

            public void setRelay(String relay) {
                this.relay = relay;
            }

            public String getForumnames() {
                return forumnames;
            }

            public void setForumnames(String forumnames) {
                this.forumnames = forumnames;
            }

            public String getOrdertype() {
                return ordertype;
            }

            public void setOrdertype(String ordertype) {
                this.ordertype = ordertype;
            }

            public String getFavorited() {
                return favorited;
            }

            public void setFavorited(String favorited) {
                this.favorited = favorited;
            }

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public static class ReplycreditRuleBean {
                /**
                 * extcreditstype : 2
                 */

                private String extcreditstype;

                public String getExtcreditstype() {
                    return extcreditstype;
                }

                public void setExtcreditstype(String extcreditstype) {
                    this.extcreditstype = extcreditstype;
                }
            }
        }

        public static class SpecialActivityBean {
            /**
             * tid : 309
             * uid : 1
             * aid : 0
             * cost : 20
             * starttimefrom : 1&nbsp;小时前
             * starttimeto : 2019-7-18 18:15
             * place : 北京
             * class : 朋友聚会
             * gender : 0
             * number : 10
             * applynumber : 0
             * expiration : 2019-7-18 18:15
             * ufield : {"userfield":"","extfield":[]}
             * credit : 100
             * thumb :
             * attachurl :
             * allapplynum : 0
             * creditcost : 100 威望
             * joinfield : []
             * userfield : null
             * extfield : null
             * basefield : []
             * closed : 0
             * status : join
             * button : join
             */

            private String tid;
            private String uid;
            private String aid;
            private String cost;
            private String starttimefrom;
            private String starttimeto;
            private String place;
            @SerializedName("class")
            private String classX;
            private String gender;
            private String number;
            private String applynumber;
            private String expiration;
            private UfieldBean ufield;
            private String credit;
            private String thumb;
            private String attachurl;
            private String allapplynum;
            private String creditcost;
            private Object userfield;
            private Object extfield;
            private String closed;
            private String status;
            private String button;
            private List<?> joinfield;
            private List<?> basefield;

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public String getStarttimefrom() {
                return starttimefrom;
            }

            public void setStarttimefrom(String starttimefrom) {
                this.starttimefrom = starttimefrom;
            }

            public String getStarttimeto() {
                return starttimeto;
            }

            public void setStarttimeto(String starttimeto) {
                this.starttimeto = starttimeto;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getClassX() {
                return classX;
            }

            public void setClassX(String classX) {
                this.classX = classX;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getApplynumber() {
                return applynumber;
            }

            public void setApplynumber(String applynumber) {
                this.applynumber = applynumber;
            }

            public String getExpiration() {
                return expiration;
            }

            public void setExpiration(String expiration) {
                this.expiration = expiration;
            }

            public UfieldBean getUfield() {
                return ufield;
            }

            public void setUfield(UfieldBean ufield) {
                this.ufield = ufield;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getAttachurl() {
                return attachurl;
            }

            public void setAttachurl(String attachurl) {
                this.attachurl = attachurl;
            }

            public String getAllapplynum() {
                return allapplynum;
            }

            public void setAllapplynum(String allapplynum) {
                this.allapplynum = allapplynum;
            }

            public String getCreditcost() {
                return creditcost;
            }

            public void setCreditcost(String creditcost) {
                this.creditcost = creditcost;
            }

            public Object getUserfield() {
                return userfield;
            }

            public void setUserfield(Object userfield) {
                this.userfield = userfield;
            }

            public Object getExtfield() {
                return extfield;
            }

            public void setExtfield(Object extfield) {
                this.extfield = extfield;
            }

            public String getClosed() {
                return closed;
            }

            public void setClosed(String closed) {
                this.closed = closed;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getButton() {
                return button;
            }

            public void setButton(String button) {
                this.button = button;
            }

            public List<?> getJoinfield() {
                return joinfield;
            }

            public void setJoinfield(List<?> joinfield) {
                this.joinfield = joinfield;
            }

            public List<?> getBasefield() {
                return basefield;
            }

            public void setBasefield(List<?> basefield) {
                this.basefield = basefield;
            }

            public static class UfieldBean {
                /**
                 * userfield :
                 * extfield : []
                 */

                private String userfield;
                private List<?> extfield;

                public String getUserfield() {
                    return userfield;
                }

                public void setUserfield(String userfield) {
                    this.userfield = userfield;
                }

                public List<?> getExtfield() {
                    return extfield;
                }

                public void setExtfield(List<?> extfield) {
                    this.extfield = extfield;
                }
            }
        }

        public static class ForumBean {
            /**
             * password : 0
             * threadtypes : {"required":"","listable":"1","prefix":"0","types":{"13":"aaaaaaa   aaaaaaaaaa","14":"[color]哈哈哈[color]","15":"ccccccccccccccccccc","16":"dddddddddddddddd"},"icons":{"13":"","14":"","15":"","16":""},"moderators":{"13":null,"14":null,"15":null,"16":null},"dbtypes":{"13":"aaaaaaa   aaaaaaaaaa","14":"[color]哈哈哈[color]","15":"ccccccccccccccccccc","16":"dddddddddddddddd"}}
             */

            private String password;
            private ThreadtypesBean threadtypes;

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public ThreadtypesBean getThreadtypes() {
                return threadtypes;
            }

            public void setThreadtypes(ThreadtypesBean threadtypes) {
                this.threadtypes = threadtypes;
            }

            public static class ThreadtypesBean {
                /**
                 * required :
                 * listable : 1
                 * prefix : 0
                 * types : {"13":"aaaaaaa   aaaaaaaaaa","14":"[color]哈哈哈[color]","15":"ccccccccccccccccccc","16":"dddddddddddddddd"}
                 * icons : {"13":"","14":"","15":"","16":""}
                 * moderators : {"13":null,"14":null,"15":null,"16":null}
                 * dbtypes : {"13":"aaaaaaa   aaaaaaaaaa","14":"[color]哈哈哈[color]","15":"ccccccccccccccccccc","16":"dddddddddddddddd"}
                 */

                private String required;
                private String listable;
                private String prefix;
                private TypesBean types;
                private IconsBean icons;
                private ModeratorsBean moderators;
                private DbtypesBean dbtypes;

                public String getRequired() {
                    return required;
                }

                public void setRequired(String required) {
                    this.required = required;
                }

                public String getListable() {
                    return listable;
                }

                public void setListable(String listable) {
                    this.listable = listable;
                }

                public String getPrefix() {
                    return prefix;
                }

                public void setPrefix(String prefix) {
                    this.prefix = prefix;
                }

                public TypesBean getTypes() {
                    return types;
                }

                public void setTypes(TypesBean types) {
                    this.types = types;
                }

                public IconsBean getIcons() {
                    return icons;
                }

                public void setIcons(IconsBean icons) {
                    this.icons = icons;
                }

                public ModeratorsBean getModerators() {
                    return moderators;
                }

                public void setModerators(ModeratorsBean moderators) {
                    this.moderators = moderators;
                }

                public DbtypesBean getDbtypes() {
                    return dbtypes;
                }

                public void setDbtypes(DbtypesBean dbtypes) {
                    this.dbtypes = dbtypes;
                }

                public static class TypesBean {
                    /**
                     * 13 : aaaaaaa   aaaaaaaaaa
                     * 14 : [color]哈哈哈[color]
                     * 15 : ccccccccccccccccccc
                     * 16 : dddddddddddddddd
                     */

                    @SerializedName("13")
                    private String _$13;
                    @SerializedName("14")
                    private String _$14;
                    @SerializedName("15")
                    private String _$15;
                    @SerializedName("16")
                    private String _$16;

                    public String get_$13() {
                        return _$13;
                    }

                    public void set_$13(String _$13) {
                        this._$13 = _$13;
                    }

                    public String get_$14() {
                        return _$14;
                    }

                    public void set_$14(String _$14) {
                        this._$14 = _$14;
                    }

                    public String get_$15() {
                        return _$15;
                    }

                    public void set_$15(String _$15) {
                        this._$15 = _$15;
                    }

                    public String get_$16() {
                        return _$16;
                    }

                    public void set_$16(String _$16) {
                        this._$16 = _$16;
                    }
                }

                public static class IconsBean {
                    /**
                     * 13 :
                     * 14 :
                     * 15 :
                     * 16 :
                     */

                    @SerializedName("13")
                    private String _$13;
                    @SerializedName("14")
                    private String _$14;
                    @SerializedName("15")
                    private String _$15;
                    @SerializedName("16")
                    private String _$16;

                    public String get_$13() {
                        return _$13;
                    }

                    public void set_$13(String _$13) {
                        this._$13 = _$13;
                    }

                    public String get_$14() {
                        return _$14;
                    }

                    public void set_$14(String _$14) {
                        this._$14 = _$14;
                    }

                    public String get_$15() {
                        return _$15;
                    }

                    public void set_$15(String _$15) {
                        this._$15 = _$15;
                    }

                    public String get_$16() {
                        return _$16;
                    }

                    public void set_$16(String _$16) {
                        this._$16 = _$16;
                    }
                }

                public static class ModeratorsBean {
                    /**
                     * 13 : null
                     * 14 : null
                     * 15 : null
                     * 16 : null
                     */

                    @SerializedName("13")
                    private Object _$13;
                    @SerializedName("14")
                    private Object _$14;
                    @SerializedName("15")
                    private Object _$15;
                    @SerializedName("16")
                    private Object _$16;

                    public Object get_$13() {
                        return _$13;
                    }

                    public void set_$13(Object _$13) {
                        this._$13 = _$13;
                    }

                    public Object get_$14() {
                        return _$14;
                    }

                    public void set_$14(Object _$14) {
                        this._$14 = _$14;
                    }

                    public Object get_$15() {
                        return _$15;
                    }

                    public void set_$15(Object _$15) {
                        this._$15 = _$15;
                    }

                    public Object get_$16() {
                        return _$16;
                    }

                    public void set_$16(Object _$16) {
                        this._$16 = _$16;
                    }
                }

                public static class DbtypesBean {
                    /**
                     * 13 : aaaaaaa   aaaaaaaaaa
                     * 14 : [color]哈哈哈[color]
                     * 15 : ccccccccccccccccccc
                     * 16 : dddddddddddddddd
                     */

                    @SerializedName("13")
                    private String _$13;
                    @SerializedName("14")
                    private String _$14;
                    @SerializedName("15")
                    private String _$15;
                    @SerializedName("16")
                    private String _$16;

                    public String get_$13() {
                        return _$13;
                    }

                    public void set_$13(String _$13) {
                        this._$13 = _$13;
                    }

                    public String get_$14() {
                        return _$14;
                    }

                    public void set_$14(String _$14) {
                        this._$14 = _$14;
                    }

                    public String get_$15() {
                        return _$15;
                    }

                    public void set_$15(String _$15) {
                        this._$15 = _$15;
                    }

                    public String get_$16() {
                        return _$16;
                    }

                    public void set_$16(String _$16) {
                        this._$16 = _$16;
                    }
                }
            }
        }

        public static class PostlistBean {
            /**
             * pid : 532
             * tid : 309
             * first : 1
             * author : admin
             * authorid : 1
             * dateline : 3&nbsp;分钟前
             * message : 哈哈哈
             * anonymous : 0
             * attachment : 0
             * status : 264
             * replycredit : 0
             * position : 1
             * username : admin
             * adminid : 1
             * groupid : 1
             * memberstatus : 0
             * number : 1
             * dbdateline : 1563271225
             * groupiconid : admin
             */

            private String pid;
            private String tid;
            private String first;
            private String author;
            private String authorid;
            private String dateline;
            private String message;
            private String anonymous;
            private String attachment;
            private String status;
            private String replycredit;
            private String position;
            private String username;
            private String adminid;
            private String groupid;
            private String memberstatus;
            private String number;
            private String dbdateline;
            private String groupiconid;

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getFirst() {
                return first;
            }

            public void setFirst(String first) {
                this.first = first;
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

            public String getAnonymous() {
                return anonymous;
            }

            public void setAnonymous(String anonymous) {
                this.anonymous = anonymous;
            }

            public String getAttachment() {
                return attachment;
            }

            public void setAttachment(String attachment) {
                this.attachment = attachment;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getReplycredit() {
                return replycredit;
            }

            public void setReplycredit(String replycredit) {
                this.replycredit = replycredit;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAdminid() {
                return adminid;
            }

            public void setAdminid(String adminid) {
                this.adminid = adminid;
            }

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }

            public String getMemberstatus() {
                return memberstatus;
            }

            public void setMemberstatus(String memberstatus) {
                this.memberstatus = memberstatus;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getDbdateline() {
                return dbdateline;
            }

            public void setDbdateline(String dbdateline) {
                this.dbdateline = dbdateline;
            }

            public String getGroupiconid() {
                return groupiconid;
            }

            public void setGroupiconid(String groupiconid) {
                this.groupiconid = groupiconid;
            }
        }
    }
}
