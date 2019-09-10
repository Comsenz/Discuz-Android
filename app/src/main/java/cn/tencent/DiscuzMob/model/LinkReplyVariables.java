package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by AiWei on 2016/6/27.
 */
public class LinkReplyVariables extends BaseVariables {

    private String tid;
    private String pid;
    private String noticetrimstr;

    public LinkReplyVariables() {
    }

    public static BaseModel<LinkReplyVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<LinkReplyVariables>>() {
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

    public String getNoticetrimstr() {
        return noticetrimstr;
    }

    public void setNoticetrimstr(String noticetrimstr) {
        this.noticetrimstr = noticetrimstr;
    }

}
