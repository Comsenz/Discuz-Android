package cn.tencent.DiscuzMob.model;

/**
 * Created by kurt on 15-6-12.
 */
public class SendMessageVariables extends  BaseVariables {

    private String pmid;

    public SendMessageVariables(){

    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }
}
