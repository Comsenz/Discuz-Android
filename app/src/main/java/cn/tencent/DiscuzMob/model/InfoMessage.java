package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by kurt on 15-5-26.
 */
public class InfoMessage implements Serializable {

    private String messageval;
    private String messagestr;
    private int messagestatus;

    public int getMessagestatus() {
        return messagestatus;
    }

    public void setMessagestatus(int messagestatus) {
        this.messagestatus = messagestatus;
    }

    public InfoMessage(){}

    public String getMessageval() {
        return messageval;
    }

    public void setMessageval(String messageval) {
        this.messageval = messageval;
    }

    public String getMessagestr() {
        return messagestr;
    }

    public void setMessagestr(String messagestr) {
        this.messagestr = messagestr;
    }
}
