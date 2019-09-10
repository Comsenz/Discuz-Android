package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

public class ReplyVariables extends BaseVariables {

    private List<HotTThread> threadlist;
    private int tpp;
    private int page;
    private int listcount;

    public ReplyVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ReplyVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ReplyVariables>>() {
        }.getType());
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

    public int getListcount() {
        return listcount;
    }

    public void setListcount(int listcount) {
        this.listcount = listcount;
    }

    public List<HotTThread> getThreadlist() {
        return threadlist;
    }

    public void setThreadlist(List<HotTThread> threadlist) {
        this.threadlist = threadlist;
    }

}
