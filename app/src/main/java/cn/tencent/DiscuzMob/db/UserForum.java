package cn.tencent.DiscuzMob.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import cn.tencent.DiscuzMob.model.MyFavForumBean;

/**
 * Created by AiWei on 2016/5/27.
 */
public class UserForum implements Parcelable, BaseColumns {

    public static final Uri URI = new Uri.Builder().scheme("content").authority(RednetContentProvider.authority).path("userforum").build();

    private String uid;
    private String fid;
    private String favid;
    private String title;
    private String dateline;
    private int todayposts;

    public static final class Table {
        public static final String SQL_CREATE_TABLE = "create table userforum(_id INTEGER PRIMARY KEY,uid text not null,fid text,favid text,title text,dateline text,todayposts integer" +
                ",UNIQUE(uid,fid,favid),FOREIGN KEY(uid) REFERENCES user(member_uid) on delete cascade on update cascade)";
        public static final String SQL_DROP_TABLE = "drop table userforum";
    }

    public UserForum() {
    }

    public UserForum(Cursor cursor) {
        uid = cursor.getString(cursor.getColumnIndex("uid"));
        fid = cursor.getString(cursor.getColumnIndex("fid"));
        favid = cursor.getString(cursor.getColumnIndex("favid"));
       title = cursor.getString(cursor.getColumnIndex("title"));
       dateline = cursor.getString(cursor.getColumnIndex("dateline"));
        todayposts = cursor.getInt(cursor.getColumnIndex("todayposts"));
    }

    private UserForum(Parcel source) {
        uid = source.readString();
        fid = source.readString();
        favid = source.readString();
        title = source.readString();
        dateline = source.readString();
        todayposts = source.readInt();
    }

//    public Forum covertForm(){
////        return new Forum(0L,fid,title,todayposts);
//        return new Forum(fid,title,todayposts);
//    }

    public static UserForum has(Context context, String uid, String fid) {
        Cursor cursor = context.getContentResolver().query(UserForum.URI, null, "uid=? and fid=?", new String[]{uid, fid}, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                UserForum userForum = new UserForum();
                userForum.uid = cursor.getString(cursor.getColumnIndex("uid"));
                userForum.fid = cursor.getString(cursor.getColumnIndex("fid"));
                userForum.favid = cursor.getString(cursor.getColumnIndex("favid"));
                userForum.title = cursor.getString(cursor.getColumnIndex("title"));
                userForum.dateline = cursor.getString(cursor.getColumnIndex("dateline"));
                userForum.todayposts = cursor.getInt(cursor.getColumnIndex("todayposts"));
                return userForum;
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
            Uri uri = context.getContentResolver().insert(UserForum.URI, values);
            return uri != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Context context, String uid, String fid) {
        try {
            return context.getContentResolver().delete(UserForum.URI, "uid=? and fid=?", new String[]{uid, fid}) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ContentValues getContentValues(MyFavForumBean.VariablesBean.ListBean forum) {
        if (forum != null) {
            ContentValues values = new ContentValues();
            values.put("uid", forum.getUid());
            values.put("fid", forum.getId());
            values.put("favid", forum.getFavid());
            values.put("title", forum.getTitle());
            values.put("dateline", forum.getDateline());
            values.put("todayposts", forum.getTodayposts());
            return values;
        } else
            return null;
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

    public void setFid(String id) {
        this.fid = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTodayposts() {
        return todayposts;
    }

    public void setTodayposts(int todayposts) {
        this.todayposts = todayposts;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(fid);
        dest.writeString(favid);
        dest.writeString(title);
        dest.writeString(dateline);
        dest.writeInt(todayposts);
    }

    public static final Parcelable.Creator<UserForum> CREATOR = new Creator<UserForum>() {
        @Override
        public UserForum createFromParcel(Parcel source) {
            return new UserForum(source);
        }

        @Override
        public UserForum[] newArray(int size) {
            return new UserForum[0];
        }
    };

}
