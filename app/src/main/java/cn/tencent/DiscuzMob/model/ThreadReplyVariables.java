package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2015/5/6.
 */
public class ThreadReplyVariables extends BaseVariables {

    private String tid;
    private String pid;

    public ThreadReplyVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ThreadReplyVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ThreadReplyVariables>>() {
        }.getType());
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
