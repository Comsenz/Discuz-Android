package cn.tencent.DiscuzMob.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.request.HttpRequest;
import cn.tencent.DiscuzMob.request.OnRequestListener;
import cn.tencent.DiscuzMob.ui.adapter.ActivityBindAdapter;
import cn.tencent.DiscuzMob.ui.bean.BindBean;
import cn.tencent.DiscuzMob.utils.GsonUtil;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

public class BindManagerActivity extends BaseActivity {
    ListView bind_listview;
    String formhash,url;
    ImageView login_back;
    public static void  ComeIn(Activity activity,String url,String formhash){
        Intent intent = new Intent(activity,BindManagerActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("formhash",formhash);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_manager);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        formhash = intent.getStringExtra("formhash");
        initView();

    }
    BindBean bindBean;
    private void initData() {
        String cookie1 = CacheUtils.getString(RedNetApp.getInstance(), "cookie1");
        LogUtils.i( cookie1);
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", cookie1)
                .url(Api.getInstance().URL + "?module=oauths&version=5")
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String json = response.body().string();
                        LogUtils.i(json);
                        bindBean = GsonUtil.fromJson(json,BindBean.class);
                        handler.sendEmptyMessage(1);
                    }
                });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ActivityBindAdapter activityBindAdapter = new ActivityBindAdapter(BindManagerActivity.this,bindBean.getVariables().getUsers(),formhash);
                    bind_listview.setAdapter(activityBindAdapter);
                    break;
            }
        }
    };
    private void initView() {
        bind_listview = findViewById(R.id.bind_listview);
        login_back = findViewById(R.id.login_back);
        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
