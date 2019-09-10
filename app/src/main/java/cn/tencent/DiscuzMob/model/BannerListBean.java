package cn.tencent.DiscuzMob.model;

import java.util.List;

/**
 * Created by cg on 2017/4/27.
 */

public class BannerListBean {

    /**
     * siteName : Discuz! Board
     * siteUrl : http://bbs.wsq.comsenz-service.com/newwsq/
     * ucenterurl : http://bbs.wsq.comsenz-service.com/newwsq/uc_server
     * version : X3.3
     * siteLogo : static/image/common/logo.png
     * apiUrl : http://bbs.wsq.comsenz-service.com/newwsq/api/mobile/
     * cookie : {"cookiepre":"IiUw_2132_","cookiedomain":"","cookiepath":"/"}
     * loginSeccode : 1
     * iweset : {"iwechat_allow":"1","iwechat_forumdisplay_reply":"0","iwechat_allowregister":"1","iwechat_allowfastregister":"0","iwechat_disableregrule":"1","iwechat_confirmtype":"0","iwechat_newusergroupid":"","iwechat_mtype":"2","recommend":[{"title":"12123","link":"124124","imagefile":false,"imageurl":"http://bbs.wsq.comsenz-service.com/newwsq/static/image/admincp/logo.gif"},{"title":"asdfasdf","link":"adsfasdf","imagefile":false,"imageurl":"http://bbs.wsq.comsenz-service.com/newwsq/static/image/common/logo.png"}]}
     */

    private String siteName;
    private String siteUrl;
    private String ucenterurl;
    private String version;
    private String siteLogo;
    private String apiUrl;
    private CookieBean cookie;
    private String loginSeccode;
    private IwesetBean iweset;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getUcenterurl() {
        return ucenterurl;
    }

    public void setUcenterurl(String ucenterurl) {
        this.ucenterurl = ucenterurl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public CookieBean getCookie() {
        return cookie;
    }

    public void setCookie(CookieBean cookie) {
        this.cookie = cookie;
    }

    public String getLoginSeccode() {
        return loginSeccode;
    }

    public void setLoginSeccode(String loginSeccode) {
        this.loginSeccode = loginSeccode;
    }

    public IwesetBean getIweset() {
        return iweset;
    }

    public void setIweset(IwesetBean iweset) {
        this.iweset = iweset;
    }

    public static class CookieBean {
        /**
         * cookiepre : IiUw_2132_
         * cookiedomain :
         * cookiepath : /
         */

        private String cookiepre;
        private String cookiedomain;
        private String cookiepath;

        public String getCookiepre() {
            return cookiepre;
        }

        public void setCookiepre(String cookiepre) {
            this.cookiepre = cookiepre;
        }

        public String getCookiedomain() {
            return cookiedomain;
        }

        public void setCookiedomain(String cookiedomain) {
            this.cookiedomain = cookiedomain;
        }

        public String getCookiepath() {
            return cookiepath;
        }

        public void setCookiepath(String cookiepath) {
            this.cookiepath = cookiepath;
        }
    }

    public static class IwesetBean {
        /**
         * iwechat_allow : 1
         * iwechat_forumdisplay_reply : 0
         * iwechat_allowregister : 1
         * iwechat_allowfastregister : 0
         * iwechat_disableregrule : 1
         * iwechat_confirmtype : 0
         * iwechat_newusergroupid :
         * iwechat_mtype : 2
         * recommend : [{"title":"12123","link":"124124","imagefile":false,"imageurl":"http://bbs.wsq.comsenz-service.com/newwsq/static/image/admincp/logo.gif"},{"title":"asdfasdf","link":"adsfasdf","imagefile":false,"imageurl":"http://bbs.wsq.comsenz-service.com/newwsq/static/image/common/logo.png"}]
         */

        private String iwechat_allow;
        private String iwechat_forumdisplay_reply;
        private String iwechat_allowregister;
        private String iwechat_allowfastregister;
        private String iwechat_disableregrule;
        private String iwechat_confirmtype;
        private String iwechat_newusergroupid;
        private String iwechat_mtype;
        private List<RecommendBean> recommend;

        public String getIwechat_allow() {
            return iwechat_allow;
        }

        public void setIwechat_allow(String iwechat_allow) {
            this.iwechat_allow = iwechat_allow;
        }

        public String getIwechat_forumdisplay_reply() {
            return iwechat_forumdisplay_reply;
        }

        public void setIwechat_forumdisplay_reply(String iwechat_forumdisplay_reply) {
            this.iwechat_forumdisplay_reply = iwechat_forumdisplay_reply;
        }

        public String getIwechat_allowregister() {
            return iwechat_allowregister;
        }

        public void setIwechat_allowregister(String iwechat_allowregister) {
            this.iwechat_allowregister = iwechat_allowregister;
        }

        public String getIwechat_allowfastregister() {
            return iwechat_allowfastregister;
        }

        public void setIwechat_allowfastregister(String iwechat_allowfastregister) {
            this.iwechat_allowfastregister = iwechat_allowfastregister;
        }

        public String getIwechat_disableregrule() {
            return iwechat_disableregrule;
        }

        public void setIwechat_disableregrule(String iwechat_disableregrule) {
            this.iwechat_disableregrule = iwechat_disableregrule;
        }

        public String getIwechat_confirmtype() {
            return iwechat_confirmtype;
        }

        public void setIwechat_confirmtype(String iwechat_confirmtype) {
            this.iwechat_confirmtype = iwechat_confirmtype;
        }

        public String getIwechat_newusergroupid() {
            return iwechat_newusergroupid;
        }

        public void setIwechat_newusergroupid(String iwechat_newusergroupid) {
            this.iwechat_newusergroupid = iwechat_newusergroupid;
        }

        public String getIwechat_mtype() {
            return iwechat_mtype;
        }

        public void setIwechat_mtype(String iwechat_mtype) {
            this.iwechat_mtype = iwechat_mtype;
        }

        public List<RecommendBean> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<RecommendBean> recommend) {
            this.recommend = recommend;
        }

        public static class RecommendBean {
            /**
             * title : 12123
             * link : 124124
             * imagefile : false
             * imageurl : http://bbs.wsq.comsenz-service.com/newwsq/static/image/admincp/logo.gif
             */

            private String title;
            private String link;
            private boolean imagefile;
            private String imageurl;
            private String link_type;
            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public boolean isImagefile() {
                return imagefile;
            }

            public void setImagefile(boolean imagefile) {
                this.imagefile = imagefile;
            }

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public String getLink_type() {
                return link_type;
            }

            public void setLink_type(String link_type) {
                this.link_type = link_type;
            }
        }
    }

    @Override
    public String toString() {
        return "BannerListBean{" +
                "siteName='" + siteName + '\'' +
                ", siteUrl='" + siteUrl + '\'' +
                ", ucenterurl='" + ucenterurl + '\'' +
                ", version='" + version + '\'' +
                ", siteLogo='" + siteLogo + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", cookie=" + cookie +
                ", loginSeccode='" + loginSeccode + '\'' +
                ", iweset=" + iweset +
                '}';
    }
}
