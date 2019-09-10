package cn.tencent.DiscuzMob.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.model.BaseMessageVariables;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.MyMessage;
import cn.tencent.DiscuzMob.net.ApiVersion5;
import cn.tencent.DiscuzMob.ui.adapter.MyNoticeAdapter;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/6/16.
 */
public class MessagePubMineActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, ILoadListener {

    private ViewAnimator mTip;
    private SwipeRefreshLayout mRefreshView;
    private ListView mList;
    private FooterForList mFooter;
    private MyNoticeAdapter mAdapter;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        mTip = (ViewAnimator) findViewById(R.id.tip);
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mList = (ListView) findViewById(R.id.list);
        ((TextView) findViewById(R.id.title)).setText("系统消息");
        mFooter = new FooterForList(mList);
        mList.setAdapter(mAdapter = new MyNoticeAdapter(this));
        findViewById(R.id.back).setOnClickListener(this);
        mList.setOnItemClickListener(this);
        mRefreshView.setOnRefreshListener(this);
        mFooter.setOnLoadListener(this);
        mRefreshView.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    void load(final boolean append) {
        ApiVersion5.INSTANCE.requestCommonMessage(append ? ++mPage : (mPage = 1),
                new ApiVersion5.Result<Object>(this, null, false, append ? mFooter : null, !append) {
                    @Override
                    public void onResult(int code, Object value) {
                        if (value instanceof BaseModel) {
                            BaseMessageVariables<MyMessage> variables = ((BaseModel<BaseMessageVariables<MyMessage>>) value).getVariables();
                            if (append) {
                                mAdapter.append(variables.getList());
                            } else {
                                mAdapter.set(variables.getList());
                                mFooter.reset();
                            }
                            if (variables.isFinishAll()) mFooter.finishAll();
                        }
                        if (code == 0) mPage = Math.max(1, --mPage);
                        mRefreshView.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            load(false);
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onLoad() {
        if (!mRefreshView.isRefreshing() && RednetUtils.networkIsOk(this)) {
            load(true);
        } else {
            mFooter.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("MessagePubMineActivity(系统消息)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("MessagePubMineActivity(系统消息)");
    }

}
