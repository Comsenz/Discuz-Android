package cn.tencent.DiscuzMob.utils;

import android.content.Context;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.cache.DiskLruCache;


/**
 * Created by AiWei on 2015/5/11.
 */
public class ObjectCache {

    static volatile ObjectCache sInstance;
    private Context mContext;
    private final LruCache<String, Object> mMemoryCache = new LruCache<>(1024 * 1024);//SoftReference
    private DiskLruCache mDiskCache;

    public ObjectCache(Context context) {
        this.mContext = context;
        ensure();
    }

    private synchronized void ensure() {
        if (mDiskCache == null) {
            if (mContext == null) {
                mContext = RedNetApp.getInstance();
            }
            try {
                File storageDir = RedNetApp.getInstance().getStorageDir("objects");
                mDiskCache = DiskLruCache.open(storageDir != null && storageDir.exists() ? storageDir : new File(mContext.getExternalCacheDir(), "objects")
                        , 1, 1, 125 * 1204 * 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void writeString(String key, String content) {
        ensure();
        if (!TextUtils.isEmpty(content)) {
            mMemoryCache.put(key, content);
            if (mDiskCache != null) {
                DiskLruCache.Editor editor = null;
                try {
                    editor = mDiskCache.edit(key);
                    editor.set(0, content);
                    editor.commit();
                } catch (IOException e) {
                    if (editor != null) {
                        editor.abortUnlessCommitted();
                    }
                }
            }
        }
    }

    public synchronized String readString(String key) {
        ensure();
        String mc = (String) mMemoryCache.get(key);
        if (!TextUtils.isEmpty(mc)) {
            return mc;
        } else {
            mMemoryCache.remove(key);
            if (mDiskCache != null) {
                DiskLruCache.Snapshot snapshot = null;
                try {
                    String str = null;
                    snapshot = mDiskCache.get(key);
                    if (snapshot == null || TextUtils.isEmpty(str = snapshot.getString(0))) {
                        mDiskCache.remove(key);
                    } else {
                        mMemoryCache.put(key, str);
                    }
                    return str;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (snapshot != null) {
                        snapshot.close();
                    }
                }
            }
            return null;
        }
    }

    public synchronized void remove(String key) {
        ensure();
        mMemoryCache.remove(key);
        if (mDiskCache != null) {
            try {
                mDiskCache.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void delete() {
        ensure();
        mMemoryCache.evictAll();
        if (mDiskCache != null) {
            try {
                mDiskCache.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void close() {
        mMemoryCache.evictAll();
        if (mDiskCache != null) {
            try {
                mDiskCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sInstance = null;
    }

    public static synchronized ObjectCache getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ObjectCache(context);
        }
        return sInstance;
    }

}
