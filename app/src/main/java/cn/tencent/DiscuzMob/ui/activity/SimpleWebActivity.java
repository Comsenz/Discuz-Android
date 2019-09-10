package cn.tencent.DiscuzMob.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.umeng.analytics.MobclickAgent;

import cn.tencent.DiscuzMob.R;
import cn.tencent.DiscuzMob.base.BaseActivity;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.RednetUtils;

/**
 * Created by AiWei on 2016/5/11.
 * 社区板块 阅读专栏都是进入这里  (app登录需要webview也登录)
 */
public class SimpleWebActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mRefreshView;
    private TextView mTitleView;
    private WebView mWebView;
    private ViewAnimator mTip;
    private String mTitle;
    private String mUrl;
    private boolean mFromIndex;

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!TextUtils.isEmpty(url)) {
                Uri uri = Uri.parse(url);
                if ("cn.rednet.bbs".equalsIgnoreCase(uri.getScheme())) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } catch (ActivityNotFoundException e) {
                    }
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mRefreshView != null) {
                mRefreshView.setRefreshing(false);
                mTip.setVisibility(View.GONE);
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_web);

        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");
        mFromIndex = getIntent().getBooleanExtra("fromIndex", false);
        mTip = (ViewAnimator) findViewById(R.id.tip);
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRefreshView.setColorSchemeResources(R.color.blue);
        mTitleView = (TextView) findViewById(R.id.title);
        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.setWebViewClient(mWebViewClient);
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setAllowFileAccess(true);
//        settings.setUseWideViewPort(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setAppCacheEnabled(true);
//        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT >= 11) {
            settings.setDisplayZoomControls(false);
        }
        mTitleView.setText(mTitle);
        mTip.setVisibility(View.VISIBLE);
        findViewById(R.id.back).setOnClickListener(this);
        mRefreshView.setOnRefreshListener(this);

        if (!TextUtils.isEmpty(mUrl) && RedNetApp.getInstance().isLogin()) {
            CookieSyncManager.createInstance(SimpleWebActivity.this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();//移除
            cookieManager.setCookie(mUrl, RedNetApp.getInstance().getCookie());
            cookieManager.setCookie(mUrl, RedNetApp.getInstance().getCookie2());
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();//然后同步Cookie
            } else {
                CookieManager.getInstance().flush();
            }
        }
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onRefresh() {
        if (RednetUtils.networkIsOk(this)) {
            mWebView.reload();
        } else {
            mRefreshView.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (mFromIndex) {
            startActivity(new Intent(SimpleWebActivity.this, RednetMainActivity.class));
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mFromIndex) {
            startActivity(new Intent(SimpleWebActivity.this, RednetMainActivity.class));
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("SimpleWebActivity(外部链接)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("SimpleWebActivity(外部链接)");
    }

}
