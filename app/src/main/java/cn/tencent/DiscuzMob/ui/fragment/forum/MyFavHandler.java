package cn.tencent.DiscuzMob.ui.fragment.forum;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;

import java.util.ArrayList;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.UserForum;
import cn.tencent.DiscuzMob.model.Forum;
import cn.tencent.DiscuzMob.ui.adapter.ForumIndexExpandableListAdapter;
import cn.tencent.DiscuzMob.db.User;

/**
 * Created by Wind on 2016/9/24.
 */
public  class MyFavHandler implements LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentActivity mActivity;
    private ForumIndexExpandableListAdapter mAdapter;
    private ContentObserver mContentObserver = new ContentObserver(RedNetApp.INTERNAL_HANDLER) {
        @Override
        public void onChange(boolean selfChange) {
            mActivity.getSupportLoaderManager().getLoader(0).forceLoad();
        }
    };
    private ContentObserver mUserObserver = new ContentObserver(RedNetApp.INTERNAL_HANDLER) {
        @Override
        public void onChange(boolean selfChange) {
            if (!hasLogin()) {
//                mAdapter.removeCat(new Cat(-1l,"-1",null));
            }else{
//            mActivity.getSupportLoaderManager().getLoader(0).startLoading();
            }
        }
    };

    public MyFavHandler() {
    }

    public ForumIndexExpandableListAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ForumIndexExpandableListAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void onAttach(FragmentActivity activity) {
        this.mActivity=activity;
        activity.getContentResolver().registerContentObserver(UserForum.URI, true, mContentObserver);
        activity.getContentResolver().registerContentObserver(User.URI, true, mUserObserver);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mActivity.getSupportLoaderManager().initLoader(0, null, this);
        if (hasLogin()) {
            mActivity.getSupportLoaderManager().getLoader(0).startLoading();
        }
    }

    public void onDetach(Activity activity) {
        activity.getContentResolver().unregisterContentObserver(mContentObserver);
        activity.getContentResolver().unregisterContentObserver(mUserObserver);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mActivity, UserForum.URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor!=null&&cursor.getCount()>0){
            ArrayList<Forum> list = new ArrayList<>();
            for (;cursor.moveToNext();){
//                list.add(new UserForum(cursor).covertForm());
            }
//            mAdapter.addCat(new Cat(-1l,"-1","我的收藏"),list,true);
        }
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public boolean hasLogin() {
        return RedNetApp.getInstance().isLogin();
    }

}
