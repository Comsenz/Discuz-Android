package cn.tencent.DiscuzMob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.SearchBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.request.HttpRequest;
import cn.tencent.DiscuzMob.request.OnRequestListener;
import cn.tencent.DiscuzMob.ui.adapter.SearchAdapter;
import cn.tencent.DiscuzMob.utils.LogUtils;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.widget.FooterForList;
import cn.tencent.DiscuzMob.widget.ILoadListener;

/**
 * Created by AiWei on 2016/5/5.
 */
public class SearchActivity extends BaseActivity implements View.OnKeyListener, View.OnClickListener, ILoadListener, AdapterView.OnItemClickListener {

    private View mCancel;
    private ViewAnimator mTip;
    private EditText mEditor;
    private ListView mListView;
    private SearchAdapter mAdapter;
    private FooterForList mFooter;
    private String mKey;
    private int mCurrentPage = 1;
    private List<SearchBean.VariablesBean.ThreadlistBean> items = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mCancel = findViewById(R.id.cancel);
        mTip = (ViewAnimator) findViewById(R.id.tip);
        mListView = (ListView) findViewById(R.id.list);
        mFooter = new FooterForList(mListView);
        mEditor = (EditText) findViewById(R.id.editor);
        mEditor.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditor.setImeActionLabel("搜索", KeyEvent.KEYCODE_SEARCH);
        ((TextView) mTip.getChildAt(1)).setText("没有相关帖子,换个关键字试试");
        mTip.getChildAt(1).setOnClickListener(this);
        mEditor.setOnKeyListener(this);
        mCancel.setOnClickListener(this);
        mFooter.setOnLoadListener(this);
        mListView.setOnItemClickListener(this);
    }

    SearchBean.VariablesBean variables1;

    void load(final boolean append) {
        if (!TextUtils.isEmpty(mKey)) {
            if (RedNetApp.getInstance().networkIsOk()) {
                if (!append) {
                    mTip.setDisplayedChild(0);
                    mTip.setVisibility(View.VISIBLE);
                }
                LogUtils.i(AppNetConfig.SEARCH);
                mCurrentPage = append ? ++mCurrentPage : 1;
                LogUtils.i("搜索:page=" + mCurrentPage);
                OkHttpUtils.post()
                        .url(AppNetConfig.SEARCH + "&page =" + mCurrentPage + "&srchtxt=" + mKey)
                        .addParams("", mCurrentPage + "")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(SearchActivity.this, "搜索失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.i("搜索成功=" + response);
                        SearchBean searchBean = new Gson().fromJson(response, SearchBean.class);
                        variables1 = searchBean.getVariables();
                        if (getWindow() != null) {
                            if (variables1 == null) {
                                mTip.setDisplayedChild(1);
                                mCurrentPage = Math.max(1, --mCurrentPage);
                                if (append) {
                                    mFooter.finishAll();
                                } else {
                                    mFooter.reset();
                                }
                            } else {
                                List<SearchBean.VariablesBean.ThreadlistBean> thread = variables1.getThreadlist();
                                if (mAdapter != null) {
                                    if (null != thread && thread.size() > 0) {
                                        if (append) {
                                            mAdapter.append(thread);
                                            items.addAll(thread);
                                            mFooter.finish();
                                        } else {
                                            items.clear();
                                            items.addAll(thread);
                                            mAdapter.setData(thread);
                                            mFooter.reset();
                                        }
                                    } else {
                                        if (append) {
                                            mFooter.finishAll();
                                        } else {
                                            mFooter.reset();
                                        }

                                    }

                                }

                                if (mTip != null) {
                                    if (mAdapter == null || mAdapter.getCount() == 0) {
                                        mTip.setDisplayedChild(1);
                                    } else {
                                        mTip.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    }
                });

            } else {
                RednetUtils.toast(this, "网络不可用");
            }
        } else

        {
            RednetUtils.toast(this, "请输入关键字");
        }

    }

    @Override
    public void onClick(View v) {
        if (v.equals(mCancel)) {
            finish();
        } else {
            load(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getItemAtPosition(position);
        if (items.size() > 0) {
            String special = items.get(position).getSpecial();
//            startActivity(new Intent(this, ThreadDetailsActivity.class).putExtra("id", (variables1.getThread().get(position).getTid())).putExtra("fid", (variables1.getThread().get(position).getFid())));
            Class claz;
            special = TextUtils.isEmpty(special) ? "0" : special;
            if ("1".equals(special)) {
                claz = VoteThreadDetailsActivity.class;
            } else if ("4".equals(special)) {
                claz = ActivityThreadDetailsActivity.class;
            } else {
                claz = ThreadDetailsActivity.class;
            }
            Intent intent = new Intent(parent.getContext(), claz);
            intent.putExtra("id", items.get(position).getTid());
            intent.putExtra("title", "");
            intent.putExtra("fid", items.get(position).getFid());
            startActivity(intent);
        }
    }

    @Override
    public void onLoad() {
        LogUtils.d("onLoad()");
        load(true);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && (keyCode == KeyEvent.KEYCODE_SEARCH
                || keyCode == KeyEvent.KEYCODE_ENTER)) {
            String key = mEditor.getText().toString();
            if (!TextUtils.isEmpty(key)) {
                mKey = mEditor.getText().toString();
                mListView.setAdapter(mAdapter = new SearchAdapter(mKey));
                load(false);
            } else {
                RednetUtils.toast(this, "请输入关键字");
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("SearchActivity(帖子搜索)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("SearchActivity(帖子搜索)");
    }

}
