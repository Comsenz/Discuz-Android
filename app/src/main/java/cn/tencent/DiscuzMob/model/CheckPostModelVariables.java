package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by kurt on 15-6-12.
 */
public class CheckPostModelVariables extends BaseVariables {

    private CheckPost allowperm;

    public CheckPostModelVariables() {
    }

    @JSONParseMethod
    public static BaseModel<CheckPostModelVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<CheckPostModelVariables>>() {
        }.getType());
    }

    public CheckPost getAllowperm() {
        return allowperm;
    }

    public void setAllowperm(CheckPost allowperm) {
        this.allowperm = allowperm;
    }

}
