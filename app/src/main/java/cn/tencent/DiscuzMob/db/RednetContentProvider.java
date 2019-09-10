package cn.tencent.DiscuzMob.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.SparseArray;

/**
 * Created by AiWei on 2016/5/27.
 */
public final class RednetContentProvider extends ContentProvider {

    public static final String authority = "cn.zslt.bbs.provider";
    private static final UriMatcher sUriMatcher = new UriMatcher(1600);
    private static final SparseArray<String> sTableMapping = new SparseArray<>();

    //mime vnd.android.cursor.dir/uri vnd.android.cursor.item/uri
    static {
        sUriMatcher.addURI(authority, "user", 1);
        sUriMatcher.addURI(authority, "userforum", 2);
        sUriMatcher.addURI(authority, "userthread", 3);
        sTableMapping.put(1, "user");
        sTableMapping.put(2, "userforum");
        sTableMapping.put(3, "userthread");
    }

    private RednetSQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public RednetContentProvider() {
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new RednetSQLiteOpenHelper(getContext());
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return mDatabase != null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/" + authority + "." + sTableMapping.get(sUriMatcher.match(uri));
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = sUriMatcher.match(uri);
        if (code > 0) {
            Cursor cursor = mDatabase.query(true, sTableMapping.get(code), projection, selection, selectionArgs, null, null, sortOrder, null);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);//检出无论有无数据都要通知以更新UI
            return cursor;
        } else
            return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int code = sUriMatcher.match(uri);
        if (code > 0 && values != null && values.length > 0) {
            int count = 0;
            for (ContentValues value : values) {
                mDatabase.beginTransaction();
                try {
                    long rowID = mDatabase.insertWithOnConflict(sTableMapping.get(code), null, value, SQLiteDatabase.CONFLICT_REPLACE);
                    mDatabase.setTransactionSuccessful();
                    if (rowID != -1) {
                        count++;
                    }
                } finally {
                    mDatabase.endTransaction();
                }
            }
            if (count > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return count;
        } else
            return -1;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = sUriMatcher.match(uri);
        if (code > 0) {
            mDatabase.beginTransaction();
            try {
                long rowID = mDatabase.insertWithOnConflict(sTableMapping.get(code), null, values, SQLiteDatabase.CONFLICT_REPLACE);
                mDatabase.setTransactionSuccessful();
                if (rowID != -1) {
                    Uri _uri = ContentUris.withAppendedId(uri, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
            } finally {
                mDatabase.endTransaction();
            }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = sUriMatcher.match(uri);
        if (code > 0) {
            mDatabase.beginTransaction();
            try {
                int count = mDatabase.delete(sTableMapping.get(code), selection, selectionArgs);
                mDatabase.setTransactionSuccessful();
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            } finally {
                mDatabase.endTransaction();
            }
        }
        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int code = sUriMatcher.match(uri);
        if (code > 0) {
            mDatabase.beginTransaction();
            try {
                int count = mDatabase.update(sTableMapping.get(code), values, selection, selectionArgs);
                mDatabase.setTransactionSuccessful();
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            } finally {
                mDatabase.endTransaction();
            }
        }
        return -1;
    }

    @Override
    public void shutdown() {
        mDatabase.close();
        mDatabaseHelper.close();
        super.shutdown();
    }

}
