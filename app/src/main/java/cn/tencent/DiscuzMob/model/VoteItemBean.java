package cn.tencent.DiscuzMob.model;

import android.net.Uri;

/**
 * Created by cg on 2017/6/26.
 */

public class VoteItemBean {

    String choice;
    Uri uri;

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "VoteItemBean{" +
                "choice='" + choice + '\'' +
                ", uri=" + uri +
                '}';
    }
}
