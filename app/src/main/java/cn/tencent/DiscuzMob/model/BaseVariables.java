package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

/**
 * Created by AiWei on 2015/5/11.
 */
public class BaseVariables implements Serializable {

    private String cookiepre;
    private String auth;
    private String saltkey;
    private String member_uid;
    private String member_username;
    private String member_avatar;
    private String member_loginstatus;
    private String groupid;
    private String formhash;
    private String ismoderator;
    private String readaccess;
    private Notice notice;
    private String noticeString;

    public BaseVariables() {
    }

    public static BaseModel<BaseVariables> create(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<BaseVariables>>() {
        }.getType());
    }

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

    public Notice getNotice() {
        if (notice == null && noticeString != null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            notice = gson.fromJson(noticeString, Notice.class);
        }
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public void setNoticeString(String noticeString) {
        this.noticeString = noticeString;
    }

    public String getNoticeString() {
        if (notice != null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            noticeString = gson.toJson(notice);
        }
        return noticeString;
    }
}
