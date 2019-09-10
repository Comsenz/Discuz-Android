package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by kurt on 15-6-16.
 */
public class Profile implements Serializable {

    private Long id;
    private String uid;
    private String username;
    private String status;
    private String avatar;
    private String regdate;
    private String threadCount;
    private String replyCount;
    private String groupString;
    private String numericalString;
    private ProfileInfo.GroupBean group;
    private HashMap<String,ProfileCredit>  numericals;

    public Profile(){}

    public Profile(Long id,String uid,String username,String status,String avatar,String regdate,String threadCount,String replyCount,String groupString,String numericalString){
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.status = status;
        this.avatar = avatar;
        this.regdate = regdate;
        this.threadCount = threadCount;
        this.replyCount = replyCount;
        this.groupString = groupString;
        this.numericalString = numericalString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(String threadCount) {
        this.threadCount = threadCount;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getNumericalString() {
        return numericalString;
    }

    public void setNumericalString(String numericalString) {
        this.numericalString = numericalString;
    }

    public String getGroupString() {
        return groupString;
    }

    public void setGroupString(String groupString) {
        this.groupString = groupString;
    }

    public ProfileInfo.GroupBean getGroup() {
        if(group == null){
            Gson gson = new GsonBuilder().serializeNulls().create();
            group =  gson.fromJson(getGroupString(), ProfileInfo.GroupBean.class);
        }
        return group;
    }

    public void setGroup(ProfileInfo.GroupBean group) {
        this.group = group;
    }

    public HashMap<String, ProfileCredit> getNumericals() {
        if(numericals == null){
           numericals = new Gson().fromJson(numericalString, new TypeToken<HashMap<String, ProfileCredit>>() {
            }.getType());
        }
        return numericals;
    }

    public void setNumericals(HashMap<String, ProfileCredit> numericals) {
        this.numericals = numericals;
    }

}
