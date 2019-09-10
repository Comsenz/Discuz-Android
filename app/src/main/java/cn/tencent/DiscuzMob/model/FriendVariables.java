package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/6/21.
 */
public class FriendVariables extends BaseVariables {

    private List<Star> list;
    private int count;

    public FriendVariables() {
    }

    @JSONParseMethod
    public static BaseModel<FriendVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<FriendVariables>>() {
        }.getType());
    }

    public List<Star> getList() {
        return list;
    }

    public void setList(List<Star> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
