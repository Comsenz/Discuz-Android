package cn.tencent.DiscuzMob.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import cn.tencent.DiscuzMob.annotation.JSONParseMethod;

/**
 * Created by AiWei on 2016/6/15.
 */
public class MyNotice {

    private String id;
    private String uid;
    private String type;
    @SerializedName("new")
    private String inew;
    private String authorid;
    private String author;
    private String note;
    private long dateline;
    private String from_id;
    private String from_idtype;
    private String from_num;
    private String style;
    private String rowid;
    private Map<String, String> notevar;/* "notevar":{
                    "tid":"273",
                    "pid":"561",
                    "subject":"公共信息回复",
                    "actoruid":"3",
                    "actorusername":"zhangjitao"
                }*/

    public MyNotice() {
    }

    @JSONParseMethod
    public static BaseModel<BaseMessageVariables<MyNotice>> parse(String json) {
        return new Gson().fromJson(json, new TypeToken<BaseModel<BaseMessageVariables<MyNotice>>>() {
        }.getType());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getFrom_idtype() {
        return from_idtype;
    }

    public void setFrom_idtype(String from_idtype) {
        this.from_idtype = from_idtype;
    }

    public String getFrom_num() {
        return from_num;
    }

    public void setFrom_num(String from_num) {
        this.from_num = from_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDateline() {
        return dateline;
    }

    public void setDateline(long dateline) {
        this.dateline = dateline;
    }

    public String getInew() {
        return inew;
    }

    public void setInew(String inew) {
        this.inew = inew;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Map<String, String> getNotevar() {
        return notevar;
    }

    public void setNotevar(Map<String, String> notevar) {
        this.notevar = notevar;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
