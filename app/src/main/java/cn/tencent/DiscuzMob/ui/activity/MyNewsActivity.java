package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.R;

public class MyNewsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;//返回键
    private LinearLayout ll_news;//我的消息
    private LinearLayout ll_thread;//我的帖子
    private LinearLayout ll_interaction;//坛友互动
    private LinearLayout ll_recommend;//系统提醒
    private LinearLayout ll_administration;//管理工作
    private Map<String, Object> strings = new HashMap<>();
    private TextView tv_news;
    private TextView tv_administration;
    private TextView tv_new1, tv_new2, tv_new3, tv_new4, tv_new5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);
        initView();
        getDataFromNet();
    }

    private void getDataFromNet() {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_auth") + ";" + CacheUtils.getString(RedNetApp.getInstance(), "cookiepre_saltkey"))
                .url(AppNetConfig.NEWS)
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                        Log.e("TAG", "是否有新的消息 =" + response.body().string());
                        try {
                            JSONObject jsonObject = JSON.parseObject(response.body().string());
                            JSONObject variables = jsonObject.getJSONObject("Variables");
                            if (variables != null) {
                                final String newpmcount = variables.getString("newpmcount");
                                if (null != newpmcount && !newpmcount.equals("0")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv_new1.setVisibility(View.VISIBLE);
                                            tv_new1.setText("(" + newpmcount + ")");
                                        }
                                    });

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv_new1.setVisibility(View.GONE);
                                        }
                                    });

                                }
                                strings.clear();
                                JSONObject category_num = variables.getJSONObject("category_num");
                                if (null != category_num) {
                                    for (Map.Entry<String, Object> entry : (category_num).entrySet()) {
                                        strings.put(entry.getKey(), entry.getValue().toString());
                                    }
                                    final Object mypost = strings.get("mypost");
                                    if (null != mypost && !mypost.equals("0")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv_new2.setVisibility(View.VISIBLE);
                                                tv_new2.setText("(" + mypost.toString() + ")");
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv_new2.setVisibility(View.GONE);
                                            }
                                        });

                                    }
                                    final Object interactive = strings.get("interactive");
                                    if (null != interactive && !interactive.equals("0")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv_new3.setVisibility(View.VISIBLE);
                                                tv_new3.setText("(" + interactive.toString() + ")");
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv_new3.setVisibility(View.GONE);
                                            }
                                        });

                                    }
                                    final Object system = strings.get("system");
                                    if (null != system && !system.equals("0")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv_new4.setVisibility(View.VISIBLE);
                                                tv_new4.setText("(" + system.toString() + ")");
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv_new4.setVisibility(View.GONE);
                                            }
                                        });

                                    }
                                    final Object manage = strings.get("manage");
                                    if (null != manage && !manage.equals("0")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv_new5.setVisibility(View.VISIBLE);
                                                tv_new5.setText("(" + manage.toString() + ")");
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv_new5.setVisibility(View.GONE);
                                            }
                                        });

                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv_new2.setVisibility(View.GONE);
                                            tv_new3.setVisibility(View.GONE);
                                            tv_new4.setVisibility(View.GONE);
                                            tv_new5.setVisibility(View.GONE);
                                        }
                                    });

                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ll_news = (LinearLayout) findViewById(R.id.ll_news);
        ll_news.setOnClickListener(this);
        ll_thread = (LinearLayout) findViewById(R.id.ll_thread);
        ll_interaction = (LinearLayout) findViewById(R.id.ll_interaction);
        ll_recommend = (LinearLayout) findViewById(R.id.ll_recommend);
        ll_administration = (LinearLayout) findViewById(R.id.ll_administration);
        iv_back.setOnClickListener(this);
        ll_thread.setOnClickListener(this);
        ll_interaction.setOnClickListener(this);
        ll_recommend.setOnClickListener(this);
        ll_administration.setOnClickListener(this);
        tv_news = (TextView) findViewById(R.id.tv_news);
        tv_administration = (TextView) findViewById(R.id.tv_administration);
        tv_new1 = (TextView) findViewById(R.id.tv_new1);
        tv_new2 = (TextView) findViewById(R.id.tv_new2);
        tv_new3 = (TextView) findViewById(R.id.tv_new3);
        tv_new4 = (TextView) findViewById(R.id.tv_new4);
        tv_new5 = (TextView) findViewById(R.id.tv_new5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回键
                finish();
                break;
            case R.id.ll_news://我的消息
                startActivity(new Intent(MyNewsActivity.this, MessageMineActivity.class));
                break;
            case R.id.ll_thread://我的帖子
                startActivity(new Intent(MyNewsActivity.this, MyThreadActivity.class));
                break;
            case R.id.ll_interaction://坛友互动
                startActivity(new Intent(MyNewsActivity.this, InteractionActivity.class));
                break;
            case R.id.ll_recommend://系统提醒
                startActivity(new Intent(MyNewsActivity.this, SystemActivity.class));
                break;
            case R.id.ll_administration://管理工作
                startActivity(new Intent(MyNewsActivity.this, AdministrationActivity.class));
                break;

        }
    }
}
