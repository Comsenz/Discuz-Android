package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by kurt on 15-6-12.
 */
public class SecureVariables extends BaseVariables {

    private String sechash;
    private String seccode;
    private String secqaa;

    public SecureVariables() {
    }

    @JSONParseMethod
    public static BaseModel<SecureVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<SecureVariables>>() {
        }.getType());
    }

    public String getSechash() {
        return sechash;
    }

    public void setSechash(String sechash) {
        this.sechash = sechash;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public String getSecqaa() {
        return secqaa;
    }

    public void setSecqaa(String secqaa) {
        this.secqaa = secqaa;
    }

}
