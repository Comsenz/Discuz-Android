package cn.tencent.DiscuzMob.ui.Event;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by cg on 2017/8/14.
 */

public class RefrshLive {
    JSONObject jsonObject;

    public RefrshLive(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
