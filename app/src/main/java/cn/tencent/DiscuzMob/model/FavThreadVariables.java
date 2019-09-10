package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by kurt on 15-6-12.
 */
public class FavThreadVariables extends BaseVariables {

    private FavModel favthread;

    public FavThreadVariables() {
    }

    @JSONParseMethod
    public static BaseModel<FavThreadVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<FavThreadVariables>>() {
        }.getType());
    }

    public FavModel getFavthread() {
        return favthread;
    }

    public void setFavthread(FavModel favthread) {
        this.favthread = favthread;
    }
}
