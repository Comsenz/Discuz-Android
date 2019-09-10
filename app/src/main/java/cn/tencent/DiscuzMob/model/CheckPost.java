package cn.tencent.DiscuzMob.model;

import java.util.HashMap;

/**
 * Created by kurt on 15-6-26.
 */
public class CheckPost {

    private String allowpost;
    private String allowreply;
    private HashMap<String, String> allowupload;
    private HashMap<String, String> attachremain;
    private String uploadhash;

    public CheckPost() {
    }

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

    public HashMap<String, String> getAllowupload() {
        return allowupload;
    }

    public boolean canUploadImages() {
        if (allowupload != null) {
            if (!"1".equals(allowupload.get("jpg")) && !"1".equals(allowupload.get("jpeg"))) ;
            return false;
        } else
            return true;
    }

    public void setAllowupload(HashMap<String, String> allowupload) {
        this.allowupload = allowupload;
    }

    public HashMap<String, String> getAttachremain() {
        return attachremain;
    }

    public void setAttachremain(HashMap<String, String> attachremain) {
        this.attachremain = attachremain;
    }

    public String getUploadhash() {
        return uploadhash;
    }

    public void setUploadhash(String uploadhash) {
        this.uploadhash = uploadhash;
    }

}
