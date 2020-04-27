package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.AllPostBean;
import cn.tencent.DiscuzMob.model.ColecteBean;
import cn.tencent.DiscuzMob.model.MyFavForumBean;
import cn.tencent.DiscuzMob.model.SublistBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.adapter.ForumListAdaper;
import cn.tencent.DiscuzMob.ui.adapter.ListDataAdapter;
import cn.tencent.DiscuzMob.ui.adapter.RecommendAdapter;
import cn.tencent.DiscuzMob.ui.adapter.TopAdapter;
import cn.tencent.DiscuzMob.utils.DensityUtil;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.StringUtil;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.widget.transformer.ListViewForScrollView;

/*版块的详情*/
public class ForumListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, ILoadListener {
    protected ViewAnimator mTip;
    protected NestedSwipeRefreshLayout mRefreshView;
    protected ListView mListView;
    private View vHead;
    private boolean isCollection;
    private FooterForList mFooter;
    private ImageView iv_edit;
    private FrameLayout toolbar;
    /*HeadView的控件-------------------------------------------------------------------*/
    private ImageView iv_item;
    private TextView tv_forum, tv_collect, tv_today, tv_theme_count, tv_ranking, tv_sub,
            tv_all, tv_new, tv_hot, tv_hot_forum, tv_essence;
    private ImageView iv_collection, iv_show;
    private LinearLayout ll_sublist, ll_all, ll_new, ll_hot, ll_hot_forum, ll_essence;
    private RelativeLayout rl_show, rl_collection;
    private ListViewForScrollView toplist;
    private ImageView iv_nothing;
    /*数据--------------------------------------------------------------------------------*/
    private RecommendAdapter adapter;
    private String favid;
    private String cookiepre_auth;
    private String cookiepre_saltkey;
    private static final String LASTPOST = "&filter=lastpost&orderby=lastpost";//最新
    private static final String HEAT = "&filter=heat&orderby=heats";//热门
    private static final String HOT = "&filter=heat&orderby=heats";//热帖
    private static final String DIGEST = "&filter=digest&digest=1";// 精华
    //    private List<JSONArray> list = new ArrayList();
    private JSONArray list = new JSONArray();
    private List<JSONObject> top = new ArrayList<>();
    private String type;
    private int page;
    String fid;
    private String threads;
    private PopupWindow mPopWindow;
    private Intent intent;
    private ListViewForScrollView subListView;
    private ListDataAdapter listDataAdapter;
    private boolean isSub = false;
    private boolean isShow = false;
    String formhash;
    String allowpost;//是否允许发帖
    String allowspecialonly;//判断允许发帖的类型
    String allowpostactivity;//判断是否允许发活动帖
    String allowpostdebate;//判断是否允许发辩论帖
    private String allowpostpoll;//判断是否允许发投票贴
    private String allowpostthread;//判断是否允许发一般帖
    private HashMap<String, String> postType;
    private LinearLayout ll_top;
    private RelativeLayout rl_threads;
    private ArrayList<String> activityType;
    private ArrayList<String> activityfieldKeys;
    private ArrayList<String> activityfieldValues;
    private ForumListAdaper forumListAdaper;
    private String mIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_forum_list_activiy);
        rl_threads = (RelativeLayout) findViewById(R.id.rl_threads);
        iv_nothing = (ImageView) findViewById(R.id.iv_nothing);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_edit.setVisibility(View.VISIBLE);
        iv_edit.setOnClickListener(this);
        toolbar = (FrameLayout) findViewById(R.id.toolbar);
        cookiepre_auth = CacheUtils.getString(this, "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(this, "cookiepre_saltkey");
        mTip = (ViewAnimator) findViewById(R.id.tip);
        mRefreshView = (NestedSwipeRefreshLayout) findViewById(R.id.refresh);
        mListView = (ListView) findViewById(R.id.list);
        vHead = View.inflate(this, R.layout.activity_forum_list_headview, null);
        rl_show = (RelativeLayout) vHead.findViewById(R.id.rl_show);
        rl_show.setOnClickListener(this);
        iv_item = (ImageView) vHead.findViewById(R.id.iv_item);
        iv_show = (ImageView) vHead.findViewById(R.id.iv_show);
        iv_collection = (ImageView) vHead.findViewById(R.id.iv_collection);
        tv_forum = (TextView) vHead.findViewById(R.id.tv_forum);
        tv_collect = (TextView) vHead.findViewById(R.id.tv_collect);
        tv_sub = (TextView) vHead.findViewById(R.id.tv_sub);
        tv_today = (TextView) vHead.findViewById(R.id.tv_today);
        tv_theme_count = (TextView) vHead.findViewById(R.id.tv_theme_count);
        tv_all = (TextView) vHead.findViewById(R.id.tv_all);
        tv_new = (TextView) vHead.findViewById(R.id.tv_new);
        tv_hot = (TextView) vHead.findViewById(R.id.tv_hot);
        tv_hot_forum = (TextView) vHead.findViewById(R.id.tv_hot_forum);
        tv_essence = (TextView) vHead.findViewById(R.id.tv_essence);
        tv_ranking = (TextView) vHead.findViewById(R.id.tv_ranking);
        ll_sublist = (LinearLayout) vHead.findViewById(R.id.ll_sublist);
        ll_top = (LinearLayout) vHead.findViewById(R.id.ll_top);
        rl_show = (RelativeLayout) vHead.findViewById(R.id.rl_show);
        rl_collection = (RelativeLayout) vHead.findViewById(R.id.rl_collection);
        subListView = (ListViewForScrollView) vHead.findViewById(R.id.listview);
        toplist = (ListViewForScrollView) vHead.findViewById(R.id.toplist);
        tv_all.setOnClickListener(this);
        tv_new.setOnClickListener(this);
        tv_hot.setOnClickListener(this);
        tv_hot_forum.setOnClickListener(this);
        tv_essence.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        rl_collection.setOnClickListener(this);
        listDataAdapter = new ListDataAdapter();
        subListView.setAdapter(listDataAdapter);
        mListView.addHeaderView(vHead);
        adapter = new RecommendAdapter(this);
        forumListAdaper = new ForumListAdaper(this);
        mListView.setAdapter(forumListAdaper);
        tv_all.setSelected(true);
        type = "";
        mRefreshView.setOnRefreshListener(this);
        mFooter = new FooterForList(mListView);
        mFooter.setOnLoadListener(this);
        mTip.setDisplayedChild(1);
        fid = getIntent().getStringExtra("fid");
        Intent intent = getIntent();
        String threads = intent.getStringExtra("threads");
        String todayposts = intent.getStringExtra("todayposts");
        String name = intent.getStringExtra("name");
        setData(threads, todayposts, name);
        iv_show.setImageResource(R.drawable.right);
        subListView.setVisibility(View.GONE);
        rl_threads.setBackgroundColor(Color.parseColor("#f0eff5"));
        onRefresh();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                tv_all.setSelected(true);
                tv_new.setSelected(false);
                tv_hot.setSelected(false);
                tv_hot_forum.setSelected(false);
                tv_essence.setSelected(false);
                type = "";
                onRefresh();
                break;
            case R.id.tv_new:
                tv_all.setSelected(false);
                tv_new.setSelected(true);
                tv_hot.setSelected(false);
                tv_hot_forum.setSelected(false);
                tv_essence.setSelected(false);
                type = HEAT;
                onRefresh();
                break;
            case R.id.tv_hot:
                tv_all.setSelected(false);
                tv_new.setSelected(false);
                tv_hot.setSelected(true);
                tv_hot_forum.setSelected(false);
                tv_essence.setSelected(false);
                type = HEAT;
                onRefresh();
                break;
            case R.id.tv_hot_forum:
                tv_all.setSelected(false);
                tv_new.setSelected(false);
                tv_hot.setSelected(false);
                tv_hot_forum.setSelected(true);
                tv_essence.setSelected(false);
                type = HOT;
                onRefresh();
                break;
            case R.id.tv_essence:
                tv_all.setSelected(false);
                tv_new.setSelected(false);
                tv_hot.setSelected(false);
                tv_hot_forum.setSelected(false);
                tv_essence.setSelected(true);
                type = DIGEST;
                onRefresh();
                break;
            case R.id.rl_collection://收藏
                if (RednetUtils.hasLogin(this)) {
                    if (tv_collect.getText().equals("收藏")) {
                        CollectForum();
                    } else {
                        CancelCollection();
                    }
                }
                break;
            case R.id.back:
                if (isSub == false) {
                    finish();
                } else {
                    isSub = false;
                    Intent intent = getIntent();
                    fid = intent.getStringExtra("fid");
                    String threads = intent.getStringExtra("threads");
                    String todayposts = intent.getStringExtra("todayposts");
                    String name = intent.getStringExtra("name");
                    setData(threads, todayposts, name);
                    onRefresh();
                }

                break;
            case R.id.iv_edit:
                if (RednetUtils.hasLogin(this)) {
                    if (null != allowpost && allowpost.equals("1")) {
                        backgroundAlpha(0.5f);
                        allowpostactivity = postType.get("allowpostactivity");
                        allowpostdebate = postType.get("allowpostdebate");
                        allowpostpoll = postType.get("allowpostpoll");
                        if (null != allowspecialonly && allowspecialonly.equals("1")) {
                            allowpostthread = "0";
                            showPopupWindow(allowpostthread, allowpostactivity, allowpostdebate, allowpostpoll);
                        } else {
                            allowpostthread = "1";
                            showPopupWindow(allowpostthread, allowpostactivity, allowpostdebate, allowpostpoll);
                        }

                    } else {
                        Toast.makeText(ForumListActivity.this, "没有权限发帖", Toast.LENGTH_SHORT).show();
                    }
                }


                break;

            case R.id.ll_ordinary:
                backgroundAlpha(1f);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }
                if (RednetUtils.hasLogin(this)) {
                    intent = new Intent(this, SendThreadActivity.class);
                    if (types != null) {
                        intent.putExtra("types", types.toString());
                    }
                    intent.putExtra("fid", getIntent().getStringExtra("fid"));
                    startActivity(intent);
                }

                break;

            case R.id. ll_vote:

                backgroundAlpha(1f);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }
                if (RednetUtils.hasLogin(this)) {
                    intent = new Intent(this, SendVoteThreadActivity.class);
                    if (types != null) {
                        intent.putExtra("types", types.toString());
                    }
                    intent.putExtra("fid", getIntent().getStringExtra("fid"));
                    startActivity(intent);
                }
                break;

            case R.id.ll_activity:
                backgroundAlpha(1f);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }
                if (RednetUtils.hasLogin(this) && activityType != null) {
                    intent = new Intent(this, SendActivityThreadActivity.class);
                    intent.putExtra("fid", getIntent().getStringExtra("fid"));
                    Log.e("TAG", "activityType=" + activityType.toString());
                    intent.putExtra("activityType", (Serializable) activityType);
                    intent.putExtra("activityfieldKeys", (Serializable) activityfieldKeys);
                    intent.putExtra("activityfieldValues", (Serializable) activityfieldValues);
                    if (types != null) {
                        intent.putExtra("types", types.toString());
                    }
                    startActivity(intent);
                }
                break;
            case R.id.tv_close:
                backgroundAlpha(1f);
                mPopWindow.dismiss();
                mPopWindow = null;
                break;
            case R.id.rl_show:
                if (isShow == true) {
                    isShow = false;
                    iv_show.setImageResource(R.drawable.right);
                    subListView.setVisibility(View.GONE);
                } else {
                    isShow = true;
                    iv_show.setImageResource(R.drawable.down);
                    subListView.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.ll_debate:
                Toast.makeText(ForumListActivity.this, "发送辩论帖", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (isSub == false) {
                finish();
            } else {
                isSub = false;
                Intent intent = getIntent();
                fid = intent.getStringExtra("fid");
                String threads = intent.getStringExtra("threads");
                String todayposts = intent.getStringExtra("todayposts");
                String name = intent.getStringExtra("name");
                setData(threads, todayposts, name);
                onRefresh();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void getCollectionData() {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.MYFAV)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_collect.setVisibility(View.VISIBLE);
                                iv_collection.setImageResource(R.drawable.collection);
                                tv_collect.setText("收藏");
                                isCollection = false;
                                tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                rl_collection.setBackgroundResource(R.drawable.bg_collections);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String string = response.body().string();
                        try {
                            if (string != null && !TextUtils.isEmpty(string)) {
                                MyFavForumBean myFavForumBean = new Gson().fromJson(string, MyFavForumBean.class);
                                List<MyFavForumBean.VariablesBean.ListBean> list = myFavForumBean.getVariables().getList();
//
                                if (list != null && list.size() > 0) {
                                    Log.e("TAG", "list=" + list.size());
                                    for (int i = 0; i < list.size(); i++) {
                                        if (fid.equals(list.get(i).getId())) {

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    isCollection = true;
                                                    tv_collect.setText("已收藏");
                                                    tv_collect.setTextColor(Color.GRAY);
                                                    iv_collection.setVisibility(View.GONE);
                                                    rl_collection.setBackgroundResource(R.drawable.bg_collection);
//                                                    iv_collection.setImageResource(R.drawable.collection_selete);
                                                }
                                            });
                                            favid = list.get(i).getFavid();
                                            break;
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    tv_collect.setText("收藏");
                                                    tv_collect.setVisibility(View.VISIBLE);
                                                    iv_collection.setImageResource(R.drawable.collection);
                                                    rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                                    tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                                    isCollection = false;
                                                }
                                            });


                                        }
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv_collect.setVisibility(View.VISIBLE);
                                            iv_collection.setImageResource(R.drawable.collection);
                                            tv_collect.setText("收藏");
                                            isCollection = false;
                                            tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                            rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                        }
                                    });


                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_collect.setVisibility(View.VISIBLE);
                                        iv_collection.setImageResource(R.drawable.collection);
                                        tv_collect.setText("收藏");
                                        isCollection = false;
                                        tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                        rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                    }
                                });


                            }


                        } catch (JsonSyntaxException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_collect.setVisibility(View.VISIBLE);
                                    iv_collection.setImageResource(R.drawable.collection);
                                    rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                    tv_collect.setText("收藏");
                                    isCollection = false;
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
//
    }

    private void updateIcon() {
        if (mIcon != null && !mIcon.isEmpty()) {
            Picasso.with(getApplicationContext()).load(mIcon).into(iv_item);
        }
    }

    private void setData(String threads, String todayposts, String name) {
        if (null != todayposts && todayposts.equals("0")) {
            iv_item.setImageResource(R.drawable.old);
        } else {
            iv_item.setImageResource(R.drawable.newp);
        }
        if (mIcon != null && !mIcon.isEmpty()) {
            Picasso.with(getApplicationContext()).load(mIcon).into(iv_item);
        }
        tv_today.setText(todayposts);
        tv_theme_count.setText(threads);
        tv_forum.setText(name);
        if (CacheUtils.getBoolean(this, "login") == true) {
            getCollectionData();
        } else {
            tv_collect.setVisibility(View.VISIBLE);
            iv_collection.setImageResource(R.drawable.collection);
            tv_collect.setText("收藏");
            isCollection = false;
        }
    }

    private void CancelCollection() {
//        String formhash = CacheUtils.getString(this, "formhash");
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                .url(AppNetConfig.UNCOLLECTION + fid)
                .post(new MultipartBuilder("----kDdwDwoddGegowwdSmoqdaAesgjk")
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("formhash", formhash)
                        .addFormDataPart("deletesubmit", "true")
                        .build())
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("TAG", "收藏失败");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForumListActivity.this, "取消收藏请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        ColecteBean colecteBean = null;
//                        Log.e("TAG", "取消收藏="+response.body().string());
//                        Log.e("TAG", "取消收藏url="+AppNetConfig.UNCOLLECTTHEAD + fid + "&type=forum");
                        try {
                            colecteBean = new Gson().fromJson(response.body().string(), ColecteBean.class);
                            ColecteBean.MessageBean message = colecteBean.getMessage();

                            if (null != message) {
                                final String messagestr = message.getMessagestr();
                                String messageval = message.getMessageval();
                                if (messageval.equals("do_success")) {
//                                    iv_collection.setImageResource(R.drawable.collection);
//                                    tv_collect.setText("收藏");
                                    isCollection = false;
//                                    getCollectionData();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            iv_collection.setVisibility(View.VISIBLE);
                                            iv_collection.setImageResource(R.drawable.collection);
                                            tv_collect.setTextColor(getResources().getColor(R.color.blue));
                                            tv_collect.setText("收藏");
                                            rl_collection.setBackgroundResource(R.drawable.bg_collections);
                                            Toast.makeText(ForumListActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ForumListActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                        } catch (JsonSyntaxException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ForumListActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
    }

    //
    private void CollectForum() {
        final String fid = getIntent().getStringExtra("fid");
        LogUtils.i(AppNetConfig.COLLECTION_BANKUAI + "&id=" + fid + "&formhash=" + formhash);
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                .url(AppNetConfig.COLLECTION_BANKUAI + "&id=" + fid + "&formhash=" + formhash)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForumListActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String string = response.body().string();
                        ColecteBean colecteBean = null;
                        LogUtils.i(string);
                        try {
                            colecteBean = new Gson().fromJson(string, ColecteBean.class);
                            final ColecteBean.MessageBean message = colecteBean.getMessage();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (message != null) {
                                        String messagestr = message.getMessagestr();
                                        String messageval = message.getMessageval();
                                        if (messageval.equals("favorite_do_success")) {
                                            isCollection = true;
//                                            getCollectionData();
                                            iv_collection.setVisibility(View.GONE);
                                            tv_collect.setText("已收藏");
                                            tv_collect.setTextColor(Color.GRAY);
                                            rl_collection.setBackgroundResource(R.drawable.bg_collection);
                                            Toast.makeText(ForumListActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ForumListActivity.this, messagestr, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(ForumListActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } catch (JsonSyntaxException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ForumListActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
    }

    private Map<String, Object> strings = new HashMap<>();

    TopAdapter topAdapter;
    JSONObject Variables;
    JSONObject types;

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            forumListAdaper.cleanData();
            if (topAdapter != null) {
                topAdapter.cleanData();
            }
            page = 1;
            Log.e("TAG", "板块详情的url=" + AppNetConfig.BASEURL + "?version=5&module=forumdisplay&submodule=checkpost&fid=" + fid + "&width=160&height=120&page=" + page + "" + "&submodule=checkpost&smiley=no&convimg=1&mobiletype=Android" + type);
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                    .url(AppNetConfig.BASEURL + "?version=5&module=forumdisplay&submodule=checkpost&fid=" + fid + "&width=160&height=120&page=" + page + "" + "&submodule=checkpost&smiley=no&convimg=1&mobiletype=Android" + type)
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                    .enqueue(new com.squareup.okhttp.Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RedNetApp.getInstance(), "网络请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
//                            Log.e("TAG", "帖子列表=" + response.body().string());

                            String s = response.body().string();
                            LogUtils.e("板块详情   ---" + s);
                            JSONObject jso = JSON.parseObject(s);
                            if (null != jso) {
                                Variables = (JSONObject) jso.get("Variables");
                                strings.clear();
                                if (null != Variables.get("groupiconid")) {
                                    for (Map.Entry<String, Object> entry : ((JSONObject) Variables.get("groupiconid")).entrySet()) {
                                        strings.put(entry.getKey(), entry.getValue().toString());
                                    }
                                }
                                JSONObject threadtypes = Variables.getJSONObject("threadtypes");
                                if (null != threadtypes) {
                                    types = threadtypes.getJSONObject("types");
                                }
                                JSONObject activity_setting = Variables.getJSONObject("activity_setting");
                                JSONArray activitytype = activity_setting.getJSONArray("activitytype");
                                JSONObject activityfield = activity_setting.getJSONObject("activityfield");
                                activityfieldKeys = new ArrayList<String>();
                                activityfieldValues = new ArrayList<String>();
                                activityfieldKeys.clear();
                                activityfieldValues.clear();
                                if (null != activityfield) {
                                    for (Map.Entry<String, Object> entry : activityfield.entrySet()) {
                                        activityfieldKeys.add(entry.getKey());
                                        activityfieldValues.add(entry.getValue().toString());
//                                        activityfields.put(entry.getKey(), entry.getValue().toString());
                                    }
                                }
                                activityType = new ArrayList<String>();
                                activityType.clear();
                                for (int i = 0; i < activitytype.size(); i++) {
                                    Object o = activitytype.get(i);
                                    activityType.add(o.toString());
                                }
                                JSONObject allowperm = Variables.getJSONObject("allowperm");
                                JSONObject forum = Variables.getJSONObject("forum");
                                JSONObject group = Variables.getJSONObject("group");
                                final String rank = forum.getString("rank");
                                allowpost = allowperm.getString("allowpost");
                                allowspecialonly = forum.getString("allowspecialonly");
                                mIcon = forum.getString("icon");
                                String uploadhash = allowperm.getString("uploadhash");
                                CacheUtils.putString(ForumListActivity.this, "uploadhash", uploadhash);
                                postType = new HashMap<String, String>();
                                for (Map.Entry<String, Object> entry : (group).entrySet()) {
                                    postType.put(entry.getKey(), entry.getValue().toString());
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_ranking.setText(rank);
                                        updateIcon();
                                    }
                                });
                            }
                            AllPostBean allPostBean = null;
                            try {
                                allPostBean = new Gson().fromJson(jso.toString(), AllPostBean.class);
                                if (mRefreshView != null) {
                                    if (allPostBean != null) {
                                        AllPostBean.VariablesBean variables = allPostBean.getVariables();
//                                        final List<ForumThreadlistBean> forum_threadlist = variables.getForum_threadlist();
                                        final JSONArray forum_threadlist1 = Variables.getJSONArray("forum_threadlist");
                                        final String name = variables.getForum().getName();
                                        threads = variables.getForum().getThreadcount();
                                        formhash = variables.getFormhash();
                                        CacheUtils.putString(ForumListActivity.this, "formhash1", formhash);
                                        final List<SublistBean> sublist = variables.getSublist();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (null != sublist && sublist.size() > 0) {
                                                    ll_sublist.setVisibility(View.VISIBLE);
                                                    tv_sub.setText("子版块 (" + sublist.size() + ")");
                                                    setListData(sublist);
                                                } else {
                                                    ll_sublist.setVisibility(View.GONE);
                                                }
                                                list.clear();
                                                list.addAll(forum_threadlist1);
                                                top.clear();
                                                for (int i = 0; i < forum_threadlist1.size(); i++) {
//                                                    String displayorder = forum_threadlist1.get(i).getDisplayorder();
                                                    String displayorder = forum_threadlist1.getJSONObject(i).getString("displayorder");
                                                    if (!displayorder.equals("0")) {
                                                        list.remove(forum_threadlist1.get(i));
                                                        top.add(forum_threadlist1.getJSONObject(i));
                                                    }
                                                }
                                                list.removeAll(top);
                                                if (top.size() > 0) {
                                                    ll_top.setVisibility(View.VISIBLE);
                                                    topAdapter = new TopAdapter(top, name, fid);
                                                    toplist.setAdapter(topAdapter);
                                                } else {
                                                    ll_top.setVisibility(View.GONE);
                                                }

//                                                adapter.addData(list, name, fid, strings);
                                                forum_threadlist1.removeAll(top);
                                                forumListAdaper.setData(forum_threadlist1, name, fid, strings, types);
                                                forumListAdaper.notifyDataSetChanged();
                                                if (null != threads && !TextUtils.isEmpty(threads) && list.size() < Integer.parseInt(threads)) {
//                                                    iv_nothing.setVisibility(View.GONE);
                                                    mFooter.reset();
                                                } else {
//                                                    iv_nothing.setVisibility(View.VISIBLE);
                                                    mFooter.finishAll();
                                                }
                                            }
                                        });

                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mRefreshView.setRefreshing(false);
                                            if (mTip.getDisplayedChild() != 0) {
                                                mTip.setDisplayedChild(0);
                                            }
                                        }
                                    });

                                }

                            } catch (final JsonSyntaxException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.e("TAG", "错误日志=" + e.getMessage().toString());
                                        Toast.makeText(ForumListActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                                        mRefreshView.setRefreshing(false);
                                        if (mTip.getDisplayedChild() != 0) {
                                            mTip.setDisplayedChild(0);
                                        }
                                    }
                                });

                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            mRefreshView.setRefreshing(false);
        }
    }


    @Override
    public void onLoad() {
        if (list.size() < Integer.parseInt(threads)) {
            ++page;
//            mTip.setDisplayedChild(1);
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                    .url(AppNetConfig.BASEURL + "?version=5&module=forumdisplay&fid=" + fid + "&width=160&height=120&page=" + page + "" + "&submodule=checkpost&smiley=no&convimg=1&mobiletype=Android" + type)
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                    .enqueue(new com.squareup.okhttp.Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ForumListActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            JSONObject jso = null;
                            try {
                                jso = JSON.parseObject(response.body().string());
                                if (null != jso) {
                                    Variables = (JSONObject) jso.get("Variables");
                                    Object groupiconid = Variables.get("groupiconid");
                                    if (null != groupiconid) {
                                        for (Map.Entry<String, Object> entry : ((JSONObject) groupiconid).entrySet()) {

                                            if (!strings.containsKey(entry.getKey())) {
                                                strings.put(entry.getKey(), entry.getValue().toString());
                                            }
                                        }
                                    }

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
            RedNet.mHttpClient.newCall(new Request.Builder()
                    .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey + ";")
                    .url(AppNetConfig.BASEURL + "?version=5&module=forumdisplay&fid=" + fid + "&width=160&height=120&page=" + page + "" + "&submodule=checkpost&smiley=no&convimg=1&mobiletype=Android" + type)
                    .cacheControl(new CacheControl.Builder().noStore().noCache().build())
                    .build())
                    .enqueue(new com.squareup.okhttp.Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RedNetApp.getInstance(), "网络请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {

                            final AllPostBean allPostBean = new Gson().fromJson(response.body().string(), AllPostBean.class);
                            if (mRefreshView != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (allPostBean != null) {
                                            AllPostBean.VariablesBean variables = allPostBean.getVariables();
                                            formhash = variables.getFormhash();
                                            CacheUtils.putString(ForumListActivity.this, "formhash1", formhash);
//                                            cookiepre = variables.getCookiepre();
//                                            List<ForumThreadlistBean> forum_threadlist = variables.getForum_threadlist();
                                            JSONArray forum_threadlist = Variables.getJSONArray("forum_threadlist");
                                            list.addAll(forum_threadlist);
                                            String name = variables.getForum().getName();
                                            if (forum_threadlist.size() > 0) {
//                                        allPostAdapter.insertData(list);
                                                forumListAdaper.addData(forum_threadlist, name, fid, strings);
                                                forumListAdaper.notifyDataSetChanged();
                                                mFooter.reset();
                                            } else {
                                                mFooter.finishAll();
                                            }
                                        }
                                        mRefreshView.setRefreshing(false);
                                        if (mTip.getDisplayedChild() != 0) {
                                            mTip.setDisplayedChild(0);
                                        }
                                    }
                                });

                            }

                        }
                    });
        } else {
            mFooter.finishAll();
            mRefreshView.setRefreshing(false);
        }
    }

    //


    private void showPopupWindow(String allowpostthread, String allowpostactivity, String allowpostdebate, String allowpostpoll) {
        View contentView = LayoutInflater.from(ForumListActivity.this).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(DensityUtil.dip2px(this, 240));
//        mPopWindow.setHeight(DensityUtil.dip2px(this, 240));
        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_ordinary);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_vote);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_activity);
        TextView pop_debate = (TextView) contentView.findViewById(R.id.pop_debate);
        TextView tv_close = (TextView) contentView.findViewById(R.id.tv_close);
        LinearLayout ll_ordinary = (LinearLayout) contentView.findViewById(R.id.ll_ordinary);
        LinearLayout ll_activity = (LinearLayout) contentView.findViewById(R.id.ll_activity);
        LinearLayout ll_vote = (LinearLayout) contentView.findViewById(R.id.ll_vote);
        LinearLayout ll_debate = (LinearLayout) contentView.findViewById(R.id.ll_debate);
        ll_ordinary.setOnClickListener(this);
        ll_activity.setOnClickListener(this);
        ll_vote.setOnClickListener(this);
        ll_debate.setOnClickListener(this);
        int h = 0;
        if (!StringUtil.isEmpty(allowpostthread)) {
            if (allowpostthread.equals("1")) {
                ll_ordinary.setVisibility(View.VISIBLE);
                h = h + 1;
            } else {
                ll_ordinary.setVisibility(View.GONE);
            }
        }else{
            ll_ordinary.setVisibility(View.GONE);
        }
        if (!StringUtil.isEmpty(allowpostactivity)) {
            if (allowpostactivity.equals("1")) {
                ll_activity.setVisibility(View.VISIBLE);
                h = h + 1;
            } else {
                ll_activity.setVisibility(View.GONE);
            }
        }else{
            ll_activity.setVisibility(View.GONE);
        }
        if (!StringUtil.isEmpty(allowpostpoll)) {
            if (allowpostpoll.equals("1")) {
                ll_vote.setVisibility(View.VISIBLE);
                h = h + 1;
            } else {
                ll_vote.setVisibility(View.GONE);
            }
        }else{
            ll_vote.setVisibility(View.GONE);
        }
        if (h==1){
            mPopWindow.setHeight(DensityUtil.dip2px(this, h * 90));
        }else {
            mPopWindow.setHeight(DensityUtil.dip2px(this, h * 70));
        }

        if (!StringUtil.isEmpty(allowpostdebate)) {
            if (allowpostdebate.equals("1")) {
                ll_debate.setVisibility(View.VISIBLE);
            } else {
                ll_debate.setVisibility(View.GONE);
            }
        }else{
            ll_debate.setVisibility(View.GONE);
        }
        tv_close.setOnClickListener(this);

//        tv1.setOnClickListener(this);
//        tv2.setOnClickListener(this);
//        tv3.setOnClickListener(this);
//        pop_debate.setOnClickListener(this);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.showAtLocation(toolbar, Gravity.CENTER_HORIZONTAL, 0, 0);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //改变显示的按钮图片为正常状态
                backgroundAlpha(1f);
            }
        });
    }

    private void setListData(final List<SublistBean> sublist) {
        listDataAdapter.addData(sublist);
        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isSub = true;
                fid = sublist.get(position).getFid();
                String name = sublist.get(position).getName();
                String threads = sublist.get(position).getThreads();
                Log.e("TAG", "子版块的主题数=" + threads);
                String todayposts = sublist.get(position).getTodayposts();
                ll_sublist.setVisibility(View.GONE);
                setData(threads, todayposts, name);
                onRefresh();
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Subscribe
    public void onEventMainThread(ReFreshImg img) {
        Log.e("TAG", "无图模式");
        onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
