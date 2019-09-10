package cn.tencent.DiscuzMob.model;

/**
 * Created by cg on 2017/4/25.
 */

public class JurisdictionBean {

    /**
     * Version : 1
     * Charset : UTF-8
     * Variables : {"cookiepre":"68nN_2132_","auth":"0884YeR0dq2kuHODgnQOmB2SLGzvaWZAsaFSAcW47bxqTjQuJdleHTWhkXZUX5InMMx1ThD2WSri3qC0WWrJ","saltkey":"zQTHB9ht","member_uid":"7","member_username":"陈国","member_avatar":"http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg","member_conisbind":"0","member_weixinisbind":"0","member_loginstatus":"0","groupid":"10","formhash":"8d1c9b1e","ismoderator":"","readaccess":"10","notice":{"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"},"allowperm":{"allowpost":"1","allowreply":"1","allowupload":{"jpg":"-1","jpeg":"-1","gif":"-1","png":"-1","mp3":"0","txt":"0","zip":"-1","rar":"-1","pdf":"-1"},"attachremain":{"size":"-1","count":"-1"},"uploadhash":"8cd6240b0d2728a90f33d0e1d580191f"},"tid":null,"pid":"0"}
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
         * auth : 0884YeR0dq2kuHODgnQOmB2SLGzvaWZAsaFSAcW47bxqTjQuJdleHTWhkXZUX5InMMx1ThD2WSri3qC0WWrJ
         * saltkey : zQTHB9ht
         * member_uid : 7
         * member_username : 陈国
         * member_avatar : http://10.0.6.58/uc_server/data/avatar/000/00/00/07_avatar_small.jpg
         * member_conisbind : 0
         * member_weixinisbind : 0
         * member_loginstatus : 0
         * groupid : 10
         * formhash : 8d1c9b1e
         * ismoderator :
         * readaccess : 10
         * notice : {"newpush":"0","newpm":"0","newprompt":"0","newmypost":"0"}
         * allowperm : {"allowpost":"1","allowreply":"1","allowupload":{"jpg":"-1","jpeg":"-1","gif":"-1","png":"-1","mp3":"0","txt":"0","zip":"-1","rar":"-1","pdf":"-1"},"attachremain":{"size":"-1","count":"-1"},"uploadhash":"8cd6240b0d2728a90f33d0e1d580191f"}
         * tid : null
         * pid : 0
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
        private AllowpermBean allowperm;
        private Object tid;
        private String pid;

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

        public AllowpermBean getAllowperm() {
            return allowperm;
        }

        public void setAllowperm(AllowpermBean allowperm) {
            this.allowperm = allowperm;
        }

        public Object getTid() {
            return tid;
        }

        public void setTid(Object tid) {
            this.tid = tid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
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

        public static class AllowpermBean {
            /**
             * allowpost : 1
             * allowreply : 1
             * allowupload : {"jpg":"-1","jpeg":"-1","gif":"-1","png":"-1","mp3":"0","txt":"0","zip":"-1","rar":"-1","pdf":"-1"}
             * attachremain : {"size":"-1","count":"-1"}
             * uploadhash : 8cd6240b0d2728a90f33d0e1d580191f
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
                 * mp3 : 0
                 * txt : 0
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
    }
}
