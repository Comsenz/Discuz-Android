package cn.tencent.DiscuzMob.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cg on 2017/5/22.
 */

public class FootprintDB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    Context context;
    public FootprintDB(Context context) {
        super(context, "account.db", null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FootprintTable.CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
