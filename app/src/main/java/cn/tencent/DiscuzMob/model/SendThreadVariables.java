package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by kurt on 15-6-12.
 */
public class SendThreadVariables extends BaseVariables {

    private String tid;
    private String pid;

    public SendThreadVariables() {
    }

    @JSONParseMethod
    public static BaseModel<SendThreadVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<SendThreadVariables>>() {
        }.getType());
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
