package cn.tencent.DiscuzMob.model;

import java.io.File;

/**
 * Created by cg on 2017/6/26.
 */

public class FileBean {
    //文件
    private File file;
    //文件时长
    private int fileLength;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }
}
