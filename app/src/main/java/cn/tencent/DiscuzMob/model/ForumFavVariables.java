package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/5/16.
 */
public class ForumFavVariables extends BaseVariables {

    private List<FavModel> list;
    private int perpage;
    private int count;

    public ForumFavVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ForumFavVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ForumFavVariables>>() {
        }.getType());
    }

    public List<FavModel> getList() {
        return list;
    }

    public void setList(List<FavModel> list) {
        this.list = list;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
