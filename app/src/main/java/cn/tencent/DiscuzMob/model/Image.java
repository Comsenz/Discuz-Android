package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by AiWei on 2016/4/22.
 */
public class Image implements Serializable {

    private String title;
    private String imgsrc;
    private String imgurl;

    public Image() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

}
