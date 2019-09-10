package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/5/29.
 */
public class BaseVariablesModel extends BaseModel<BaseVariables> implements Serializable {

    @JSONParseMethod
    public static BaseVariablesModel parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseVariablesModel>() {
        }.getType());
    }

}
