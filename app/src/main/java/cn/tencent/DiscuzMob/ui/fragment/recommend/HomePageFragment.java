package cn.tencent.DiscuzMob.ui.fragment.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.db.Modal;
import cn.tencent.DiscuzMob.model.BannerListBean;
import cn.tencent.DiscuzMob.model.ForumThreadlistBean;
import cn.tencent.DiscuzMob.model.HomePageBean;
import cn.tencent.DiscuzMob.model.Hot;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.Event.RefreshUserInfo;
import cn.tencent.DiscuzMob.ui.activity.ActivityThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ForumListActivity;
import cn.tencent.DiscuzMob.ui.activity.SearchActivity;
import cn.tencent.DiscuzMob.ui.activity.SimpleWebActivity;
import cn.tencent.DiscuzMob.ui.activity.ThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.activity.ToolbarActivity;
import cn.tencent.DiscuzMob.ui.activity.VoteThreadDetailsActivity;
import cn.tencent.DiscuzMob.ui.adapter.HomePageAdapter;
import cn.tencent.DiscuzMob.ui.fragment.BaseFragment;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;

import static android.R.attr.type;

/**
 * 推荐
 * Created by cg on 2017/4/7.
 */

public class HomePageFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View mSearch;
    //设置轮播图的发送延迟消息
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //获取当前的图片的位置
//            Log.e("TAG", "viewpager.getCurrentItem()=" + viewpager.getCurrentItem());
//            Log.e("TAG", "recommend.size()=" + recommend.size());
            if (recommend.size() > 0) {
                int currentTime = (viewpager.getCurrentItem() + 1) % recommend.size();
                //设置当前的图片
                viewpager.setCurrentItem(currentTime);
                //移除发送消息
                handler.removeMessages(0);
                //重新发送延迟消息
                handler.sendEmptyMessageDelayed(0, 2000);
            }

        }
    };
    private HomePageAdapter adapter;
    private ImageView iv_nothing;
    private int page;
    private int currentPosition;
    private boolean isAdd;
    private ArrayList<String> tids;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    private ViewPager viewpager;
    private CirclePageIndicator circle;
    private FrameLayout fl_banner;
    protected ViewAnimator mTip;
    protected NestedSwipeRefreshLayout mRefreshView;
    protected ListView mListView;
    View headview;
    List<BannerListBean.IwesetBean.RecommendBean> recommend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        mTip = (ViewAnimator) view.findViewById(R.id.tip);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mListView = (ListView) view.findViewById(R.id.list);
        headview = View.inflate(RedNetApp.getAppContext(), R.layout.recommend_headview, null);
        iv_nothing = (ImageView) headview.findViewById(R.id.iv_nothing);
        mSearch = view.findViewById(R.id.search);
        viewpager = (ViewPager) headview.findViewById(R.id.viewpager);
        circle = (CirclePageIndicator) headview.findViewById(R.id.circle);
        circle.setFillColor(getResources().getColor(R.color.blue));
        fl_banner = (FrameLayout) headview.findViewById(R.id.fl_banner);
        adapter = new HomePageAdapter(getActivity());
        mListView.setAdapter(adapter);
        mRefreshView.setOnRefreshListener(this);
        mTip.setDisplayedChild(1);
//        mFooter = new FooterForList(mListView);
//        mFooter.setOnLoadListener(this);
        return view;
    }

    List<Hot.VariablesBean.DataBean> data;
    HomeTopViewPagerAdapter homeTopViewPagerAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
//        getImgFromNet();
        onRefresh();
    }

//    private void getImgFromNet() {
//        OkHttpUtils.get()
//                .url(AppNetConfig.IMAGES)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        fl_banner.setVisibility(View.GONE);
//                        Toast.makeText(RedNetApp.getInstance(), "网络请求失败", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        if (response != null && !TextUtils.isEmpty(response) && !response.contains("board_closed")) {
//                            try {
//                                LogUtils.i(response);
//                                BannerListBean bannerListBean = new Gson().fromJson(response, BannerListBean.class);
//                                if (bannerListBean != null) {
//                                    recommend = bannerListBean.getIweset().getRecommend();
//                                    if (null != recommend && recommend.size() > 0) {
//                                        int headerViewsCount = mListView.getHeaderViewsCount();
//                                        if (headerViewsCount == 0) {
////                                            mListView.removeAllViews();
//                                            mListView.addHeaderView(headview);
//                                        }
//                                        // 轮播图显示的当前页
//                                        currentPosition = Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % recommend.size());
//                                        homeTopViewPagerAdapter = new HomeTopViewPagerAdapter(recommend);
//                                        viewpager.setAdapter(homeTopViewPagerAdapter);
//                                        circle.setViewPager(viewpager);
//                                        handler.sendEmptyMessageDelayed(0, 3000);
//                                    } else {
//                                        fl_banner.setVisibility(View.GONE);
//                                    }
//                                } else {
//                                    fl_banner.setVisibility(View.GONE);
//                                }
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mRefreshView.setRefreshing(false);
//                                        mTip.setDisplayedChild(0);
//                                    }
//                                });
//                            } catch (Exception e) {
//                                fl_banner.setVisibility(View.GONE);
//                                e.printStackTrace();
//                            }
//                        } else {
//                            fl_banner.setVisibility(View.GONE);
//                        }
//                    }
//                });
//    }


    @Override
    public void onClick(View v) {
        if (v.equals(mSearch)) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        } else {
            if (v.getId() == R.id.label) {
                if (RednetUtils.networkIsOk(getActivity())) {
                    onRefresh();
                }
            }
        }
    }

    void initToolbar() {
        if (getSupportToolbar() != null) {
            getSupportToolbar().show(ToolbarActivity.Toolbar.DEFAULT);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initToolbar();
        }
    }

    @Override
    public void onRefresh() {
//        mRefreshView.setRefreshing(false);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTip.setDisplayedChild(0);
                if (homeTopViewPagerAdapter != null) {
                    homeTopViewPagerAdapter.cleanData();
                }
                adapter.cleanData();
//                getImgFromNet();
                if (RednetUtils.networkIsOk(RedNetApp.getInstance())) {
                    load(false);
                } else {
                    mRefreshView.setRefreshing(false);
                }
            }
        });


    }

    private List<HomePageBean.VariablesBean.RecommonthreadListBean> list = new ArrayList<>();
    private Map<String, String> strings = new HashMap<>();
    private Map<String, String> forums = new HashMap<>();
    private JSONObject types;

    private void load(final boolean append) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey") + ";")
                .url(AppNetConfig.RECOMMEND)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshView.setRefreshing(false);
                                mTip.setDisplayedChild(0);
                                Toast.makeText(RedNetApp.getInstance(), "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        JSONObject jso = JSON.parseObject(response.body().string());
                        if (null != jso) {
                            final JSONObject Variables = (JSONObject) jso.get("Variables");
                            if (null != Variables) {
                                strings.clear();
                                Object groupiconid = Variables.get("groupiconid");
                                if (null != groupiconid) {
                                    for (Map.Entry<String, Object> entry : ((JSONObject) groupiconid).entrySet()) {
                                        strings.put(entry.getKey(), entry.getValue().toString());
                                    }
                                }
                                JSONObject threadtypes = Variables.getJSONObject("threadtypes");
                                if (null != threadtypes) {
                                    types = threadtypes.getJSONObject("types");
                                }
                                CacheUtils.putString(RedNetApp.getInstance(), "cookiepre", Variables.getString("cookiepre"));
                                CacheUtils.putString(RedNetApp.getInstance(), "formhash", Variables.getString("formhash"));
                                CacheUtils.putForeverString(RedNetApp.getInstance(), "formhash", Variables.getString("formhash"));
                                final JSONArray recommonthread_list = Variables.getJSONArray("recommonthread_list");
                                if (null != recommonthread_list && recommonthread_list.size() > 0) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.cleanData();
                                            adapter.addData(recommonthread_list, strings, Variables.getString("formhash"), types);
                                        }
                                    });

                                } else {
//                                    getActivity().runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            iv_nothing.setVisibility(View.VISIBLE);
//                                        }
//                                    });

                                }

                            } else {
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
////                                        iv_nothing.setVisibility(View.VISIBLE);
//                                    }
//                                });
                            }


                        } else {
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    iv_nothing.setVisibility(View.VISIBLE);
//                                }
//                            });
                        }


                    }
                });

    }


    private class HomeTopViewPagerAdapter extends PagerAdapter {
        List<BannerListBean.IwesetBean.RecommendBean> list;

        public HomeTopViewPagerAdapter(List<BannerListBean.IwesetBean.RecommendBean> list) {
            this.list = list;
        }

        public void cleanData() {
            if (list != null) {
                list.clear();
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            AsyncRoundedImageView iv_picture = new AsyncRoundedImageView(container.getContext());
            View inflate = View.inflate(RedNetApp.getInstance(), R.layout.banner_item, null);
            ImageView banner = (ImageView) inflate.findViewById(R.id.iv_picture);
            TextView titie = (TextView) inflate.findViewById(R.id.tv_title);
            //获取集合数据中指定位置图片的url，联网将图片下载并设置给imageView
            String imageUrl = list.get(position).getImageurl();
            final String link = list.get(position).getLink();
//            String url = AppNetConfig.IMGURL + imageUrl;
            //获取图片
//            iv_picture.load(imageUrl);
            int imageSetting = RedNetPreferences.getImageSetting();
            if (imageSetting == 0) {
                Picasso.with(RedNetApp.getInstance())
                        .load(imageUrl)
                        .into(banner);
            } else {
                banner.setImageResource(R.drawable.wutu);
            }

            titie.setText(list.get(position).getTitle());

            onTouchViewPager(inflate, position, link, list.get(position).getTitle());
            container.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void onTouchViewPager(final View inflate, final int position, final String link, final String title) {
        inflate.setOnTouchListener(new View.OnTouchListener() {

            private long downTime;
            private int downX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:// 按下去时，记录按下的坐标和时间，用于判断是否是点击事件
                        handler.removeCallbacksAndMessages(null);// 手指按下时，取消所有事件，即轮播图不在滚动了
                        downX = (int) event.getX();
                        downTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:// 抬起手指时，判断落下抬起的时间差和坐标，符合以下条件为点击
                        // Toast.makeText(context, "手指抬起了", 0).show();
                        if (System.currentTimeMillis() - downTime < 500
                                && Math.abs(downX - event.getX()) < 30) {// ★考虑到手按下和抬起时的坐标不可能完全重合，这里给出30的坐标偏差
                            // 点击事件被触发
//                            RednetUtils.newWebIntent(RedNetApp.getInstance(), link);
//                            Toast.makeText(RedNetApp.getInstance(),
//                                    "这里就不弹出对应页面了，您打开的是第" + position + "张图片", Toast.LENGTH_SHORT)
//                                    .show();
                            String link_type = recommend.get(position).getLink_type();
                            if (link_type.equals("1") && null != link && !TextUtils.isEmpty(link)) {//link == tid
                                RedNet.mHttpClient.newCall(new Request.Builder()
                                        .url(AppNetConfig.BASEURL + "?module=viewthread&tid=" + link + "&submodule=checkpost&version=5&page=" + 1 + "&width=360&height=480&checkavatar=1&submodule=checkpost")
                                        .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                                        .enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Request request, IOException e) {
                                            }

                                            @Override
                                            public void onResponse(Response response) throws IOException {
                                                JSONObject jsonObject = JSON.parseObject(response.body().string());
                                                if (null != jsonObject) {
                                                    JSONObject variables = jsonObject.getJSONObject("Variables");
                                                    if (null != variables) {
                                                        JSONObject thread = variables.getJSONObject("thread");
                                                        if (null != thread) {
                                                            String tid1 = thread.getString("tid");
                                                            long count = Modal.getInstance().getUserAccountDao().getCount();
                                                            List<ForumThreadlistBean> data = Modal.getInstance().getUserAccountDao().getScrollData(0, count);

                                                            if (null != data) {
                                                                if (data.size() > 0) {
                                                                    tids = new ArrayList<String>();
                                                                    for (int i = 0; i < data.size(); i++) {
                                                                        String tid = data.get(i).getTid();
                                                                        tids.add(tid);
                                                                    }
                                                                    if (!tids.contains(tid1)) {
                                                                        isAdd = true;
                                                                    } else {
                                                                        isAdd = false;
                                                                    }
                                                                } else {
                                                                    isAdd = true;
                                                                }

                                                            } else {
                                                                isAdd = false;
                                                            }
                                                            if (isAdd == true) {
                                                                ForumThreadlistBean forumThreadlistBean = new ForumThreadlistBean();
                                                                forumThreadlistBean.setAuthor(thread.getString("author"));
                                                                forumThreadlistBean.setAvatar("");
                                                                forumThreadlistBean.setTid(tid1);
                                                                forumThreadlistBean.setSpecial(thread.getString("special"));
                                                                forumThreadlistBean.setSubject(thread.getString("subject"));
                                                                forumThreadlistBean.setViews(thread.getString("views"));
                                                                forumThreadlistBean.setReplies(thread.getString("replies"));
                                                                forumThreadlistBean.setDateline(thread.getString("dateline"));
                                                                forumThreadlistBean.setRecommend_add(thread.getString("recommend_add"));
                                                                forumThreadlistBean.setDigest(thread.getString("digest"));
                                                                forumThreadlistBean.setDisplayorder(thread.getString("displayorder"));
                                                                forumThreadlistBean.setLevel("");
                                                                forumThreadlistBean.setImglist(null);
                                                                Modal.getInstance().getUserAccountDao().addAccount(forumThreadlistBean);
                                                            }
                                                            String special = thread.getString("special");
                                                            Class claz;
                                                            special = TextUtils.isEmpty(special) ? "0" : special;
                                                            if ("1".equals(special)) {
                                                                claz = VoteThreadDetailsActivity.class;
                                                            } else if ("4".equals(special)) {
                                                                claz = ActivityThreadDetailsActivity.class;
                                                            } else {
                                                                claz = ThreadDetailsActivity.class;
                                                            }
                                                            Intent intent = new Intent(getActivity(), claz);
                                                            intent.putExtra("id", thread.getString("tid"));
                                                            intent.putExtra("title", thread.getString("forumnames"));
                                                            intent.putExtra("fid", thread.getString("fid"));
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            } else if (link_type.equals("2") && null != link && !TextUtils.isEmpty(link)) {//link == fid
                                RedNet.mHttpClient.newCall(new Request.Builder()
                                        .url(AppNetConfig.BASEURL + "?version=5&module=forumdisplay&fid=" + link + "&width=160&height=120&page=" + page + "" + "&submodule=checkpost&smiley=no&convimg=1&mobiletype=Android" + type)
                                        .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                                        .enqueue(new com.squareup.okhttp.Callback() {
                                            @Override
                                            public void onFailure(Request request, IOException e) {

                                            }

                                            @Override
                                            public void onResponse(Response response) throws IOException {
                                                JSONObject jso = JSON.parseObject(response.body().string());
                                                if (null != jso) {
                                                    JSONObject Variables = (JSONObject) jso.get("Variables");
                                                    JSONObject forum = Variables.getJSONObject("forum");
                                                    if (null != forum) {

                                                        for (Map.Entry<String, Object> entry : (forum).entrySet()) {
                                                            forums.put(entry.getKey(), entry.getValue().toString());
                                                        }
                                                    }
                                                    Intent intent = new Intent(getActivity(), ForumListActivity.class);
                                                    intent.putExtra("threads", forums.get("threads"));
                                                    intent.putExtra("todayposts", 0);
                                                    intent.putExtra("fid", forums.get("fid"));
                                                    intent.putExtra("name", forums.get("name"));
                                                    startActivity(intent);
                                                }
                                            }
                                        });

                            } else {
                                Intent intent = new Intent(getActivity(), SimpleWebActivity.class);
                                intent.putExtra("title", title);
                                intent.putExtra("url", link);
                                intent.putExtra("fromIndex", false);
                                startActivity(intent);
                            }

                        }
                        startRoll();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        // ★写这个的目的为了让用户在手指滑动完图片后，能够让轮播图继续自动滚动
                        startRoll();
                        break;
                }
                return true;
            }
        });
    }

    private void startRoll() {
        if (homeTopViewPagerAdapter == null) {
            // 1.第一次初始化适配器
            homeTopViewPagerAdapter = new HomeTopViewPagerAdapter(recommend);
            viewpager.setAdapter(homeTopViewPagerAdapter);
            viewpager.setCurrentItem(currentPosition);
        } else {// 8.第二次，只需要通知适配器数据发生了变化，要刷新Ui
            homeTopViewPagerAdapter.notifyDataSetChanged();
        }
        // 2.发送一个延时的消息，3秒后执行runnableTask类里run方法里的操作
        // ★（为什么执行的是runnableTask，而不是handleMessage呢？这里涉及到handler消息机制源码解析）
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(ReFreshImg img) {
        Log.e("TAG", "无图模式");
        onRefresh();
    }

    @Subscribe
    public void onEventMainThread(RefreshUserInfo finish) {
        String id = finish.getId();
        Log.e("TAG", "id=" + id);
        if (id != null && !TextUtils.isEmpty(id)) {
//            onRefresh();
            onRefresh();
        }
    }
}
