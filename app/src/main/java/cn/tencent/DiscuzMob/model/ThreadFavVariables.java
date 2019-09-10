package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/5/26.
 */
public class ThreadFavVariables extends BaseVariables {

    private List<FavModel> list;

    public ThreadFavVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ThreadFavVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ThreadFavVariables>>() {
        }.getType());
    }

    public List<FavModel> getList() {
        return list;
    }

    public void setList(List<FavModel> list) {
        this.list = list;
    }

}
