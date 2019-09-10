package cn.tencent.DiscuzMob.ui.Event;

import cn.tencent.DiscuzMob.model.FileBean;

/**
 * Created by cg on 2017/5/27.
 */

public class SendAudio {
    FileBean fileBean;

    public SendAudio(FileBean fileBean) {
        this.fileBean = fileBean;
    }

    public FileBean getFileBean() {
        return fileBean;
    }

    public void setFileBean(FileBean fileBean) {
        this.fileBean = fileBean;
    }
}
