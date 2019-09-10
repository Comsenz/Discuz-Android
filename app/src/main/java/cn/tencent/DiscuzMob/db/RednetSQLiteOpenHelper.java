package cn.tencent.DiscuzMob.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by AiWei on 2016/5/27.
 */
public class RednetSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String NAME = "CN_REDNET_BBS_INTERNAL_DB";
    public static final int VERSION = 1;

    RednetSQLiteOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    void setOnForeignKeys(SQLiteDatabase db) {//trigger  create trigger name after delete on user begin delete from userthread; end
        if (Build.VERSION.SDK_INT >= 16) {
            db.setForeignKeyConstraintsEnabled(true);
        } else {
            db.execSQL("PRAGMA foreign_keys = ON");
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        setOnForeignKeys(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        setOnForeignKeys(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.Table.SQL_CREATE_TABLE);
        db.execSQL(UserForum.Table.SQL_CREATE_TABLE);
        db.execSQL(UserThread.Table.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(User.Table.SQL_DROP_TABLE);
        db.execSQL(UserForum.Table.SQL_DROP_TABLE);
        db.execSQL(UserThread.Table.SQL_DROP_TABLE);
        onCreate(db);
    }

}
