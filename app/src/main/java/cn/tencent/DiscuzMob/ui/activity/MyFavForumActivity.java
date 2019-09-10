package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.UserForum;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.db.User;
import cn.tencent.DiscuzMob.ui.adapter.MyForumAdapter;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 15-6-18.
 */
public class MyFavForumActivity extends BaseActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ViewAnimator mTip;
    private SwipeRefreshLayout mRefreshView;
    private ListView mListView;
    private MyForumAdapter mAdapter;

    private ContentObserver mContentObserver = new ContentObserver(RedNetApp.INTERNAL_HANDLER) {
        @Override
        public void onChange(boolean selfChange) {
            if (mAdapter != null) {
                mAdapter.changeCursor(getContentResolver().query(UserForum.URI, null, null, null, null));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_refresh);
        mTip = (ViewAnimator) findViewById(R.id.tip);
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(mAdapter = new MyForumAdapter(this, null, true));
        mListView.setOnItemClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        getContentResolver().registerContentObserver(UserForum.URI, true, mContentObserver);
        mTip.setDisplayedChild(1);
        mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return getContentResolver().query(UserForum.URI, null, null, null, null);
            }
        });
        mAdapter.getFilter().filter("queryForUserForum", new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int count) {
                mTip.setDisplayedChild(0);
            }
        });
       /* mTip.setDisplayedChild(1);
        ApiVersion5.INSTANCE.requstForumFav(true,
                new ApiVersion5.Result<Object>(this, ForumFavVariables.class) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                ForumFavVariables variables = ((BaseModel<ForumFavVariables>) value).getVariables();
                                if (variables != null) {
                                    mAdapter.set(variables.getList());
                                }
                            } else {
                                mRefreshView.setRefreshing(true);
                            }
                            mTip.setDisplayedChild(0);
                            onRefresh();
                        }
                    }
                });*/
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            User.prepareForum(this, new Callback() {
                @Override
                public void onCallback(Object obj) {
                    if (mRefreshView != null) {
                        mRefreshView.setRefreshing(false);
                    }
                }
            });
          /*  ApiVersion5.INSTANCE.requstForumFav(false,
                    new ApiVersion5.Result<Object>(this, ForumFavVariables.class, null, true) {
                        @Override
                        public void onResult(int code, Object value) {
                            if (mRefreshView != null) {
                                if (value instanceof BaseModel) {
                                    ForumFavVariables variables = ((BaseModel<ForumFavVariables>) value).getVariables();
                                    if (variables != null) {
                                        mAdapter.set(variables.getList());
                                    }
                                }
                                mRefreshView.setRefreshing(false);
                                if (mTip.getDisplayedChild() != 0) {
                                    mTip.setDisplayedChild(0);
                                }
                            }
                        }
                    });*/
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /* Object obj = parent.getItemAtPosition(position);
        if (obj instanceof String) {
            startActivity(new Intent(this, ForumDetailsActivity.class).putExtra("fid", (String) obj));
        }*/
        Object obj = parent.getItemAtPosition(position);
        if (obj instanceof Cursor) {
            Cursor cursor = (Cursor) obj;
            String fid = cursor.getString(cursor.getColumnIndex("fid"));
            if (!TextUtils.isEmpty(fid)) {
                startActivity(new Intent(this, ForumDetailsActivity.class).putExtra("fid", fid));
            } else {
                RednetUtils.toast(this, "无效的id");
            }
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
        MobclickAgent.onPageStart("MyFavForumActivity(我的收藏(版块))");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("MyFavForumActivity(我的收藏(版块))");
    }

}
