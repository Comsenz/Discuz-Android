package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by AiWei on 2015/5/11.
 */
public class Cat implements Serializable {

    private Long id;
    private String fid;
    private String name;
    private String[] forums;

    public Cat() {
    }

    public Cat(Long id,String fid,String name){
        this.id = id;
        this.fid = fid;
        this.name = name;

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getForums() {
        return forums;
    }

    public void setForums(String[] forums) {
        this.forums = forums;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Cat&&((Cat)o).id==id&&(((Cat)o).fid).equals(fid);
    }

    @Override
    public int hashCode() {
        return 16092414;
    }
}
