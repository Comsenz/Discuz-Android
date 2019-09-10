package cn.tencent.DiscuzMob.db;

import android.content.Context;

import java.util.EventListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cg on 2017/5/22.
 */

public class Modal {
    private static Modal instance = new Modal();
    private Context mContext;
    private FootprintDao mUserAccountDao;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private EventListener eventListener;
    private FootprintDB mHelper;
    public static Modal getInstance() {
        return instance;
    }

    public void init(Context context) {
        mContext = context;
//        mHelper = new FootprintDB(context);
        mUserAccountDao = new FootprintDao(context);
    }

    public FootprintDao getUserAccountDao() {
        return mUserAccountDao;
    }

    public FootprintDB getFootprintDB(){
        return mHelper;
    }
}
