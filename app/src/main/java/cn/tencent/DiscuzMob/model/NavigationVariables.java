package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/4/21.
 */
public class NavigationVariables extends BaseVariables {

    private Navigation[] banere;

    public NavigationVariables() {

    }

    @JSONParseMethod
    public static BaseModel<NavigationVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<NavigationVariables>>() {
        }.getType());
    }

    public Navigation[] getBanere() {
        return banere;
    }

    public void setBanere(Navigation[] banere) {
        this.banere = banere;
    }

    public boolean isEmpty() {
        return banere == null || banere.length == 0;
    }

    public int getCount() {
        return isEmpty() ? 0 : banere.length;
    }

}
