package cn.tencent.DiscuzMob.model;

import java.io.Serializable;


/**
 * Created by kurt on 15-6-29.
 */
public class Smiley implements Serializable{
    private String code;
    private String image;
    private int resCode;

    public Smiley(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }
}
