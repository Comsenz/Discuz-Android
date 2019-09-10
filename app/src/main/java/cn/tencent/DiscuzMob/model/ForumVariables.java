package cn.tencent.DiscuzMob.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2015/5/11.
 */
public class ForumVariables extends BaseVariables {



    private String member_email;
    private String member_credits;
    private String setting_bbclosed;
    private Group group;
    private List<Cat> catlist;
    private List<Forum> forumlist;

    public ForumVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ForumVariables> parse(String json) {
        Log.e("TAG", "json =" + json);
        Object o = new Gson().fromJson(json, new TypeToken<BaseModel<ForumVariables>>() {
        }.getType());
        Log.e("TAG", "o="+o.toString());
        return new Gson().fromJson(json, new TypeToken<BaseModel<ForumVariables>>() {
        }.getType());
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_credits() {
        return member_credits;
    }

    public void setMember_credits(String member_credits) {
        this.member_credits = member_credits;
    }

    public String getSetting_bbclosed() {
        return setting_bbclosed;
    }

    public void setSetting_bbclosed(String setting_bbclosed) {
        this.setting_bbclosed = setting_bbclosed;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Cat> getCatlist() {
        return catlist;
    }

    public void setCatlist(List<Cat> catlist) {
        this.catlist = catlist;
    }

    public List<Forum> getForumlist() {
        return forumlist;
    }

    public void setForumlist(List<Forum> forumlist) {
        this.forumlist = forumlist;
    }
}
