package cn.tencent.DiscuzMob.ui.fragment.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.LiveThredBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.Event.ReFreshImg;
import cn.tencent.DiscuzMob.ui.activity.LiveDetialActivity;
import cn.tencent.DiscuzMob.ui.adapter.HotLiveAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.R;


/**
 * Created by cg on 2017/5/10.
 * 直播界面
 */

public class LiveFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListener, View.OnClickListener {
    private List<Integer> ints;
    private List<String> strings;
    private static final int MESSAGE_LOGIN = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    RednetUtils.toast(RedNetApp.getInstance(), "获取直播失败");
                    mTip.setDisplayedChild(0);
                    break;
                case 1:
                    RednetUtils.toast(RedNetApp.getInstance(), "请求异常");
                    mTip.setDisplayedChild(0);
                    break;
                case MESSAGE_LOGIN:
                    getLiveThred(false);
                    break;
            }
        }
    };
    private int mPage;
    HotLiveAdapter hotLiveAdapter;
    //    private LinearLayout ll_img;
//    private RelativeLayout rl_one, rl_two, rl_three;
    private LinearLayout rl_one;
    private ImageView iv_one, iv_two, iv_three;
    private TextView tv_one, tv_two, tv_three;
    private LinearLayout ll_one, ll_two, ll_three;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View header = View.inflate(RedNetApp.getInstance(), R.layout.live_header, null);
        mListView.addHeaderView(header);
//        ll_img = (LinearLayout) header.findViewById(R.id.ll_img);
        rl_one = (LinearLayout) header.findViewById(R.id.rl_one);
        ll_one = (LinearLayout) header.findViewById(R.id.ll_one);
        ll_two = (LinearLayout) header.findViewById(R.id.ll_two);
        ll_three = (LinearLayout) header.findViewById(R.id.ll_three);
//        rl_two = (RelativeLayout) header.findViewById(rl_two);
//        rl_three = (RelativeLayout) header.findViewById(rl_three);
        iv_one = (ImageView) header.findViewById(R.id.iv_one);
        iv_two = (ImageView) header.findViewById(R.id.iv_two);
        iv_three = (ImageView) header.findViewById(R.id.iv_three);
        tv_one = (TextView) header.findViewById(R.id.tv_one);
        tv_two = (TextView) header.findViewById(R.id.tv_two);
        tv_three = (TextView) header.findViewById(R.id.tv_three);
        iv_one.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_two.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_three.setScaleType(ImageView.ScaleType.FIT_XY);
        mTip.setDisplayedChild(1);
        iv_one.setOnClickListener(this);
        iv_two.setOnClickListener(this);
        iv_three.setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mPage = 1;
        ints = new ArrayList<>();
        strings = new ArrayList<>();
        ints.add(R.drawable.picture);
        ints.add(R.drawable.picture);
        ints.add(R.drawable.picture);
        for (int i = 0; i < 3; i++) {
            strings.add("第" + i + "张");
        }
        mListView.setDividerHeight(0);
        hotLiveAdapter = new HotLiveAdapter();
        mListView.setAdapter(hotLiveAdapter);
        onRefresh();//获取直播列表的数据
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private Map<String, Object> lvs = new HashMap<>();
    private Map<String, Object> lvs1 = new HashMap<>();
    JSONArray recommonthread_list;
    String formhash;

    private void getLiveThred(final boolean appeand) {
        OkHttpUtils.get()
                .url(AppNetConfig.LIVETOADY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(String response) {
                        if (null != response) {
                            JSONObject jsonObject = JSON.parseObject(response);
                            if (null != jsonObject) {
                                JSONObject variables = jsonObject.getJSONObject("Variables");
                                if (null != variables) {
                                    formhash = variables.getString("formhash");
                                    CacheUtils.putString(RedNetApp.getInstance(), "formhash", formhash);
//                                    CacheUtils.putString(RedNetApp.getInstance(), "formhash1", formhash);
                                    recommonthread_list = variables.getJSONArray("recommonthread_list");
                                    if (null != (JSONObject) variables.get("groupiconid")) {
                                        for (Map.Entry<String, Object> entry : ((JSONObject) variables.get("groupiconid")).entrySet()) {
                                            lvs1.put(entry.getKey(), entry.getValue().toString());
                                        }
                                    }

                                    if (null != recommonthread_list && RedNetPreferences.getImageSetting() != 1) {
                                        int size = recommonthread_list.size();
                                        Log.e("TAG", "size=" + size);
                                        if (size >= 3) {
                                            setData(recommonthread_list);
                                        } else {
                                            if (size == 0) {
                                                rl_one.setVisibility(View.GONE);
                                            } else if (size == 1) {
                                                ll_one.setVisibility(View.VISIBLE);
                                                rl_one.setVisibility(View.VISIBLE);
                                                iv_one.setVisibility(View.VISIBLE);
                                                tv_one.setVisibility(View.VISIBLE);
                                                JSONObject jsonObject1 = recommonthread_list.getJSONObject(size - 1);
                                                String subject = jsonObject1.getString("subject");
                                                JSONObject imagelist = jsonObject1.getJSONObject("imagelist");
                                                rl_one.setVisibility(View.VISIBLE);
                                                tv_one.setText(subject);
                                                if (null != imagelist && imagelist.size() > 0) {
                                                    String url = imagelist.getString("url");
                                                    String attachment = imagelist.getString("attachment");
                                                    int imageSetting = RedNetPreferences.getImageSetting();
                                                    if (imageSetting == 0) {
                                                        Picasso.with(RedNetApp.getInstance())
                                                                .load(AppNetConfig.IMGURL1 + url + attachment)
                                                                .into(iv_one);
                                                    } else {
                                                        iv_one.setImageResource(R.drawable.livetwo);
                                                    }

                                                } else {
                                                    iv_one.setImageResource(R.drawable.liveone);
                                                }
                                            } else if (size == 2) {
                                                ll_one.setVisibility(View.VISIBLE);
                                                ll_two.setVisibility(View.VISIBLE);
                                                rl_one.setVisibility(View.VISIBLE);
                                                iv_one.setVisibility(View.VISIBLE);
                                                iv_two.setVisibility(View.VISIBLE);
                                                tv_one.setVisibility(View.VISIBLE);
                                                tv_two.setVisibility(View.VISIBLE);
                                                JSONObject jsonObject1 = recommonthread_list.getJSONObject(0);
                                                JSONObject jsonObject2 = recommonthread_list.getJSONObject(1);


                                                String subject = jsonObject1.getString("subject");
                                                JSONObject imagelist = jsonObject1.getJSONObject("imagelist");
                                                rl_one.setVisibility(View.VISIBLE);
                                                tv_one.setText(subject);

                                                if (null != imagelist) {
                                                    String url = imagelist.getString("url");
                                                    String attachment = imagelist.getString("attachment");
                                                    int imageSetting = RedNetPreferences.getImageSetting();
                                                    if (imageSetting == 0) {
                                                        Picasso.with(RedNetApp.getInstance())
                                                                .load(AppNetConfig.IMGURL1 + url + attachment)
                                                                .into(iv_one);
                                                    } else {
                                                        iv_one.setImageResource(R.drawable.livetwo);
                                                    }

                                                } else {
                                                    iv_one.setImageResource(R.drawable.livetwo);
                                                }

                                                String subject1 = jsonObject2.getString("subject");
                                                JSONObject imagelist1 = jsonObject2.getJSONObject("imagelist");
                                                tv_two.setText(subject1);

                                                if (null != imagelist1) {
                                                    String url = imagelist1.getString("url");
                                                    String attachment = imagelist1.getString("attachment");
                                                    int imageSetting = RedNetPreferences.getImageSetting();
                                                    if (imageSetting == 0) {
                                                        Picasso.with(RedNetApp.getInstance())
                                                                .load(AppNetConfig.IMGURL1 + url + attachment)
                                                                .into(iv_two);
                                                    } else {
                                                        iv_two.setImageResource(R.drawable.livetwo);
                                                    }
                                                } else {
                                                    iv_two.setImageResource(R.drawable.livetwo);
                                                }
                                            } else if (size == 3) {
                                                setData(recommonthread_list);
                                            }

                                        }
                                    } else {
                                        rl_one.setVisibility(View.GONE);
                                    }
                                } else {
                                    rl_one.setVisibility(View.GONE);
                                }
                            } else {
                                rl_one.setVisibility(View.GONE);
                            }
                        } else {
                            rl_one.setVisibility(View.GONE);
                        }
                    }
                });
        OkHttpUtils.get()
                .url(AppNetConfig.LIVETHREAD)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(String response) {
                        LiveThredBean liveThredBean = null;
                        if (response != null && !TextUtils.isEmpty(response) && !response.contains("error")) {
                            try {
                                JSONObject jso = JSON.parseObject(response);
                                JSONObject Variables = (JSONObject) jso.get("Variables");
                                lvs.clear();
                                JSONObject object = (JSONObject) Variables.get("groupiconid");
                                Log.e("TAG", "object=" + object);
                                if (null != object) {
                                    for (Map.Entry<String, Object> entry : (object).entrySet()) {
                                        lvs.put(entry.getKey(), entry.getValue().toString());
                                    }
                                }

                                Iterator iter = lvs.entrySet().iterator();
                                while (iter.hasNext()) {
                                    Map.Entry entry = (Map.Entry) iter.next();
                                    Object key = entry.getKey();
                                    Object val = entry.getValue();
                                }
                                liveThredBean = new Gson().fromJson(response, LiveThredBean.class);
                                if (null != liveThredBean) {
                                    LiveThredBean.VariablesBean variables = liveThredBean.getVariables();
                                    String formhash = variables.getFormhash();
                                    CacheUtils.putString(RedNetApp.getInstance(), "formhash", formhash);
//                                CacheUtils.putString(RedNetApp.getInstance(), "formhash1", formhash);
                                    if (null != variables) {
                                        List<LiveThredBean.VariablesBean.LivethreadListBean> livethread_list = variables.getLivethread_list();
                                        if (null != livethread_list && livethread_list.size() > 0) {
                                            mListView.setVisibility(View.VISIBLE);
                                            imageView.setVisibility(View.GONE);
                                            if (appeand == false) {
                                                hotLiveAdapter.setData(livethread_list, lvs, formhash);
                                                hotLiveAdapter.notifyDataSetInvalidated();
                                            } else {
                                                hotLiveAdapter.addData(livethread_list, lvs);
                                                hotLiveAdapter.notifyDataSetInvalidated();
                                            }
                                        } else {
                                            mListView.setVisibility(View.GONE);
                                            imageView.setVisibility(View.VISIBLE);
                                        }


                                        mRefreshView.setRefreshing(false);
                                        if (mTip.getDisplayedChild() != 0) {
                                            mTip.setDisplayedChild(0);
                                        }
                                    } else {
                                        handler.sendEmptyMessage(0);
                                    }
                                } else {
                                    handler.sendEmptyMessage(0);
                                }
                            } catch (JsonSyntaxException e) {
                                handler.sendEmptyMessage(1);
                                e.printStackTrace();
                            }
                        } else {
                            handler.sendEmptyMessage(0);
                        }

                    }
                });
    }

    private void setData(JSONArray recommonthread_list) {
//        ll_img.setVisibility(View.VISIBLE);
        ll_one.setVisibility(View.VISIBLE);
        ll_two.setVisibility(View.VISIBLE);
        ll_three.setVisibility(View.VISIBLE);
        rl_one.setVisibility(View.VISIBLE);
        iv_one.setVisibility(View.VISIBLE);
        iv_two.setVisibility(View.VISIBLE);
        iv_three.setVisibility(View.VISIBLE);
        tv_one.setVisibility(View.VISIBLE);
        tv_two.setVisibility(View.VISIBLE);
        tv_three.setVisibility(View.VISIBLE);
//        rl_two.setVisibility(View.VISIBLE);
//        rl_three.setVisibility(View.VISIBLE);
        JSONObject jsonObject1 = recommonthread_list.getJSONObject(0);
        JSONObject jsonObject2 = recommonthread_list.getJSONObject(1);
        JSONObject jsonObject3 = recommonthread_list.getJSONObject(2);


        String subject = jsonObject1.getString("subject");
        JSONObject imagelist = jsonObject1.getJSONObject("imagelist");
        rl_one.setVisibility(View.VISIBLE);
        tv_one.setText(subject);
        if (null != imagelist) {
            String url = imagelist.getString("url");
            String attachment = imagelist.getString("attachment");
            int imageSetting = RedNetPreferences.getImageSetting();
            if (imageSetting == 0) {
                Picasso.with(RedNetApp.getInstance())
                        .load(AppNetConfig.IMGURL1 + url + attachment)
                        .into(iv_one);
            } else {
                iv_one.setImageResource(R.drawable.livethree);
            }
        } else {
            iv_one.setImageResource(R.drawable.livethree);
        }

        String subject1 = jsonObject2.getString("subject");
        JSONObject imagelist1 = jsonObject2.getJSONObject("imagelist");
        tv_two.setText(subject1);
        if (null != imagelist1) {
            String url = imagelist1.getString("url");
            String attachment = imagelist1.getString("attachment");
            int imageSetting = RedNetPreferences.getImageSetting();
            if (imageSetting == 0) {
                Picasso.with(RedNetApp.getInstance())
                        .load(AppNetConfig.IMGURL1 + url + attachment)
                        .into(iv_two);
            } else {
                iv_two.setImageResource(R.drawable.livethree);
            }
        } else {
            iv_two.setImageResource(R.drawable.livethree);
        }
        String subject2 = jsonObject3.getString("subject");
//                                                JSONArray imagelist2 = jsonObject3.getJSONArray("imagelist");
        JSONObject imagelist2 = jsonObject3.getJSONObject("imagelist");
        tv_three.setText(subject2);
        if (null != imagelist2) {
//                                                    JSONObject imagelist0 = imagelist2.getJSONObject(0);

            String url = imagelist2.getString("url");
            String attachment = imagelist2.getString("attachment");
            int imageSetting = RedNetPreferences.getImageSetting();
            if (imageSetting == 0) {
                Picasso.with(RedNetApp.getInstance())
                        .load(AppNetConfig.IMGURL1 + url + attachment)
                        .into(iv_three);
            } else {
                iv_three.setImageResource(R.drawable.livethree);
            }
        } else {
            iv_three.setImageResource(R.drawable.livethree);
        }

    }

    @Override
    public void onRefresh() {
        rl_one.setVisibility(View.GONE);
        hotLiveAdapter.clearData();
        hotLiveAdapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(MESSAGE_LOGIN, 500);
    }

    @Override
    public void onLoad() {
        getLiveThred(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_one:
                if (recommonthread_list != null) {
                    JSONObject jsonObject = recommonthread_list.getJSONObject(0);
                    String authorid = jsonObject.getString("authorid");
                    Object o = lvs1.get(authorid);
                    String fid = jsonObject.getString("fid");
                    String tid = jsonObject.getString("tid");
                    String special = jsonObject.getString("special");
                    JSONObject forumnames = jsonObject.getJSONObject("forumnames");
                    String name = forumnames.getString("name");
                    Intent intent = new Intent(getActivity(), LiveDetialActivity.class);
                    intent.putExtra("subject", jsonObject.getString("subject"));
                    intent.putExtra("fid", fid);
                    intent.putExtra("author", jsonObject.getString("author"));
                    intent.putExtra("avatar", jsonObject.getString("avatar"));
                    intent.putExtra("authorid", jsonObject.getString("authorid"));
                    intent.putExtra("tid", tid);
                    intent.putExtra("formhash;", formhash);
                    boolean numeric = RednetUtils.isNumeric(o.toString());
                    String level;
                    if (numeric == true) {
                        level = "Lv" + o.toString();
                    } else {
                        level = o.toString();
                    }
                    intent.putExtra("level", level);
                    startActivity(intent);
                }
                break;
            case R.id.iv_two:
                if (recommonthread_list != null) {
                    JSONObject jsonObject = recommonthread_list.getJSONObject(1);
                    String authorid = jsonObject.getString("authorid");
                    Object o = lvs1.get(authorid);
                    String fid = jsonObject.getString("fid");
                    String tid = jsonObject.getString("tid");
                    String special = jsonObject.getString("special");
                    JSONObject forumnames = jsonObject.getJSONObject("forumnames");
                    String name = forumnames.getString("name");
                    Intent intent = new Intent(getActivity(), LiveDetialActivity.class);
                    intent.putExtra("subject", jsonObject.getString("subject"));
                    intent.putExtra("fid", fid);
                    intent.putExtra("author", jsonObject.getString("author"));
                    intent.putExtra("avatar", jsonObject.getString("avatar"));
                    intent.putExtra("authorid", jsonObject.getString("authorid"));
                    intent.putExtra("tid", tid);
                    intent.putExtra("formhash", formhash);
                    boolean numeric = RednetUtils.isNumeric(o.toString());
                    String level;
                    if (numeric == true) {
                        level = "Lv" + o.toString();
                    } else {
                        level = o.toString();
                    }
                    intent.putExtra("level", level);
                    startActivity(intent);

                }
                break;
            case R.id.iv_three:
                if (recommonthread_list != null) {
                    JSONObject jsonObject = recommonthread_list.getJSONObject(2);
                    String authorid = jsonObject.getString("authorid");
                    Object o = lvs1.get(authorid);
                    String fid = jsonObject.getString("fid");
                    String tid = jsonObject.getString("tid");
                    String special = jsonObject.getString("special");
                    JSONObject forumnames = jsonObject.getJSONObject("forumnames");
                    String name = forumnames.getString("name");
                    Intent intent = new Intent(getActivity(), LiveDetialActivity.class);
                    intent.putExtra("subject", jsonObject.getString("subject"));
                    intent.putExtra("fid", fid);
                    intent.putExtra("author", jsonObject.getString("author"));
                    intent.putExtra("avatar", jsonObject.getString("avatar"));
                    intent.putExtra("authorid", jsonObject.getString("authorid"));
                    intent.putExtra("tid", tid);
                    intent.putExtra("formhash", formhash);
                    boolean numeric = RednetUtils.isNumeric(o.toString());
                    String level;
                    if (numeric == true) {
                        level = "Lv" + o.toString();
                    } else {
                        level = o.toString();
                    }
                    intent.putExtra("level", level);
                    startActivity(intent);

                }
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(ReFreshImg img) {
        Log.e("TAG", "无图模式");
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
