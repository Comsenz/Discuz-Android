package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by AiWei on 2015/5/6.
 * "Version": "1",
 * "Charset": "GBK",
 * "Variables":{}
 * "data": []
 * "perpage": "50"
 */
public class BaseModel<V> implements Serializable {

    private String Version;
    private String Charset;
    private V Variables;
    private String perpage;
    private InfoMessage Message;

    public BaseModel() {
    }

    public String getCharset() {
        return Charset;
    }

    public void setCharset(String charset) {
        Charset = charset;
    }

    public V getVariables() {
        return Variables;
    }

    public void setVariables(V variables) {
        Variables = variables;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public InfoMessage getMessage() {
        return Message;
    }

    public void setMessage(InfoMessage message) {
        Message = message;
    }

}
