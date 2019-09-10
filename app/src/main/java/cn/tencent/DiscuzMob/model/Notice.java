package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by AiWei on 2015/5/6.
 */
public class Notice implements Serializable {

    private String newpush;
    private String newpm;
    private String newprompt;
    private String newmypost;

    public Notice() {
    }

    public String getNewmypost() {
        return newmypost;
    }

    public void setNewmypost(String newmypost) {
        this.newmypost = newmypost;
    }

    public String getNewpm() {
        return newpm;
    }

    public void setNewpm(String newpm) {
        this.newpm = newpm;
    }

    public String getNewprompt() {
        return newprompt;
    }

    public void setNewprompt(String newprompt) {
        this.newprompt = newprompt;
    }

    public String getNewpush() {
        return newpush;
    }

    public void setNewpush(String newpush) {
        this.newpush = newpush;
    }
}
