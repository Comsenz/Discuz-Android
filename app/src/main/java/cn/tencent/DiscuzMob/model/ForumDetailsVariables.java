package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2015/5/14.
 */
public class ForumDetailsVariables extends BaseVariables {

    private Forum forum;
    private HashMap<String, String> groupiconid;
    private Group group;
    private List<HotTThread> forum_threadlist;
    private List<Forum> sublist;
    private int tpp;
    private int page;
    private String reward_unit;
    //private ThreadTypes threadtypes;

    public ForumDetailsVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ForumDetailsVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ForumDetailsVariables>>() {
        }.getType());
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public HashMap<String, String> getGroupiconid() {
        return groupiconid;
    }

    public void setGroupiconid(HashMap<String, String> groupiconid) {
        this.groupiconid = groupiconid;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<HotTThread> getForum_threadlist() {
        return forum_threadlist;
    }

    public void setForum_threadlist(List<HotTThread> forum_threadlist) {
        this.forum_threadlist = forum_threadlist;
    }

    public List<Forum> getSublist() {
        return sublist;
    }

    public void setSublist(List<Forum> sublist) {
        this.sublist = sublist;
    }

    public int getTpp() {
        return tpp;
    }

    public void setTpp(int tpp) {
        this.tpp = tpp;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getReward_unit() {
        return reward_unit;
    }

    public void setReward_unit(String reward_unit) {
        this.reward_unit = reward_unit;
    }

   /* public ThreadTypes getThreadtypes() {
        return threadtypes;
    }

    public void setThreadtypes(ThreadTypes threadtypes) {
        this.threadtypes = threadtypes;
    }*/


}
