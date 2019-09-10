package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/5/6.
 */
public class StarVariables extends BaseVariables {

    private List<Star> diyuser;

    public StarVariables() {
    }

    @JSONParseMethod
    public static BaseModel<StarVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<StarVariables>>() {
        }.getType());
    }

    public List<Star> getDiyuser() {
        return diyuser;
    }

    public void setDiyuser(List<Star> diyuser) {
        this.diyuser = diyuser;
    }

}
