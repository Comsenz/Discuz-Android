package cn.tencent.DiscuzMob.model;

import java.util.HashMap;

/**
 * Created by cg on 2017/4/17.
 */

public class UserInfoBean {

    /**
     * Charset : UTF-8
     * Variables : {"auth":"c82bJyS/3o1DyWdMtLaAq++U/lwXs07VZMPBZ9ckU3NYIw7TaVcl33LMUVarJY79jykPInHzSg0VSX9fc9ecew","cookiepre":"IiUw_2132_","extcredits":{"1":{"img":"","ratio":"0","title":"威望","unit":""},"2":{"img":"","ratio":"0","title":"金钱","unit":""},"3":{"img":"","ratio":"0","title":"贡献","unit":""}},"formhash":"50840983","groupid":"10","member_avatar":"http://bbs.wsq.comsenz-service.com/newwsq/uc_server/avatar.php?uid=84&size=small","member_uid":"84","member_username":"测试00","notice":{"newmypost":"0","newpm":"0","newprompt":"0","newpush":"0"},"readaccess":"10","saltkey":"s4PERogP","space":{"acceptemail":[],"accessmasks":"0","addfriend":"0","address":"","addsize":"0","admingroup":{"icon":""},"adminid":"0","affectivestatus":"","albums":"0","alipay":"","allowadmincp":"0","attachsize":"   0 B ","attentiongroup":"","authstr":"","avatarstatus":"0","bio":"","birthcity":"","birthcommunity":"","birthday":"0","birthdist":"","birthmonth":"0","birthprovince":"","birthyear":"0","blacklist":"0","blockposition":"","blogs":"0","bloodtype":"","buyercredit":"0","buyerrank":"0","company":"","conisbind":"1","constellation":"","credits":"4","customshow":"26","customstatus":"","digestposts":"0","doings":"0","domain":"","education":"","emailstatus":"0","extcredits1":"0","extcredits2":"4","extcredits3":"0","extcredits4":"0","extcredits5":"0","extcredits6":"0","extcredits7":"0","extcredits8":"0","extgroupids":"","favtimes":"0","feedfriend":"","feeds":"0","field1":"","field2":"","field3":"","field4":"","field5":"","field6":"","field7":"","field8":"","follower":"0","following":"0","freeze":"0","friends":"0","gender":"0","graduateschool":"","group":{"allowbegincode":"0","allowgetattach":"1","allowgetimage":"1","allowmediacode":"0","color":"","creditshigher":"0","creditslower":"50","grouptitle":"新手上路","icon":"","maxsigsize":"80","readaccess":"10","stars":"1","type":"member","userstatusby":"1"},"groupexpiry":"0","groupiconid":"1","groupid":"10","groups":"","groupterms":"","height":"","icq":"","idcard":"","idcardtype":"","interest":"","invisible":"0","lastactivity":"2017-6-2 11:22","lastactivitydb":"1496373775","lastpost":"0","lastsendmail":"0","lastvisit":"2017-6-2 11:22","lookingfor":"","magicgift":"","medals":"","menunum":"0","mobile":"","msn":"","nationality":"","newfollower":"0","newpm":"0","newprompt":"0","notifysound":"0","occupation":"","oltime":"2","onlyacceptfriendpm":"0","port":"33071","position":"","posts":"0","privacy":{"feed":{"blog":"1","doing":"1","newthread":"1","poll":"1","upload":"1"},"profile":[],"view":{"album":"0","blog":"0","doing":"0","friend":"0","home":"0","index":"0","share":"0","wall":"0"}},"profileprogress":"0","publishfeed":"0","qq":"","realname":"","recentnote":"","regdate":"2017-6-1 11:36","residecity":"","residecommunity":"","residedist":"","resideprovince":"","residesuite":"","revenue":"","self":"1","sellercredit":"0","sellerrank":"0","sharetimes":"0","sharings":"0","sightml":"","site":"","spacecss":"","spacedescription":"","spacename":"","spacenote":"","status":"0","stickblogs":"","taobao":"","telephone":"","theme":"","threads":"0","timeoffset":"9999","todayattachs":"0","todayattachsize":"0","uid":"84","upgradecredit":"46","upgradeprogress":"8","username":"测试00","videophoto":"","videophotostatus":"0","views":"0","weight":"","yahoo":"","zipcode":"","zodiac":""},"wsq":{}}
     * Version : 4
     */

    private String Charset;
    private VariablesBean Variables;
    private String Version;
    /**
     * Message : {"messageval":"register_succeed","messagestr":"感谢您注册 Discuz! Board，现在将以 新手上路 身份登录站点"}
     */

    private MessageBean Message;

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

    public MessageBean getMessage() {
        return Message;
    }

    public void setMessage(MessageBean Message) {
        this.Message = Message;
    }

    public static class VariablesBean {


        private String auth;
        private String cookiepre;
        private HashMap<String, ProfileCredit> extcredits;
        private String formhash;
        private String groupid;
        private String member_avatar;
        private String member_uid;
        private String member_username;
        private NoticeBean notice;
        private String readaccess;
        private String saltkey;
        private ProfileInfo space;
        private WsqBean wsq;
//        public Iwechat_userBean iwechat_user;

        @Override
        public String toString() {
            return "VariablesBean{" +
                    "auth='" + auth + '\'' +
                    ", cookiepre='" + cookiepre + '\'' +
                    ", extcredits=" + extcredits +
                    ", formhash='" + formhash + '\'' +
                    ", groupid='" + groupid + '\'' +
                    ", member_avatar='" + member_avatar + '\'' +
                    ", member_uid='" + member_uid + '\'' +
                    ", member_username='" + member_username + '\'' +
                    ", notice=" + notice +
                    ", readaccess='" + readaccess + '\'' +
                    ", saltkey='" + saltkey + '\'' +
                    ", space=" + space +
                    ", wsq=" + wsq +
//                    ", iwechat_user=" + iwechat_user +
                    '}';
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

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

        public ProfileInfo getSpace() {
            return space;
        }

        public void setSpace(ProfileInfo space) {
            this.space = space;
        }

        public WsqBean getWsq() {
            return wsq;
        }

        public void setWsq(WsqBean wsq) {
            this.wsq = wsq;
        }

        public HashMap<String, ProfileCredit> getExtcredits() {
            return extcredits;
        }

        public void setExtcredits(HashMap<String, ProfileCredit> extcredits) {
            this.extcredits = extcredits;
        }

//        public Iwechat_userBean getIwechat_user() {
//            return iwechat_user;
//        }
//
//        public void setIwechat_user(Iwechat_userBean iwechat_user) {
//            this.iwechat_user = iwechat_user;
//        }


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
        public static class WsqBean {
        }
    }

    public static class MessageBean {
        /**
         * messageval : register_succeed
         * messagestr : 感谢您注册 Discuz! Board，现在将以 新手上路 身份登录站点
         */

        private String messageval;
        private String messagestr;

        public String getMessageval() {
            return messageval;
        }

        public void setMessageval(String messageval) {
            this.messageval = messageval;
        }

        public String getMessagestr() {
            return messagestr;
        }

        public void setMessagestr(String messagestr) {
            this.messagestr = messagestr;
        }
    }
}
