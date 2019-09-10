package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by kurt on 15-6-18.
 */
public class ForumThreadType implements Serializable {

    private Long id;
    private String typeId;
    private String typeName;
    private String fid;

    public ForumThreadType(){}

    public ForumThreadType(Long id,String typeId,String typeName,String fid){
        this.id = id;
        this.typeId = typeId;
        this.typeName = typeName;
        this.fid = fid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

}
