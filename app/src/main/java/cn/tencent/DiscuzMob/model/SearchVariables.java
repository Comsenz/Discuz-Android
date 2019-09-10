package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/5/5.
 */
public class SearchVariables extends BaseVariables {

    private List<Search> thread;

    public SearchVariables() {
    }

    @JSONParseMethod
    public static BaseModel<SearchVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<SearchVariables>>() {
        }.getType());
    }

    public List<Search> getThread() {
        return thread;
    }

    public void setThread(List<Search> thread) {
        this.thread = thread;
    }

}
