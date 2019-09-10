package cn.tencent.DiscuzMob.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import cn.tencent.DiscuzMob.model.MyFavThreadBean;

/**
 * Created by AiWei on 2016/5/27.
 */
public class UserThread implements Parcelable, BaseColumns {

    public static final Uri URI = new Uri.Builder().scheme("content").authority(RednetContentProvider.authority).path("userthread").build();

    private String uid;
    private String tid;
    private String favid;
    private String fid;
    private String title;
    private String author;
    private String dateline;
    private int special;

    public static final class Table {
        public static final String SQL_CREATE_TABLE = "create table userthread(_id INTEGER PRIMARY KEY,uid text not null,tid text,favid text,fid text," +
                "title text,author text,dateline text,special integer,UNIQUE(uid,tid,fid,favid),FOREIGN KEY(uid) REFERENCES user(member_uid) on delete cascade on update cascade)";
        public static final String SQL_DROP_TABLE = "drop table userthread";
    }

    public UserThread() {
    }

    public UserThread(Parcel source) {
        uid = source.readString();
        tid = source.readString();
        favid = source.readString();
        fid = source.readString();
        title = source.readString();
        dateline = source.readString();
        author = source.readString();
        special = source.readInt();
    }

    public static UserThread has(Context context, String uid, String tid) {
        Cursor cursor = context.getContentResolver().query(UserThread.URI, null, "uid=? and tid=?", new String[]{uid, tid}, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                UserThread thread = new UserThread();
                thread.uid = cursor.getString(cursor.getColumnIndex("uid"));
                thread.tid = cursor.getString(cursor.getColumnIndex("tid"));
                thread.favid = cursor.getString(cursor.getColumnIndex("favid"));
                thread.fid = cursor.getString(cursor.getColumnIndex("fid"));
                thread.title = cursor.getString(cursor.getColumnIndex("title"));
                thread.dateline = cursor.getString(cursor.getColumnIndex("dateline"));
                thread.author = cursor.getString(cursor.getColumnIndex("author"));
                thread.special = cursor.getInt(cursor.getColumnIndex("special"));
                return thread;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean save(Context context, ContentValues values) {
        try {
            Uri uri = context.getContentResolver().insert(UserThread.URI, values);
            return uri != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Context context, String uid, String tid) {
        try {
            return context.getContentResolver().delete(UserThread.URI, "uid=? and tid=?", new String[]{uid, tid}) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ContentValues getContentValues(MyFavThreadBean.VariablesBean.ListBean thread) {
        if (thread != null) {
            ContentValues values = new ContentValues();
            values.put("uid", thread.getUid());
            values.put("tid", thread.getId());
            values.put("favid", thread.getFavid());
            values.put("fid", thread.getId());
            values.put("title", thread.getTitle());
            values.put("author", thread.getAuthor());
            values.put("dateline", thread.getDateline());
            values.put("special", thread.getSpaceuid());
            return values;
        } else
            return null;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String id) {
        this.tid = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    @Override
    public int describeContents() {
        return 2;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(tid);
        dest.writeString(favid);
        dest.writeString(fid);
        dest.writeString(title);
        dest.writeString(dateline);
        dest.writeString(author);
        dest.writeInt(special);
    }

    public static final Parcelable.Creator<UserThread> CREATOR = new Creator<UserThread>() {
        @Override
        public UserThread createFromParcel(Parcel source) {
            return new UserThread(source);
        }

        @Override
        public UserThread[] newArray(int size) {
            return new UserThread[0];
        }
    };

}
