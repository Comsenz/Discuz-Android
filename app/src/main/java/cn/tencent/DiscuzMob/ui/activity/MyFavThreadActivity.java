package cn.tencent.DiscuzMob.ui.activity;

import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.User;
import cn.tencent.DiscuzMob.db.UserThread;
import cn.tencent.DiscuzMob.ui.adapter.MyThreadAdapter;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-6-18.
 */
public class MyFavThreadActivity extends BaseActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ViewAnimator mTip;
    private SwipeRefreshLayout mRefreshView;
    private ListView mListView;
    private MyThreadAdapter mAdapter;
    private ContentObserver mContentObserver = new ContentObserver(RedNetApp.INTERNAL_HANDLER) {
        @Override
        public void onChange(boolean selfChange) {
            mAdapter.getFilter().filter("Constraint_UserThread");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_refresh);
        mTip = (ViewAnimator) findViewById(R.id.tip);
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(mAdapter = new MyThreadAdapter(this, null, true));
        mListView.setOnItemClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mTip.setDisplayedChild(1);
        getContentResolver().registerContentObserver(UserThread.URI, true, mContentObserver);
        mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return getContentResolver().query(UserThread.URI, null, null, null, null);
            }
        });
        mAdapter.getFilter().filter("Constraint_UserThread", new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int count) {
                mTip.setDisplayedChild(0);
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            User.prepareThread(this, new Callback() {
                @Override
                public void onCallback(Object obj) {
                    if (mRefreshView != null) {
                        mRefreshView.setRefreshing(false);
                    }
                    if (mTip != null && mTip.getDisplayedChild() != 0) {
                        mTip.setDisplayedChild(0);
                    }
                }
            });
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        if (obj instanceof Cursor) {
            Cursor cursor = (Cursor) obj;
            BaseFragment.toThreadDetails(this, cursor.getString(cursor.getColumnIndex("fid"))
                    , cursor.getString(cursor.getColumnIndex("tid"))
                    , String.valueOf(cursor.getInt(cursor.getColumnIndex("special"))));
        }
    }

    @Override
    protected void onDestroy() {
        getContentResolver().unregisterContentObserver(mContentObserver);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("MyFavThreadActivity(我的收藏(帖子))");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("MyFavThreadActivity(我的收藏(帖子))");
    }

}
