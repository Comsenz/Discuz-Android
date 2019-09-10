package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/12/10.
 */
public final class Empty {

    @JSONParseMethod
    public static BaseModel<Empty> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<Empty>>() {
        }.getType());
    }

}
