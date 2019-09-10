package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/7/27.
 */

public class JoinManagerBean {

    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"a9ja_2132_","auth":"657fnisTMx3xKZwUPj4iAGTPx3K2Mg3B3dlkPHxN2/lHJ/POvTI4nMKHtiHbUG2hccsVcHA4fKuwKt633QgP","saltkey":"uuuY279f","member_uid":"1","member_username":"admin","member_avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=1&size=small","groupid":"1","formhash":"43112009","ismoderator":"1","readaccess":"200","notice":{"newpush":"0","newpm":"0","newprompt":"5","newmypost":"2"},"activityapplylist":{"applylist":[{"applyid":"7","tid":"283","username":"admin","uid":"1","message":"哈哈哈爱","verified":"1","dateline":"2019-07-16 15:23","payment":"-1","ufielddata":"<li>QQ&nbsp;&nbsp;:&nbsp;&nbsp;1346464661<\/li><li>真实姓名&nbsp;&nbsp;:&nbsp;&nbsp;金桔<\/li>","dbufielddata":{"qq":{"title":"QQ","value":"1346464661"},"realname":{"title":"真实姓名","value":"金桔"}}}],"activityinfo":{"subject":"Shhdhdhdhdhshsysy[北京]","applynumber":"1","number":"10","days":"2019-8-16 16:03"}}}
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
         * notice : {"newpush":"0","newpm":"0","newprompt":"5","newmypost":"2"}
         * activityapplylist : {"applylist":[{"applyid":"7","tid":"283","username":"admin","uid":"1","message":"哈哈哈爱","verified":"1","dateline":"2019-07-16 15:23","payment":"-1","ufielddata":"<li>QQ&nbsp;&nbsp;:&nbsp;&nbsp;1346464661<\/li><li>真实姓名&nbsp;&nbsp;:&nbsp;&nbsp;金桔<\/li>","dbufielddata":{"qq":{"title":"QQ","value":"1346464661"},"realname":{"title":"真实姓名","value":"金桔"}}}],"activityinfo":{"subject":"Shhdhdhdhdhshsysy[北京]","applynumber":"1","number":"10","days":"2019-8-16 16:03"}}
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
        private ActivityapplylistBean activityapplylist;

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

        public ActivityapplylistBean getActivityapplylist() {
            return activityapplylist;
        }

        public void setActivityapplylist(ActivityapplylistBean activityapplylist) {
            this.activityapplylist = activityapplylist;
        }

        public static class NoticeBean {
            /**
             * newpush : 0
             * newpm : 0
             * newprompt : 5
             * newmypost : 2
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

        public static class ActivityapplylistBean {
            /**
             * applylist : [{"applyid":"7","tid":"283","username":"admin","uid":"1","message":"哈哈哈爱","verified":"1","dateline":"2019-07-16 15:23","payment":"-1","ufielddata":"<li>QQ&nbsp;&nbsp;:&nbsp;&nbsp;1346464661<\/li><li>真实姓名&nbsp;&nbsp;:&nbsp;&nbsp;金桔<\/li>","dbufielddata":{"qq":{"title":"QQ","value":"1346464661"},"realname":{"title":"真实姓名","value":"金桔"}}}]
             * activityinfo : {"subject":"Shhdhdhdhdhshsysy[北京]","applynumber":"1","number":"10","days":"2019-8-16 16:03"}
             */

            private ActivityinfoBean activityinfo;
            private List<ApplylistBean> applylist;

            public ActivityinfoBean getActivityinfo() {
                return activityinfo;
            }

            public void setActivityinfo(ActivityinfoBean activityinfo) {
                this.activityinfo = activityinfo;
            }

            public List<ApplylistBean> getApplylist() {
                return applylist;
            }

            public void setApplylist(List<ApplylistBean> applylist) {
                this.applylist = applylist;
            }

            public static class ActivityinfoBean {
                /**
                 * subject : Shhdhdhdhdhshsysy[北京]
                 * applynumber : 1
                 * number : 10
                 * days : 2019-8-16 16:03
                 */

                private String subject;
                private String applynumber;
                private String number;
                private String days;

                public String getSubject() {
                    return subject;
                }

                public void setSubject(String subject) {
                    this.subject = subject;
                }

                public String getApplynumber() {
                    return applynumber;
                }

                public void setApplynumber(String applynumber) {
                    this.applynumber = applynumber;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getDays() {
                    return days;
                }

                public void setDays(String days) {
                    this.days = days;
                }
            }

            public static class ApplylistBean {
                /**
                 * applyid : 7
                 * tid : 283
                 * username : admin
                 * uid : 1
                 * message : 哈哈哈爱
                 * verified : 1
                 * dateline : 2019-07-16 15:23
                 * payment : -1
                 * ufielddata : <li>QQ&nbsp;&nbsp;:&nbsp;&nbsp;1346464661</li><li>真实姓名&nbsp;&nbsp;:&nbsp;&nbsp;金桔</li>
                 * dbufielddata : {"qq":{"title":"QQ","value":"1346464661"},"realname":{"title":"真实姓名","value":"金桔"}}
                 */

                private String applyid;
                private String tid;
                private String username;
                private String uid;
                private String message;
                private String verified;
                private String dateline;
                private String payment;
                private String ufielddata;
                private DbufielddataBean dbufielddata;

                public String getApplyid() {
                    return applyid;
                }

                public void setApplyid(String applyid) {
                    this.applyid = applyid;
                }

                public String getTid() {
                    return tid;
                }

                public void setTid(String tid) {
                    this.tid = tid;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public String getMessage() {
                    return message;
                }

                public void setMessage(String message) {
                    this.message = message;
                }

                public String getVerified() {
                    return verified;
                }

                public void setVerified(String verified) {
                    this.verified = verified;
                }

                public String getDateline() {
                    return dateline;
                }

                public void setDateline(String dateline) {
                    this.dateline = dateline;
                }

                public String getPayment() {
                    return payment;
                }

                public void setPayment(String payment) {
                    this.payment = payment;
                }

                public String getUfielddata() {
                    return ufielddata;
                }

                public void setUfielddata(String ufielddata) {
                    this.ufielddata = ufielddata;
                }

                public DbufielddataBean getDbufielddata() {
                    return dbufielddata;
                }

                public void setDbufielddata(DbufielddataBean dbufielddata) {
                    this.dbufielddata = dbufielddata;
                }

                public static class DbufielddataBean {
                    /**
                     * qq : {"title":"QQ","value":"1346464661"}
                     * realname : {"title":"真实姓名","value":"金桔"}
                     */

                    private QqBean qq;
                    private RealnameBean realname;

                    public QqBean getQq() {
                        return qq;
                    }

                    public void setQq(QqBean qq) {
                        this.qq = qq;
                    }

                    public RealnameBean getRealname() {
                        return realname;
                    }

                    public void setRealname(RealnameBean realname) {
                        this.realname = realname;
                    }

                    public static class QqBean {
                        /**
                         * title : QQ
                         * value : 1346464661
                         */

                        private String title;
                        private String value;

                        public String getTitle() {
                            return title;
                        }

                        public void setTitle(String title) {
                            this.title = title;
                        }

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }
                    }

                    public static class RealnameBean {
                        /**
                         * title : 真实姓名
                         * value : 金桔
                         */

                        private String title;
                        private String value;

                        public String getTitle() {
                            return title;
                        }

                        public void setTitle(String title) {
                            this.title = title;
                        }

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }
                    }
                }
            }
        }
    }
}
