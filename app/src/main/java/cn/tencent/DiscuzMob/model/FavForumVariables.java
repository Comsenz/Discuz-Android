package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by kurt on 15-6-12.
 */
public class FavForumVariables extends BaseVariables {

    private FavModel favforum;

    public FavForumVariables() {
    }

    @JSONParseMethod
    public static BaseModel<FavForumVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<FavForumVariables>>() {
        }.getType());
    }

    public FavModel getFavforum() {
        return favforum;
    }

    public void setFavforum(FavModel favforum) {
        this.favforum = favforum;
    }

}
