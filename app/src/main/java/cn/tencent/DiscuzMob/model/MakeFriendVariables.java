package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/6/22.
 */
public class MakeFriendVariables extends BaseVariables {

    private int code;
    private String message;

    public MakeFriendVariables() {
    }

    @JSONParseMethod
    public static BaseModel<MakeFriendVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<MakeFriendVariables>>() {
        }.getType());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
