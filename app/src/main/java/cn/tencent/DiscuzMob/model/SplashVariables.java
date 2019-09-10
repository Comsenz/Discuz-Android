package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/4/21.
 */
public class SplashVariables extends BaseVariables {

    private Image openimage;

    public SplashVariables() {
    }

    @JSONParseMethod
    public static BaseModel<SplashVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<SplashVariables>>() {
        }.getType());
    }

    public Image getOpenimage() {
        return openimage;
    }

    public void setOpenimage(Image openimage) {
        this.openimage = openimage;
    }

}
