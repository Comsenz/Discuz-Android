package cn.tencent.DiscuzMob.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AiWei on 2015/5/11.
 */
public class PollPotionVariables extends BaseVariables {
    /**
     * Version : 5
     * Charset : UTF-8
     * Variables : {"cookiepre":"a9ja_2132_","auth":"657fnisTMx3xKZwUPj4iAGTPx3K2Mg3B3dlkPHxN2/lHJ/POvTI4nMKHtiHbUG2hccsVcHA4fKuwKt633QgP","saltkey":"uuuY279f","member_uid":"1","member_username":"admin","member_avatar":"https://guanjia.comsenz-service.com/uc_server/avatar.php?uid=1&size=small","groupid":"1","formhash":"43112009","ismoderator":"1","readaccess":"200","notice":{"newpush":"0","newpm":"0","newprompt":"5","newmypost":"0"},"viewvote":{"polloptions":[{"polloptionid":"63","tid":"276","votes":"1","displayorder":"0","polloption":"Shdhdhdh","voterids":"1\t"},{"polloptionid":"64","tid":"276","votes":"0","displayorder":"0","polloption":"Dudhdhdhdh","voterids":""}]}}
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
         * viewvote : {"polloptions":[{"polloptionid":"63","tid":"276","votes":"1","displayorder":"0","polloption":"Shdhdhdh","voterids":"1\t"},{"polloptionid":"64","tid":"276","votes":"0","displayorder":"0","polloption":"Dudhdhdhdh","voterids":""}]}
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
            private List<PolloptionsBean> polloptions;

            public List<PolloptionsBean> getPolloptions() {
                return polloptions;
            }

            public void setPolloptions(List<PolloptionsBean> polloptions) {
                this.polloptions = polloptions;
            }

            public static class PolloptionsBean {
                /**
                 * polloptionid : 63
                 * tid : 276
                 * votes : 1
                 * displayorder : 0
                 * polloption : Shdhdhdh
                 * voterids : 1
                 */

                private String polloptionid;
                private String tid;
                private String votes;
                private String displayorder;
                private String polloption;
                private String voterids;

                public String getPolloptionid() {
                    return polloptionid;
                }

                public void setPolloptionid(String polloptionid) {
                    this.polloptionid = polloptionid;
                }

                public String getTid() {
                    return tid;
                }

                public void setTid(String tid) {
                    this.tid = tid;
                }

                public String getVotes() {
                    return votes;
                }

                public void setVotes(String votes) {
                    this.votes = votes;
                }

                public String getDisplayorder() {
                    return displayorder;
                }

                public void setDisplayorder(String displayorder) {
                    this.displayorder = displayorder;
                }

                public String getPolloption() {
                    return polloption;
                }

                public void setPolloption(String polloption) {
                    this.polloption = polloption;
                }

                public String getVoterids() {
                    return voterids;
                }

                public void setVoterids(String voterids) {
                    this.voterids = voterids;
                }
            }
        }
    }

//    private List<Polloption> polloption;
//
//    public PollPotionVariables() {
//    }
//
//    public List<Polloption> getPolloption() {
//        return polloption;
//    }
//
//    public void setPolloption(List<Polloption> polloption) {
//        this.polloption = polloption;
//    }

}
