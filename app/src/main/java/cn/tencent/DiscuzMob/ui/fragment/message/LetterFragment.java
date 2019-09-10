package cn.tencent.DiscuzMob.ui.fragment.message;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.LetterBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.ui.activity.MyPmActivity;
import cn.tencent.DiscuzMob.ui.adapter.LetterAdapter;
import cn.tencent.DiscuzMob.ui.fragment.SimpleRefreshFragment;
import cn.tencent.DiscuzMob.utils.DensityUtil;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;

/**
 * Created by cg on 2017/4/19.
 * 私人消息
 */

public class LetterFragment extends SimpleRefreshFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListener {
    //    private AppAdapter mAdapter;
    private SwipeMenuListView mListView;
    String cookiepre_auth;
    String cookiepre_saltkey;
    String formhash;
    private ImageView iv_nothing;//没有数据的背景
    List<LetterBean.VariablesBean.ListBean> list1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    RednetUtils.toast(RedNetApp.getInstance(), "请求失败");
                    break;
                case 1:
                    RednetUtils.toast(RedNetApp.getInstance(), "请求异常");
                    break;
            }
        }
    };
    private int mPage;
    private FooterForList mFooter;
    LetterAdapter letterAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mListView.setAdapter(mAdapter = new MyForumAdapter(getActivity(), null, true));
        cookiepre_auth = CacheUtils.getString(getActivity(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(getActivity(), "cookiepre_saltkey");
        formhash = CacheUtils.getString(getActivity(), "formhash");
        mRefreshView.setOnRefreshListener(this);
        mFooter = new FooterForList(listView);
        mFooter.setOnLoadListener(this);
        mTip.setDisplayedChild(1);
        mPage = 1;
        listView.setVisibility(View.VISIBLE);
        letterAdapter = new LetterAdapter();
        listView.setAdapter(letterAdapter);
        rl_threads.setBackgroundColor(Color.parseColor("#f0eff5"));
        onRefresh();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cookiepre_auth = CacheUtils.getString(getActivity(), "cookiepre_auth");
        cookiepre_saltkey = CacheUtils.getString(getActivity(), "cookiepre_saltkey");
        formhash = CacheUtils.getString(getActivity(), "formhash");
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(RedNetApp.getInstance());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                openItem.setWidth(DensityUtil.dip2px(RedNetApp.getInstance(), 70));
                openItem.setTitle("删除");
                openItem.setTitleSize(16);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);
        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        OkHttpUtils.get()
                                .url(AppNetConfig.DELETELETTER + "&id=" + list1.get(position).getTouid() + "&formhash=" + formhash + "&mobiletype=IOS&ios=1")
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Request request, Exception e) {
                                        handler.sendEmptyMessage(0);
                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (null != jsonObject) {
                                                JSONObject message = jsonObject.getJSONObject("Message");
                                                if (null != message) {
                                                    String messagestr = message.getString("messagestr");//中文
                                                    String messageval = message.getString("messageval");//英文
                                                    if (null != messageval && messageval.contains("success")) {
                                                        getDataFromNet(false);
                                                        RednetUtils.toast(RedNetApp.getInstance(), messagestr);
                                                    } else {
                                                        RednetUtils.toast(RedNetApp.getInstance(), messagestr);
                                                    }
                                                } else {
                                                    handler.sendEmptyMessage(0);
                                                }
                                            } else {
                                                handler.sendEmptyMessage(0);
                                            }
                                        } catch (JSONException e) {
                                            handler.sendEmptyMessage(1);
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        break;

                }
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(parent.getContext(), MyPmActivity.class).putExtra("touid", list1.get(position).getTouid()).putExtra("tousername", list1.get(position).getTousername()).putExtra("formhash", formhash));
            }
        });
    }

    private void getDataFromNet(final boolean b) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookiepre_auth + ";" + cookiepre_saltkey)
                .url(AppNetConfig.PRIVATE_LETTER + "&page=" + String.valueOf(b ? ++mPage : (mPage = 1)))
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "我的私信失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                        Log.e("TAG", "response=" + response.body().string());
                        LetterBean letterBean = null;
                        try {
                            letterBean = new Gson().fromJson(response.body().string(), LetterBean.class);
                            if (null != letterBean) {
                                list1 = letterBean.getVariables().getList();
                                formhash = letterBean.getVariables().getFormhash();
                                final String perpage = letterBean.getVariables().getPerpage();
                                if (null != list1 && list1.size() > 0) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageView.setVisibility(View.GONE);
                                            listView.setVisibility(View.VISIBLE);
                                            if (b) {
                                                if (list1.size() < Integer.parseInt(perpage)) {
                                                    mFooter.finishAll();
                                                } else {
                                                    mFooter.reset();
                                                }
                                                letterAdapter.appendData(list1);
                                            } else {
                                                if (list1.size() < Integer.parseInt(perpage)) {
                                                    mFooter.finishAll();
                                                } else {
                                                    mFooter.reset();
                                                }
                                                letterAdapter.setData(list1);
                                            }

                                        }
                                    });
                                } else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listView.setVisibility(View.GONE);
                                            imageView.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }

                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listView.setVisibility(View.GONE);
                                        imageView.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        } catch (JsonSyntaxException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        } catch (IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "请求异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshView.setRefreshing(false);
                                if (mTip.getDisplayedChild() != 0) {
                                    mTip.setDisplayedChild(0);
                                }
                            }
                        });
                    }

                });
    }

    @Override
    public void onRefresh() {
        if (mFooter != null && mFooter.isLoading()) {
            RednetUtils.toast(RedNetApp.getInstance(), "正在加载");
            mRefreshView.setRefreshing(false);
        } else if (RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            if (letterAdapter != null) {
                letterAdapter.cleanData();
            }
            getDataFromNet(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onLoad() {
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(RedNetApp.getInstance())) {
            getDataFromNet(true);
        } else {
            mFooter.finish();
        }
    }
}

