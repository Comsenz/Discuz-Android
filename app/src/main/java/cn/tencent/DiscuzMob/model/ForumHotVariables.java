package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/4/27.
 */
public class ForumHotVariables extends BaseVariables {

    private List<Forum> data;
    private int perpage;

    public ForumHotVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ForumHotVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ForumHotVariables>>() {
        }.getType());
    }

    public List<Forum> getData() {
        return data;
    }

    public void setData(List<Forum> data) {
        this.data = data;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }
}
