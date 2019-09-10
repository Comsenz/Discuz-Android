package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by kurt on 15-6-12.
 */
public class ProfileVariables extends BaseVariables {

    private ProfileInfo space;

    private HashMap<String, ProfileCredit> extcredits;

    public ProfileVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ProfileVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ProfileVariables>>() {
        }.getType());
    }

    public ProfileInfo getSpace() {
        return space;
    }

    public void setSpace(ProfileInfo space) {
        this.space = space;
    }

    public HashMap<String, ProfileCredit> getExtcredits() {
        return extcredits;
    }

    public void setExtcredits(HashMap<String, ProfileCredit> extcredits) {
        this.extcredits = extcredits;
    }
}
