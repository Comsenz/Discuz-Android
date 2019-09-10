package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/6/15.
 */
public class MyMessage {

    private String plid;
    private String isnew;
    private String pmnum;
    private long lastupdate;
    private long lastdateline;
    private String authorid;
    private String pmtype;
    private String subject;
    private String members;
    private long dateline;
    private String touid;
    private String pmid;
    private String lastauthorid;
    private String lastauthor;
    private String lastsummary;
    private String msgfromid;
    private String msgfrom;
    private String message;
    private String msgtoid;
    private String tousername;
    private String toatavar;

    public MyMessage() {
    }

    @JSONParseMethod
    public static BaseModel<BaseMessageVariables<MyMessage>> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<BaseMessageVariables<MyMessage>>>() {
        }.getType());
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public long getDateline() {
        return dateline;
    }

    public void setDateline(long dateline) {
        this.dateline = dateline;
    }

    public long getLastdateline() {
        return lastdateline;
    }

    public void setLastdateline(long lastdateline) {
        this.lastdateline = lastdateline;
    }

    public long getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(long lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getLastauthor() {
        return lastauthor;
    }

    public void setLastauthor(String lastauthor) {
        this.lastauthor = lastauthor;
    }

    public String getLastauthorid() {
        return lastauthorid;
    }

    public void setLastauthorid(String lastauthorid) {
        this.lastauthorid = lastauthorid;
    }

    public String getLastsummary() {
        return lastsummary;
    }

    public void setLastsummary(String lastsummary) {
        this.lastsummary = lastsummary;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgfrom() {
        return msgfrom;
    }

    public void setMsgfrom(String msgfrom) {
        this.msgfrom = msgfrom;
    }

    public String getMsgfromid() {
        return msgfromid;
    }

    public void setMsgfromid(String msgfromid) {
        this.msgfromid = msgfromid;
    }

    public String getMsgtoid() {
        return msgtoid;
    }

    public void setMsgtoid(String msgtoid) {
        this.msgtoid = msgtoid;
    }

    public String getPlid() {
        return plid;
    }

    public void setPlid(String plid) {
        this.plid = plid;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getPmnum() {
        return pmnum;
    }

    public void setPmnum(String pmnum) {
        this.pmnum = pmnum;
    }

    public String getPmtype() {
        return pmtype;
    }

    public void setPmtype(String pmtype) {
        this.pmtype = pmtype;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTouid() {
        return touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public String getTousername() {
        return tousername;
    }

    public void setTousername(String tousername) {
        this.tousername = tousername;
    }

    public String getToatavar() {
        return toatavar;
    }

    public void setToatavar(String toatavar) {
        this.toatavar = toatavar;
    }
}
