package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/5/4.
 */
public class ArticleGuideVariables extends BaseVariables {

    private List<ArticleGuide> data;
    private Object attlists;
    private int perpage;
    private int threads;//总条数

    public ArticleGuideVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ArticleGuideVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ArticleGuideVariables>>() {
        }.getType());
    }

    public List<ArticleGuide> getData() {
        return data;
    }

    public void setData(List<ArticleGuide> data) {
        this.data = data;
    }

    public Object getAttlists() {
        return attlists;
    }

    public void setAttlists(Object attlists) {
        this.attlists = attlists;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int perpage) {
        this.threads = threads;
    }


}
