package cn.tencent.DiscuzMob.ui.Event;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by cg on 2017/8/15.
 */

public class RefrshLiveClean {
   JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public RefrshLiveClean(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
