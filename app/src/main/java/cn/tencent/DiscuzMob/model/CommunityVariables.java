package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/4/29.
 */
public class CommunityVariables extends BaseVariables {

    private List<Banere> banerehead;
    private List<ArticleSnap> artlist;
    private List<Banere> banerefoot;

    public CommunityVariables() {
    }

    @JSONParseMethod
    public static BaseModel<CommunityVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<CommunityVariables>>() {
        }.getType());
    }

    public List<Banere> getBanerefoot() {
        return banerefoot;
    }

    public void setBanerefoot(List<Banere> banerefoot) {
        this.banerefoot = banerefoot;
    }

    public List<ArticleSnap> getArtlist() {
        return artlist;
    }

    public void setArtlist(List<ArticleSnap> artlist) {
        this.artlist = artlist;
    }

    public List<Banere> getBanerehead() {
        return banerehead;
    }

    public void setBanerehead(List<Banere> banerehead) {
        this.banerehead = banerehead;
    }
}
