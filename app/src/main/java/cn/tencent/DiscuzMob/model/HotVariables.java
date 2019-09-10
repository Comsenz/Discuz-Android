package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

public class HotVariables extends BaseVariables {

    private List<HotTThread> data;
    private int perpage;

    public HotVariables() {
    }

    @JSONParseMethod
    public static BaseModel<HotVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<HotVariables>>() {
        }.getType());
    }

    public List<HotTThread> getData() {
        return data;
    }

    public void setData(List<HotTThread> data) {
        this.data = data;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

}
