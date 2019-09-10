package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.manager.DataLoaderCallback;
import cn.tencent.DiscuzMob.manager.InterDataLoaderListener;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.Profile;
import cn.tencent.DiscuzMob.model.VoterVariables;
import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.ui.adapter.SampleProfileAdapter;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;

/**
 * Created by kurt on 15-8-11.
 */
public class VoterActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTitle;
    private SwipeRefreshLayout mRefreshView;
    private ListView mList;
    private SampleProfileAdapter mAdapter;
    private String tid;
    private String pollid;
    private int title;
    String formhash;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mRefreshView != null) {
                if (msg.what == 1) {
                    List<VoterVariables.VariablesBean.ViewvoteBean.VoterlistBean> data = (List<VoterVariables.VariablesBean.ViewvoteBean.VoterlistBean>) msg.obj;
                    if (data != null && !data.isEmpty()) {
                        mAdapter.setData(data, formhash);
                    } else {
                        RednetUtils.toast(VoterActivity.this, "无投票此选项的人");
                    }
                } else {
                    RednetUtils.toast(VoterActivity.this, "请求失败");
                }
                mRefreshView.setRefreshing(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sample_layout);
        tid = getIntent().getStringExtra("tid");
        pollid = getIntent().getStringExtra("pollid");
        title = getIntent().getIntExtra("title", 0);
        mTitle = (TextView) findViewById(R.id.action_bar_title);
        int index = title + 1;
        mTitle.setText("选择第" + index + "项的人");
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mList = (ListView) findViewById(R.id.my_thread_list);
        mList.setAdapter(mAdapter = new SampleProfileAdapter(this));
        findViewById(R.id.back).setOnClickListener(this);
        mList.setOnItemClickListener(onItemClickListener);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setRefreshing(true);
        onRefresh();
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
            Object uid = parent.getItemAtPosition(position);
            if (uid instanceof String) {
                startActivity(new Intent(VoterActivity.this, UserDetailActivity.class).putExtra("userId", (String) uid));
            }
        }
    };

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            Api.getInstance().requestVoter(tid, pollid, new DataLoaderCallback(new InterDataLoaderListener() {
                @Override
                public void onLoadFinished(Object object) {
                    String result = (String) object;
                    LogUtils.i(result);
                   VoterVariables voterVariablesBaseModel = new Gson().fromJson(result, new TypeToken<VoterVariables>() {
                    }.getType());
                    if (voterVariablesBaseModel != null) {
                        RedNetApp.setFormHash(voterVariablesBaseModel.getVariables().getFormhashX());
                        List<VoterVariables.VariablesBean.ViewvoteBean.VoterlistBean> data = voterVariablesBaseModel.getVariables().getViewvote().getVoterlist();
                        formhash = voterVariablesBaseModel.getVariables().getFormhashX();
                        mHandler.sendMessage(Message.obtain(mHandler, 1, data));
                    } else {
                        mHandler.sendEmptyMessage(2);
                    }
                }

                @Override
                public void onError(Object object) {
                    mHandler.sendEmptyMessage(2);
                }
            }));
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("VoterActivity(查看投票人)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("VoterActivity(查看投票人)");
    }
}
