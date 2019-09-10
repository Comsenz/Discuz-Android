package cn.tencent.DiscuzMob.model;

/**
 * Created by kurt on 15-6-12.
 */
public class ForumTypeVariables extends  BaseVariables {

    private ThreadType threadtypes;

    public ForumTypeVariables(){

    }

    public ThreadType getThreadtypes() {
        return threadtypes;
    }

    public void setThreadtypes(ThreadType threadtypes) {
        this.threadtypes = threadtypes;
    }
}
