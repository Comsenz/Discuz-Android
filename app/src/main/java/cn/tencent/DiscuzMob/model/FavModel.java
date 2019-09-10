package cn.tencent.DiscuzMob.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kurt on 15-6-10.
 */
public class FavModel implements Serializable {

    private Long _id;
    private String favid;
    private String uid;
    private String id;
    private int special;
    private String idtype;
    private String title;
    private String description;
    private String dateline;
    private int spaceuid;
    private String author;
    private String fname;
    private String posttime;
    private String fid;
    private String todayposts;
    private List<String> allowpostspecial;
    private String allowspecialonly;
    private String allowpostspecialString;

    public FavModel() {

    }

    public FavModel(Long _id, String favid, String uid, String id, String idtype, String title, String description, String dateline, String fname,
                    String posttime, String fid, String todayposts, String allowspecialonly, String allowpostspecialString) {
        this._id = _id;
        this.favid = favid;
        this.uid = uid;
        this.id = id;
        this.idtype = idtype;
        this.title = title;
        this.description = description;
        this.dateline = dateline;
        this.fname = fname;
        this.posttime = posttime;
        this.fid = fid;
        this.todayposts = todayposts;
        this.allowspecialonly = allowspecialonly;
        this.allowpostspecialString = allowpostspecialString;
    }


    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSpaceuid() {
        return spaceuid;
    }

    public void setSpaceuid(int spaceuid) {
        this.spaceuid = spaceuid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTodayposts() {
        return todayposts;
    }

    public void setTodayposts(String todayposts) {
        this.todayposts = todayposts;
    }

    public List<String> getAllowpostspecial() {
        return allowpostspecial;
    }

    public void setAllowpostspecial(List<String> allowpostspecial) {
        this.allowpostspecial = allowpostspecial;
    }

    public String getAllowspecialonly() {
        return allowspecialonly;
    }

    public void setAllowspecialonly(String allowspecialonly) {
        this.allowspecialonly = allowspecialonly;
    }

    public String getAllowpostspecialString() {
        return allowpostspecialString;
    }

    public void setAllowpostspecialString(String allowpostspecialString) {
        this.allowpostspecialString = allowpostspecialString;
    }
    
}
