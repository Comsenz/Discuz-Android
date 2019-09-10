package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.manager.DataLoaderCallback;
import cn.tencent.DiscuzMob.manager.InterDataLoaderListener;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.PollPictureBean;
import cn.tencent.DiscuzMob.model.PollPotionVariables;
import cn.tencent.DiscuzMob.model.Polloption;
import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.ui.adapter.PollPotionAdapter;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;

/**
 * Created by kurt on 15-8-11.
 */
public class VoterPollpotionActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private NestedSwipeRefreshLayout mRefresh;
    private ListView mPollpotionList;
    private PollPotionAdapter mAdapter;
    private String tid;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mRefresh != null) {
                if (msg.what == 1) {
                    mAdapter.setmPolloptions((ArrayList<PollPotionVariables.VariablesBean.ViewvoteBean.PolloptionsBean>) msg.obj);
                } else {
                    RednetUtils.toast(VoterPollpotionActivity.this, "请求失败");
                }
                mRefresh.setRefreshing(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voters);
        tid = getIntent().getStringExtra("tid");
        mRefresh = (NestedSwipeRefreshLayout) findViewById(R.id.refresh);
        mPollpotionList = (ListView) findViewById(R.id.pollpotion_list);
        mPollpotionList.setAdapter(mAdapter = new PollPotionAdapter());
        findViewById(R.id.back).setOnClickListener(this);
        mPollpotionList.setOnItemClickListener(onItemClickListener);
        mRefresh.setOnRefreshListener(this);

        mRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(true);
                onRefresh();
            }
        }, 500);
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            Api.getInstance().requestVoterPollpotion(tid, new DataLoaderCallback(new InterDataLoaderListener() {
                @Override
                public void onLoadFinished(Object object) {
                    String result = (String) object;
                    LogUtils.i(result);
                    PollPotionVariables pollPotionVariablesBaseModel = new Gson().fromJson(result, new TypeToken<PollPotionVariables>() {
                    }.getType());
                    if (pollPotionVariablesBaseModel != null) {
                        RedNetApp.setFormHash(pollPotionVariablesBaseModel.getVariables().getFormhashX());
                        mHandler.sendMessage(Message.obtain(mHandler, 1, pollPotionVariablesBaseModel.getVariables().getViewvote().getPolloptions()));
                    }
                }

                @Override
                public void onError(Object object) {
                    mHandler.sendEmptyMessage(0);
                }
            }));
        } else {
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PollPotionVariables.VariablesBean.ViewvoteBean.PolloptionsBean polloption = (PollPotionVariables.VariablesBean.ViewvoteBean.PolloptionsBean) mAdapter.getItem(position);
            startActivity(new Intent(VoterPollpotionActivity.this, VoterActivity.class)
                    .putExtra("tid", tid)
                    .putExtra("pollid", polloption.getPolloptionid())
                    .putExtra("title", position));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("VoterPollpotionActivity(查看投票项)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("VoterPollpotionActivity(查看投票项)");
    }

}
