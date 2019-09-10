package cn.tencent.DiscuzMob.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.UserForum;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.BaseVariablesModel;
import cn.tencent.DiscuzMob.model.FavForumVariables;
import cn.tencent.DiscuzMob.model.Forum;
import cn.tencent.DiscuzMob.model.ForumDetailsVariables;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.ui.adapter.ForumDetailsRecyclerAdapter;
import cn.tencent.DiscuzMob.ui.menu.ForumGroupPopupWindow;
import cn.tencent.DiscuzMob.ui.menu.OnMenuItemClickListener;
import cn.tencent.DiscuzMob.ui.menu.ThreadPostPopup;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2015/5/12.
 * 板块详情(（置顶）贴子，子版块)
 */
public class ForumDetailsActivity extends BaseActivity implements View.OnClickListener, ILoadListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int SEND_REQUEST_CODE = 0;
    private static final int FACOURITE_REQUEST_CODE = 1;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private NestedSwipeRefreshLayout mRefreshView;
    private ForumDetailsRecyclerAdapter mAdapter;
    private ThreadPostPopup mThreadPostPopup;
    private ImageView mTypeTag;
    private ImageView mPost, mBackBtn;
    private TextView mTitle, mForumTitle, mForumDesc, mFavLabel;
    private ForumGroupPopupWindow typePopupWindow;

    private String showType = "common";
    private int page = 1;
    private String mFid;//fid
    private UserForum mUserForum;
    private Forum mForum;

    private OnMenuItemClickListener mPostClickListener = new OnMenuItemClickListener() {
        @Override
        public void onItemClick(Object obj, View v, int position, CharSequence title) {
            if (canPost()) {
                startActivity(new Intent(ForumDetailsActivity.this, (Class<?>) obj).putExtra("fid", mFid));
            }
            mThreadPostPopup.dismiss();
        }
    };
    private OnMenuItemClickListener mGroupClickListener = new OnMenuItemClickListener() {
        @Override
        public void onItemClick(Object obj, View v, int position, CharSequence title) {
            if (!title.equals(showType)) {
                showType = title.toString();
                mAdapter.makeType(showType);
            }
            typePopupWindow.dismiss();
        }
    };

    private Handler mHandler = new Handler() {
        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
        @Override
        public void handleMessage(Message msg) {
            if (mRefreshView != null) {
                mRefreshView.setRefreshing(false);
                mProgressBar.setVisibility(View.GONE);
                if (msg.what == 1) {
                } else if (msg.what == 2) {  //收藏成功
                } else if (msg.what == 3) {//取消收藏成功
                } else if (msg.what == 4) {
                } else if (msg.what == 5) {
                    mPost.callOnClick();
                } else {
                    RednetUtils.toast(ForumDetailsActivity.this, "请求失败");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_details);
        mFid = getIntent().getStringExtra("fid");
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mPost = (ImageView) findViewById(R.id.post);
        mTitle = (TextView) findViewById(R.id.title);
        mTypeTag = (ImageView) findViewById(R.id.type_tag);
        mForumTitle = (TextView) findViewById(R.id.forum_title);
        mForumDesc = (TextView) findViewById(R.id.forum_desc);
        mFavLabel = (TextView) findViewById(R.id.fav);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mRefreshView = (NestedSwipeRefreshLayout) findViewById(R.id.pulltorefresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new ForumDetailsRecyclerAdapter(this, mRecyclerView, mFid, this));
        mThreadPostPopup = new ThreadPostPopup(this, null, mPostClickListener);
        typePopupWindow = new ForumGroupPopupWindow(this, mGroupClickListener);
        mTitle.setOnClickListener(this);
        mPost.setOnClickListener(this);
        mFavLabel.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        typePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mTypeTag.setImageResource(R.drawable.arrow_t);
            }
        });

        ApiVersion5.INSTANCE.requestPostsOfForum(true, mFid, page = 1, true,
                new ApiVersion5.Result<Object>(this, ForumDetailsVariables.class, true, null, false) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                ForumDetailsVariables variables = ((BaseModel<ForumDetailsVariables>) value).getVariables();
                                updateHeader(variables.getForum());
                                if (variables.getForum_threadlist() != null)
                                    mAdapter.setData(new ArrayList<>(variables.getForum_threadlist()));
                            }
                            mProgressBar.setVisibility(View.GONE);
                            mRefreshView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mRefreshView.setRefreshing(true);
                                    onRefresh();
                                }
                            }, 500);
                        }
                    }
                });
    }

    public static void start(Context context, String fid) {
        context.startActivity(new Intent(context, ForumDetailsActivity.class).putExtra("fid", fid));
    }

    void updateHeader(Forum forum) {
        if (forum != null) {
            mForum = forum;
            mTitle.setText(forum.getName());
            mForumTitle.setText(forum.getName());
            String desc = getString(R.string.forum_desc_today) + "<font color='#3c96d6'>" + forum.getTodayposts() + "</font>" + "  " + getString(R.string.forum_desc_threads) + "<font color='#3c96d6'>" + forum.getThreads() + "</font>";
            mForumDesc.setText(Html.fromHtml(desc));
            if (RedNetApp.getInstance().isLogin()) {
                mUserForum = UserForum.has(this, RedNetApp.getInstance().getUid(), mFid);
                mFavLabel.setSelected(mUserForum != null);
            } else {
                mFavLabel.setSelected(false);
            }
            mFavLabel.setText(mFavLabel.isSelected() ? "取消" : "收藏");
//            mThreadPostPopup = new ThreadPostPopup(this, forum.getAllowpostspecial(), mPostClickListener);
        }
    }

    void load(final boolean refresh) {
        ApiVersion5.INSTANCE.requestPostsOfForum(false, mFid, refresh ? page = 1 : ++page, true,
                new ApiVersion5.Result<Object>(this, ForumDetailsVariables.class, false, refresh ? null : mAdapter.getFooter(), true) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (mRefreshView != null) {
                            if (value instanceof BaseModel) {
                                ForumDetailsVariables variables = ((BaseModel<ForumDetailsVariables>) value).getVariables();
                                if (variables != null) {
                                    if (refresh) {
                                        updateHeader(variables.getForum());
                                        if (variables.getForum_threadlist() != null && variables.getForum_threadlist().size() > 0) {
                                            mAdapter.setData(new ArrayList<>(variables.getForum_threadlist()));
                                            if (mAdapter.getFooter() != null) {
                                                mAdapter.getFooter().reset();
                                            }
                                        }
                                    } else {
                                        mAdapter.setAppendData(new ArrayList<>(variables.getForum_threadlist()));
                                    }
                                    if (mAdapter.getFooter() != null) {
                                        if (variables.getForum().getThreads() <= page * variables.getTpp()) {
                                            mAdapter.getFooter().finishAll();
                                        } else {
                                            mAdapter.getFooter().finish();
                                        }
                                    }
                                }
                            }
                            mProgressBar.setVisibility(View.GONE);
                            mRefreshView.setRefreshing(false);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post:
                if (RednetUtils.hasLogin(this)) {
                    mThreadPostPopup.showAsDropDown(v);
                }
                break;
            case R.id.fav:
                if (RednetUtils.networkIsOk(this) && RednetUtils.hasLogin(this)) {
                    mFavLabel.setEnabled(false);
                    if (mFavLabel.isSelected()) {
                        ApiVersion5.INSTANCE.requestUnFavouriteForum(mUserForum.getFavid(), new ApiVersion5.Result<Object>(this, BaseVariablesModel.class, false, null, true) {
                            @Override
                            public void onResult(int code, Object value) {
                                if (code == 200 && value instanceof BaseVariablesModel) {
                                    if (UserForum.delete(ForumDetailsActivity.this, RedNetApp.getInstance().getUid(), mFid)) {
                                        if (mFavLabel != null) {
                                            mFavLabel.setSelected(false);
                                        }
                                    }
                                }
                                if (mFavLabel != null) {
                                    mFavLabel.setText(mFavLabel.isSelected() ? "取消" : "收藏");
                                    mFavLabel.setEnabled(true);
                                }
                            }
                        });
                    } else {
                        ApiVersion5.INSTANCE.requestFavouriteForum(mFid, new ApiVersion5.Result<Object>(this, FavForumVariables.class, false, null, true) {
                            @Override
                            public void onResult(int code, Object value) {
                                if (code == 200 && value instanceof BaseModel) {
                                    FavForumVariables variables = ((BaseModel<FavForumVariables>) value).getVariables();
//                                    if (variables != null && variables.getFavforum() != null) {
//                                        /*陈国注释做到这个页面的时候释放*/
//                                        ContentValues values = UserForum.getContentValues(variables.getFavforum());
//                                        if (values != null) {
//                                            UserForum.save(ForumDetailsActivity.this, values);
//                                            mUserForum = UserForum.has(ForumDetailsActivity.this, RedNetApp.getInstance().getUid(), mFid);
//                                            if (mFavLabel != null) {
//                                                mFavLabel.setSelected(mUserForum != null);
//                                            }
//                                        }
//                                    }
                                }
                                if (mFavLabel != null) {
                                    mFavLabel.setText(mFavLabel.isSelected() ? "取消" : "收藏");
                                    mFavLabel.setEnabled(true);
                                }
                            }
                        });
                    }
                }
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.title:
                typePopupWindow.showAsDropDown(v);
                mTypeTag.setImageResource(R.drawable.arrow_b);
                break;
            default:
                break;
        }
    }

    boolean canPost() {
        if (!RednetUtils.hasLogin(this)) {
            return false;
        } else if (mForum == null) {
            RednetUtils.toast(this, "请刷新重试");
            return false;
        }
//        else if ("0".equals(mForum.getAllowpostspecialString())) {
//            RednetUtils.toast(this, "您还没有发帖权限");
//            return false;
//        }
        else {
            return true;
        }
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            load(true);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onLoad() {
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(this)) {
            load(false);
        } else {
            if (mAdapter.getFooter() != null) {
                mAdapter.getFooter().finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SEND_REQUEST_CODE) {
                mHandler.sendEmptyMessage(5);
            } else if (requestCode == FACOURITE_REQUEST_CODE) {
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("ForumDetailsActivity(版块列表)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("ForumDetailsActivity(版块列表)");
    }

}
