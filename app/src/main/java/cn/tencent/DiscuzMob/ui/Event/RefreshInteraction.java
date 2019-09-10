package cn.tencent.DiscuzMob.ui.Event;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by cg on 2017/8/15.
 */

public class RefreshInteraction {
    JSONObject jsonObject;

    public RefreshInteraction(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
