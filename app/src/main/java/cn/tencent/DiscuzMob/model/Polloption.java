package cn.tencent.DiscuzMob.model;

import java.io.Serializable;

/**
 * Created by kurt on 15-8-11.
 */
public class Polloption implements Serializable {
    private String polloptionid;
    private String tid;
    private String votes;
    private String displayorder;
    private String polloption;


    public Polloption(){
    }

    public String getPolloptionid() {
        return polloptionid;
    }

    public void setPolloptionid(String polloptionid) {
        this.polloptionid = polloptionid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getPolloption() {
        return polloption;
    }

    public void setPolloption(String polloption) {
        this.polloption = polloption;
    }
}
