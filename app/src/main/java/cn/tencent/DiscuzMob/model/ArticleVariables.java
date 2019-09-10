package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/4/22.
 */
public class ArticleVariables extends BaseVariables {

    private Forum forum;
    private List<Article> forum_threadlist;
    private List<Image> imageinfo;
    private int tpp;
    private int page;

    public ArticleVariables() {
    }

    @JSONParseMethod
    public static BaseModel<ArticleVariables> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<ArticleVariables>>() {
        }.getType());
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public List<Article> getForum_threadlist() {
        return forum_threadlist;
    }

    public void setForum_threadlist(List<Article> forum_threadlist) {
        this.forum_threadlist = forum_threadlist;
    }

    public List<Image> getImageinfo() {
        return imageinfo;
    }

    public void setImageinfo(List<Image> imageinfo) {
        this.imageinfo = imageinfo;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTpp() {
        return tpp;
    }

    public void setTpp(int tpp) {
        this.tpp = tpp;
    }

}
