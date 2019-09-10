package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/6/17.
 */
public class MyMessageInfo {

    private String plid;
    private String subject;
    private String pmid;
    private String message;
    private String touid;
    private String msgfromid;
    private String msgfrom;
    private String vdateline;
    private String fromavatar;
    private String toavatar;

    public MyMessageInfo() {
    }

    @JSONParseMethod
    public static BaseModel<BaseMessageVariables<MyMessageInfo>> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<BaseMessageVariables<MyMessageInfo>>>() {
        }.getType());
    }

    public String getPlid() {
        return plid;
    }

    public void setPlid(String plid) {
        this.plid = plid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTouid() {
        return touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public String getMsgfromid() {
        return msgfromid;
    }

    public void setMsgfromid(String msgfromid) {
        this.msgfromid = msgfromid;
    }

    public String getMsgfrom() {
        return msgfrom;
    }

    public void setMsgfrom(String msgfrom) {
        this.msgfrom = msgfrom;
    }

    public String getVdateline() {
        return vdateline;
    }

    public void setVdateline(String vdateline) {
        this.vdateline = vdateline;
    }

    public String getFromavatar() {
        return fromavatar;
    }

    public void setFromavatar(String fromavatar) {
        this.fromavatar = fromavatar;
    }

    public String getToavatar() {
        return toavatar;
    }

    public void setToavatar(String toavatar) {
        this.toavatar = toavatar;
    }
}
