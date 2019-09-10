package cn.tencent.DiscuzMob.model;

/**
 * Created by cg on 2017/6/9.
 */

public class PollPictureBean {

    /**
     * aid : 46
     * smallimg : data/attachment/forum/201706/09/152914pfl0mgrmylm60g3p.jpg.thumb.jpg
     * bigimg : data/attachment/forum/201706/09/152914pfl0mgrmylm60g3p.jpg
     * errorcode : 0
     */

    private int aid;
    private String smallimg;
    private String bigimg;
    private int errorcode;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getSmallimg() {
        return smallimg;
    }

    public void setSmallimg(String smallimg) {
        this.smallimg = smallimg;
    }

    public String getBigimg() {
        return bigimg;
    }

    public void setBigimg(String bigimg) {
        this.bigimg = bigimg;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }
}
