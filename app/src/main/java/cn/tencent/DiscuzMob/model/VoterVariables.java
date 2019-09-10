package cn.tencent.DiscuzMob.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kurt on 15-6-12.
 */
public class VoterVariables  {
    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"a9ja_2132_","auth":"657fnisTMx3xKZwUPj4iAGTPx3K2Mg3B3dlkPHxN2/lHJ/POvTI4nMKHtiHbUG2hccsVcHA4fKuwKt633QgP","saltkey":"uuuY279f","member_uid":"1","member_username":"admin","member_avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=1&size=small","groupid":"1","formhash":"43112009","ismoderator":"1","readaccess":"200","notice":{"newpush":"0","newpm":"0","newprompt":"5","newmypost":"0"},"viewvote":{"voterlist":[{"uid":"1","email":"admin@admin.com","username":"admin","password":"058e1ac3230d70d2236b808a22049156","status":"0","emailstatus":"0","avatarstatus":"1","videophotostatus":"0","adminid":"1","groupid":"1","groupexpiry":"0","extgroupids":"","regdate":"1558507945","credits":"377","notifysound":"0","timeoffset":"","newpm":"0","newprompt":"5","accessmasks":"0","allowadmincp":"1","onlyacceptfriendpm":"0","conisbind":"0","freeze":"0","avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=1&size=small"}]}}
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
         * viewvote : {"voterlist":[{"uid":"1","email":"admin@admin.com","username":"admin","password":"058e1ac3230d70d2236b808a22049156","status":"0","emailstatus":"0","avatarstatus":"1","videophotostatus":"0","adminid":"1","groupid":"1","groupexpiry":"0","extgroupids":"","regdate":"1558507945","credits":"377","notifysound":"0","timeoffset":"","newpm":"0","newprompt":"5","accessmasks":"0","allowadmincp":"1","onlyacceptfriendpm":"0","conisbind":"0","freeze":"0","avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=1&size=small"}]}
         */

        @SerializedName("cookiepre")
        private String cookiepreX;
        @SerializedName("auth")
        private String authX;
        @SerializedName("saltkey")
        private String saltkeyX;
        @SerializedName("member_uid")
        private String member_uidX;
        @SerializedName("member_username")
        private String member_usernameX;
        @SerializedName("member_avatar")
        private String member_avatarX;
        @SerializedName("groupid")
        private String groupidX;
        @SerializedName("formhash")
        private String formhashX;
        @SerializedName("ismoderator")
        private String ismoderatorX;
        @SerializedName("readaccess")
        private String readaccessX;
        @SerializedName("notice")
        private Notice noticeX;
        private ViewvoteBean viewvote;

        public String getCookiepreX() {
            return cookiepreX;
        }

        public void setCookiepreX(String cookiepreX) {
            this.cookiepreX = cookiepreX;
        }

        public String getAuthX() {
            return authX;
        }

        public void setAuthX(String authX) {
            this.authX = authX;
        }

        public String getSaltkeyX() {
            return saltkeyX;
        }

        public void setSaltkeyX(String saltkeyX) {
            this.saltkeyX = saltkeyX;
        }

        public String getMember_uidX() {
            return member_uidX;
        }

        public void setMember_uidX(String member_uidX) {
            this.member_uidX = member_uidX;
        }

        public String getMember_usernameX() {
            return member_usernameX;
        }

        public void setMember_usernameX(String member_usernameX) {
            this.member_usernameX = member_usernameX;
        }

        public String getMember_avatarX() {
            return member_avatarX;
        }

        public void setMember_avatarX(String member_avatarX) {
            this.member_avatarX = member_avatarX;
        }

        public String getGroupidX() {
            return groupidX;
        }

        public void setGroupidX(String groupidX) {
            this.groupidX = groupidX;
        }

        public String getFormhashX() {
            return formhashX;
        }

        public void setFormhashX(String formhashX) {
            this.formhashX = formhashX;
        }

        public String getIsmoderatorX() {
            return ismoderatorX;
        }

        public void setIsmoderatorX(String ismoderatorX) {
            this.ismoderatorX = ismoderatorX;
        }

        public String getReadaccessX() {
            return readaccessX;
        }

        public void setReadaccessX(String readaccessX) {
            this.readaccessX = readaccessX;
        }

        public Notice getNoticeX() {
            return noticeX;
        }

        public void setNoticeX(Notice noticeX) {
            this.noticeX = noticeX;
        }

        public ViewvoteBean getViewvote() {
            return viewvote;
        }

        public void setViewvote(ViewvoteBean viewvote) {
            this.viewvote = viewvote;
        }

        public static class ViewvoteBean {
            private List<VoterlistBean> voterlist;

            public List<VoterlistBean> getVoterlist() {
                return voterlist;
            }

            public void setVoterlist(List<VoterlistBean> voterlist) {
                this.voterlist = voterlist;
            }

            public static class VoterlistBean {
                /**
                 * uid : 1
                 * email : admin@admin.com
                 * username : admin
                 * password : 058e1ac3230d70d2236b808a22049156
                 * status : 0
                 * emailstatus : 0
                 * avatarstatus : 1
                 * videophotostatus : 0
                 * adminid : 1
                 * groupid : 1
                 * groupexpiry : 0
                 * extgroupids :
                 * regdate : 1558507945
                 * credits : 377
                 * notifysound : 0
                 * timeoffset :
                 * newpm : 0
                 * newprompt : 5
                 * accessmasks : 0
                 * allowadmincp : 1
                 * onlyacceptfriendpm : 0
                 * conisbind : 0
                 * freeze : 0
                 * avatar : https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=1&size=small
                 */

                private String uid;
                private String email;
                private String username;
                private String password;
                private String status;
                private String emailstatus;
                private String avatarstatus;
                private String videophotostatus;
                private String adminid;
                @SerializedName("groupid")
                private String groupidX;
                private String groupexpiry;
                private String extgroupids;
                private String regdate;
                private String credits;
                private String notifysound;
                private String timeoffset;
                private String newpm;
                private String newprompt;
                private String accessmasks;
                private String allowadmincp;
                private String onlyacceptfriendpm;
                private String conisbind;
                private String freeze;
                private String avatar;

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getEmailstatus() {
                    return emailstatus;
                }

                public void setEmailstatus(String emailstatus) {
                    this.emailstatus = emailstatus;
                }

                public String getAvatarstatus() {
                    return avatarstatus;
                }

                public void setAvatarstatus(String avatarstatus) {
                    this.avatarstatus = avatarstatus;
                }

                public String getVideophotostatus() {
                    return videophotostatus;
                }

                public void setVideophotostatus(String videophotostatus) {
                    this.videophotostatus = videophotostatus;
                }

                public String getAdminid() {
                    return adminid;
                }

                public void setAdminid(String adminid) {
                    this.adminid = adminid;
                }

                public String getGroupidX() {
                    return groupidX;
                }

                public void setGroupidX(String groupidX) {
                    this.groupidX = groupidX;
                }

                public String getGroupexpiry() {
                    return groupexpiry;
                }

                public void setGroupexpiry(String groupexpiry) {
                    this.groupexpiry = groupexpiry;
                }

                public String getExtgroupids() {
                    return extgroupids;
                }

                public void setExtgroupids(String extgroupids) {
                    this.extgroupids = extgroupids;
                }

                public String getRegdate() {
                    return regdate;
                }

                public void setRegdate(String regdate) {
                    this.regdate = regdate;
                }

                public String getCredits() {
                    return credits;
                }

                public void setCredits(String credits) {
                    this.credits = credits;
                }

                public String getNotifysound() {
                    return notifysound;
                }

                public void setNotifysound(String notifysound) {
                    this.notifysound = notifysound;
                }

                public String getTimeoffset() {
                    return timeoffset;
                }

                public void setTimeoffset(String timeoffset) {
                    this.timeoffset = timeoffset;
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

                public String getAccessmasks() {
                    return accessmasks;
                }

                public void setAccessmasks(String accessmasks) {
                    this.accessmasks = accessmasks;
                }

                public String getAllowadmincp() {
                    return allowadmincp;
                }

                public void setAllowadmincp(String allowadmincp) {
                    this.allowadmincp = allowadmincp;
                }

                public String getOnlyacceptfriendpm() {
                    return onlyacceptfriendpm;
                }

                public void setOnlyacceptfriendpm(String onlyacceptfriendpm) {
                    this.onlyacceptfriendpm = onlyacceptfriendpm;
                }

                public String getConisbind() {
                    return conisbind;
                }

                public void setConisbind(String conisbind) {
                    this.conisbind = conisbind;
                }

                public String getFreeze() {
                    return freeze;
                }

                public void setFreeze(String freeze) {
                    this.freeze = freeze;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }
            }
        }
    }

//    private List<Profile> polloption;
//
//    public VoterVariables() {
//    }
//
//    public List<Profile> getPolloption() {
//        return polloption;
//    }
//
//    public void setPolloption(List<Profile> polloption) {
//        this.polloption = polloption;
//    }

}
