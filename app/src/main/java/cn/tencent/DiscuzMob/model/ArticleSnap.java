package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by AiWei on 2016/4/29.
 */
public class ArticleSnap implements Serializable {

    private String title;
    private String summary;
    private String imgurl;
    private String tourl;
    private String dateline;

    public ArticleSnap() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTourl() {
        return tourl;
    }

    public void setTourl(String tourl) {
        this.tourl = tourl;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

}
