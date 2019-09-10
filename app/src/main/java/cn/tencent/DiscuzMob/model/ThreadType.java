package cn.tencent.DiscuzMob.model;

import java.util.HashMap;

/**
 * Created by kurt on 15-6-18.
 */
public class ThreadType {
    private String required;
    private String listable;
    private HashMap<String ,String > types;

    public  ThreadType(){

    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getListable() {
        return listable;
    }

    public void setListable(String listable) {
        this.listable = listable;
    }

    public HashMap<String, String> getTypes() {
        return types;
    }

    public void setTypes(HashMap<String, String> types) {
        this.types = types;
    }
}
